package repository;

import app.repository.dao.business.IJoinPointDao;
import app.repository.entities.business.JoinPoint;
import configuration.HibernateConfigurationTest;
import org.hibernate.SessionFactory;
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

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigurationTest.class})
@Rollback
@Transactional
public class JoinPointDaoTest {

    private JoinPoint testEntity;

    @Autowired
    private IJoinPointDao joinPointDao;

    @Autowired
    private SessionFactory sessionFactory;

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
        joinPoint.setKksName("Point_Test_4");
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
