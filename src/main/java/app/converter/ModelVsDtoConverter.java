package app.converter;

import app.dto.models.*;
import app.repository.entities.business.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class designed to directive transform entities to DTO and DTO to entities
 */
public final class ModelVsDtoConverter {

    private ModelVsDtoConverter() {
    }

    public static CableDto transformCable(Cable cable) {
        CableDto cableDto = new CableDto();
        cableDto.setKksName(cable.getKksName());
        cableDto.setJournal(cable.getJournalName());
        cableDto.setCableDimensions(cable.getCableDimensions());
        cableDto.setCableType(cable.getCableType());
        cableDto.setEndEquipment(cable.getEnd().getFullName());
        cableDto.setStartEquipment(cable.getStart().getFullName());
        cableDto.setReserving(cable.getReserving());
        cableDto.setLength(cable.getLength());
        cableDto.setNumberInJournal(cable.getNumberInJournal());
        return cableDto;
    }

    public static Cable transformCableDto(CableDto cableDto, Equipment start, Equipment end) {
        Cable cable = new Cable();
        cable.setKksName(cableDto.getKksName());
        cable.setJournalName(cableDto.getJournal());
        cable.setCableDimensions(cableDto.getCableDimensions());
        cable.setCableType(cableDto.getCableType());
        cable.setEnd(start);
        cable.setStart(end);
        cable.setReserving(cableDto.getReserving());
        cable.setLength(cableDto.getLength());
        cable.setNumberInJournal(cableDto.getNumberInJournal());
        return null;
    }
    public static EquipmentDto transformEquipment(Equipment equipment) {
        EquipmentDto equipmentDto = new EquipmentDto();
        equipmentDto.setFullName(equipment.getFullName());
        equipmentDto.setCommonKks(equipment.getCommonKks());
        equipmentDto.setJoinPoint(transformJoinPoint(equipment.getJoinPoint()));
        equipmentDto.setCableConnectionAddLength(equipment.getCableConnectionAddLength());
        equipmentDto.setX(equipment.getX());
        equipmentDto.setY(equipment.getY());
        equipmentDto.setZ(equipment.getZ());
        return equipmentDto;
    }
    public static Equipment transformEquipmentDto(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment();
        equipment.setFullName(equipmentDto.getFullName());
        equipment.setCommonKks(equipmentDto.getCommonKks());
        equipment.setJoinPoint(transformJoinPointDto(equipmentDto.getJoinPoint()));
        equipment.setCableConnectionAddLength(equipmentDto.getCableConnectionAddLength());
        equipment.setX(equipmentDto.getX());
        equipment.setY(equipmentDto.getY());
        equipment.setZ(equipmentDto.getZ());
        return equipment;
    }
    public static JoinPointDto transformJoinPoint(JoinPoint joinPoint) {
        JoinPointDto joinPointDto = new JoinPointDto();
        joinPointDto.setKksName(joinPoint.getKksName());
        joinPointDto.setX(joinPoint.getX());
        joinPointDto.setY(joinPoint.getY());
        joinPointDto.setZ(joinPoint.getZ());
        return joinPointDto;
    }
    public static JoinPoint transformJoinPointDto(JoinPointDto joinPointDto) {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setKksName(joinPointDto.getKksName());
        joinPoint.setX(joinPointDto.getX());
        joinPoint.setY(joinPointDto.getY());
        joinPoint.setZ(joinPointDto.getZ());
        return joinPoint;
    }
    public static JournalDto transformJournal(Journal journal) {
        JournalDto journalDto = new JournalDto();
        journalDto.setKksName(journal.getKksName());
        journalDto.setFileName(journal.getFileName());
        List<Cable> cables = journal.getCables();
        if (cables != null && !cables.isEmpty()) {
            List<CableDto> cableDtos = new ArrayList<>();
            cables.forEach(o -> cableDtos.add(transformCable(o)));
            journalDto.setCables(cableDtos);
        }
        return journalDto;
    }
    public static Journal transformJournalDto(JournalDto journalDto, List<Cable> cables) {
        Journal journal = new Journal();
        journal.setKksName(journalDto.getKksName());
        journal.setFileName(journalDto.getFileName());
        journal.setCables(cables);
        return journal;
    }
    public static LineDto transformLine(Line line) {
        LineDto lineDto = new LineDto();
        lineDto.setEndPoint(transformJoinPoint(line.getEndPoint()));
        lineDto.setStartPoint(transformJoinPoint(line.getStartPoint()));
        lineDto.setTraced(line.isTraced());
        List<Route> routes = line.getRoutesList();
        if (routes != null && !routes.isEmpty()) {
            List<RouteDto> routeDtoList = new ArrayList<>();
            routes.forEach(o -> routeDtoList.add(transformRoute(o)));
            lineDto.setRoutesList(routeDtoList);
        }
        return lineDto;
    }
    public static Line transformLineDto(LineDto lineDto, List<Route> routes) {
        Line line = new Line();
        line.setEndPoint(transformJoinPointDto(lineDto.getEndPoint()));
        line.setStartPoint(transformJoinPointDto(lineDto.getStartPoint()));
        line.setTraced(lineDto.isTraced());
        line.setRoutesList(routes);
        return line;
    }
    public static RouteDto transformRoute(Route route) {
        RouteDto routeDto = new RouteDto();
        routeDto.setKksName(route.getKksName());
        routeDto.setFirstEnd(transformJoinPoint(route.getFirstEnd()));
        routeDto.setSecondEnd(transformJoinPoint(route.getSecondEnd()));
        routeDto.setHeight(route.getHeight());
        routeDto.setLength(route.getLength());
        routeDto.setShelvesCount(route.getShelvesCount());
        routeDto.setRouteType(transformRouteType(route.getRouteType()));
        List<Cable> cablesList = route.getCablesList();
        if (cablesList != null && !cablesList.isEmpty()) {
            List<CableDto> cableDtoList = new ArrayList<>();
            cablesList.forEach(o -> cableDtoList.add(transformCable(o)));
            routeDto.setCablesList(cableDtoList);
        }
        return routeDto;
    }
    public static Route transformRouteDto(RouteDto routeDto, List<Cable> cables) {
        Route route = new Route();
        route.setKksName(routeDto.getKksName());
        route.setFirstEnd(transformJoinPointDto(routeDto.getFirstEnd()));
        route.setSecondEnd(transformJoinPointDto(routeDto.getSecondEnd()));
        route.setHeight(routeDto.getHeight());
        route.setLength(routeDto.getLength());
        route.setShelvesCount(routeDto.getShelvesCount());
        route.setRouteType(transformRouteTypeDto(routeDto.getRouteType()));
        route.setCablesList(cables);
        return route;
    }
    public static RouteTypeDto transformRouteType(RouteType routeType) {
        RouteTypeDto routeTypeDto = new RouteTypeDto();
        routeTypeDto.setMarker(routeType.getMarker());
        routeTypeDto.setName(routeType.getName());
        return routeTypeDto;
    }
    public static RouteType transformRouteTypeDto(RouteTypeDto routeTypeDto) {
        RouteType routeType = new RouteType();
        routeType.setMarker(routeTypeDto.getMarker());
        routeType.setName(routeTypeDto.getName());
        return routeType;
    }
}
