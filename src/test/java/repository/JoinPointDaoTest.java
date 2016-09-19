package repository;

import app.repository.dao.business.IJoinPointDao;
import app.repository.dao.business.implsql.JoinPointDaoImpl;
import app.repository.entities.business.JoinPoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JoinPointDaoTest {
    JoinPoint testEntity;
    @Before
    public void beforeTest() {
        testEntity = new JoinPoint();
        testEntity.setKksName("TestPoint#1");
        testEntity.setX(-1);
        testEntity.setY(2);
        testEntity.setZ(3.5);

    }

    @Test
    public void readByXyz() {
        IJoinPointDao dao = new JoinPointDaoImpl();
        Assert.assertEquals(testEntity, dao.readByXyz(-1, 2, 3.5));
        Assert.assertEquals(null, dao.readByXyz(1, 2, 3.5));
    }

    @Test
    public void create(JoinPoint joinPoint) {
        Assert.fail("TODO implement");
    }

    @Test
    public void createOrUpdate() {
        Assert.fail("TODO implement");
        JoinPoint entity;
    }

    @Test
    public void read() {
        Assert.fail("TODO implement");
        String uniqueName;
    }

    @Test
    public void update() {
        Assert.fail("TODO implement");
        String uniqueName;
        JoinPoint entity;
    }

    @Test
    public void delete() {
        Assert.fail("TODO implement");
        String uniqueName;
    }

    @Test
    public void getAll() {
        Assert.fail("TODO implement");
    }
}
