package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.IRouteTypeDao;
import app.repository.entities.business.RouteType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class RouteTypeDaoImpl extends AbstractDao<String, RouteType> implements IRouteTypeDao {

    static final Logger logger = LoggerFactory.getLogger(RouteTypeDaoImpl.class);

    @Override
    public boolean create(RouteType routeType) {
        logger.info("Creating new routeType in DB: {}",  routeType.toString());
        persist(routeType);
        return getByKey(routeType.getMarker()) != null;
    }

    @Override
    public boolean createOrUpdate(RouteType routeType) {
        logger.info("Create or update routeType {} in DB!", routeType.toString());
        saveOrUpdate(routeType);
        return getByKey(routeType.getUniqueName()).equals(routeType);
    }


    @Override
    public boolean update (String marker, RouteType routeType) {
        logger.info("Updating routeType: {}", routeType.toString());
        super.update(routeType);
        return getByKey(marker).equals(routeType);
    }

    @Override
    public RouteType read (String marker) {
        logger.info("Reading routeType by ID: {}", marker);
        return getByKey(marker);
    }

    @Override
    public boolean delete(String marker) {
        logger.info("Deleting routeType: {}", marker);
        RouteType routeType = getByKey(marker);
        delete(routeType);
        if (routeType == null) return true;
        else {
            super.delete(routeType);
            return getByKey(marker) == null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RouteType> getAll(){
        logger.info("Reading all routeTypes");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("marker"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<RouteType>) criteria.list();
    }

}
