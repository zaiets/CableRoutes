package app.repository.dao.business;

import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Route;
import app.repository.entities.business.RouteType;

import java.util.List;


public interface IRouteDao extends IDao<Route> {

	List<Route> readAllByRouteType(RouteType routeType);

	List<Route> readAllByJoinPoint(JoinPoint joinPoint);

	List<Route> readAllByTwoJoinPoints(JoinPoint point1, JoinPoint point2);

}

