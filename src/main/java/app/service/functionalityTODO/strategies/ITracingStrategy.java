package app.service.functionalityTODO.strategies;

import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;
import app.repository.entities.business.Route;

import java.util.List;

/**
 * Created by Levsha on 18.08.2016.
 */
public interface ITracingStrategy {
    //Defines avaliable shortest list of routes between two end of cable
    List<Route> defineTrace(Line cable, List<JoinPoint> points, List<Route> routes);
}
