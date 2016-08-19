package app.repository.entities.business;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="LINE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private int id;
    @OneToOne
    @JoinColumn(name = "START_JOIN_POINT_KKS")
    private JoinPoint startPoint;
    @OneToOne
    @JoinColumn(name = "END_JOIN_POINT_KKS")
    private JoinPoint endPoint;
    @ManyToMany
    @JoinTable(name = "LINES_ROUTES",
            joinColumns = @JoinColumn(name = "ROUTE_KKS"),
            inverseJoinColumns = @JoinColumn(name = "LINE_ID"))
    private List<Route> routesList;
    @Basic
    @Column(name = "TRACED")
    private boolean traced;

    public Line () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setStartPoint(JoinPoint startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(JoinPoint endPoint) {
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

        if (getId() != line.getId()) return false;
        if (isTraced() != line.isTraced()) return false;
        if (getStartPoint() != null ? !getStartPoint().equals(line.getStartPoint()) : line.getStartPoint() != null)
            return false;
        if (getEndPoint() != null ? !getEndPoint().equals(line.getEndPoint()) : line.getEndPoint() != null)
            return false;
        return getRoutesList() != null ? getRoutesList().equals(line.getRoutesList()) : line.getRoutesList() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getStartPoint() != null ? getStartPoint().hashCode() : 0);
        result = 31 * result + (getEndPoint() != null ? getEndPoint().hashCode() : 0);
        result = 31 * result + (getRoutesList() != null ? getRoutesList().hashCode() : 0);
        result = 31 * result + (isTraced() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Line{");
        sb.append("id=").append(id);
        sb.append(", startPoint=").append(startPoint);
        sb.append(", endPoint=").append(endPoint);
        sb.append(", routesList=").append(routesList);
        sb.append(", traced=").append(traced);
        sb.append('}');
        return sb.toString();
    }
}
