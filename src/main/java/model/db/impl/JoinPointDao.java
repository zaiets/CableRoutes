package model.db.impl;

import model.db.IDao;
import model.db.InMemoryDB;
import model.entities.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class JoinPointDao implements IDao<JoinPoint> {
    @Autowired
    InMemoryDB database;

    List<JoinPoint> joinPoints = database.getJoinPoints();


    @Override
    public boolean create(JoinPoint joinPoint) {
        if (joinPoint == null) return false;
        for (JoinPoint o : joinPoints) {
            if (o.getKksName().equals(joinPoint.getKksName())) {
                throw new RuntimeException("Duplicates in joinPoint list");
            }
        }
        return joinPoints.add(joinPoint);
    }

    @Override
    public boolean createOrUpdate(JoinPoint joinPoint) {
        try {
            if (!create(joinPoint)) {
                return update(joinPoint);
            }
        } catch (RuntimeException ex) {
            return update(joinPoint);
        }
        return true;
    }

    @Override
    public JoinPoint read(String uniqueName) {
        JoinPoint joinPoint = null;
        for (JoinPoint o : joinPoints) {
            if (o.getKksName().equals(uniqueName)) {
                if (joinPoint == null) {joinPoint = o;}
                else throw new RuntimeException("Duplicates in joinPoint list");
            }
        }
        return joinPoint;
    }

    @Override
    public boolean update(JoinPoint joinPoint) {
        if (joinPoint == null) return false;
        for (JoinPoint o : joinPoints) {
            if (o.getKksName().equals(joinPoint.getKksName())) {
                joinPoints.remove(o);
                joinPoints.add(joinPoint);
            } else {
                throw new RuntimeException("Can't find joinPoint in list:" + joinPoint.getKksName());
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        JoinPoint joinPoint = null;
        for (JoinPoint o : joinPoints) {
            if (o.getKksName().equals(uniqueName)) {
                if (joinPoint == null) {joinPoint = o;}
                else throw new RuntimeException("Duplicates in joinPoint list");
            }
        }
        return joinPoints.remove(joinPoint);
    }

    @Override
    public List<JoinPoint> getAll(){
        return joinPoints;
    }
}
