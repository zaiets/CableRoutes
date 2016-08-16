package app.functionalityTODO.utils;

import app.repository.entities.business.Cable;
import app.repository.entities.business.Route;

import java.util.ArrayList;
import java.util.List;

public final class CalculatorUtil {

    public static final int FIRST_HEIGHT_MARKER = 2;

    private CalculatorUtil() {
    }

    public static List<Long> getInfo(Cable cable) {
        if (cable.getRoutesList() == null) return null;
        List<Long> currentCableInfo = new ArrayList<>();
        double existingRoutes = 0;
        double newRoutes;
        double b_low = 0;
        double b_high = 0;
        double l_low = 0;
        double l_high = 0;
        double t_low = 0;
        double t_high = 0;
        double c = 0;
        double g = 0;
        double p = 0;
        double summary_low;
        double summary_high;
        for (Route route : cable.getRoutesList()) {
            double length = route.getLength();
            if (route.getKksName().contains("*")) {
                existingRoutes += length;
            }
            String type = route.getRouteType();
            if (type.contains("B")) {
                if (route.getHeight() < FIRST_HEIGHT_MARKER) b_low += length;
                else b_high += length;
            } else if (type.contains("L")) {
                if (route.getHeight() < FIRST_HEIGHT_MARKER) l_low += length;
                else l_high += length;
            } else if (type.contains("T")) {
                if (route.getHeight() < FIRST_HEIGHT_MARKER) t_low += length;
                else t_high += length;
            } else if (type.contains("C")) {
                c += length;
            } else if (type.contains("G")) {
                g += length;
            } else if (type.contains("P")) {
                p += length;
            }
        }
        newRoutes = cable.getLength() - existingRoutes;
        summary_low = b_low + l_low + t_low + c + g + p;
        summary_high = b_high + l_high + t_high;

        double extraKoef = cable.getLength() / (summary_low + summary_high);
        currentCableInfo.add(Math.round(existingRoutes));
        currentCableInfo.add(Math.round(newRoutes));
        currentCableInfo.add(Math.round(b_low * extraKoef));
        currentCableInfo.add(Math.round(b_high * extraKoef));
        currentCableInfo.add(Math.round(l_low * extraKoef));
        currentCableInfo.add(Math.round(l_high * extraKoef));
        currentCableInfo.add(Math.round(t_low * extraKoef));
        currentCableInfo.add(Math.round(t_high * extraKoef));
        currentCableInfo.add(Math.round(c * extraKoef));
        currentCableInfo.add(Math.round(g * extraKoef));
        currentCableInfo.add(Math.round(p * extraKoef));
        currentCableInfo.add(Math.round(summary_low * extraKoef));
        currentCableInfo.add(Math.round(summary_high * extraKoef));
        return currentCableInfo;
    }
}
