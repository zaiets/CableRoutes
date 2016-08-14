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

import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CableDaoImpl extends AbstractDao<String, Cable> implements ICableDao {

    static final Logger logger = LoggerFactory.getLogger(CableDaoImpl.class);

    @Override
    public boolean create(Cable cable) {
        logger.info("Creating new cable in DB: {}", cable.getUniqueName());
        persist(cable);
        return getByKey(cable.getUniqueName()) != null;
    }

    @Override
    public boolean createOrUpdate(Cable cable) {
        logger.info("Create or update cable {} in DB!", cable.getUniqueName());
        saveOrUpdate(cable);
        return getByKey(cable.getUniqueName()).equals(cable);
    }

    @Override
    public Cable read(String kks) {
        logger.info("Reading cable by kks: {}", kks);
        Cable cable = getByKey(kks);
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
        return getByKey(kks).equals(cable);
    }

    @Override
    public boolean delete(String kks) {
        logger.info("Deleting cable: {}", kks);
        Cable cable = getByKey(kks);
        if (cable == null) return true;
        else {
            super.delete(cable);
            return getByKey(kks) == null;
        }
    }

    @Override
    public List<Cable> getAll(){
        logger.info("Reading all cables");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("NUMBER_IN_JOURNAL"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Cable> cables = (List<Cable>) criteria.list();
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }


    @Override
    public List<Cable> readAllByTwoEquipments(Equipment equipOne, Equipment equipTwo) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by equipments {} and {}", equipOne.getUniqueName(), equipTwo.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria1.add(Restrictions.like("EQUIPMENT_START_NAME", equipOne.getUniqueName()));
        criteria1.add(Restrictions.like("EQUIPMENT_END_NAME", equipTwo.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria.add(Restrictions.like("EQUIPMENT_START_NAME", equipTwo.getUniqueName()));
        criteria.add(Restrictions.like("EQUIPMENT_END_NAME", equipOne.getUniqueName()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    public List<Cable> readAllByEquipment(Equipment equipment) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by equipment {}", equipment.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria1.add(Restrictions.like("EQUIPMENT_START_NAME", equipment.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria2 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria2.add(Restrictions.like("EQUIPMENT_END_NAME", equipment.getUniqueName()));
        criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria2.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    public List<Cable> readAllByJoinPoint(JoinPoint joinPoint) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by join point {}", joinPoint.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria1.add(Restrictions.like("START_JOIN_POINT_KKS", joinPoint.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria2 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria2.add(Restrictions.like("END_JOIN_POINT_KKS", joinPoint.getUniqueName()));
        criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria2.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    public List<Cable> readAllByTwoJoinPoints(JoinPoint pointOne, JoinPoint pointTwo) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by join points {} and {}", pointOne.getUniqueName(), pointTwo.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria1.add(Restrictions.like("START_JOIN_POINT_KKS", pointOne.getUniqueName()));
        criteria1.add(Restrictions.like("END_JOIN_POINT_KKS", pointTwo.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria1.list());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria.add(Restrictions.like("START_JOIN_POINT_KKS", pointTwo.getUniqueName()));
        criteria.add(Restrictions.like("END_JOIN_POINT_KKS", pointOne.getUniqueName()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

    @Override
    public List<Cable> readAllByJournal(Journal journal) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by journal {}", journal.getUniqueName());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("NUMBER_IN_JOURNAL"));
        criteria.add(Restrictions.like("JOURNAL_KKS", journal.getUniqueName()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cables.addAll((List<Cable>) criteria.list());
        for(Cable cable : cables){
            Hibernate.initialize(cable.getStart());
            Hibernate.initialize(cable.getEnd());
        }
        return cables;
    }

}
