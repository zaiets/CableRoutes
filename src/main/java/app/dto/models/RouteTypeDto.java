package app.dto.models;

import org.springframework.stereotype.Component;

@Component
public class RouteTypeDto {
    private String name;
    private String marker;

    public RouteTypeDto() {}

    //TODO delete?
    public RouteTypeDto(String name, String type) {
        this.name = name;
        this.marker = type;
    }

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
        if (!(o instanceof RouteTypeDto)) return false;

        RouteTypeDto that = (RouteTypeDto) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getMarker() != null ? getMarker().equals(that.getMarker()) : that.getMarker() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getMarker() != null ? getMarker().hashCode() : 0);
        return result;
    }
}
