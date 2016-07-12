package app.business.excel;

import app.repository.dao.business.IDao;
import app.repository.entities.business.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static app.business.excel.utils.ExcelUtils.*;

@Component

public class ExcelDBService {
    @Autowired
    private IDao<Route> routeDao;
    @Autowired
    private IDao<JoinPoint> joinPointDao;
    @Autowired
    private IDao<Equipment> equipmentDao;

    public ExcelDBService() {
    }

    public List<JoinPoint> readJoinPoints(File joinPointsFile) {
        List<JoinPoint> joinPoints = new ArrayList<>();
        try {
            Iterator<Row> it = getWorkbookSheetIterator(joinPointsFile);
            int stringNumber = 1;
            while (it.hasNext()) {
                Row row = it.next();
                stringNumber++;
                if (stringNumber > 3) {
                    Iterator<Cell> cells = row.iterator();
                    String name = cells.next().getStringCellValue();
                    double[] xyz = new double[3];
                    xyz[0] = getDoubleCellValue(cells.next());
                    xyz[1] = getDoubleCellValue(cells.next());
                    xyz[2] = getDoubleCellValue(cells.next());
                    JoinPoint current = new JoinPoint(name, xyz);
                    //TODO logging
                    //System.out.println(current);
                    joinPoints.add(current);
                }
            }
            return joinPoints;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Equipment> readEquipments(File equipmentsFile) {
        List<Equipment> equipments = new ArrayList<>();
        try {
            Iterator<Row> it = getWorkbookSheetIterator(equipmentsFile);
            int stringNumber = 1;
            while (it.hasNext()) {
                Row row = it.next();
                stringNumber++;
                if (stringNumber > 3 && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
                    Iterator<Cell> cells = row.iterator();
                    String equipmentKKS = getStringCellValue(cells.next());
                    String equipmentName = getStringCellValue(cells.next());
                    if (equipmentKKS.equals("")) {
                        equipmentKKS = extractKKS(equipmentName);
                    }
                    Double[] xyz = new Double[3];
                    for (int i = 0; i < 3; i++) {
                        xyz[i] = getDoubleCellValue(cells.next());
                    }
                    Cell cellF = cells.next();
                    JoinPoint closestTracePoint = null;
                    if (cellF != null) {
                        String closestPointName = getStringCellValue(cellF);
                        closestTracePoint = joinPointDao.read(closestPointName);
                    }
                    Cell cellG = cells.next();
                    int extraCableLength = 0;
                    if (cellF != null) {
                        try {
                            extraCableLength = getIntCellValue(cellG);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    Equipment current = new Equipment(equipmentKKS, equipmentName, extraCableLength, xyz, closestTracePoint);

                    //TODO logging
                    //System.out.println(current);

                    equipments.add(current);
                }
            }
            return equipments;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Route> readRoutes(File routesFile) {
        List<Route> routes = new ArrayList<>();
        try {
            Iterator<Row> it = getWorkbookSheetIterator(routesFile);
            int stringNumber = 1;
            while (it.hasNext()) {
                Row row = it.next();
                stringNumber++;
                if (stringNumber > 3) {
                    Iterator<Cell> cells = row.iterator();
                    String routeKks = getStringCellValue(cells.next());
                    String routeType = getStringCellValue(cells.next());
                    double length = getDoubleCellValue(cells.next());
                    JoinPoint point1 = joinPointDao.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    JoinPoint point2 = joinPointDao.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    int shelvesCount = getDoubleCellValue(cells.next()).intValue();
                    Double height = getDoubleCellValue(cells.next());
                    Route current = new Route(routeKks, routeType, length, height, shelvesCount, point1, point2);
                    //TODO logging
                    //System.out.println(current);
                    routes.add(current);
                }
            }
            return routes;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Journal readJournal(File journalFile) {
        List<Cable> cables = new ArrayList<>();
        try {
            Iterator<Row> it = getWorkbookSheetIterator(journalFile);
            int stringNumber = 1;
            while (it.hasNext()) {
                Row row = it.next();
                stringNumber++;
                if (stringNumber > 6 && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
                    Iterator<Cell> cells = row.iterator();
                    int numberInJournal = getDoubleCellValue(cells.next()).intValue();
                    String cableKKSCode = getStringCellValue(cells.next());
                    String reserving = getStringCellValue(cells.next());
                    String[] cableType = {getStringCellValue(cells.next()), getStringCellValue(cells.next())};
                    Equipment startEquip = equipmentDao.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    Equipment endEquip = equipmentDao.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    int previousLength = getIntCellValue(cells.next());
                    List<Route> previouslyDefinedRoutes = parceRoutes(getStringCellValue(cells.next()));
                    Cable cable = new Cable(cableKKSCode, journalFile.getName(), numberInJournal, cableType,
                            reserving, startEquip, endEquip, previouslyDefinedRoutes, previousLength);
                    cables.add(cable);
                }
            }
            return new Journal(journalFile.getAbsolutePath(), journalFile.getName(), cables);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private List<Route> parceRoutes(String routesString) {
        if (routesString.isEmpty() || routesString.equals("")) return null;
        List<Route> actualRoutes = new ArrayList<>();
        String[] fragments = routesString.split(";");
        Stream.of(fragments).forEachOrdered(o -> {
            Route r = routeDao.read(o.trim());
            if (r != null) actualRoutes.add(r);
        });
        return actualRoutes;
    }
}
