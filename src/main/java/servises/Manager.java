package servises;

import model.db.ExcelDBService;
import model.db.IDao;
import model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import properties.PropertiesHolder;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static excel.utils.ExcelUtils.buildFileName;

@Repository
@Scope(value = "singleton")
public final class Manager {
    @Autowired
    private ExcelDBService excelDBService;
    @Autowired
    private PropertiesHolder propertiesHolder;
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
            String path = propertiesHolder.get("default.inputPathName");
            String fileExtension = propertiesHolder.get("default.excelFileType");
            String joinPointsFileName = propertiesHolder.get("default.joinPointsFileName");
            joinPointsFile = new File(buildFileName(path, projectName, joinPointsFileName, null, fileExtension));
        }
        try {
            List<JoinPoint> joinPoints = excelDBService.readJoinPoints(joinPointsFile);
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
            String path = propertiesHolder.get("default.inputPathName");
            String fileExtension = propertiesHolder.get("default.excelFileType");
            String equipmentsFileName = propertiesHolder.get("default.equipmentsFileName");
            equipmentsFile = new File(buildFileName(path, projectName, equipmentsFileName, null, fileExtension));
        }
        try {
            List<Equipment> equipments = excelDBService.readEquipments(equipmentsFile);
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
            String path = propertiesHolder.get("default.inputPathName");
            String fileExtension = propertiesHolder.get("default.excelFileType");
            String routesFileName = propertiesHolder.get("default.routesFileName");
            routesFile = new File(buildFileName(path, projectName, routesFileName, null, fileExtension));
        }
        try {
            List<Route> routes = excelDBService.readRoutes(routesFile);
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
            String journalsPathName = propertiesHolder.get("input.journalsPath").concat(projectName).concat("/");
            journalsPath = new File(journalsPathName);
            File[] journalMassive = journalsPath.listFiles();
            if (journalMassive != null) {
                journals = Arrays.asList(journalMassive);
            } else return false;
        }
        for (File current : journals) {
            try {
                Journal journal = excelDBService.readJournal(current);
                if (journal != null) {
                    List<Cable> cables = journal.getCables();
                    if (cables != null && !cables.isEmpty()) {
                        cables.forEach(o -> cableDao.createOrUpdate(o));
                    }
                    journalDao.createOrUpdate(journal);
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
