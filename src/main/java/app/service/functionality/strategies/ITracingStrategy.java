package app.service.functionality.strategies;

import app.dto.models.JoinPointDto;
import app.dto.models.LineDto;
import app.dto.models.RouteDto;

import java.util.List;

public interface ITracingStrategy {
    //Defines avaliable shortest list of routes between two end of cable
    List<RouteDto> defineTrace(LineDto cable, List<JoinPointDto> points, List<RouteDto> routes);
}
