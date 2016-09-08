package app.converter;

import app.dto.common.UserDto;
import app.dto.models.*;
import app.repository.entities.business.*;
import app.repository.entities.common.User;
import app.repository.enumerations.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * This util class designed for direct transformation of entities into DTO and DTO into entities
 */
public final class ModelVsDtoConverter {

    private ModelVsDtoConverter() {
    }

    public static UserDto transformUser(User user, Role role) {
        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPatronymic(user.getPatronymic());
        userDto.setPassword(user.getPassword());
        userDto.setMatchingPassword(user.getPassword());
        userDto.setRole(role.name());
        return userDto;
    }


    public static CableDto transformCable(Cable cable) {
        CableDto cableDto = new CableDto();
        cableDto.setKksName(cable.getKksName());
        cableDto.setJournal(cable.getJournalName());
        cableDto.setCableDimensions(cable.getCableDimensions());
        cableDto.setCableType(cable.getCableType());
        cableDto.setEnd(transformEquipment(cable.getEnd()));
        cableDto.setStart(transformEquipment(cable.getStart()));
        cableDto.setReserving(cable.getReserving());
        cableDto.setLength(cable.getLength());
        cableDto.setNumberInJournal(cable.getNumberInJournal());
        cableDto.setEndPoint(transformJoinPoint(cable.getEndPoint()));
        cableDto.setStartPoint(transformJoinPoint(cable.getStartPoint()));
        cableDto.setTraced(cable.isTraced());
        List<Route> routes = cable.getRoutesList();
        if (routes != null && !routes.isEmpty()) {
            List<RouteDto> routeDtoList = new ArrayList<>();
            routes.forEach(o -> routeDtoList.add(transformRoute(o)));
            cableDto.setRoutesList(routeDtoList);
        }
        return cableDto;
    }

    public static Cable transformCableDto(CableDto cableDto) {
        Cable cable = new Cable();
        cable.setKksName(cableDto.getKksName());
        cable.setJournalName(cableDto.getJournal());
        cable.setCableDimensions(cableDto.getCableDimensions());
        cable.setCableType(cableDto.getCableType());
        cable.setEnd(transformEquipmentDto(cableDto.getEnd()));
        cable.setStart(transformEquipmentDto(cableDto.getStart()));
        cable.setReserving(cableDto.getReserving());
        cable.setLength(cableDto.getLength());
        cable.setNumberInJournal(cableDto.getNumberInJournal());
        List<RouteDto> routeDtoList = cableDto.getRoutesList();
        if (routeDtoList != null && !routeDtoList.isEmpty()) {
            List<Route> routeList = new ArrayList<>();
            routeDtoList.forEach(o -> routeList.add(transformRouteDto(o)));
            cable.setRoutesList(routeList);
        }
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

    public static JournalDto transformJournal(Journal journal, List<CableDto> cableDtos) {
        JournalDto journalDto = new JournalDto();
        journalDto.setKksName(journal.getKksName());
        journalDto.setFile(journal.getFile());
        journalDto.setCables(cableDtos);
        return journalDto;
    }

    public static Journal transformJournalDto(JournalDto journalDto) {
        Journal journal = new Journal();
        journal.setKksName(journalDto.getKksName());
        journal.setFile(journalDto.getFile());
        return journal;
    }

    public static LineDto transformLine(Line line) {
        LineDto lineDto = new LineDto();
        lineDto.setId(line.getId());
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

    public static Line transformLineDto(LineDto lineDto) {
        Line line = new Line();
        line.setId(lineDto.getId());
        line.setEndPoint(transformJoinPointDto(lineDto.getEndPoint()));
        line.setStartPoint(transformJoinPointDto(lineDto.getStartPoint()));
        line.setTraced(lineDto.isTraced());
        List<RouteDto> routeDtoList = lineDto.getRoutesList();
        if (routeDtoList != null && !routeDtoList.isEmpty()) {
            List<Route> routeList = new ArrayList<>();
            routeDtoList.forEach(o -> routeList.add(transformRouteDto(o)));
            line.setRoutesList(routeList);
        }
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
        return routeDto;
    }

    public static Route transformRouteDto(RouteDto routeDto) {
        Route route = new Route();
        route.setKksName(routeDto.getKksName());
        route.setFirstEnd(transformJoinPointDto(routeDto.getFirstEnd()));
        route.setSecondEnd(transformJoinPointDto(routeDto.getSecondEnd()));
        route.setHeight(routeDto.getHeight());
        route.setLength(routeDto.getLength());
        route.setShelvesCount(routeDto.getShelvesCount());
        route.setRouteType(transformRouteTypeDto(routeDto.getRouteType()));
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
