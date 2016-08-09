package app.business.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.business.properties.PropertiesHolder;
import app.repository.entities.business.Cable;
import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;
import app.repository.entities.business.Route;

import java.util.*;

@Component
public class CommonUtil {
    public CommonUtil() {
    }

    @Autowired
    private PropertiesHolder propertiesHolder;

    public String getRoutesListForExcel(Line line) {
        if (line.isTraced()) {
            StringBuilder builder = new StringBuilder();
            String firstWord = null;
            String secondWord = null;
            String beforeLastWord = null;
            String lastWord = null;
            if (propertiesHolder.get("tracer.showAlternatePointsInTrace", Boolean.class)) {
                firstWord = line.getStartPoint().getCommonKks();
                lastWord = line.getEndPoint().getCommonKks();
            }
            if (propertiesHolder.get("tracer.showApproximateDeterminationOfTraceMessage", Boolean.class)) {
                if (line instanceof Cable) {
                    if (((Cable) line).getStart().getCableConnectionAddLength() >= propertiesHolder.get("cableLength.minimalForApproxDetermMessage", Double.class)) {
                        secondWord = propertiesHolder.get("message.approximateDeterminationOfTrace");
                    }
                    if (((Cable) line).getEnd().getCableConnectionAddLength() >= propertiesHolder.get("cableLength.minimalForApproxDetermMessage", Double.class)) {
                        beforeLastWord = propertiesHolder.get("message.approximateDeterminationOfTrace");
                    }
                }
            }
            if (firstWord != null) {
                builder.append("[");
                builder.append(firstWord);
                builder.append("]; ");
            }
            if (secondWord != null) {
                builder.append(secondWord);
                builder.append("; ");
            }
            for (Route rou : line.getRoutesList()) {
                builder.append(rou.getCommonKks());
                builder.append("; ");
            }
            if (beforeLastWord != null) {
                builder.append(beforeLastWord);
            }
            if (firstWord != null) {
                builder.append("; [");
                builder.append(lastWord);
                builder.append("]");
            }
            return builder.toString();
        } else if (propertiesHolder.get("tracer.showInTracesListWarningNoTrace", Boolean.class)) {
            return (propertiesHolder.get("message.warningNoTrace"));
        } else
            return propertiesHolder.get("message.approximateDeterminationOfTrace");
    }

    public void defineAndSetCableLength(Cable cable) {
        double cableLength = 0;
        if (!(cable.getRoutesList() == null)) {
            for (Route r : cable.getRoutesList()) {
                cableLength += r.getLength();
            }
            if (cableLength > propertiesHolder.get("reserveRatio.step2", Integer.class)) {
                cableLength = cableLength * propertiesHolder.get("reserveRatio.length>step2", Double.class);
            } else if (cableLength > propertiesHolder.get("reserveRatio.step1", Integer.class)) {
                cableLength = cableLength * propertiesHolder.get("reserveRatio.length>step1", Double.class);
            } else {
                cableLength = cableLength * propertiesHolder.get("reserveRatio.length<step1", Double.class);
            }
            cableLength = (int) cableLength + cable.getStart().getCableConnectionAddLength() + cable.getEnd().getCableConnectionAddLength();
        } else {
            if (!cable.getStartPoint().equals(cable.getEndPoint())) {
                cableLength = defineDistance(cable.getStart().getXyz(), cable.getEnd().getXyz()) +
                        cable.getStart().getCableConnectionAddLength() + cable.getEnd().getCableConnectionAddLength();
            } else {
                cableLength = 0.7 * (cable.getStart().getCableConnectionAddLength() + cable.getEnd().getCableConnectionAddLength());
            }
        }
        //добираем длину до кратной 5
        do {
            cableLength += 1;
        } while ((int)cableLength % 5 != 0);
        //сверим с определенной ранее длиной
        if (cable.getLength() < cableLength) {
            cable.setLength((int) cableLength);
        }
    }

    private static double defineDistance(Double [] xyz1, Double [] xyz2) {
        try {
            double sX = xyz1[0];
            double sY = xyz1[1];
            double sZ = xyz1[2];
            double eX = xyz2[0];
            double eY = xyz2[1];
            double eZ = xyz2[2];
            return Math.abs(sX - eX) + Math.abs(sY - eY) + Math.abs(sZ - eZ);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static Object[] defineNearestPoint(Double[] equipXyz, List<JoinPoint> pointList, double reserveRatio) {
        double a = equipXyz[0];
        double b = equipXyz[1];
        double c = equipXyz[2];
        TreeMap<Double, JoinPoint> map = new TreeMap<>();
        for (JoinPoint point : pointList) {
            double x = point.getXyz()[0];
            double y = point.getXyz()[1];
            double z = point.getXyz()[2];
            Double length = minus(x, a) + minus(y, b) + minus(z, c);
            map.put(length, point);
        }
        Integer extraLength = (int) Math.round(map.firstEntry().getKey() * reserveRatio);
        JoinPoint closestPoint = map.firstEntry().getValue();
        return new Object[]{closestPoint, extraLength};
    }

    private static double minus(double m, double n) {
        if (m > n) return Math.abs(m - n);
        else return Math.abs(n - m);
    }

}
