package app.service.functionalityTODO;

import app.dto.models.*;

import java.io.File;
import java.util.List;

/**
 * Lists all main app functions like:
 * tracing of cables, calculating the cables lengths for estimate calculations ect.
 */
public interface IFunctionalityService {
    //for initial data parsing (excel files)
    List<CableDto> parseNewJournalFile (List<File> filesList);
    List<EquipmentDto> parseNewEquipmentDataFile (File file);
    List<JoinPointDto> parseNewJoinPointDataFile (File file);
    List<RouteDto> parseNewRouteFile (File file);

    //main program functionality
    List<CableDto> traceCables (List<CableDto> cables);
    List<CableDto> traceJournals (List<JournalDto> journals);
    List<CableDto> calculateCables (List<CableDto> cables);
    List<CableDto> calculateJournals (List<JournalDto> journals);

    //TODO maybe this must be private?
    //defines closest joinpoints in equipments
    List<EquipmentDto> analyseEquipments (List<EquipmentDto> equipments);
    //defines all new equipments in journals
    List<JournalDto> analyseJournals (List<JournalDto> journals);

    //TODO else?

}
