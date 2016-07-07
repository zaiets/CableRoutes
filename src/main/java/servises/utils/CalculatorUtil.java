package servises.utils;

import model.entities.Cable;
import model.entities.Route;

import java.util.ArrayList;
import java.util.List;

public class CalculatorUtil {

    public static List<Long> getInfo(Cable cable) {
        if (cable.getRoutesList() != null) return null;
        List<Long> currentCableInfo = new ArrayList<>();
        double existingRoutes = 0;
        double newRoutes = 0;
        double b_low = 0;
        double b_high = 0;
        double l_low = 0;
        double l_high = 0;
        double t_low = 0;
        double t_high = 0;
        double c = 0;
        double g = 0;
        double p = 0;
        for (Route route : cable.getRoutesList()) {
            double length = route.getLength();
            if (route.getKksName().contains("\\*")) existingRoutes += length;
            else newRoutes += length;
            switch (route.getRouteType()) {
                case "B":
                    if (route.getHeight() < 5) b_low += length;
                    else b_high += length;
                    break;
                case "L":
                    if (route.getHeight() < 5) l_low += length;
                    else l_high += length;
                    break;
                case "T":
                    if (route.getHeight() < 5) t_low += length;
                    else t_high += length;
                    break;
                case "C":
                    c += length;
                    break;
                case "G":
                    g += length;
                    break;
                case "P":
                    p += length;
                    break;
                default:
                    break;
            }
            double extraKoef = cable.getLength() / (existingRoutes + newRoutes);
            currentCableInfo.add(Math.round(existingRoutes));
            currentCableInfo.add(Math.round(b_low * extraKoef));
            currentCableInfo.add(Math.round(b_high * extraKoef));
            currentCableInfo.add(Math.round(l_low * extraKoef));
            currentCableInfo.add(Math.round(l_high * extraKoef));
            currentCableInfo.add(Math.round(t_low * extraKoef));
            currentCableInfo.add(Math.round(t_high * extraKoef));
            currentCableInfo.add(Math.round(c * extraKoef));
            currentCableInfo.add(Math.round(g * extraKoef));
            currentCableInfo.add(Math.round(p * extraKoef));
        }
        return currentCableInfo;
    }
}
