package model.db.impl;

import model.db.IDao;
import model.db.InMemoryDB;
import model.entities.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class RouteDao implements IDao<Route> {
    @Autowired
    InMemoryDB database;

    List<Route> routes = database.getRoutes();


    @Override
    public boolean create(Route route) {
        if (route == null) return false;
        for (Route o : routes) {
            if (o.getKksName().equals(route.getKksName())) {
                throw new RuntimeException("Duplicates in route list");
            }
        }
        return routes.add(route);
    }

    @Override
    public boolean createOrUpdate(Route route) {
        try {
            if (!create(route)) {
                return update(route);
            }
        } catch (RuntimeException ex) {
            return update(route);
        }
        return true;
    }

    @Override
    public Route read(String uniqueName) {
        Route route = null;
        for (Route r : routes) {
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
        for (Route r : routes) {
            if (r.getKksName().equals(route.getKksName())) {
                routes.remove(r);
                routes.add(route);
            } else {
                throw new RuntimeException("Can't find route in list:" + route.getKksName());
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Route route = null;
        for (Route r : routes) {
            if (r.getKksName().equals(uniqueName)) {
                if (route == null) {route = r;}
                else throw new RuntimeException("Duplicates in route list");
            }
        }
        return routes.remove(route);
    }

    @Override
    public List<Route> getAll(){
        return routes;
    }
}
