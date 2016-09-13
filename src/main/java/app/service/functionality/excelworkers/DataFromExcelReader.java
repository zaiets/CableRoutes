package app.service.functionality.excelworkers;

import app.dto.models.*;
import app.service.entities.IEquipmentService;
import app.service.entities.IJoinPointService;
import app.service.entities.IRouteService;
import app.service.entities.IRouteTypeService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static app.service.functionality.excelworkers.ExcelUtils.*;

@Service
public final class DataFromExcelReader {
    @Autowired
    static final Logger logger = LoggerFactory.getLogger(DataFromExcelReader.class);
    @Autowired
    private IRouteService routeService;
    @Autowired
    private IJoinPointService joinPointService;
    @Autowired
    private IEquipmentService equipmentService;
    @Autowired
    private IRouteTypeService routeTypeService;

    public DataFromExcelReader() {
    }


    public List<JoinPointDto> readJoinPoints(File joinPointsFile) {
        List<JoinPointDto> joinPoints = new ArrayList<>();
        try {
            Iterator<Row> it = getWorkbookSheetIterator(joinPointsFile);
            int stringNumber = 1;
            while (it.hasNext()) {
                Row row = it.next();
                stringNumber++;
                if (stringNumber > 3) {
                    Iterator<Cell> cells = row.iterator();
                    String name = cells.next().getStringCellValue();
                    double x, y, z;
                    x = getDoubleCellValue(cells.next());
                    y = getDoubleCellValue(cells.next());
                    z = getDoubleCellValue(cells.next());
                    JoinPointDto current = new JoinPointDto(name, x, y, z);
                    joinPoints.add(current);
                    logger.info("readJoinPoints reads join point {} from file", name);
                }
            }
            return joinPoints;
        } catch (Exception ex) {
            logger.warn("readJoinPoints read trouble detected: {}", joinPointsFile.getName());
        }
        return null;
    }

    public List<EquipmentDto> readEquipments(File equipmentsFile) {
        List<EquipmentDto> equipments = new ArrayList<>();
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
                    Double x = getDoubleCellValue(cells.next());
                    Double y = getDoubleCellValue(cells.next());
                    Double z = getDoubleCellValue(cells.next());

                    Cell cellF = cells.next();
                    JoinPointDto closestTracePoint = null;
                    if (cellF != null) {
                        String closestPointName = getStringCellValue(cellF);
                        closestTracePoint = joinPointService.read(closestPointName);
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
                    EquipmentDto current = new EquipmentDto(equipmentKKS, equipmentName, extraCableLength, x, y, z, closestTracePoint);
                    equipments.add(current);
                    logger.info("readEquipments read equipment {} from file", equipmentName);
                }
            }
            return equipments;
        } catch (Exception ex) {
            logger.warn("readEquipments read trouble detected: {}", equipmentsFile);
        }
        return null;
    }

    public List<RouteDto> readRoutes(File routesFile) {
        List<RouteDto> routes = new ArrayList<>();
        try {
            Iterator<Row> it = getWorkbookSheetIterator(routesFile);
            int stringNumber = 1;
            while (it.hasNext()) {
                Row row = it.next();
                stringNumber++;
                if (stringNumber > 3) {
                    Iterator<Cell> cells = row.iterator();
                    String routeKks = getStringCellValue(cells.next());
                    RouteTypeDto routeType = routeTypeService.read(getStringCellValue(cells.next()));
                    double length = getDoubleCellValue(cells.next());
                    JoinPointDto point1 = joinPointService.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    JoinPointDto point2 = joinPointService.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    int shelvesCount = getDoubleCellValue(cells.next()).intValue();
                    Double height = getDoubleCellValue(cells.next());
                    RouteDto current = new RouteDto(routeKks, routeType, length, height, shelvesCount, point1, point2);
                    routes.add(current);
                    logger.info("readRoutes read route {} from file", routeKks);
                }
            }
            return routes;
        } catch (Exception ex) {
            logger.warn("readRoutes read trouble detected: {}", routesFile);
        }
        return null;
    }

    public JournalDto readJournal(File journalFile) {
        List<CableDto> cables = new ArrayList<>();
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
                    String cableType = getStringCellValue(cells.next());
                    String dimentions = getStringCellValue(cells.next());
                    EquipmentDto startEquip = equipmentService.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    EquipmentDto endEquip = equipmentService.read(getStringCellValue(cells.next()));
                    cells.next();
                    cells.next();
                    cells.next();
                    int previousLength = getIntCellValue(cells.next());
                    boolean isTraced = false;
                    List<RouteDto> previouslyDefinedRoutes = parseRoutes(getStringCellValue(cells.next()));
                    if (previouslyDefinedRoutes != null && !previouslyDefinedRoutes.isEmpty()) {
                        isTraced = true;
                    }
                    CableDto cable = new CableDto(cableKKSCode, journalFile.getName(), numberInJournal, cableType,
                            dimentions, reserving, startEquip, endEquip, previousLength, previouslyDefinedRoutes, isTraced);
                    cables.add(cable);
                    logger.info("readJournal read cable {} from journal {}", cableKKSCode, journalFile.getName());
                }
            }
            return new JournalDto(journalFile.getName(), journalFile, cables);
        } catch (Exception ex) {
            logger.warn("readJournal read trouble detected: {}", journalFile.getName());
        }
        return null;
    }


    private List<RouteDto> parseRoutes(String routesString) {
        if (routesString.isEmpty() || routesString.equals("")) return null;
        List<RouteDto> actualRoutes = new ArrayList<>();
        String[] fragments = routesString.split(";");
        Stream.of(fragments).forEachOrdered(o -> {
            RouteDto r = routeService.read(o.trim());
            if (r != null) actualRoutes.add(r);
        });
        return actualRoutes;
    }
}
