package servises.tracerlogic;


import model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import properties.PropertiesHolder;

import java.util.*;

@Component
public class TracingHelper {

    @Autowired
    private PropertiesHolder propertiesHolder;

    public String getRoutesListForExcel(Line line) {
        if (line.isTraced()) {
            StringBuilder builder = new StringBuilder();
            String firstWord = null;
            String secondWord = "";
            String beforeLastWord = "";
            String lastWord = null;
            if (propertiesHolder.get("tracer.showAlternatePointsInTrace", Boolean.class)) {
                firstWord = line.getStartPoint().getKksName();
                lastWord = line.getEndPoint().getKksName();
            }
            if (propertiesHolder.get("tracer.showApproximateDeterminationOfTraceMessage", Boolean.class)) {
                if (line instanceof Cable) {
                    if (defineExtraLength(((Cable) line).getStart()) >= propertiesHolder.get("cableLength.minimalForApproxDetermMessage", Double.class)) {
                        secondWord = propertiesHolder.get("message.approximateDeterminationOfTrace");
                    }
                    if (defineExtraLength(((Cable) line).getEnd()) >= propertiesHolder.get("cableLength.minimalForApproxDetermMessage", Double.class)) {
                        beforeLastWord = propertiesHolder.get("message.approximateDeterminationOfTrace");
                    }
                }
            }
            if (firstWord != null) {
                builder.append("[");
                builder.append(firstWord);
                builder.append("]; ");
            }
            builder.append(secondWord);
            builder.append("; ");
            for (Route rou : line.getRoutesList()) {
                builder.append(rou.getKksName());
                builder.append("; ");
            }
            builder.append(beforeLastWord);
            if (firstWord != null) {
                builder.append("; [");
                builder.append(lastWord);
                builder.append("]");
            }
            return builder.toString();
        } else if (propertiesHolder.get("tracer.showInTracesListWarningNoTrace", Boolean.class)) {
            return (propertiesHolder.get("message.warningNoTrace"));
        } else
            return "";
    }

    public void defineCableLength(Cable cable) {
        if (cable.getRoutesList() == null) {
            return;
        }
        double cableLength = 0;
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
        //добираем длину до кратной 5
        do {
            cableLength += 1;
        } while (cableLength % 5 != 0);
        //сверим с определенной ранее длиной
        if (cable.getLength() < cableLength) {
            cable.setLength((int) cableLength);
        }
    }

    private static double defineExtraLength(Equipment equipment) {
        double sX = equipment.getXyz()[0];
        double sY = equipment.getXyz()[1];
        double sZ = equipment.getXyz()[2];
        double eX = equipment.getJoinPoint().getXyz()[0];
        double eY = equipment.getJoinPoint().getXyz()[1];
        double eZ = equipment.getJoinPoint().getXyz()[2];
        return Math.abs(sX - eX) + Math.abs(sY - eY) + Math.abs(sZ - eZ);
    }

    public static Object [] defineNearestPoint(double[] equipXyz, List<JoinPoint> pointList, double reserveRatio) {
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
        Integer extraLength = (int)Math.round(map.firstEntry().getKey()*reserveRatio);
        JoinPoint closestPoint = map.firstEntry().getValue();
        return new Object []{closestPoint, extraLength};
    }

    private static double minus (double m, double n) {
        if (m > n) return Math.abs(m - n);
        else return Math.abs(n - m);
    }

}
