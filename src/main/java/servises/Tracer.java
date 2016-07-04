package servises;

import model.db.IDao;
import model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import properties.PropertiesHolder;
import repository.IOExcelForTracer;
import servises.tracerlogic.TracingHelper;
import servises.tracerlogic.TracingLogicUtils;

import java.io.File;
import java.util.List;

@Service
@Scope(value = "singleton")
public final class Tracer {
    @Autowired
    private PropertiesHolder propertiesHolder;
    //excel writer
    @Autowired
    IOExcelForTracer ioExcelForTracer;
    @Autowired
    TracingHelper tracingHelper;
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
            for (Journal journal : traceJournals(journalDao.getAll())) {
                ioExcelForTracer.writeToFileTracedJournal(projectName, journal, targetPath);
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
                List<Route> routes = TracingLogicUtils.defineTrace(cable, joinPointDao.getAll(), routeDao.getAll());
                if (!routes.isEmpty()) {
                    cable.setTraced(true);
                    cable.setRoutesList(routes);
                    // define length after tracing cable
                    tracingHelper.defineCableLength(cable);
                    for (Route rou : routes) {
                        rou.getCablesList().add(cable);
                    }
                }
            }
        }
        return journalList;
    }

}
