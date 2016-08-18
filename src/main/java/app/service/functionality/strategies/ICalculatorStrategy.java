package app.service.functionality.strategies;

import app.repository.entities.business.Cable;

import java.util.List;

public interface ICalculatorStrategy {
    //Value, predefined due to standards
    int FIRST_HEIGHT_MARKER = 2;

    List<Long> getInfo(Cable cable);
}
