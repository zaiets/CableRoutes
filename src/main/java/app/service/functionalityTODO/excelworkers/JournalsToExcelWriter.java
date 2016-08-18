package app.service.functionalityTODO.excelworkers;

import app.repository.entities.business.Cable;
import app.repository.entities.business.Journal;
import app.properties.PropertiesManager;
import app.service.functionalityTODO.strategies.ICalculatorStrategy;
import app.service.functionalityTODO.strategies.IDataManagementStrategy;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static app.service.functionalityTODO.excelworkers.ExcelUtils.*;

@Component
public final class JournalsToExcelWriter {

    @Autowired
    static final Logger logger = LoggerFactory.getLogger(JournalsToExcelWriter.class);

    public static final int START_CELL_IN_TEMPLATE_FOR_CALC = 16;
    public static final int LAST_TITLE_ROW_NUM_IN_TEMPLATE = 5;

    @Autowired
    private PropertiesManager propertiesManager;
    @Autowired
    private IDataManagementStrategy dataManagementStrategy;
    @Autowired
    private ICalculatorStrategy calculatorStrategy;

    public JournalsToExcelWriter() {
    }

    /**
     * Writing info from Journal 'j' in to new Excel file (for traced journals)
     */
    public File createTracedJournalFile(Journal journal) {
        try {
            logger.info("createTracedJournalFile generates xlsx file of journal: {}", journal.getKksName());
            String newMessage = propertiesManager.get("output.suffix.tracedJournals");
            String dataTimeStamp = LocalDate.now().format(DateTimeFormatter.ofPattern("hh-mm-uuuu-MM-dd"));
            String targetFileName = buildFileName(null, null, journal.getKksName(), newMessage, dataTimeStamp);
            String fileSuffix = propertiesManager.get("default.excel.type");
            File targetFile = File.createTempFile(targetFileName, fileSuffix);
            File templateJournalFile = new File(propertiesManager.get("tracer.journalTemplateFile"));
            Workbook workbook = getWorkbook(templateJournalFile);
            Sheet sheet = workbook.getSheetAt(FIRST_SHEET_INDEX);
            workbook.setSheetName(0, journal.getKksName());

            Row row = sheet.getRow(0);
            Cell cell = row.createCell(0);

            //TODO this will be replased by current project name
            cell.setCellValue("TEST");

            CellStyle style = workbook.createCellStyle();
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_MEDIUM);
            style.setBorderRight(CellStyle.BORDER_MEDIUM);
            style.setBorderTop(CellStyle.BORDER_THIN);

            CellStyle style1 = workbook.createCellStyle();
            style1.cloneStyleFrom(style);
            Font font1 = workbook.createFont();
            font1.setFontHeightInPoints((short) 6);
            style1.setFont(font1);

            CellStyle style2 = workbook.createCellStyle();
            style2.cloneStyleFrom(style);
            Font font2 = workbook.createFont();
            font2.setColor(IndexedColors.DARK_RED.getIndex());
            style2.setAlignment(CellStyle.ALIGN_LEFT);
            style2.setFont(font2);

            CellStyle style3 = sheet.getRow(0).getCell(8).getCellStyle();

            int lastTitleRow = 5;
            for (Cable cable : journal.getCables()) {
                row = sheet.createRow(lastTitleRow++);
                row.createCell(0).setCellValue(cable.getNumberInJournal());
                row.createCell(1).setCellValue(cable.getKksName());
                row.createCell(2).setCellValue(cable.getReserving());
                row.createCell(3).setCellValue(cable.getCableType());
                row.createCell(4).setCellValue(cable.getCableDimensions());
                row.createCell(5).setCellValue(cable.getStart().getFullName());
                row.createCell(6).setCellValue(cable.getStart().getX());
                row.createCell(7).setCellValue(cable.getStart().getY());
                row.createCell(8).setCellValue(cable.getStart().getZ());
                row.createCell(9).setCellValue(cable.getEnd().getFullName());
                row.createCell(10).setCellValue(cable.getEnd().getY());
                row.createCell(11).setCellValue(cable.getEnd().getY());
                row.createCell(12).setCellValue(cable.getEnd().getZ());
                row.createCell(13).setCellValue(cable.getLength());
                row.createCell(14).setCellValue(dataManagementStrategy.getRoutesListForExcel(cable));
                row.createCell(15);
                for (int i = 0; i < 16; i++) {
                    row.getCell(i).setCellStyle(style);
                }
                row.getCell(2).setCellStyle(style1);
                row.getCell(14).setCellStyle(style2);
                //z axis
                row.getCell(8).setCellStyle(style3);
                row.getCell(12).setCellStyle(style3);
            }
            writeWorkbook(workbook, targetFile);
            return targetFile;
        } catch (Exception ex) {
            logger.warn("Can't create generate xlsx file of traced journal: {}", journal.getKksName());
        }
        return null;
    }


    /**
     * Writing info from Journal 'j' in to new Excel file (for traced and calculated journals)
     */
    public File createEstimatedJournalFile(Journal journal) {
        try {
            logger.info("createEstimatedJournalFile generates xlsx file of journal: {}", journal.getKksName());
            String newMessage = propertiesManager.get("output.suffix.calculatedJournals");
            String dataTimeStamp = LocalDate.now().format(DateTimeFormatter.ofPattern("hh-mm-uuuu-MM-dd"));
            String targetFileName = buildFileName(null, null, journal.getKksName(), newMessage, dataTimeStamp);
            String fileSuffix = propertiesManager.get("default.excel.type");
            File targetFile = File.createTempFile(targetFileName, fileSuffix);
            File templateJournalFile = new File(propertiesManager.get("calc.journalTemplateFile"));
            Workbook workbook = getWorkbook(templateJournalFile);
            Sheet sheet = workbook.getSheetAt(0);
            workbook.setSheetName(0, journal.getKksName());

            Row row = sheet.getRow(0);
            Cell cell = row.createCell(0);

            //TODO this will be replased by current project name
            cell.setCellValue("TEST");

            //делаем набор стилей для требуемых возможных видов ячеек
            //базовый стиль
            CellStyle style0 = workbook.createCellStyle();
            style0.setAlignment(CellStyle.ALIGN_CENTER);
            style0.setBorderBottom(CellStyle.BORDER_THIN);
            style0.setBorderLeft(CellStyle.BORDER_MEDIUM);
            style0.setBorderRight(CellStyle.BORDER_MEDIUM);
            style0.setBorderTop(CellStyle.BORDER_THIN);

            CellStyle style1 = workbook.createCellStyle();
            style1.cloneStyleFrom(style0);
            Font font1 = workbook.createFont();
            font1.setFontHeightInPoints((short) 6);
            style1.setFont(font1);

            CellStyle style2 = workbook.createCellStyle();
            style2.cloneStyleFrom(style0);
            Font font2 = workbook.createFont();
            font2.setColor(IndexedColors.DARK_RED.getIndex());
            style2.setAlignment(CellStyle.ALIGN_LEFT);
            style2.setFont(font2);

            //стили для блока калькуляции длин
            CellStyle styleCalc1 = workbook.createCellStyle();
            styleCalc1.cloneStyleFrom(style0);
            styleCalc1.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleCalc1.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            Font font = workbook.createFont();
            font.setColor(IndexedColors.DARK_BLUE.getIndex());
            font.setFontHeightInPoints((short) 10);
            styleCalc1.setFont(font);

            CellStyle styleCalc2 = workbook.createCellStyle();
            styleCalc2.cloneStyleFrom(styleCalc1);
            styleCalc2.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());

            CellStyle styleCalc3 = workbook.createCellStyle();
            styleCalc3.cloneStyleFrom(styleCalc1);
            styleCalc3.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

            //стиль для поторно показываемых ячеек (они нужны для удобства)
            CellStyle styleCalcInfo = workbook.createCellStyle();
            styleCalcInfo.cloneStyleFrom(styleCalc1);
            styleCalcInfo.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

            CellStyle style3 = sheet.getRow(0).getCell(8).getCellStyle();

            int calcStartCell = START_CELL_IN_TEMPLATE_FOR_CALC;
            int lastTitleRow = LAST_TITLE_ROW_NUM_IN_TEMPLATE;
            for (Cable cable : journal.getCables()) {
                row = sheet.createRow(lastTitleRow++);
                row.createCell(0).setCellValue(cable.getNumberInJournal());
                row.createCell(1).setCellValue(cable.getKksName());
                row.createCell(2).setCellValue(cable.getReserving());
                row.createCell(3).setCellValue(cable.getCableType());
                row.createCell(4).setCellValue(cable.getCableDimensions());
                row.createCell(5).setCellValue(cable.getStart().getFullName());
                row.createCell(6).setCellValue(cable.getStart().getX());
                row.createCell(7).setCellValue(cable.getStart().getY());
                row.createCell(8).setCellValue(cable.getStart().getZ());
                row.createCell(9).setCellValue(cable.getEnd().getFullName());
                row.createCell(10).setCellValue(cable.getEnd().getX());
                row.createCell(11).setCellValue(cable.getEnd().getY());
                row.createCell(12).setCellValue(cable.getEnd().getZ());
                row.createCell(13).setCellValue(cable.getLength());
                row.createCell(14).setCellValue(dataManagementStrategy.getRoutesListForExcel(cable));
                row.createCell(15).setCellValue(cable.getLength());
                for (int i = 1; i < 16; i++) {
                    row.getCell(i).setCellStyle(style0);
                }
                row.getCell(2).setCellStyle(style1);
                row.getCell(14).setCellStyle(style2);
                row.getCell(8).setCellStyle(style3);
                row.getCell(12).setCellStyle(style3);

                //добавляем данные по части калькуляции
                List<Long> cableInfo = calculatorStrategy.getInfo(cable);
                if (!(cableInfo == null)) {
                    for (int i = 0; i < 13; i++) {
                        row.createCell(calcStartCell + i);
                        Cell thisCell = row.getCell(calcStartCell + i);
                        if (cableInfo.get(i) != 0) {
                            thisCell.setCellValue(cableInfo.get(i));
                            if (i < 2) thisCell.setCellStyle(styleCalc1);
                            else if (i < 11) thisCell.setCellStyle(styleCalc2);
                            else thisCell.setCellStyle(styleCalc3);
                        }
                    }
                } else {
                    row.createCell(calcStartCell + 1);
                    row.createCell(calcStartCell + 11);
                    row.getCell(calcStartCell + 1).setCellValue(cable.getLength());
                    row.getCell(calcStartCell + 1).setCellStyle(styleCalc1);
                    row.getCell(calcStartCell + 11).setCellValue(cable.getLength());
                    row.getCell(calcStartCell + 11).setCellStyle(style0);
                }
                //информационные ячейки (повторяющиеся)
                row.createCell(calcStartCell + 13).setCellValue(row.getCell(3).getStringCellValue());
                row.getCell(calcStartCell + 13).setCellStyle(styleCalcInfo);
                row.createCell(calcStartCell + 14).setCellValue(row.getCell(4).getStringCellValue());
                row.getCell(calcStartCell + 14).setCellStyle(styleCalcInfo);
            }
            writeWorkbook(workbook, targetFile);
            return targetFile;
        } catch (Exception ex) {
            logger.warn("Can't create generate xlsx file of estimated journal: {}", journal.getKksName());
        }
        return null;
    }
}
