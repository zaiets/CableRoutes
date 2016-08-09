package app.repository.entities.business;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ROUTE_TYPE")
public class RouteType {
    @Basic
    @Column(name = "NAME")
    String name;
    @Column(name = "MARKER", nullable = false)
    CharSequence marker;

    public RouteType () {}


    //TODO delete
    public RouteType(String name, CharSequence type) {
        this.name = name;
        this.marker = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharSequence getMarker() {
        return marker;
    }

    public void setMarker(CharSequence marker) {
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
}
