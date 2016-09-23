package repository.stubs;

import app.repository.dao.business.IJoinPointDao;
import app.repository.entities.business.JoinPoint;

import java.util.Collections;
import java.util.List;

public class JoinPointDaoImplStub implements IJoinPointDao{
    JoinPoint jp;

    {
        jp = new JoinPoint();
        jp.setKksName("Point_Stub");
        jp.setX(11);
        jp.setY(11);
        jp.setZ(11);
    }

    @Override
    public JoinPoint readByXyz(double x, double y, double z) {
        if (x == jp.getX() && y == jp.getY() && z == jp.getZ()) {
            return jp;
        }
        else {
            return null;
        }
    }

    @Override
    public boolean create(JoinPoint joinPoint) {
        return joinPoint != null;
    }

    @Override
    public boolean createOrUpdate(JoinPoint joinPoint) {
        return joinPoint != null;
    }

    @Override
    public JoinPoint read(String uniqueName) {
        return uniqueName.equals("TestPoint#1")? jp : null;
    }

    @Override
    public boolean update(String uniqueName, JoinPoint joinPoint) {
        return uniqueName.equals("TestPoint#1")&&joinPoint != null;
    }

    @Override
    public boolean delete(String uniqueName) {
        return uniqueName.equals("TestPoint#1");
    }

    @Override
    public List<JoinPoint> getAll() {
        return Collections.singletonList(jp);
    }
}
