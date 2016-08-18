package app.dto.models;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LineDto {
    private Integer id;
    private JoinPointDto startPoint;
    private JoinPointDto endPoint;
    private List<RouteDto> routesList;
    private boolean traced;

    public LineDto() {
    }

    public LineDto(JoinPointDto startPoint, JoinPointDto endPoint, List<RouteDto> routesList, boolean traced) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.routesList = routesList;
        this.traced = traced;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JoinPointDto getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(JoinPointDto startPoint) {
        this.startPoint = startPoint;
    }

    public JoinPointDto getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(JoinPointDto endPoint) {
        this.endPoint = endPoint;
    }

    public List<RouteDto> getRoutesList() {
        return routesList;
    }

    public void setRoutesList(List<RouteDto> routesList) {
        this.routesList = routesList;
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
        if (!(o instanceof LineDto)) return false;

        LineDto lineDto = (LineDto) o;

        if (isTraced() != lineDto.isTraced()) return false;
        if (getStartPoint() != null ? !getStartPoint().equals(lineDto.getStartPoint()) : lineDto.getStartPoint() != null)
            return false;
        if (getEndPoint() != null ? !getEndPoint().equals(lineDto.getEndPoint()) : lineDto.getEndPoint() != null)
            return false;
        return getRoutesList() != null ? getRoutesList().equals(lineDto.getRoutesList()) : lineDto.getRoutesList() == null;

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
        final StringBuilder sb = new StringBuilder("LineDto{");
        sb.append("startPoint=").append(startPoint);
        sb.append(", endPoint=").append(endPoint);
        sb.append('}');
        return sb.toString();
    }
}
