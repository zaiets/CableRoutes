package model.db.implInMemory;

import model.db.IDao;
import model.db.InMemoryDB;
import model.entities.Cable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class CableDao implements IDao<Cable> {
    @Autowired
    InMemoryDB inMemoryDB;


    @Override
    public boolean create(Cable cable) {
        if (cable == null) return false;
        for (Cable o : inMemoryDB.getCables()) {
            if (o.getKksName().equals(cable.getKksName())) {
                //throw new RuntimeException("Duplicates in cables list");
                return false;
            }
        }
        return inMemoryDB.getCables().add(cable);
    }

    @Override
    public boolean createOrUpdate(Cable cable) {
        try {
            if (!create(cable)) {
                return update(cable);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return update(cable);
        }
        return true;
    }

    @Override
    public Cable read(String uniqueName) {
        Cable cable = null;
        for (Cable o : inMemoryDB.getCables()) {
            if (o.getKksName().equals(uniqueName)) {
                if (cable == null) {cable = o;}
                else throw new RuntimeException("Duplicates in cables list");
            }
        }
        return cable;
    }

    @Override
    public boolean update(Cable cable) {
        if (cable == null) return false;
        for (Cable o : inMemoryDB.getCables()) {
            if (o.getKksName().equals(cable.getKksName())) {
                o.setCableType(cable.getCableType());
                o.setStart(cable.getStart());
                o.setLength(cable.getLength());
                o.setEnd(cable.getEnd());
                o.setJournalName(cable.getJournalName());
                o.setNumberInJournal(cable.getNumberInJournal());
                o.setReserving(cable.getReserving());
                if (!o.isTraced()) {
                    o.setRoutesList(cable.getRoutesList());
                    o.setTraced(cable.isTraced());
                }
            } else {
                create(o);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Cable cable = null;
        for (Cable o : inMemoryDB.getCables()) {
            if (o.getKksName().equals(uniqueName)) {
                if (cable == null) {cable = o;}
                else throw new RuntimeException("Duplicates in cables list");
            }
        }
        return inMemoryDB.getCables().remove(cable);
    }

    @Override
    public List<Cable> getAll(){
        return inMemoryDB.getCables();
    }
}
