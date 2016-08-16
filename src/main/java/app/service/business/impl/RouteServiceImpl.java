package app.service.business.impl;

import app.dto.models.JoinPointDto;
import app.dto.models.RouteDto;
import app.dto.models.RouteTypeDto;
import app.repository.dao.business.IRouteDao;
import app.repository.entities.business.Route;
import app.service.business.IRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static app.converter.ModelVsDtoConverter.*;

@Service
public class RouteServiceImpl implements IRouteService {

    static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);

    @Autowired
    private IRouteDao routeDao;

    @Override
    public boolean create(RouteDto routeDto) {
        logger.info("RouteService is creating new Route: {}", routeDto.getKksName());
        return routeDao.create(transformRouteDto(routeDto));
    }

    @Override
    public boolean createOrUpdate(RouteDto routeDto) {
        logger.info("RouteService is creating new Route: {}", routeDto.getKksName());
        return routeDao.createOrUpdate(transformRouteDto(routeDto));
    }

    @Override
    public RouteDto read(String kks) {
        logger.info("RouteService is reading Route by kks: {}", kks);
        return transformRoute(routeDao.read(kks));
    }

    @Override
    public boolean update(String kks, RouteDto routeDto) {
        logger.info("RouteService is updating Route: {}", routeDto.getKksName());
        return routeDao.update(kks, transformRouteDto(routeDto));
    }

    @Override
    public boolean delete(String kks) {
        return routeDao.delete(kks);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RouteDto> getAll(){
        logger.info("RouteService is reading all Routes");
        List<RouteDto> routeDtoList = new ArrayList<>();
        routeDao.getAll().forEach(o -> routeDtoList.add(transformRoute(o)));
        return routeDtoList;
    }

    @Override
    public List<RouteDto> readAllByJoinPoint (JoinPointDto pointDto) {
        logger.info("RouteService is reading Routes by joinPoint: {}", pointDto.getKksName());
        List<RouteDto> routeDtoList = new ArrayList<>();
        List<Route> routeList = routeDao.readAllByJoinPoint(transformJoinPointDto(pointDto));
        if (routeList != null && !routeList.isEmpty())
            routeList.forEach(o -> routeDtoList.add(transformRoute(o)));
        return routeDtoList;
    }

    @Override
    public List<RouteDto> readAllByTwoJoinPoints (JoinPointDto point1, JoinPointDto point2) {
        logger.info("RouteService is reading Routes by two joinPoints: {} and {}", point1.getKksName(), point2.getKksName());
        List<RouteDto> routeDtoList = new ArrayList<>();
        List<Route> routeList = routeDao.readAllByTwoJoinPoints(transformJoinPointDto(point1), transformJoinPointDto(point2));
        if (routeList != null && !routeList.isEmpty())
            routeList.forEach(o -> routeDtoList.add(transformRoute(o)));
        return routeDtoList;
    }

    @Override
    public List<RouteDto> readByRouteType (RouteTypeDto routeTypeDto) {
        logger.info("RouteService is reading Routes by routeType: {}", routeTypeDto.getMarker());
        List<RouteDto> routeDtoList = new ArrayList<>();
        List<Route> routeList = routeDao.readAllByRouteType(transformRouteTypeDto(routeTypeDto));
        if (routeList != null && !routeList.isEmpty())
            routeList.forEach(o -> routeDtoList.add(transformRoute(o)));
        return routeDtoList;
    }
}
