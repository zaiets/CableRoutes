package repository;

import model.entities.Cable;
import model.entities.Journal;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import properties.PropertiesHolder;
import servises.tracerlogic.TracingHelper;

import java.io.File;

import static repository.excelutils.ExcelUtils.*;

@Component
public class IOExcelForTracer {

    @Autowired
    private PropertiesHolder propertiesHolder;
    @Autowired
    private TracingHelper tracingHelperUtil;

    /**
     * Writing info from Journal 'j' in to new Excel file named
     */
    public void writeToFileTracedJournal(String objectName, Journal journal, File targetPath) {
        String targetFileName;
        String newMessage = propertiesHolder.get("output.suffix.tracedJournals");
        String fileExtension = propertiesHolder.get("default.excelFileType");
        if (targetPath == null || !targetPath.isDirectory()) {
            String journalPathName = propertiesHolder.get("output.path");
            targetFileName = buildFileName(journalPathName, null, journal.getKksName(), newMessage, fileExtension);
        } else {
            targetFileName = buildFileName(targetPath.getAbsolutePath(), null, journal.getKksName(), newMessage, fileExtension);
        }
        File targetFile = new File(targetFileName);

        Workbook workbook = getWorkbook(new File (propertiesHolder.get("default.journalTemplateFile")));
        Sheet sheet = workbook.getSheetAt(FIRST_SHEET_INDEX);
        workbook.setSheetName(0, journal.getKksName());

        Row row = sheet.createRow(0);
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
            row.createCell(8).setCellValue(cable.getStart().getXyz()[2] + "00");
            row.createCell(9).setCellValue(cable.getEnd().getEquipmentName());
            row.createCell(10).setCellValue(cable.getEnd().getXyz()[0]);
            row.createCell(11).setCellValue(cable.getEnd().getXyz()[1]);
            row.createCell(12).setCellValue(cable.getEnd().getXyz()[2] + "00");
            row.createCell(13).setCellValue(cable.getLength());
            row.createCell(14).setCellValue(tracingHelperUtil.getRoutesListForExcel(cable));
            row.createCell(15);
            for (int i = 0; i < 16; i++) {
                row.getCell(i).setCellStyle(style);
            }
            row.getCell(2).setCellStyle(style1);
            row.getCell(14).setCellStyle(style2);
        }
        writeWorkbook(workbook, targetFile);
    }
}
