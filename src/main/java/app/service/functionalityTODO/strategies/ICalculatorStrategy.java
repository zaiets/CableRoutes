package app.service.functionalityTODO.strategies;

import app.repository.entities.business.Cable;

import java.util.List;

/**
 * Created by Levsha on 18.08.2016.
 */
public interface ICalculatorStrategy {
    //Value, predefined due to standards
    int FIRST_HEIGHT_MARKER = 2;

    List<Long> getInfo(Cable cable);
}
