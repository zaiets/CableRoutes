package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.ICableDao;
import app.repository.entities.business.*;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CableDaoImpl extends AbstractDao<Integer, Cable> implements ICableDao {

    static final Logger logger = LoggerFactory.getLogger(CableDaoImpl.class);

    private Cable getByKks(String kks) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("kksName", kks));
        return (Cable) criteria.uniqueResult();
    }

    @Override
    public boolean create(Cable cable) {
        logger.info("Creating new cable in DB: {}", cable.getUniqueName());
        persist(cable);
        return getByKks(cable.getUniqueName()) != null;
    }

    @Override
    public boolean createOrUpdate(Cable cable) {
        logger.info("Create or update cable {} in DB!", cable.getUniqueName());
        saveOrUpdate(cable);
        return getByKks(cable.getUniqueName()).equals(cable);
    }

    @Override
    public Cable read(String kks) {
        logger.info("Reading cable by kks: {}", kks);
        Cable cable = getByKks(kks);
        if(cable!=null){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cable;
    }

    @Override
    public boolean update(String kks, Cable cable) {
        logger.info("Updating cable: {}", cable.getUniqueName());
        update(cable);
        return getByKks(kks).equals(cable);
    }

    @Override
    public boolean delete(String kks) {
        logger.info("Deleting cable: {}", kks);
        Cable cable = getByKks(kks);
        if (cable == null) return true;
        else {
            super.delete(cable);
            return getByKks(kks) == null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cable> getAll(){
        logger.info("Reading all cables");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("numberInJournal"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Cable> cables = (List<Cable>) criteria.list();
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Cable> readAllByTwoEquipments(Equipment equipOne, Equipment equipTwo) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by equipments {} and {}", equipOne.getUniqueName(), equipTwo.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria1.add(Restrictions.like("start", equipOne.getUniqueName()));
        criteria1.add(Restrictions.like("end", equipTwo.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria.add(Restrictions.like("start", equipTwo.getUniqueName()));
        criteria.add(Restrictions.like("end", equipOne.getUniqueName()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cable> readAllByEquipment(Equipment equipment) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by equipment {}", equipment.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria1.add(Restrictions.like("start", equipment.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria2 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria2.add(Restrictions.like("end", equipment.getUniqueName()));
        criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria2.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cable> readAllByJoinPoint(JoinPoint joinPoint) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by join point {}", joinPoint.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria1.add(Restrictions.like("startPoint", joinPoint.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria2 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria2.add(Restrictions.like("endPoint", joinPoint.getUniqueName()));
        criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria2.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cable> readAllByTwoJoinPoints(JoinPoint pointOne, JoinPoint pointTwo) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by join points {} and {}", pointOne.getUniqueName(), pointTwo.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria1.add(Restrictions.like("startPoint", pointOne.getUniqueName()));
        criteria1.add(Restrictions.like("endPoint", pointTwo.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria.add(Restrictions.like("startPoint", pointTwo.getUniqueName()));
        criteria.add(Restrictions.like("endPoint", pointOne.getUniqueName()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cable> readAllByJournal(String journalKks) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by journal {}", journalKks);
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("numberInJournal"));
        criteria.add(Restrictions.like("journalName", journalKks));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

}
