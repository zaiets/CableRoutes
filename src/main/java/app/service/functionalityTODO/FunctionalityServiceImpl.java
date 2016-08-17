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
    @Override
    public List<CableDto> parseNewJournalFile(List<File> xlsxfilesList) {
        return null;
    }

    /*
     * parsing equipments file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<EquipmentDto> parseNewEquipmentDataFile(File xlsxfile){
        return null;
    }

    /*
     * parsing join points file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<JoinPointDto> parseNewJoinPointDataFile(File xlsxfile){
        return null;
    }

    /*
     * parsing routes file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<RouteDto> parseNewRouteFile(File xlsxfile){
        return null;
    }

    //main program functionality
    @Override
    public List<CableDto> traceCables(List<CableDto> cables){
        return null;
    }
    @Override
    public List<CableDto> traceJournals(List<JournalDto> journals){
        return null;
    }
    @Override
    public List<CableDto> calculateCables(List<CableDto> cables){
        return null;
    }
    @Override
    public List<CableDto> calculateJournals(List<JournalDto> journals){
        return null;
    }

    //TODO maybe this must be private?
    //defines closest joinpoints in equipments
    @Override
    public List<EquipmentDto> analyseEquipments(List<EquipmentDto> equipments){
        return null;
    }
    //defines all new equipments in journals
    @Override
    public List<JournalDto> analyseJournals(List<JournalDto> journals){
        return null;
    }

}
