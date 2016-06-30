package model.entities;


import java.util.List;

public class Line {
    private JoinPoint startPoint;
    private JoinPoint endPoint;
    private List<Route> routesList;
    private boolean traced;

    public Line(JoinPoint startPoint, JoinPoint endPoint) {
        setStartPoint(startPoint);
        setEndPoint(endPoint);

    }

    public JoinPoint getStartPoint() {
        return startPoint;
    }

    public JoinPoint getEndPoint() {
        return endPoint;
    }

    public List<Route> getRoutesList() {
        return routesList;
    }

    public void setRoutesList(List<Route> routesList) {
        this.routesList = routesList;
    }

    void setStartPoint(JoinPoint startPoint) {
        this.startPoint = startPoint;
    }

    void setEndPoint(JoinPoint endPoint) {
        this.endPoint = endPoint;
    }

    public boolean isTraced() {
        return traced;
    }

    public void setTraced(boolean traced) {
        this.traced = traced;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;

        Line line = (Line) o;

        if (isTraced() != line.isTraced()) return false;
        if (getStartPoint() != null ? !getStartPoint().equals(line.getStartPoint()) : line.getStartPoint() != null)
            return false;
        if (getEndPoint() != null ? !getEndPoint().equals(line.getEndPoint()) : line.getEndPoint() != null)
            return false;
        return getRoutesList() != null ? getRoutesList().equals(line.getRoutesList()) : line.getRoutesList() == null;

    }

    @Override
    public int hashCode() {
        int result = getStartPoint() != null ? getStartPoint().hashCode() : 0;
        result = 31 * result + (getEndPoint() != null ? getEndPoint().hashCode() : 0);
        result = 31 * result + (getRoutesList() != null ? getRoutesList().hashCode() : 0);
        result = 31 * result + (isTraced() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Line{");
        sb.append("startPoint=").append(startPoint);
        sb.append(", endPoint=").append(endPoint);
        sb.append(", routesList=").append(routesList);
        sb.append(", traced=").append(traced);
        sb.append('}');
        return sb.toString();
    }
}
