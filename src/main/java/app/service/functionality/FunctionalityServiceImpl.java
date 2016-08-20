package app.service.functionality;

import app.dto.models.*;
import app.properties.PropertiesManager;
import app.repository.dao.business.IJournalDao;
import app.repository.entities.business.Journal;
import app.service.entities.IEquipmentService;
import app.service.entities.IJoinPointService;
import app.service.entities.IRouteService;
import app.service.functionality.excelworkers.DataFromExcelReader;
import app.service.functionality.excelworkers.ExcelUtils;
import app.service.functionality.excelworkers.JournalsToExcelWriter;
import app.service.functionality.strategies.IDataManagementStrategy;
import app.service.functionality.strategies.ITracingStrategy;
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
        logger.info("parseNewJournalFiles operates with files");
        List<JournalDto> journalDtoList = new ArrayList<>();
        xlsxfilesList.forEach(o -> journalDtoList.add(dataFromExcelReader.readJournal(o)));
        return journalDtoList;
    }

    /**
     * parsing equipments file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<EquipmentDto> parseNewEquipmentDataFile(File xlsxfile) {
        logger.info("parseNewEquipmentDataFile operates with file: {}", xlsxfile.getName());
        return dataFromExcelReader.readEquipments(xlsxfile);
    }

    /**
     * parsing join points file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<JoinPointDto> parseNewJoinPointDataFile(File xlsxfile) {
        logger.info("parseNewJoinPointDataFile operates with file: {}", xlsxfile.getName());
        return dataFromExcelReader.readJoinPoints(xlsxfile);
    }

    /**
     * parsing routes file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<RouteDto> parseNewRouteFile(File xlsxfile) {
        logger.info("parseNewRouteFile operates with file: {}", xlsxfile.getName());
        return dataFromExcelReader.readRoutes(xlsxfile);
    }


    /**
     * main program functionality - cable tracing (input - cables list)
     */
    @Override
    public List<CableDto> traceCablesAndDefineLengths(List<CableDto> cables) {
        logger.info("traceCablesAndDefineLengths operates with cables");

        //TODO сделать проверку валидности системы трассировки?

        for (CableDto cable : cables) {
            logger.info("Defining trace for cable: {}", cable.getKksName());
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
            logger.info("traceCablesAndDefineLengths operates with journal: {}", journalDto.getKksName());
            cablesList.addAll(traceCablesAndDefineLengths(journalDto.getCables()));
        }
        return cablesList;
    }


    /**
     * defines closest joinpoints in equipments
     */
    @Override
    public List<EquipmentDto> defineEquipmentsClosestPoints(List<EquipmentDto> equipments) {
        logger.info("defineEquipmentsClosestPoints operates with equipments");
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
            logger.info("Defined point {} for equipment {}", joinPointDefined.getKksName(), equipment.getFullName());
            equipment.setJoinPoint(joinPointDefined);
            equipment.setCableConnectionAddLength(equipment.getCableConnectionAddLength() + extraLength);
        }
        return targetEquipmentList;
    }

    /**
     * defines all new equipments in journals
     */
    @Override
    public List<EquipmentDto> defineNewEquipmentsInJournals(List<JournalDto> journals) {
        logger.info("defineNewEquipmentsInJournals operates with journals");
        List<EquipmentDto> addEquip = new ArrayList<>();
        for (JournalDto journal : journals) {
            List<CableDto> cables = journal.getCables();
            cables.forEach(cable -> {
                EquipmentDto eq1 = cable.getStart();
                EquipmentDto eq2 = cable.getEnd();
                if (eq1 != null && !addEquip.contains(eq1) && equipmentService.read(eq1.getFullName()) == null) {
                    addEquip.add(eq1);
                    logger.info("Defined new equipment {} in journal {}", eq1.getFullName(), journal.getKksName());
                }
                if (eq2 != null && !addEquip.contains(eq2) && equipmentService.read(eq2.getFullName()) == null) {
                    addEquip.add(eq2);
                    logger.info("Defined new equipment {} in journal {}", eq2.getFullName(), journal.getKksName());
                }
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
        logger.info("generateJournalInExcelFormatTraced generating journal list");

        //TODO сделать проверку валидности системы?
        //Признаки: все данные в модели актуальны и полны, выполнена проверка оборудования и тд

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
        logger.info("generateJournalInExcelFormatCalculated generating journal list");

        //TODO сделать проверку валидности системы?
        //Признаки: кабели протрассированы, выполнена проверка оборудования и тд

        List<File> files = new ArrayList<>();
        for (String journalName : journalNames) {
            Journal journal = journalDao.read(journalName);
            if (journal != null) files.add(journalsToExcelWriter.createEstimatedJournalFile(journal));
        }
        return files;
    }

}
