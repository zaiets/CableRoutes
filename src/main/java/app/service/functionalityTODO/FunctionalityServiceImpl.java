package app.service.functionalityTODO;

import app.dto.models.*;
import app.properties.PropertiesManager;
import app.repository.dao.business.IJournalDao;
import app.repository.entities.business.Journal;
import app.service.entities.ICableService;
import app.service.entities.IEquipmentService;
import app.service.entities.IJoinPointService;
import app.service.entities.IRouteService;
import app.service.functionalityTODO.excelworkers.DataFromExcelReader;
import app.service.functionalityTODO.excelworkers.ExcelUtils;
import app.service.functionalityTODO.excelworkers.JournalsToExcelWriter;
import app.service.functionalityTODO.strategies.IDataManagementStrategy;
import app.service.functionalityTODO.strategies.ITracingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements all main app functions like:
 * tracing of cables, calculating the cables lengths for estimate calculations, generates excel files ect.
 */
@Service
public final class FunctionalityServiceImpl implements IFunctionalityService {
    @Autowired
    static final Logger logger = LoggerFactory.getLogger(FunctionalityServiceImpl.class);

    @Autowired
    private PropertiesManager propertiesManager;

    //entities
    @Autowired
    private ICableService cableService;
    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    private IJoinPointService joinPointService;
    @Autowired
    private IRouteService routeService;
    @Autowired
    private IJournalDao journalDao;

    //strategies
    @Autowired
    private ITracingStrategy tracingStrategy;
    @Autowired
    private IDataManagementStrategy dataManagementStrategy;
    //excel
    @Autowired
    private DataFromExcelReader dataFromExcelReader;
    @Autowired
    private JournalsToExcelWriter journalsToExcelWriter;

    /**
     * parsing journals files (.xlsx format, corresponding to default template)
     */
    @Override
    public List<JournalDto> parseNewJournalFiles(List<File> xlsxfilesList) {
        List<JournalDto> journalDtoList = new ArrayList<>();
        xlsxfilesList.forEach(o -> journalDtoList.add(dataFromExcelReader.readJournal(o)));
        return journalDtoList;
    }

    /**
     * parsing equipments file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<EquipmentDto> parseNewEquipmentDataFile(File xlsxfile) {
        return dataFromExcelReader.readEquipments(xlsxfile);
    }

    /**
     * parsing join points file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<JoinPointDto> parseNewJoinPointDataFile(File xlsxfile) {
        return dataFromExcelReader.readJoinPoints(xlsxfile);
    }

    /**
     * parsing routes file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<RouteDto> parseNewRouteFile(File xlsxfile) {
        return dataFromExcelReader.readRoutes(xlsxfile);
    }


    /**
     * main program functionality - cable tracing (input - cables list)
     */
    @Override
    public List<CableDto> traceCablesAndDefineLengths(List<CableDto> cables) {

        //TODO сделать проверку валидности системы трассировки?

        for (CableDto cable : cables) {
            List<RouteDto> routes = tracingStrategy.defineTrace(cable, joinPointService.getAll(), routeService.getAll());
            if (!routes.isEmpty()) {
                cable.setTraced(true);
                cable.setRoutesList(routes);
            }
            // define length after tracing cable
            boolean useDirect = propertiesManager.get("tracer.use.directLengthDefinitionForNotTracedCables", Boolean.class);
            dataManagementStrategy.defineAndSetCableLength(cable, useDirect);
        }
        return cables;
    }
    /**
     * main program functionality - cable tracing (input - journals list)
     */
    @Override
    public List<CableDto> traceJournalsAndDefineLengths(List<JournalDto> journals) {
        List<CableDto> cablesList = new ArrayList<>();
        for (JournalDto journalDto : journals) {
            cablesList.addAll(traceCablesAndDefineLengths(journalDto.getCables()));
        }
        return cablesList;
    }


    /**
     * defines closest joinpoints in equipments
     */
    @Override
    public List<EquipmentDto> defineEquipmentsClosestPoints(List<EquipmentDto> equipments) {
        List<EquipmentDto> targetEquipmentList = new ArrayList<>();
        //first of all we need to test if where joinpoint exists already
        equipments.forEach(o -> {
            if (o.getJoinPoint() == null || o.getJoinPoint().getKksName().equals("")) targetEquipmentList.add(o);
        });
        for (EquipmentDto equipment : targetEquipmentList) {
            double reserveRatio = propertiesManager.get("reserveRatio.approximateDeterminationOfTrace", Double.class);
            Object[] result = dataManagementStrategy.defineNearestPointData(equipment.getX(), equipment.getY(),
                    equipment.getZ(), reserveRatio);
            JoinPointDto joinPointDefined = ((JoinPointDto) result[0]);
            int extraLength = (int) result[1];
            equipment.setJoinPoint(joinPointDefined);
            equipment.setCableConnectionAddLength(equipment.getCableConnectionAddLength() + extraLength);
        }
        return targetEquipmentList;
    }

    /**
     * defines all new equipments in journals
     */
    @Override
    public List<EquipmentDto> findNewEquipmentsInJournals(List<JournalDto> journals) {
        List<EquipmentDto> addEquip = new ArrayList<>();
        for (JournalDto journal : journals) {
            List<CableDto> cables = journal.getCables();
            cables.forEach(cable -> {
                EquipmentDto eq1 = cable.getStart();
                EquipmentDto eq2 = cable.getEnd();
                if (eq1 != null && equipmentService.read(eq1.getFullName()) == null) addEquip.add(eq1);
                if (eq2 != null && equipmentService.read(eq2.getFullName()) == null) addEquip.add(eq2);
            });
        }
        if (!addEquip.isEmpty()) addEquip.forEach(eq -> eq.setCommonKks(ExcelUtils.extractKKS(eq.getFullName())));
        return addEquip;
    }

    /**
     * generates journal file .xlsx with previously traced cables
     */
    @Override
    public List<File> generateJournalInExcelFormatTraced(List<String> journalNames) {
        List<File> files = new ArrayList<>();
        for (String journalName : journalNames) {
            Journal journal = journalDao.read(journalName);
            if (journal != null) files.add(journalsToExcelWriter.createTracedJournalFile(journal));
        }
        return files;
    }

    /**
     * generates calculated journal file .xlsx with previously traced cables
     */
    @Override
    public List<File> generateJournalInExcelFormatCalculated(List<String> journalNames) {

        //TODO сделать проверку валидности системы калькуляции?
        //Признаки: кабели протрассированы, выполнена проверка оборудования и тд

        List<File> files = new ArrayList<>();
        for (String journalName : journalNames) {
            Journal journal = journalDao.read(journalName);
            if (journal != null) files.add(journalsToExcelWriter.createEstimatedJournalFile(journal));
        }
        return files;
    }

}
