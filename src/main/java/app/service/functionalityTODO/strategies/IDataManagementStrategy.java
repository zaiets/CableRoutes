package app.service.functionalityTODO.strategies;

import app.dto.models.CableDto;
import app.repository.entities.business.Line;

/**
 * Created by Levsha on 18.08.2016.
 */
public interface IDataManagementStrategy {
    String getRoutesListForExcel(Line line);

    CableDto defineAndSetCableLength(CableDto cable, boolean useDirect);

    Object[] defineNearestPointData(double a, double b, double c, double reserveRatio);
}
