package servises.tracerlogic;


import model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import properties.PropertiesHolder;

import java.util.ArrayList;
import java.util.List;

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

    public static Object [] defineNearestPoint(double[] equipXyz, List<JoinPoint> shorterPointList, double reserveRatio) {
        double a = equipXyz[0];
        double b = equipXyz[1];
        double c = equipXyz[2];
        List<Double> distance = new ArrayList<>(shorterPointList.size() - 1);
        List<Integer> length = new ArrayList<>(shorterPointList.size() - 1);
        List<JoinPoint> points = new ArrayList<>(shorterPointList.size() - 1);
        for (int i = 1; i < shorterPointList.size() - 1; i++) {
            points.add(shorterPointList.get(i));
            double x = shorterPointList.get(i).getXyz()[0];
            double y = shorterPointList.get(i).getXyz()[1];
            double z = shorterPointList.get(i).getXyz()[2];
            distance.add(Math.sqrt((x - a) * (x - a) + (y - b) * (y - b) + (z - c) * (z - c)));
            length.add((int) (Math.abs(x - a) + Math.abs(y - b) + Math.abs(z - c)));
        }
        for (int i = 1; i < distance.size(); i++) {
            if (distance.get(i) > distance.get(i - 1)) {
                double tmp1 = distance.get(i - 1);
                distance.set(i - 1, distance.get(i));
                distance.set(i, tmp1);
                int tmp2 = length.get(i - 1);
                length.set(i - 1, length.get(i));
                length.set(i, tmp2);
                JoinPoint temp = points.get(i - 1);
                points.set(i - 1, points.get(i));
                points.set(i, temp);
            }
        }
        Integer extraLength = (int)((length.get(length.size() - 1))*reserveRatio);
        JoinPoint closestPoint = points.get(points.size() - 1);
        return new Object []{closestPoint, extraLength};
    }
}
