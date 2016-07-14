package random;

import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;

public class TestApp {
    @Test
    public void testApp () {
        Map<String, String> d = new Hashtable<>();
        d.put("111", "111");
        d.put("111", "222");
        System.out.println(d.entrySet());

    }
}
