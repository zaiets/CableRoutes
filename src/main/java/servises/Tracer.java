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
import java.io.IOException;
import java.util.List;

@Service
@Scope(value = "singleton")
public final class Tracer {
    private String projectName;
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


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean testModelIsReadyForTracing() {
        return !journalDao.getAll().isEmpty() ||
                !equipmentDao.getAll().isEmpty() ||
                !routeDao.getAll().isEmpty() ||
                !joinPointDao.getAll().isEmpty();
    }

    /**
     * Main action of program Writes journals into files then job is done
     * @throws IOException
     */
    public void traceJournals(File targetPath) throws IOException {
        for (Journal journal : traceJournals(journalDao.getAll())) {
            ioExcelForTracer.writeToFileTracedJournal(getProjectName(), journal, targetPath);
        }
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
