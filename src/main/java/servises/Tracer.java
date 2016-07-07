package servises;

import model.db.IDao;
import model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import properties.PropertiesHolder;
import excel.IOExcelForTracer;
import servises.utils.CommonUtil;
import servises.utils.TracingLogic;

import java.io.File;
import java.util.List;

import static excel.utils.ExcelUtils.buildFileName;

@Service
@Scope(value = "singleton")
public final class Tracer {
    @Autowired
    private PropertiesHolder propertiesHolder;
    //excel writer
    @Autowired
    IOExcelForTracer ioExcelForTracer;
    @Autowired
    CommonUtil commonUtil;
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


    public boolean testModelIsReadyForTracing() {
        return !journalDao.getAll().isEmpty() ||
                !equipmentDao.getAll().isEmpty() ||
                !routeDao.getAll().isEmpty() ||
                !joinPointDao.getAll().isEmpty();
    }

    public boolean traceJournals(String projectName, File targetPath) {
        try {
            String newMessage = propertiesHolder.get("output.suffix.tracedJournals");
            String fileExtension = propertiesHolder.get("default.excelFileType");
            String journalPathName = propertiesHolder.get("output.path");
            File templateFile = new File (propertiesHolder.get("default.tracer.journalTemplateFile"));
            for (Journal journal : traceJournals(journalDao.getAll())) {
                String targetFileName;
                if (targetPath == null || !targetPath.isDirectory()) {
                    targetFileName = buildFileName(journalPathName, null, journal.getKksName(), newMessage, fileExtension);
                } else {
                    targetFileName = buildFileName(targetPath.getAbsolutePath(), null, journal.getKksName(), newMessage, fileExtension);
                }
                File targetFile = new File(targetFileName);
                ioExcelForTracer.writeToFileTracedJournal(projectName, journal, targetFile, templateFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    private List<Journal> traceJournals(List<Journal> journalList) {
        for (Journal journal : journalList) {
            for (Cable cable : journal.getCables()) {
                List<Route> routes = TracingLogic.defineTrace(cable, joinPointDao.getAll(), routeDao.getAll());
                if (!routes.isEmpty()) {
                    cable.setTraced(true);
                    cable.setRoutesList(routes);
                    // define length after tracing cable
                    commonUtil.defineCableLength(cable);
                    for (Route rou : routes) {
                        rou.getCablesList().add(cable);
                    }
                }
            }
        }
        return journalList;
    }

}
