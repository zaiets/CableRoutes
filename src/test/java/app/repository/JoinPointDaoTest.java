package app.repository;

import app.configuration.HibernateConfigurationTest;
import app.repository.dao.business.IJoinPointDao;
import app.repository.entities.business.JoinPoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigurationTest.class})
@Rollback
@Transactional
public class JoinPointDaoTest {

    private JoinPoint testEntity;

    @Autowired
    private IJoinPointDao joinPointDao;

    @Before
    public void beforeTest() {
        testEntity = new JoinPoint();
        testEntity.setKksName("Point_Test_1");
        testEntity.setX(-1);
        testEntity.setY(2);
        testEntity.setZ(3.5);
    }

    @Test
    public void readByXyz() {
        Assert.assertEquals(testEntity, joinPointDao.readByXyz(-1, 2, 3.5));
        Assert.assertNull(joinPointDao.readByXyz(1, 2, 3.5));
    }

    @Test
    public void create1() {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setX(10);
        joinPoint.setY(10);
        joinPoint.setZ(10);
        joinPoint.setKksName("Point_Create");
        Assert.assertTrue(joinPointDao.create(joinPoint));
    }
    @Test
    public void create2() {
        Assert.assertTrue(joinPointDao.create(testEntity));
        Assert.assertTrue(joinPointDao.create(testEntity));
    }
    @Test(expected = Exception.class)
    public void create3() {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setX(10);
        joinPoint.setY(10);
        joinPoint.setZ(10);
        Assert.assertFalse(joinPointDao.create(joinPoint));
    }
    @Test(expected = Exception.class)
    public void create4() {
        Assert.assertFalse(joinPointDao.create(null));
    }

    @Test
    public void createOrUpdate1() {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setX(10);
        joinPoint.setY(10);
        joinPoint.setZ(10);
        joinPoint.setKksName("Point_CreateOrUpdate");
        Assert.assertTrue(joinPointDao.createOrUpdate(joinPoint));
        joinPoint.setX(15);
        joinPointDao.createOrUpdate(joinPoint);
        Assert.assertTrue(joinPointDao.read("Point_CreateOrUpdate").equals(joinPoint));
    }
    @Test(expected = Exception.class)
    public void createOrUpdate2() {
        Assert.assertFalse(joinPointDao.createOrUpdate(null));
    }

    @Test
    public void read() {
        Assert.assertTrue(joinPointDao.read("Point_Test_1").equals(testEntity));
        Assert.assertNull(joinPointDao.read("Some_undefined_point"));
    }

    @Test
    public void update() {
        testEntity.setX(15);
        joinPointDao.update("Point_Test_1", testEntity);
        Assert.assertTrue(joinPointDao.read("Point_Test_1").equals(testEntity));
        Assert.assertFalse(joinPointDao.update("Point_Test_2", testEntity));
    }

    @Test
    public void delete() {
        Assert.assertTrue(joinPointDao.delete("Point_Test_1"));
        Assert.assertNull(joinPointDao.read("Point_Test_1"));
    }

    @Test
    public void getAll() {
        List<JoinPoint> points = joinPointDao.getAll();
        Assert.assertTrue(points.contains(testEntity));
        Assert.assertTrue(points.size() == 3);
    }
}
