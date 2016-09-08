package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.IEquipmentDao;
import app.repository.entities.business.Equipment;
import app.repository.entities.business.JoinPoint;
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
public class EquipmentDaoImpl extends AbstractDao<String, Equipment> implements IEquipmentDao {

    static final Logger logger = LoggerFactory.getLogger(EquipmentDaoImpl.class);

    @Override
    public boolean create(Equipment equipment) {
        logger.info("Creating new equipment in DB: {}", equipment.getUniqueName());
        persist(equipment);
        return getByKey(equipment.getUniqueName()) != null;
    }

    @Override
    public boolean createOrUpdate(Equipment equipment) {
        logger.info("Create or update equipment {} in DB!", equipment.getUniqueName());
        saveOrUpdate(equipment);
        return getByKey(equipment.getUniqueName()).equals(equipment);
    }

    @Override
    public Equipment read(String fullUniqueName) {
        logger.info("Reading equipment by full unique name: {}", fullUniqueName);
        Equipment equipment = getByKey(fullUniqueName);
        if(equipment!=null){
            Hibernate.initialize(equipment.getJoinPoint());
        }
        return equipment;
    }

    @Override
    public boolean update(String fullUniqueName, Equipment equipment) {
        logger.info("Updating equipment: {}", equipment.getUniqueName());
        update(equipment);
        return getByKey(fullUniqueName).equals(equipment);
    }

    @Override
    public boolean delete(String fullUniqueName) {
        logger.info("Deleting equipment: {}", fullUniqueName);
        Equipment equipment = getByKey(fullUniqueName);
        if (equipment == null) return true;
        else {
            super.delete(equipment);
            return getByKey(fullUniqueName) == null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Equipment> getAll(){
        logger.info("Reading all equipments");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("commonKks"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Equipment> equipments = (List<Equipment>) criteria.list();
        for(Equipment equipment : equipments){
            Hibernate.initialize(equipment.getJoinPoint());
        }
        return equipments;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Equipment> readAllByJoinPoint(JoinPoint joinPoint) {
        List<Equipment> equipments = new ArrayList<>();
        logger.info("Reading all equipments by join point {}", joinPoint.getUniqueName());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("commonKks"));
        criteria.add(Restrictions.like("joinPointKks", joinPoint.getUniqueName()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        equipments.addAll((List<Equipment>) criteria.list());
        for(Equipment equipment : equipments){
            Hibernate.initialize(equipment.getJoinPoint());
        }
        return equipments;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Equipment> readAllByKks(String kks) {
        List<Equipment> equipments = new ArrayList<>();
        logger.info("Reading all equipments by KKS {}", kks);
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("fullName"));
        criteria.add(Restrictions.like("commonKks", kks));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        equipments.addAll((List<Equipment>) criteria.list());
        for(Equipment equipment : equipments){
            Hibernate.initialize(equipment.getJoinPoint());
        }
        return equipments;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Equipment> readAllByXyz(Double x, Double y, Double z, boolean includeNull) {
        List<Equipment> equipments = new ArrayList<>();
        logger.info("Reading all equipments by coordinates {}, {}, {}", x, y, z);
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("fullName"));
        if (includeNull) {
            criteria.add(Restrictions.eq("x", x));
            criteria.add(Restrictions.eq("y", y));
            criteria.add(Restrictions.eq("z", z));
        } else {
            if (x != null) criteria.add(Restrictions.eq("x", x));
            if (y != null) criteria.add(Restrictions.eq("y", y));
            if (z != null) criteria.add(Restrictions.eq("z", z));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        equipments.addAll((List<Equipment>) criteria.list());
        for(Equipment equipment : equipments){
            Hibernate.initialize(equipment.getJoinPoint());
        }
        return equipments;
    }

}
