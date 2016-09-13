package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.IRouteDao;
import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Route;
import app.repository.entities.business.RouteType;
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
public class RouteDaoImpl extends AbstractDao<String, Route> implements IRouteDao {

    static final Logger logger = LoggerFactory.getLogger(RouteDaoImpl.class);

    @Override
    public boolean create(Route route) {
        logger.info("Creating new route in DB: {}", route.getUniqueName());
        persist(route);
        return getByKey(route.getUniqueName()) != null;
    }

    @Override
    public boolean createOrUpdate(Route route) {
        logger.info("Create or update route {} in DB!", route.getUniqueName());
        saveOrUpdate(route);
        return getByKey(route.getUniqueName()).equals(route);
    }

    @Override
    public Route read(String kks) {
        logger.info("Reading route by kks: {}", kks);
        Route route = getByKey(kks);
        if(route!=null){
            Hibernate.initialize(route.getFirstEnd());
            Hibernate.initialize(route.getSecondEnd());
            Hibernate.initialize(route.getRouteType());
        }
        return route;
    }

    @Override
    public boolean update(String kks, Route route) {
        logger.info("Updating route: {}", route.getUniqueName());
        update(route);
        return getByKey(kks).equals(route);
    }

    @Override
    public boolean delete(String kks) {
        logger.info("Deleting route: {}", kks);
        Route route = getByKey(kks);
        if (route == null) return true;
        else {
            super.delete(route);
            return getByKey(kks) == null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Route> getAll(){
        logger.info("Reading all routes");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Route> routes = (List<Route>) criteria.list();
        for(Route route : routes){
            Hibernate.initialize(route.getFirstEnd());
            Hibernate.initialize(route.getSecondEnd());
            Hibernate.initialize(route.getRouteType());
        }
        return routes;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Route> readAllByRouteType(RouteType routeType) {
        logger.info("Reading all routes by route type", routeType.toString());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria.add(Restrictions.eq("routeType", routeType.getMarker()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Route> routes = (List<Route>) criteria.list();
        for(Route route : routes){
            Hibernate.initialize(route.getFirstEnd());
            Hibernate.initialize(route.getSecondEnd());
            Hibernate.initialize(route.getRouteType());
        }
        return routes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Route> readAllByJoinPoint(JoinPoint joinPoint) {
        logger.info("Reading all routes by joinPoint", joinPoint.getUniqueName());
        List<Route> routes = new ArrayList<>();
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria1.add(Restrictions.eq("firstEnd", joinPoint.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        routes.addAll((List<Route>) criteria1.list());
        Criteria criteria2 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria2.add(Restrictions.eq("secondEnd", joinPoint.getUniqueName()));
        criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        routes.addAll((List<Route>) criteria2.list());
        for(Route route : routes){
            Hibernate.initialize(route.getFirstEnd());
            Hibernate.initialize(route.getSecondEnd());
            Hibernate.initialize(route.getRouteType());
        }
        return routes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Route> readAllByTwoJoinPoints(JoinPoint point1, JoinPoint point2) {
        logger.info("Reading all routes between joinPoint {} and {}", point1.getUniqueName(), point2.getUniqueName());
        List<Route> routes = new ArrayList<>();
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria1.add(Restrictions.eq("firstEnd", point1.getUniqueName()));
        criteria1.add(Restrictions.eq("secondEnd", point2.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        routes.addAll((List<Route>) criteria1.list());
        Criteria criteria2 = createEntityCriteria().addOrder(Order.asc("kksName"));
        criteria2.add(Restrictions.eq("secondEnd", point1.getUniqueName()));
        criteria2.add(Restrictions.eq("firstEnd", point2.getUniqueName()));
        criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        routes.addAll((List<Route>) criteria2.list());
        for(Route route : routes){
            Hibernate.initialize(route.getFirstEnd());
            Hibernate.initialize(route.getSecondEnd());
            Hibernate.initialize(route.getRouteType());
        }
        return routes;
    }
}
