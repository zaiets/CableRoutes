package model.db;

import model.entities.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static repository.excelutils.ExcelUtils.*;

@Component
public final class ExcelDBServices {

    public static List<JoinPoint> readJoinPoints(File joinPointsFile) {
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
                    joinPoints.add(new JoinPoint(name, xyz));
                }
            }
            return joinPoints;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<Equipment> readEquipments(File equipmentsFile, IDao<JoinPoint> joinPointDao) {
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
                    double[] xyz = new double[3];
                    for (int i = 0; i < 3; i++) {
                        xyz[i] = Double.valueOf(getStringCellValue(cells.next()).replace(',', '.'));
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
                            extraCableLength = Integer.parseInt(getStringCellValue(cellG));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    equipments.add(new Equipment(equipmentKKS, equipmentName, extraCableLength, xyz, closestTracePoint));
                }
            }
            return equipments;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<Route> readRoutes(File routesFile, IDao<JoinPoint> joinPointDao) {
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
                    routes.add(new Route(routeKks, routeType, length, height, shelvesCount, point1, point2));
                }
            }
            return routes;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Journal readJournal(File journalFile, IDao<Equipment> equipmentDao, IDao<Route> routeDao) {
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
                    List<Route> previouslyDefinedRoutes = parceRoutes(getStringCellValue(cells.next()), routeDao);
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


    private static List<Route> parceRoutes(String routesString, IDao<Route> routeDao) {
        List<Route> actualRoutes = new ArrayList<>();
        String[] fragments = routesString.split(";");
        Stream.of(fragments).forEachOrdered(o -> {
            Route r = routeDao.read(o.trim());
            if (r != null) actualRoutes.add(r);
        });
        return actualRoutes;
    }
}
