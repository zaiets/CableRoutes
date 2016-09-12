package app.repository.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);


    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (Exception ex) {
            logger.info("opening new session.");
            session = sessionFactory.openSession();
        }
        return session;
    }

    @SuppressWarnings("unchecked")
    protected T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    protected void persist(T entity) {
        try {
        getSession().persist(entity);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    protected void saveOrUpdate(T entity) {
        try {
            getSession().saveOrUpdate(entity);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    protected void update(T entity) {
        try {
            getSession().update(entity);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    protected void delete(T entity) {
        try {
            getSession().delete(entity);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass);
    }

}
