package model.db.impl;

import model.db.IDao;
import model.db.InMemoryDB;
import model.entities.Cable;
import model.entities.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class CableDao implements IDao<Cable> {
    @Autowired
    InMemoryDB inMemoryDB;

    List<Cable> cables = inMemoryDB.getCables();


    @Override
    public boolean create(Cable cable) {
        if (cable == null) return false;
        for (Cable o : cables) {
            if (o.getKksName().equals(cable.getKksName())) {
                throw new RuntimeException("Duplicates in cables list");
            }
        }
        return cables.add(cable);
    }

    @Override
    public boolean createOrUpdate(Cable cable) {
        try {
            if (!create(cable)) {
                return update(cable);
            }
        } catch (RuntimeException ex) {
            return update(cable);
        }
        return true;
    }

    @Override
    public Cable read(String uniqueName) {
        Cable cable = null;
        for (Cable o : cables) {
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
        for (Cable o : cables) {
            if (o.getKksName().equals(cable.getKksName())) {
                if (o.isTraced()) {
                    List<Route> existingTracing = o.getRoutesList();
                    cable.setRoutesList(existingTracing);
                }
                cables.remove(o);
                cables.add(cable);
            } else {
                throw new RuntimeException("Can't find cables in list:" + cable.getKksName());
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Cable cable = null;
        for (Cable o : cables) {
            if (o.getKksName().equals(uniqueName)) {
                if (cable == null) {cable = o;}
                else throw new RuntimeException("Duplicates in cables list");
            }
        }
        return cables.remove(cable);
    }

    @Override
    public List<Cable> getAll(){
        return cables;
    }
}
