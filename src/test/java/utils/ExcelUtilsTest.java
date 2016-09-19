package utils;

import org.junit.Assert;
import org.junit.Test;
import app.service.functionality.excelworkers.ExcelUtils;


public class ExcelUtilsTest {

    @Test
    public void buildTargetFileNameTest () {
        String expected1 = "a/a - a_a.s";
        String actual1 = ExcelUtils.buildFileName("a", "a", "a", "a", ".s");
        Assert.assertEquals(expected1, actual1);
        String expected2 = "a/a.s";
        String actual2 = ExcelUtils.buildFileName("a", null, "a", null, ".s");
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void extractKksTest () {
        String targetText = "Коробка зажимов 00SАС40ФФ002-X01";
        String expected = "00SАС40ФФ002";
        String actual = ExcelUtils.extractKKS(targetText);
        Assert.assertEquals("Extract KKS test", expected, actual);
    }
}
