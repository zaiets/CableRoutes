package app.repository.dao.business.implInMemory;

import app.repository.dao.business.IDao;
import app.repository.dao.business.InMemoryDB;
import app.repository.entities.business.JoinPoint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class JoinPointDao implements IDao<JoinPoint> {

    InMemoryDB inMemoryDB = InMemoryDB.INSTANCE;


    @Override
    public boolean create(JoinPoint joinPoint) {
        if (joinPoint == null) return false;
        for (JoinPoint o : inMemoryDB.getJoinPoints()) {
            if (o.getCommonKks().equals(joinPoint.getCommonKks())) {
                //throw new RuntimeException("Duplicates in joinPoint list");
                return false;
            }
        }
        return inMemoryDB.getJoinPoints().add(joinPoint);
    }

    @Override
    public boolean createOrUpdate(JoinPoint joinPoint) {
        try {
            if (!create(joinPoint)) {
                return update(joinPoint);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return update(joinPoint);
        }
        return true;
    }

    @Override
    public JoinPoint read(String uniqueName) {
        JoinPoint joinPoint = null;
        for (JoinPoint o : inMemoryDB.getJoinPoints()) {
            if (o.getCommonKks().equals(uniqueName)) {
                if (joinPoint == null) {joinPoint = o;}
                else throw new RuntimeException("Duplicates in joinPoint list");
            }
        }
        return joinPoint;
    }

    @Override
    public boolean update(JoinPoint joinPoint) {
        if (joinPoint == null) return false;
        for (JoinPoint o : inMemoryDB.getJoinPoints()) {
            if (o.getCommonKks().equals(joinPoint.getCommonKks())) {
                o.setXyz(joinPoint.getXyz());
            } else {
                create(o);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        JoinPoint joinPoint = null;
        for (JoinPoint o : inMemoryDB.getJoinPoints()) {
            if (o.getCommonKks().equals(uniqueName)) {
                if (joinPoint == null) {joinPoint = o;}
                else throw new RuntimeException("Duplicates in joinPoint list");
            }
        }
        return inMemoryDB.getJoinPoints().remove(joinPoint);
    }

    @Override
    public List<JoinPoint> getAll(){
        return inMemoryDB.getJoinPoints();
    }
}
