package model.db;

import model.entities.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Scope(value = "singleton")
public final class InMemoryDB {

    private List<Journal> journals = new ArrayList<>();
    private List<JoinPoint> joinPoints = new ArrayList<>();
    private List<Equipment> equipments = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
    private List<Cable> cables = new ArrayList<>();

    public InMemoryDB() {
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
