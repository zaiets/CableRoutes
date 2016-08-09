package app.business.excel;

import app.repository.entities.business.Cable;
import app.repository.entities.business.Journal;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.business.utils.CalculatorUtil;
import app.business.utils.CommonUtil;

import java.io.File;
import java.util.List;

import static app.business.excel.utils.ExcelUtils.getWorkbook;
import static app.business.excel.utils.ExcelUtils.writeWorkbook;

@Component
public class IOExcelForCalculator {

    public static final int START_CELL_IN_TEMPLATE_FOR_CALC = 16;
    @Autowired
    private CommonUtil commonUtil;

    public IOExcelForCalculator() {
    }

// public static final int START_ROW_IN_TYPES_FILE = 3;

// no needed now..
//    public List<RouteType> parseAvailableEstimateRouteTypes(File file) {
//        List<RouteType> routeTypes = new ArrayList<>();
//        Iterator<Row> it = getWorkbookSheetIterator(file);
//        int stringNumber = 1;
//        while (it.hasNext()) {
//            Row row = it.next();
//            stringNumber++;
//            if (stringNumber > START_ROW_IN_TYPES_FILE) {
//                Iterator<Cell> cells = row.iterator();
//                CharSequence typeCode = getStringCellValue(cells.next());
//                String routeTypeName = getStringCellValue(cells.next());
//                routeTypes.add(new RouteType(routeTypeName, typeCode));
//            }
//        }
//        return routeTypes;
//    }

    public void writeToFileEstimatedJournal(String projectName, Journal journal, File targetFile, File templateFile) {
        Workbook workbook = getWorkbook(templateFile);
        Sheet sheet = workbook.getSheetAt(0);
        workbook.setSheetName(0, journal.getCommonKks());

        Row row = sheet.getRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(projectName);

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

        //стили для блока калькулятора
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
        int lastTitleRow = 5;
        for (Cable cable : journal.getCables()) {
            row = sheet.createRow(lastTitleRow++);
            row.createCell(0).setCellValue(cable.getNumberInJournal());
            row.createCell(1).setCellValue(cable.getCommonKks());
            row.createCell(2).setCellValue(cable.getReserving());
            row.createCell(3).setCellValue(cable.getCableType()[0]);
            row.createCell(4).setCellValue(cable.getCableType()[1]);
            row.createCell(5).setCellValue(cable.getStart().getFullName());
            row.createCell(6).setCellValue(cable.getStart().getXyz()[0]);
            row.createCell(7).setCellValue(cable.getStart().getXyz()[1]);
            row.createCell(8).setCellValue(cable.getStart().getXyz()[2]);
            row.createCell(9).setCellValue(cable.getEnd().getFullName());
            row.createCell(10).setCellValue(cable.getEnd().getXyz()[0]);
            row.createCell(11).setCellValue(cable.getEnd().getXyz()[1]);
            row.createCell(12).setCellValue(cable.getEnd().getXyz()[2]);
            row.createCell(13).setCellValue(cable.getLength());
            row.createCell(14).setCellValue(commonUtil.getRoutesListForExcel(cable));
            row.createCell(15).setCellValue(cable.getLength());
            for (int i = 1; i < 16; i++) {
                row.getCell(i).setCellStyle(style0);
            }
            row.getCell(2).setCellStyle(style1);
            row.getCell(14).setCellStyle(style2);
            row.getCell(8).setCellStyle(style3);
            row.getCell(12).setCellStyle(style3);

            //добавляем данные по части калькуляции
            List<Long> cableInfo = CalculatorUtil.getInfo(cable);
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
                    //else {
                    //    thisCell.setCellStyle(style0);
                    //}
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
    }
}
