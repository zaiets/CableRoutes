package app.repository.dao.business;

import org.springframework.stereotype.Repository;
import app.repository.entities.business.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public enum InMemoryDB {
    INSTANCE;

    private List<Journal> journals = new ArrayList<>();
    private List<JoinPoint> joinPoints = new ArrayList<>();
    private List<Equipment> equipments = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
    private List<Cable> cables = new ArrayList<>();

    InMemoryDB() {
    }

    public List<JoinPoint> getJoinPoints() {
        return joinPoints;
    }
    public List<Equipment> getEquipments() {
        return equipments;
    }
    public List<Route> getRoutes() {
        return routes;
    }
    public List<Cable> getCables() {
        return cables;
    }
    public List<Journal> getJournals() {
        return journals;
    }
}
