package app.service.entities.impl;

import app.dto.models.RouteTypeDto;
import app.repository.dao.business.IRouteTypeDao;
import app.repository.entities.business.RouteType;
import app.service.entities.IRouteTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static app.converter.ModelVsDtoConverter.*;

@Service
public class RouteTypeServiceImpl implements IRouteTypeService {

    static final Logger logger = LoggerFactory.getLogger(RouteTypeServiceImpl.class);

    @Autowired
    private IRouteTypeDao routeTypeDao;

    @Override
    public boolean create(RouteTypeDto routeTypeDto) {
        logger.info("RouteTypeService is creating new RouteType: {}", routeTypeDto.getMarker());
        return routeTypeDao.create(transformRouteTypeDto(routeTypeDto));
    }

    @Override
    public boolean createOrUpdate(RouteTypeDto routeTypeDto) {
        logger.info("RouteTypeService is creating new RouteType: {}", routeTypeDto.getMarker());
        return routeTypeDao.createOrUpdate(transformRouteTypeDto(routeTypeDto));
    }

    @Override
    public RouteTypeDto read(String kks) {
        logger.info("RouteTypeService is reading RouteType by marker: {}", kks);
        return transformRouteType(routeTypeDao.read(kks));
    }

    @Override
    public boolean update(String marker, RouteTypeDto routeTypeDto) {
        logger.info("RouteTypeService is updating RouteType: {}", routeTypeDto.getMarker());
        return routeTypeDao.update(marker, transformRouteTypeDto(routeTypeDto));
    }

    @Override
    public boolean delete(String kks) {
        return routeTypeDao.delete(kks);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RouteTypeDto> getAll(){
        logger.info("RouteTypeService is reading all RouteTypes");
        List<RouteTypeDto> routeTypeDtoList = new ArrayList<>();
        List<RouteType> routeTypeList = routeTypeDao.getAll();
        if (routeTypeList != null && !routeTypeList.isEmpty())routeTypeList.forEach(o -> routeTypeDtoList.add(transformRouteType(o)));
        return routeTypeDtoList;
    }

}
