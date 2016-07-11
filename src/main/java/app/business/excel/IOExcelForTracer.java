package app.business.excel;

import app.repository.entities.business.Cable;
import app.repository.entities.business.Journal;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.business.utils.CommonUtil;

import java.io.File;

import static app.business.excel.utils.ExcelUtils.*;

@Component
public class IOExcelForTracer {

    @Autowired
    private CommonUtil commonUtil;

    /**
     * Writing info from Journal 'j' in to new Excel file named
     */
    public void writeToFileTracedJournal(String objectName, Journal journal, File targetFile, File templateFile) {


        Workbook workbook = getWorkbook(templateFile);
        Sheet sheet = workbook.getSheetAt(FIRST_SHEET_INDEX);
        workbook.setSheetName(0, journal.getKksName());

        Row row = sheet.getRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(objectName);

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
            row.createCell(3).setCellValue(cable.getCableType()[0]);
            row.createCell(4).setCellValue(cable.getCableType()[1]);
            row.createCell(5).setCellValue(cable.getStart().getEquipmentName());
            row.createCell(6).setCellValue(cable.getStart().getXyz()[0]);
            row.createCell(7).setCellValue(cable.getStart().getXyz()[1]);
            row.createCell(8).setCellValue(cable.getStart().getXyz()[2]);
            row.createCell(9).setCellValue(cable.getEnd().getEquipmentName());
            row.createCell(10).setCellValue(cable.getEnd().getXyz()[0]);
            row.createCell(11).setCellValue(cable.getEnd().getXyz()[1]);
            row.createCell(12).setCellValue(cable.getEnd().getXyz()[2]);
            row.createCell(13).setCellValue(cable.getLength());
            row.createCell(14).setCellValue(commonUtil.getRoutesListForExcel(cable));
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
    }


}
