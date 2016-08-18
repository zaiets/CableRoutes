package app.service.functionality.strategies;

import app.dto.models.CableDto;
import app.repository.entities.business.Line;

public interface IDataManagementStrategy {
    String getRoutesListForExcel(Line line);

    CableDto defineAndSetCableLength(CableDto cable, boolean useDirect);

    Object[] defineNearestPointData(double a, double b, double c, double reserveRatio);
}
