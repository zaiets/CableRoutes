package app.repository.dao.business.implInMemory;

import app.repository.dao.business.IDao;
import app.repository.dao.business.InMemoryDB;
import app.repository.entities.business.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class RouteDao implements IDao<Route> {
    @Autowired
    InMemoryDB inMemoryDB;


    @Override
    public boolean create(Route route) {
        if (route == null) return false;
        for (Route o : inMemoryDB.getRoutes()) {
            if (o.getKksName().equals(route.getKksName())) {
                //throw new RuntimeException("Duplicates in route list");
                return false;
            }
        }
        return inMemoryDB.getRoutes().add(route);
    }

    @Override
    public boolean createOrUpdate(Route route) {
        try {
            if (!create(route)) {
                return update(route);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return update(route);
        }
        return true;
    }

    @Override
    public Route read(String uniqueName) {
        Route route = null;
        for (Route r : inMemoryDB.getRoutes()) {
            if (r.getKksName().equals(uniqueName)) {
                if (route == null) {route = r;}
                else throw new RuntimeException("Duplicates in route list");
            }
        }
        return route;
    }

    @Override
    public boolean update(Route route) {
        if (route == null) return false;
        for (Route o : inMemoryDB.getRoutes()) {
            if (o.getKksName().equals(route.getKksName())) {
                o.setRouteType(route.getRouteType());
                o.setFirstEnd(route.getFirstEnd());
                o.setSecondEnd(route.getSecondEnd());
                o.setHeight(route.getHeight());
                o.setLength(route.getLength());
                o.setShelvesCount(route.getShelvesCount());
                o.setCablesList(route.getCablesList());
            } else {
                create(o);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Route route = null;
        for (Route r : inMemoryDB.getRoutes()) {
            if (r.getKksName().equals(uniqueName)) {
                if (route == null) {route = r;}
                else throw new RuntimeException("Duplicates in route list");
            }
        }
        return inMemoryDB.getRoutes().remove(route);
    }

    @Override
    public List<Route> getAll(){
        return inMemoryDB.getRoutes();
    }
}
