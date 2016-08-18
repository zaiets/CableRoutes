package app.service.functionalityTODO.strategies;

import app.repository.entities.business.Cable;
import app.repository.entities.business.Line;

/**
 * Created by Levsha on 18.08.2016.
 */
public interface IDataManagementStrategy {
    String getRoutesListForExcel(Line line);

    Cable defineAndSetCableLength(Cable cable, boolean useDirect);

    Object[] defineNearestPointData(double a, double b, double c, double reserveRatio);
}
