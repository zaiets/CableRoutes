package servises;

import model.db.IDao;
import model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import properties.PropertiesHolder;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static model.db.ExcelDBServices.*;
import static repository.excelutils.ExcelUtils.buildFileName;

@Service
@Scope(value = "singleton")
public final class DBManager {
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


    public boolean init(String projectName, String equipmentsFileName, String joinPointsFileName, String routesFileName, String journalsPathName) {
        if (equipmentsFileName  == null) equipmentsFileName = propertiesHolder.get("default.equipmentsFileName");
        if (joinPointsFileName == null) joinPointsFileName = propertiesHolder.get("default.joinPointsFileName");
        if (routesFileName == null) routesFileName = propertiesHolder.get("default.routesFileName");
        if (journalsPathName == null) journalsPathName = propertiesHolder.get("input.journalsPath");
        String path = propertiesHolder.get("default.inputPathName");
        String fileExtension = propertiesHolder.get("default.excelFileType");
        File joinPointsFile = new File(buildFileName(path, projectName, joinPointsFileName, null, fileExtension));
        File equipmentsFile = new File(buildFileName(path, projectName, equipmentsFileName, null, fileExtension));
        File routesFile = new File(buildFileName(path, projectName, routesFileName, null, fileExtension));
        File[] journals;
        File jou = new File(journalsPathName);
//TODO test
System.out.println(jou.getAbsolutePath());
            journals = jou.isDirectory()? jou.listFiles() : new File[] {jou};
//TODO test
System.out.println(joinPointsFile + "\n" + equipmentsFile + "\n" + routesFile + "\n" + journals);
        if (journals == null) return false;
        List<File> journalsFiles = Arrays.asList(journals);
        return initDatabase(joinPointsFile, equipmentsFile, routesFile, journalsFiles);
    }


    public boolean initDatabase(File joinPointsFile, File equipmentsFile, File routesFile, List<File> journalsFiles) {
        try {
            try {
                List<JoinPoint> joinPoints = readJoinPoints(joinPointsFile);
                if (joinPoints != null && !joinPoints.isEmpty()) {
                    joinPoints.forEach(o -> joinPointDao.createOrUpdate(o));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception("Cant init joinpoints db");
            }
            try {
                List<Equipment> equipments = readEquipments(equipmentsFile, joinPointDao);
                if (equipments != null && !equipments.isEmpty()) {
                    equipments.forEach(o -> equipmentDao.createOrUpdate(o));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception("Cant init equipments db");
            }

            try {
                List<Route> routes = readRoutes(routesFile, joinPointDao);
                if (routes != null && !routes.isEmpty()) {
                    routes.forEach(o -> routeDao.createOrUpdate(o));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception("Cant init routes db");
            }


            for (File file : journalsFiles) {
                try {
                    Journal journal = readJournal(file, equipmentDao, routeDao);
                    if (journal != null) {
                        List<Cable> cables = journal.getCables();
                        if (cables != null && !cables.isEmpty()) {
                            cables.forEach(o -> cableDao.createOrUpdate(o));
                        }
                        journalDao.createOrUpdate(journal);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Something wrong with reading journal" + file.getName());
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
