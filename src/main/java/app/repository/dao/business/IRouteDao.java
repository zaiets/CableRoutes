package app.repository.dao.business;

import app.repository.entities.business.Cable;
import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Route;
import app.repository.entities.business.RouteType;

import java.util.List;


public interface IRouteDao extends IDao<Route> {

	List<Route> readByRouteType(RouteType routeType);

	List<Route> readByJoinPoint(JoinPoint joinPoint);

	List<Route> readByTwoJoinPoints(JoinPoint point1, JoinPoint point2);

	List<Route> readByCable(Cable cable);

}

