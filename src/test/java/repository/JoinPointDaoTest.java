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
        testEntity.setKksName("TestPoint#1");
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
    public void create() {
        JoinPoint joinPoint;
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
