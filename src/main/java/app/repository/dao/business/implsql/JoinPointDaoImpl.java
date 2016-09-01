package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.IJoinPointDao;
import app.repository.entities.business.JoinPoint;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JoinPointDaoImpl extends AbstractDao<String, JoinPoint> implements IJoinPointDao {

    static final Logger logger = LoggerFactory.getLogger(JoinPointDaoImpl.class);

    @Override
    public boolean create(JoinPoint joinPoint) {
        logger.info("Creating new joinPoint in DB: {}", joinPoint.getUniqueName());
        persist(joinPoint);
        return getByKey(joinPoint.getUniqueName()) != null;
    }

    @Override
    public boolean createOrUpdate(JoinPoint joinPoint) {
        logger.info("Create or update joinPoint {} in DB!", joinPoint.getUniqueName());
        saveOrUpdate(joinPoint);
        return getByKey(joinPoint.getUniqueName()).equals(joinPoint);
    }

    @Override
    public JoinPoint read(String kks) {
        logger.info("Reading joinPoint by KKS: {}", kks);
        return getByKey(kks);
    }

    @Override
    public boolean update(String kks, JoinPoint joinPoint) {
        logger.info("Updating joinPoint: {}", joinPoint.getUniqueName());
        update(joinPoint);
        return getByKey(kks).equals(joinPoint);
    }

    @Override
    public boolean delete(String kks) {
        logger.info("Deleting joinPoint: {}", kks);
        JoinPoint joinPoint = getByKey(kks);
        if (joinPoint == null) return true;
        else {
            super.delete(joinPoint);
            return getByKey(kks) == null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JoinPoint> getAll() {
        logger.info("Reading all joinPoints");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<JoinPoint>) criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JoinPoint readByXyz(double x, double y, double z) {
        logger.info("Reading joinPoint by coordinates {}, {}, {}", x, y, z);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("X", x));
        criteria.add(Restrictions.eq("Y", y));
        criteria.add(Restrictions.eq("Z", z));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (JoinPoint)criteria.uniqueResult();
    }

}
