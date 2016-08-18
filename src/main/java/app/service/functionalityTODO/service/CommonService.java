package app.service.functionalityTODO.service;


import app.repository.dao.business.IJoinPointDao;
import app.repository.entities.business.Cable;
import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;
import app.repository.entities.business.Route;
import app.service.functionalityTODO.properties.PropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

@Service
public class CommonService {
    //This coef was defined by manual testing of program results
    public static final double COEFICIENT_OF_REALICTIC_LENGTH_DEFINITION = 0.7;

    @Autowired
    private IJoinPointDao joinPointDao;

    @Autowired
    private PropertiesManager propertiesManager;

    public CommonService() {
    }

    /**
     * Generates proper string of data to fill 'cableroutes' cell in excel file
     */
    public String getRoutesListForExcel(Line line) {
        if (line.isTraced()) {
            StringBuilder builder = new StringBuilder();
            String firstWord = null;
            String secondWord = null;
            String beforeLastWord = null;
            String lastWord = null;
            if (propertiesManager.get("tracer.showAlternatePointsInTrace", Boolean.class)) {
                firstWord = line.getStartPoint().getUniqueName();
                lastWord = line.getEndPoint().getUniqueName();
            }
            if (propertiesManager.get("tracer.showApproximateDeterminationOfTraceMessage", Boolean.class)) {
                if (line instanceof Cable) {
                    if (((Cable) line).getStart().getCableConnectionAddLength() >= propertiesManager.get("cableLength.minimalForApproxDetermMessage", Double.class)) {
                        secondWord = propertiesManager.get("message.approximateDeterminationOfTrace");
                    }
                    if (((Cable) line).getEnd().getCableConnectionAddLength() >= propertiesManager.get("cableLength.minimalForApproxDetermMessage", Double.class)) {
                        beforeLastWord = propertiesManager.get("message.approximateDeterminationOfTrace");
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
                builder.append(rou.getKksName());
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
        } else if (propertiesManager.get("tracer.showInTracesListWarningNoTrace", Boolean.class)) {
            return (propertiesManager.get("message.warningNoTrace"));
        } else
            return propertiesManager.get("message.approximateDeterminationOfTrace");
    }

    /**
     * Defines full cable length for the current cable by its routes list (only for traced cables)
     * or directly by XYZ of its start and end points
     */
    public Cable defineAndSetCableLength(Cable cable, boolean useDirect) {
        double cableLength = 0;
        if (!(cable.getRoutesList() == null)) {
            for (Route r : cable.getRoutesList()) {
                cableLength += r.getLength();
            }
            if (cableLength > propertiesManager.get("reserveRatio.step2", Integer.class)) {
                cableLength = cableLength * propertiesManager.get("reserveRatio.length>step2", Double.class);
            } else if (cableLength > propertiesManager.get("reserveRatio.step1", Integer.class)) {
                cableLength = cableLength * propertiesManager.get("reserveRatio.length>step1", Double.class);
            } else {
                cableLength = cableLength * propertiesManager.get("reserveRatio.length<step1", Double.class);
            }
            cableLength = (int) cableLength + cable.getStart().getCableConnectionAddLength() + cable.getEnd().getCableConnectionAddLength();
        } else if (!useDirect){
            //if cable if not traced and we don't want to define the approximate length
            return cable;
        } else {
            if (!cable.getStartPoint().equals(cable.getEndPoint())) {
                cableLength = defineDistance(cable.getStart().getX(), cable.getStart().getY(), cable.getStart().getZ(),
                        cable.getEnd().getX(), cable.getEnd().getY(), cable.getEnd().getZ()) +
                        cable.getStart().getCableConnectionAddLength() + cable.getEnd().getCableConnectionAddLength();
            } else {
                cableLength = COEFICIENT_OF_REALICTIC_LENGTH_DEFINITION * (cable.getStart().getCableConnectionAddLength() + cable.getEnd().getCableConnectionAddLength());
            }
        }
        //final length must be aliquot of 5
        do {
            cableLength += 1;
        } while ((int)cableLength % 5 != 0);
        //сверим с определенной ранее длиной (если она была уже ранее записана в систему)
        if (cable.getLength() < cableLength) {
            cable.setLength((int) cableLength);
        }
        return cable;
    }

    private double defineDistance(double sX, double sY, double sZ, double eX, double eY, double eZ) {
        try {
            return Math.abs(sX - eX) + Math.abs(sY - eY) + Math.abs(sZ - eZ);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Defines closest join point for the current item by it's XYZ coordinates
     */
    public Object[] defineNearestPointData(double a, double b, double c, double reserveRatio) {
        List<JoinPoint> pointList = joinPointDao.getAll();
        TreeMap<Double, JoinPoint> map = new TreeMap<>();
        for (JoinPoint point : pointList) {
            double x = point.getX();
            double y = point.getY();
            double z = point.getZ();
            Double length = minus(x, a) + minus(y, b) + minus(z, c);
            map.put(length, point);
        }
        Integer extraLength = (int) Math.round(map.firstEntry().getKey() * reserveRatio);
        JoinPoint closestPoint = map.firstEntry().getValue();
        return new Object[]{closestPoint, extraLength};
    }

    private double minus(double m, double n) {
        if (m > n) return Math.abs(m - n);
        else return Math.abs(n - m);
    }

}
