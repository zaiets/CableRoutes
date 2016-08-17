package app.service.functionalityTODO;

import app.dto.models.*;
import app.service.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Implements all main app functions like:
 * tracing of cables, calculating the cables lengths for estimate calculations ect.
 */
@Service
public class FunctionalityServiceImpl implements IFunctionalityService {
    @Autowired
    private ICableService cableService;
    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    private IJoinPointService joinPointService;
    @Autowired
    private IRouteService routeService;
    @Autowired
    private IJournalService journalService;

    /*
     * parsing journals files (.xlsx format, corresponding to default template)
     */
    public List<CableDto> parseNewJournalFile(List<File> xlsxfilesList) {
        return null;
    }

    /*
     * parsing equipments file (.xlsx format, corresponding to default template)
     */
    public List<EquipmentDto> parseNewEquipmentDataFile(File xlsxfile){
        return null;
    }

    /*
     * parsing join points file (.xlsx format, corresponding to default template)
     */
    public List<JoinPointDto> parseNewJoinPointDataFile(File xlsxfile){
        return null;
    }

    /*
     * parsing routes file (.xlsx format, corresponding to default template)
     */
    public List<RouteDto> parseNewRouteFile(File xlsxfile){
        return null;
    }

    //main program functionality
    List<CableDto> traceCables(List<CableDto> cables);
    List<CableDto> traceJournals(List<JournalDto> journals);
    List<CableDto> calculateCables(List<CableDto> cables);
    List<CableDto> calculateJournals(List<JournalDto> journals);

    //TODO maybe this must be private?
    //defines closest joinpoints in equipments
    List<EquipmentDto> analyseEquipments(List<EquipmentDto> equipments);
    //defines all new equipments in journals
    List<JournalDto> analyseJournals(List<JournalDto> journals);

    //TODO else?

}
