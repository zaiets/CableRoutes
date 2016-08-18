package app.service.functionalityTODO;

import app.dto.models.*;
import app.repository.dao.business.IJournalDao;
import app.repository.entities.business.Journal;
import app.service.entities.*;
import app.service.functionalityTODO.excelworkers.DataFromExcelReader;
import app.service.functionalityTODO.excelworkers.IOExcelForJournals;
import app.service.functionalityTODO.properties.PropertiesManager;
import app.service.functionalityTODO.strategies.IDataManagementStrategy;
import app.service.functionalityTODO.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
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
    private IJournalDao journalDao;

    @Autowired
    private PropertiesManager propertiesManager;
    @Autowired
    private IDataManagementStrategy dataManagementStrategy;
    @Autowired
    private DataFromExcelReader dataFromExcelReader;
    @Autowired
    private IOExcelForJournals ioExcelForJournals;

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
    public List<EquipmentDto> parseNewEquipmentDataFile(File xlsxfile){
        return dataFromExcelReader.readEquipments(xlsxfile);
    }

    /**
     * parsing join points file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<JoinPointDto> parseNewJoinPointDataFile(File xlsxfile){
        return dataFromExcelReader.readJoinPoints(xlsxfile);
    }

    /**
     * parsing routes file (.xlsx format, corresponding to default template)
     */
    @Override
    public List<RouteDto> parseNewRouteFile(File xlsxfile){
        return dataFromExcelReader.readRoutes(xlsxfile);
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



    //defines closest joinpoints in equipments
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

    //defines all new equipments in journals
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

    //generates journal file .xlsx with previously traced cables
    @Override
    public List<File> generateJournalInExcelFormatTraced(List<String> journalNames) {
        List<File> files = new ArrayList<>();
        for (String journalName : journalNames) {
            Journal journal = journalDao.read(journalName);
            if (journal != null) files.add(ioExcelForJournals.createTracedJournalFile(journal));
        }
        return files;
    }

    //generates journal file .xlsx with previously calculated and traced cables
    @Override
    public List<File> generateJournalInExcelFormatCalculated(List<String> journalNames) {
        List<File> files = new ArrayList<>();
        for (String journalName : journalNames) {
            Journal journal = journalDao.read(journalName);
            if (journal != null) files.add(ioExcelForJournals.createEstimatedJournalFile(journal));
        }
        return files;
    }

}
