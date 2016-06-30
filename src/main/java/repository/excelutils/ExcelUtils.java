package repository.excelutils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import test_exceptions.CellReadMyException;

import java.io.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ExcelUtils {
    public static final int FIRST_SHEET_INDEX = 0;

    /**
     * Читает любой стандартный тип ячейки книги excel в строковом представлении
     */
    public static String getStringCellValue(Cell cell) {
        StringBuilder s = new StringBuilder();
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    s.append("");
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    s.append(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    s.append(cell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    s.append(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    s.append(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    s.append(cell.getStringCellValue());
                    break;
                default:
                    s.append("");
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CellReadMyException();
        }
        return s.toString();
    }

    /**
     * Читает стандартный тип ячейки книги excel в цифровом представлении
     */
    public static Double getDoubleCellValue(Cell cell) {
        Double cellValue = null;
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    cellValue = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = Double.parseDouble(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                case Cell.CELL_TYPE_BOOLEAN:
                case Cell.CELL_TYPE_ERROR:
                case Cell.CELL_TYPE_FORMULA:
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CellReadMyException();
        }
        return cellValue;
    }

    /**
     * Читает стандартный тип ячейки книги excel в округленном до целых единиц цифровом представлении
     */
    public static int getIntCellValue(Cell cell) {
        int cellValue = 0;
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    cellValue = (int)cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = Integer.parseInt(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                case Cell.CELL_TYPE_BOOLEAN:
                case Cell.CELL_TYPE_ERROR:
                case Cell.CELL_TYPE_FORMULA:
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CellReadMyException();
        }
        return cellValue;
    }

    public static String buildFileName(String path, String project, String target, String suffix, String type) {
        StringBuilder targetFileName = new StringBuilder();
        if (path != null) targetFileName.append(path);
        if (targetFileName.length() > 0 && targetFileName.charAt(targetFileName.length()) != '/') targetFileName.append('/');
        if (project != null) targetFileName.append(project).append(" - ");
        if (target != null) targetFileName.append(target);
        if (suffix != null) targetFileName.append('_').append(suffix);
        if (type != null) targetFileName.append(type);
        return targetFileName.toString();
    }

    public static String extractKKS (String someString, String patternExpected) {
        StringBuilder extracted = new StringBuilder();
        Pattern pattern = Pattern.compile(patternExpected);
        Matcher matcher = pattern.matcher(someString);
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i ++ ) {
                extracted.append(matcher.group(i)).append('\n');
            }
        }
        return extracted.toString();
    }

    public static Workbook getWorkbook(File file) {
        Workbook workBook = null;
        try (InputStream inputStream = new FileInputStream(file)) {
            workBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workBook;
    }

    public static Iterator<Row> getWorkbookSheetIterator (File file) {
        return getWorkbook(file).getSheetAt(FIRST_SHEET_INDEX).iterator();
    }

    public static Iterator<Row> getWorkbookSheetIterator (File file, int sheetNumber) {
        return getWorkbook(file).getSheetAt(sheetNumber).iterator();
    }

    public static void writeWorkbook(Workbook workbook, File targetFile) {
        try (OutputStream outputStream = new FileOutputStream(targetFile)) {
            workbook.write(outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
