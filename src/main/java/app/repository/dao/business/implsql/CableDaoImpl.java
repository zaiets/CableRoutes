package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.ICableDao;
import app.repository.entities.business.Cable;
import app.repository.entities.business.Equipment;
import app.repository.entities.business.Journal;
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
        try {
            logger.info("Creating cable {} in DB!", cable.getUniqueName());
            if (!create(cable)) {
                return update(cable.getUniqueName(), cable);
            }
        } catch (RuntimeException ex) {
            //todo ??
            logger.error("Exception while created/updated cable {} in DB!", cable.getUniqueName());
            ex.printStackTrace();
            return update(cable.getUniqueName(), cable);
        }
        return true;
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
        return read(kks).equals(cable);
    }

    @Override
    public boolean delete(String kks) {
        logger.info("Deleting cable: {}", kks);
        Cable cable = getByKey(kks);
        if (cable == null) return true;
        else {
            super.delete(cable);
            return read(kks) == null;
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
    public List<Cable> readAllByTwoEquipments(Equipment eq1, Equipment eq2) {
        List<Cable> cables = new ArrayList<>();
        logger.info("Reading all cables by equipments {} and {}", eq1.getUniqueName(), eq2.getUniqueName());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria.add(Restrictions.like("EQUIPMENT_START_NAME", eq1.getUniqueName()));
        criteria.add(Restrictions.like("EQUIPMENT_END_NAME", eq2.getUniqueName()));
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
