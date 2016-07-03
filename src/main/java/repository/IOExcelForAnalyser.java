package repository;


import model.db.ExcelDBServices;
import model.db.IDao;
import model.entities.Equipment;
import model.entities.JoinPoint;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import properties.PropertiesHolder;
import servises.tracerlogic.TracingHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static repository.excelutils.ExcelUtils.*;

@Component
public class IOExcelForAnalyser {

    //properties
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
    public List<String[]> analyseEquipmentsInJournal(File journalFile, List<Equipment> equipmentList, File targetPath) {

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

        List<String[]> addEquip = new ArrayList<>();
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
                String kksCF = extractKKS(cf, propertiesHolder.get("default.kksPattern.regexp"));
                String kksCJ = extractKKS(cj, propertiesHolder.get("default.kksPattern.regexp"));
                String[] nameXyzCF = {kksCF, cf, getStringCellValue(cellG), getStringCellValue(cellH), getStringCellValue(cellI)};
                String[] nameXyzCJ = {kksCJ, cj, getStringCellValue(cellK), getStringCellValue(cellL), getStringCellValue(cellM)};
                boolean marker1 = false, marker2 = false;
                for (Equipment eq : equipmentList) {
                    String eqFullName = eq.getEquipmentName();
                    String eqKks = eq.getKksName();
                    if (marker1 && marker2) break;
                    if (!marker1 && cf.contains(eqKks) || cf.contains(eqFullName) || eqFullName.contains(cf)) {
                        cellF.setCellStyle(styleEquipmentConfirmed);
                        marker1 = true;
                        continue;
                    }

                    if (!marker2 && cj.contains(eqKks) || cj.contains(eqFullName) || eqFullName.contains(cj)) {
                        cellJ.setCellStyle(styleEquipmentConfirmed);
                        marker2 = true;
                    }
                }
                if ((!marker1) && !cf.isEmpty()) {
                    cellF.setCellStyle(styleNewEquipment);
                    addEquip.add(nameXyzCF);
                }
                if ((!marker2) && !cj.isEmpty()) {
                    cellJ.setCellStyle(styleNewEquipment);
                    addEquip.add(nameXyzCJ);
                }
            }
        }
        String outputPathName = targetPath != null && targetPath.isDirectory()?  targetPath.getAbsolutePath() : propertiesHolder.get("output.path");
        String journalName = journalFile.getName();
        String newMessage = propertiesHolder.get("output.suffix.analysedJournals");
        String fileExtension = propertiesHolder.get("default.excelFileType");
        String targetFileName = buildFileName(outputPathName, null, journalName, newMessage, fileExtension);

        writeWorkbook(workbook, new File(targetFileName));
        return addEquip;
    }

    public void writeToFileAllAdditionalEquipment(String projectName, List<String[]> extraEquip, File targetPath) {
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
        if (extraEquip != null) {
            Workbook workbook = getWorkbook(new File(buildFileName(equipmentsPathName, projectName, equipmentsFileName, null, fileExtension)));
            Sheet sheet = workbook.getSheetAt(0);
            int num = sheet.getLastRowNum();
            for (int i = 0; i < extraEquip.size(); i++) {
                if (!extraEquip.get(i)[0].equals("")) {
                    Row row = sheet.createRow(num + i);
                    for (int n = 0; n < extraEquip.get(0).length; n++) {
                        row.createCell(n).setCellValue(extraEquip.get(i)[n]);
                    }
                }
            }

            writeWorkbook(workbook, targetFile);
        }
    }

    public void analyseAndDefineClosestPointsOfEquipment(String projectName, File equipmentFile, File targetPath, IDao<JoinPoint> joinPointDao) {
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
            List<Equipment> allEquipment = ExcelDBServices.readEquipments(equipmentFile, joinPointDao);
            if (allEquipment == null) return;
            List<Equipment> targetEquipment = new ArrayList<>();
            allEquipment.forEach(o -> {
                if (o.getJoinPoint() == null) targetEquipment.add(o);
            });
            for (Equipment equipment : targetEquipment) {
                double reserveRatio = propertiesHolder.get("reserveRatio.approximateDeterminationOfTrace", Double.class);
                JoinPoint joinPointDefined = null;
                int extraLength = 0;
                List<JoinPoint> shorterList = new ArrayList<>();
                shorterList.addAll(joinPointDao.getAll());
                while (joinPointDefined == null || shorterList.size() == 1) {
                    Object[] result = TracingHelper.defineNearestPoint(equipment.getXyz(), shorterList, reserveRatio);
                    joinPointDefined = ((JoinPoint) result[0]);
                    extraLength = (int) result[1];
                    shorterList.remove(joinPointDefined);
                }
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
                        Cell cellF = row.getCell(5);
                        cellF.setCellStyle(pointAddedStyle);
                        cellF.setCellValue(equipment.getJoinPoint().getKksName());
                        Cell cellG = row.getCell(6);
                        cellG.setCellStyle(pointAddedStyle);
                        cellG.setCellValue(equipment.getCableConnectionAddLength());
                    }
                }
            }
            writeWorkbook(workbook, targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
