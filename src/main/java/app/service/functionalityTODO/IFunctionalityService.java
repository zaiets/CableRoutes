package app.service.functionalityTODO;

import app.dto.models.*;

import java.io.File;
import java.util.List;

/**
 * Provides all main app functions like:
 * tracing of cables, calculating the cables lengths for estimate calculations ect.
 */
public interface IFunctionalityService {
    //for initial data parsing (excel files)
    List<CableDto> parseNewJournalFile (File file);
    List<EquipmentDto> parseNewEquipmentDataFile (File file);
    List<JoinPointDto> parseNewJoinPointDataFile (File file);
    List<RouteDto> parseNewRouteFile (File file);
    List<RouteTypeDto> parseNewRouteTypeFile (File file);

    //main program functionality
    boolean traceCables (List<CableDto> cables);
    boolean traceJournals (List<JournalDto> journals);
    boolean calculateCables (List<CableDto> cables);
    boolean calculateJournals (List<JournalDto> journals);

    //TODO maybe this must be private?
    //defines closest joinpoints in equipments
    boolean analyseEquipments (List<EquipmentDto> equipments);
    //defines all new equipments in journals
    boolean analyseJournals (List<JournalDto> journals);

    //TODO else?

}
