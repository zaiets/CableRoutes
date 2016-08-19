package app.repository.entities.business;

import javax.persistence.*;

@Entity
@Table(name = "ROUTE_TYPE")
public class RouteType implements INamedByUniqueName {
    @Id
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "MARKER", nullable = false, unique = true)
    private String marker;

    public RouteType () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteType)) return false;

        RouteType routeType = (RouteType) o;

        if (getName() != null ? !getName().equals(routeType.getName()) : routeType.getName() != null) return false;
        return getMarker() != null ? getMarker().equals(routeType.getMarker()) : routeType.getMarker() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getMarker() != null ? getMarker().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RouteType{");
        sb.append("name='").append(name).append('\'');
        sb.append(", marker='").append(marker).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String getUniqueName() {
        return getMarker();
    }
}
