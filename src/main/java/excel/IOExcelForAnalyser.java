package excel;


import model.db.IDao;
import model.entities.Equipment;
import model.entities.JoinPoint;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import properties.PropertiesHolder;
import servises.utils.CommonUtil;

import java.io.File;
import java.util.*;

import static excel.utils.ExcelUtils.*;

@Component
public class IOExcelForAnalyser {

    @Autowired
    private ExcelDBService excelDBService;

    @Autowired
    private PropertiesHolder propertiesHolder;

    /**
     * ранее:
     * Находит в журнале оборудование и сверяет с каталогом оборудования.
     * В случае нахождения нового оборудования - возвращает это оборудование.
     * В файле каждого вхлодящего кабельного журнала оранжевым цветом выделяются ячейки,
     * в которых обнаружено новое оборудование и зеленым цветом - ячейки где найдено
     * оборудование, имеющееся в каталоге.
     */
    public List<String[]> analyseNewEquipmentsFromJournal(List<String[]> addEquip, File journalFile, List<Equipment> equipmentList, File targetPath) {
        try {
            Workbook workbook = getWorkbook(journalFile);
            Sheet sheet = workbook.getSheetAt(0);
            workbook.setSheetName(0, journalFile.getName());
            Iterator<Row> it = sheet.iterator();
            //new styles
            //all OK style
            CellStyle styleEquipmentConfirmed = workbook.createCellStyle();
            styleEquipmentConfirmed.setAlignment(CellStyle.ALIGN_CENTER);
            styleEquipmentConfirmed.setBorderBottom(CellStyle.BORDER_THIN);
            styleEquipmentConfirmed.setBorderLeft(CellStyle.BORDER_MEDIUM);
            styleEquipmentConfirmed.setBorderRight(CellStyle.BORDER_MEDIUM);
            styleEquipmentConfirmed.setBorderTop(CellStyle.BORDER_THIN);
            styleEquipmentConfirmed.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleEquipmentConfirmed.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            Font font1 = workbook.createFont();
            font1.setColor(IndexedColors.DARK_BLUE.getIndex());
            font1.setFontHeightInPoints((short) 10);
            styleEquipmentConfirmed.setFont(font1);
            //warning style
            //TODO inject this style?
            CellStyle styleMaybeEquipment = workbook.createCellStyle();
            styleMaybeEquipment.cloneStyleFrom(styleEquipmentConfirmed);
            styleMaybeEquipment.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            Font font2 = workbook.createFont();
            font2.setColor(IndexedColors.DARK_GREEN.getIndex());
            font2.setFontHeightInPoints((short) 10);
            styleMaybeEquipment.setFont(font2);
            //missing eq style
            CellStyle styleNewEquipment = workbook.createCellStyle();
            styleNewEquipment.cloneStyleFrom(styleEquipmentConfirmed);
            styleNewEquipment.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            Font font3 = workbook.createFont();
            font3.setColor(IndexedColors.DARK_RED.getIndex());
            font3.setFontHeightInPoints((short) 10);
            styleNewEquipment.setFont(font3);

            int stringNumber = 1;
            while (it.hasNext()) {
                Row row = it.next();
                stringNumber++;
                if (stringNumber > 5) {
                    Cell cellF = row.getCell(5);
                    Cell cellG = row.getCell(6);
                    Cell cellH = row.getCell(7);
                    Cell cellI = row.getCell(8);
                    Cell cellJ = row.getCell(9);
                    Cell cellK = row.getCell(10);
                    Cell cellL = row.getCell(11);
                    Cell cellM = row.getCell(12);
                    if (cellF == null || cellJ == null) continue;
                    String cf = getStringCellValue(cellF);
                    String cj = getStringCellValue(cellJ);
                    String kksCF = extractKKS(cf);
                    String kksCJ = extractKKS(cj);
                    String[] nameXyzCF = {kksCF, cf, getStringCellValue(cellG), getStringCellValue(cellH), getStringCellValue(cellI), journalFile.getName()};
                    String[] nameXyzCJ = {kksCJ, cj, getStringCellValue(cellK), getStringCellValue(cellL), getStringCellValue(cellM), journalFile.getName()};
                    boolean marker1 = false, marker2 = false;
                    for (Equipment eq : equipmentList) {
                        String eqFullName = eq.getEquipmentName();
                        String eqKks = eq.getKksName();
                        if (!marker1 && (kksCF.equals(eqFullName) || cf.equals(eqKks) || eqFullName.equals(cf))) {
                            cellF.setCellStyle(styleEquipmentConfirmed);
                            marker1 = true;
                            continue;
                        }
                        if (!marker2 && (kksCJ.equals(eqFullName) || cj.equals(eqKks) || eqFullName.equals(cj))) {
                            cellJ.setCellStyle(styleEquipmentConfirmed);
                            marker2 = true;
                        }
                        if (marker1 && marker2) break;
                    }
                    if ((!marker1) && !cf.equals("")) {
                        cellF.setCellStyle(styleNewEquipment);
                        if (!isContains(addEquip, nameXyzCF)) addEquip.add(nameXyzCF);
                    }
                    if ((!marker2) && !cf.equals("")) {
                        cellJ.setCellStyle(styleNewEquipment);
                        if (!isContains(addEquip, nameXyzCJ)) addEquip.add(nameXyzCJ);
                    }
                }
            }
            String outputPathName = targetPath != null && targetPath.isDirectory() ? targetPath.getAbsolutePath() : propertiesHolder.get("output.path");
            String journalName = journalFile.getName();
            String newMessage = propertiesHolder.get("output.suffix.analysedJournals");
            String fileExtension = propertiesHolder.get("default.excelFileType");
            String targetFileName = buildFileName(outputPathName, null, journalName, newMessage, fileExtension);

            writeWorkbook(workbook, new File(targetFileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return addEquip;
    }

    public boolean writeToFileAllAdditionalEquipment(String projectName, List<String[]> addEquip, File targetPath) {
        try {
            String equipmentsFileName = propertiesHolder.get("default.equipmentsFileName");
            String equipmentsPathName = propertiesHolder.get("default.inputPathName");
            String fileExtension = propertiesHolder.get("default.excelFileType");
            String newMessage = propertiesHolder.get("output.suffix.newEquipmentDefined");
            String targetFileName;
            if (targetPath == null || !targetPath.isDirectory()) {
                String journalPathName = propertiesHolder.get("output.path");

                targetFileName = buildFileName(journalPathName, projectName, equipmentsFileName, newMessage, fileExtension);
            } else {
                targetFileName = buildFileName(targetPath.getAbsolutePath(), projectName, equipmentsFileName, newMessage, fileExtension);
            }
            File targetFile = new File(targetFileName);
            if (addEquip != null) {
                Workbook workbook = getWorkbook(new File(buildFileName(equipmentsPathName, projectName, equipmentsFileName, null, fileExtension)));
                Sheet sheet = workbook.getSheetAt(0);
                int num = sheet.getLastRowNum()+1;
                int i = 0;
                for (String [] eq : addEquip) {
                    if (!eq [0].equals("")) {
                        Row row = sheet.createRow(num + i++);
                        for (int n = 0; n < eq.length-1; n++) {
                            row.createCell(n).setCellValue(eq[n]);
                        }
                        //номер журнала откуда взято
                        row.createCell(7).setCellValue(eq[eq.length-1]);
                    }
                }
                writeWorkbook(workbook, targetFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean analyseAndDefineClosestPointsOfEquipment(String projectName, File equipmentFile, File targetPath, IDao<JoinPoint> joinPointDao) {
        try {
            String equipmentsFileName = propertiesHolder.get("default.equipmentsFileName");

            String fileExtension = propertiesHolder.get("default.excelFileType");
            String newMessage = propertiesHolder.get("output.suffix.pointsForEquipmentDefined");
            String equipmentPath = propertiesHolder.get("default.inputPathName");
            if (equipmentFile == null) {
                equipmentFile = new File(buildFileName(equipmentPath, projectName, equipmentsFileName, null, fileExtension));
            }
            String targetFileName;
            if (targetPath == null || !targetPath.isDirectory()) {
                String journalPathName = propertiesHolder.get("output.path");
                targetFileName = buildFileName(journalPathName, null, equipmentsFileName, newMessage, fileExtension);
            } else {
                targetFileName = buildFileName(targetPath.getAbsolutePath(), null, equipmentsFileName, newMessage, fileExtension);
            }
            File targetFile = new File(targetFileName);
            List<Equipment> allEquipment = excelDBService.readEquipments(equipmentFile);
            if (allEquipment == null) return false;
            List<Equipment> targetEquipment = new ArrayList<>();
            allEquipment.forEach(o -> {
                if (o.getJoinPoint() == null || o.getJoinPoint().getKksName().equals("")) targetEquipment.add(o);
            });
            for (Equipment equipment : targetEquipment) {
                System.out.println(equipment.getKksName());
                double reserveRatio = propertiesHolder.get("reserveRatio.approximateDeterminationOfTrace", Double.class);
                Object[] result = CommonUtil.defineNearestPoint(equipment.getXyz(), joinPointDao.getAll(), reserveRatio);
                JoinPoint joinPointDefined = ((JoinPoint) result[0]);
                int extraLength = (int) result[1];
                equipment.setJoinPoint(joinPointDefined);
                equipment.setCableConnectionAddLength(equipment.getCableConnectionAddLength() + extraLength);
            }

            Workbook workbook = getWorkbook(equipmentFile);
            CellStyle pointAddedStyle = workbook.createCellStyle();
            pointAddedStyle.setAlignment(CellStyle.ALIGN_CENTER);
            pointAddedStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            pointAddedStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            Font font = workbook.createFont();
            font.setColor(IndexedColors.DARK_BLUE.getIndex());
            font.setFontHeightInPoints((short) 11);
            pointAddedStyle.setFont(font);

            Sheet sheet = workbook.getSheetAt(FIRST_SHEET_INDEX);
            Iterator<Row> it = sheet.rowIterator();
            while (it.hasNext()) {
                Row row = it.next();
                String cellB = getStringCellValue(row.getCell(1));
                for (Equipment equipment : targetEquipment) {
                    if (cellB.equals(equipment.getEquipmentName())) {
                        Cell cellF = row.createCell(5);
                        cellF.setCellStyle(pointAddedStyle);
                        cellF.setCellValue(equipment.getJoinPoint().getKksName());
                        Cell cellG = row.createCell(6);
                        cellG.setCellStyle(pointAddedStyle);
                        cellG.setCellValue(equipment.getCableConnectionAddLength());
                    }
                }
            }
            writeWorkbook(workbook, targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean isContains(List<String[]> list, String[] current) {
        if (list.size() == 0 || current == null) return false;
        for (String[] obj : list) {
            if (obj[1].equals(current[1])) return true;
        }
        return false;
    }
}
