package app.service.functionalityTODO.service;

import app.service.functionalityTODO.excel.ExcelDataReader;
import app.service.functionalityTODO.properties.PropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import app.repository.dao.business.IDao;
import app.repository.entities.business.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static app.service.functionalityTODO.excel.utils.ExcelUtils.buildFileName;

@Repository
public final class Manager {
    @Autowired
    private ExcelDataReader excelDataReader;
    @Autowired
    private PropertiesManager propertiesManager;
    @Autowired
    private IDao<JoinPoint> joinPointDao;
    @Autowired
    private IDao<Equipment> equipmentDao;
    @Autowired
    private IDao<Cable> cableDao;
    @Autowired
    private IDao<Journal> journalDao;
    @Autowired
    private IDao<Route> routeDao;


    public boolean initJoinPoints(String projectName, File joinPointsFile) {
        if (joinPointsFile == null || !joinPointsFile.canRead()) {
            String path = propertiesManager.get("default.inputPathName");
            String fileExtension = propertiesManager.get("default.excel.type");
            String joinPointsFileName = propertiesManager.get("default.joinPointsFileName");
            joinPointsFile = new File(buildFileName(path, projectName, joinPointsFileName, null, fileExtension));
        }
        try {
            List<JoinPoint> joinPoints = excelDataReader.readJoinPoints(joinPointsFile);
            if (joinPoints != null && !joinPoints.isEmpty()) {
                joinPoints.forEach(o -> joinPointDao.createOrUpdate(o));
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean initEquipments(String projectName, File equipmentsFile) {
        if (equipmentsFile == null || !equipmentsFile.canRead()) {
            String path = propertiesManager.get("default.inputPathName");
            String fileExtension = propertiesManager.get("default.excel.type");
            String equipmentsFileName = propertiesManager.get("default.equipmentsFileName");
            equipmentsFile = new File(buildFileName(path, projectName, equipmentsFileName, null, fileExtension));
        }
        try {
            List<Equipment> equipments = excelDataReader.readEquipments(equipmentsFile);
            if (equipments != null && !equipments.isEmpty()) {
                equipments.forEach(o -> equipmentDao.createOrUpdate(o));
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean initRoutes(String projectName, File routesFile) {
        if (routesFile == null || !routesFile.canRead()) {
            String path = propertiesManager.get("default.inputPathName");
            String fileExtension = propertiesManager.get("default.excel.type");
            String routesFileName = propertiesManager.get("default.routesFileName");
            routesFile = new File(buildFileName(path, projectName, routesFileName, null, fileExtension));
        }
        try {
            List<Route> routes = excelDataReader.readRoutes(routesFile);
            if (routes != null && !routes.isEmpty()) {
                routes.forEach(o -> routeDao.createOrUpdate(o));
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean initJournals(String projectName, List<File> journals) {
        File journalsPath;
        if (journals == null || journals.isEmpty()) {
            String journalsPathName = propertiesManager.get("input.journalsPath").concat(projectName).concat("/");
            journalsPath = new File(journalsPathName);
            File[] journalMassive = journalsPath.listFiles();
            if (journalMassive != null) {
                journals = Arrays.asList(journalMassive);
            } else return false;
        }
        for (File current : journals) {
            try {
                Journal journal = excelDataReader.readJournal(current);
                if (journal != null) {
                    List<Cable> cables = journal.getCables();
                    if (cables != null && !cables.isEmpty()) {
                        cables.forEach(o -> cableDao.createOrUpdate(o));
                    }
                    journalDao.createOrUpdate(journal);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
}
