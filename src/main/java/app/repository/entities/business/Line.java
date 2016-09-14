package app.repository.entities.business;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="LINE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = true)
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JoinPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(JoinPoint startPoint) {
        this.startPoint = startPoint;
    }

    public JoinPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(JoinPoint endPoint) {
        this.endPoint = endPoint;
    }

    public List<Route> getRoutesList() {
        return routesList;
    }

    public void setRoutesList(List<Route> routesList) {
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
        if (!(o instanceof Line)) return false;

        Line line = (Line) o;

        if (isTraced() != line.isTraced()) return false;
        if (getId() != null ? !getId().equals(line.getId()) : line.getId() != null) return false;
        if (getStartPoint() != null ? !getStartPoint().equals(line.getStartPoint()) : line.getStartPoint() != null)
            return false;
        if (getEndPoint() != null ? !getEndPoint().equals(line.getEndPoint()) : line.getEndPoint() != null)
            return false;
        return getRoutesList() != null ? getRoutesList().equals(line.getRoutesList()) : line.getRoutesList() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStartPoint() != null ? getStartPoint().hashCode() : 0);
        result = 31 * result + (getEndPoint() != null ? getEndPoint().hashCode() : 0);
        result = 31 * result + (getRoutesList() != null ? getRoutesList().hashCode() : 0);
        result = 31 * result + (isTraced() ? 1 : 0);
        return result;
    }
}
