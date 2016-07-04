package model.db.implInMemory;

import model.db.IDao;
import model.db.InMemoryDB;
import model.entities.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class JournalDao implements IDao<Journal> {
    @Autowired
    InMemoryDB inMemoryDB;


    @Override
    public boolean create(Journal journal) {
        if (journal == null) return false;
        for (Journal o : inMemoryDB.getJournals()) {
            if (o.getKksName().equals(journal.getKksName())) {
                //throw new RuntimeException("Duplicates in journal list");
                return false;
            }
        }
        return inMemoryDB.getJournals().add(journal);
    }

    @Override
    public boolean createOrUpdate(Journal journal) {
        try {
            if (!create(journal)) {
                return update(journal);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return update(journal);
        }
        return true;
    }

    @Override
    public Journal read(String uniqueName) {
        Journal journal = null;
        for (Journal o : inMemoryDB.getJournals()) {
            if (o.getKksName().equals(uniqueName)) {
                if (journal == null) {journal = o;}
                else throw new RuntimeException("Duplicates in journal list");
            }
        }
        return journal;
    }

    @Override
    public boolean update(Journal journal) {
        if (journal == null) return false;
        for (Journal o : inMemoryDB.getJournals()) {
            if (o.getKksName().equals(journal.getKksName())) {
                o.setFileName(journal.getFileName());
                o.setCables(journal.getCables());
            } else {
                create(o);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Journal journal = null;
        for (Journal o : inMemoryDB.getJournals()) {
            if (o.getKksName().equals(uniqueName)) {
                if (journal == null) {journal = o;}
                else throw new RuntimeException("Duplicates in journal list");
            }
        }
        return inMemoryDB.getJournals().remove(journal);
    }

    @Override
    public List<Journal> getAll(){
        return inMemoryDB.getJournals();
    }
}
