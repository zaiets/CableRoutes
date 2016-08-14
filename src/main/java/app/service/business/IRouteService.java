package app.service.business;

import app.dto.models.JoinPointDto;
import app.dto.models.RouteDto;
import app.dto.models.RouteTypeDto;

import java.util.List;


public interface IRouteService extends IService<RouteDto, String> {

	List<RouteDto> readByRouteType(RouteTypeDto routeType);

	List<RouteDto> readByJoinPoint(JoinPointDto point);

	List<RouteDto> readByTwoJoinPoints(JoinPointDto point1, JoinPointDto point2);

}

