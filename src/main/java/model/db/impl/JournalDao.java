package model.db.impl;

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
    InMemoryDB database;

    List<Journal> journals = database.getJournals();


    @Override
    public boolean create(Journal journal) {
        if (journal == null) return false;
        for (Journal o : journals) {
            if (o.getKksName().equals(journal.getKksName())) {
                throw new RuntimeException("Duplicates in journal list");
            }
        }
        return journals.add(journal);
    }

    @Override
    public boolean createOrUpdate(Journal journal) {
        try {
            if (!create(journal)) {
                return update(journal);
            }
        } catch (RuntimeException ex) {
            return update(journal);
        }
        return true;
    }

    @Override
    public Journal read(String uniqueName) {
        Journal journal = null;
        for (Journal o : journals) {
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
        for (Journal o : journals) {
            if (o.getKksName().equals(journal.getKksName())) {
                journals.remove(o);
                journals.add(journal);
            } else {
                throw new RuntimeException("Can't find journal in list:" + journal.getKksName());
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Journal journal = null;
        for (Journal o : journals) {
            if (o.getKksName().equals(uniqueName)) {
                if (journal == null) {journal = o;}
                else throw new RuntimeException("Duplicates in journal list");
            }
        }
        return journals.remove(journal);
    }

    @Override
    public List<Journal> getAll(){
        return journals;
    }
}
