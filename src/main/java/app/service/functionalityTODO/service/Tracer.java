package app.service.functionalityTODO.service;

import app.service.functionalityTODO.excel.IOExcelForTracer;
import app.service.functionalityTODO.properties.PropertiesManager;
import app.service.functionalityTODO.strategies.TracingSimpleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.dao.business.IDao;
import app.repository.entities.business.*;

import java.io.File;
import java.util.List;

import static app.service.functionalityTODO.excel.utils.ExcelUtils.buildFileName;

@Service
public final class Tracer {
    @Autowired
    private PropertiesManager propertiesManager;
    //models.excel writer
    @Autowired
    IOExcelForTracer ioExcelForTracer;
    @Autowired
    CommonService commonService;
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
        String templateFileName = propertiesManager.get("tracer.journalTemplateFile");
        try {
            String newMessage = propertiesManager.get("output.suffix.tracedJournals");
            String fileExtension = propertiesManager.get("default.excel.type");
            String journalPathName = propertiesManager.get("output.path");
            File templateFile = new File(templateFileName);
            File targetFile;
            for (Journal journal : traceJournals(journalDao.getAll())) {
                String targetFileName;
                if (targetPath == null || !targetPath.isDirectory()) {
                    targetFileName = buildFileName(journalPathName, null, journal.getCommonKks(), newMessage, fileExtension);
                } else {
                    targetFileName = buildFileName(targetPath.getAbsolutePath(), null, journal.getCommonKks(), newMessage, fileExtension);
                }
                targetFile = new File(targetFileName);
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
                List<Route> routes = TracingSimpleStrategy.defineTrace(cable, joinPointDao.getAll(), routeDao.getAll());
                if (!routes.isEmpty()) {
                    cable.setTraced(true);
                    cable.setRoutesList(routes);
                    for (Route rou : routes) {
                        rou.getCablesList().add(cable);
                    }
                }
                // define length after tracing cable
                commonService.defineAndSetCableLength(cable);


            }
        }
        return journalList;
    }

}
