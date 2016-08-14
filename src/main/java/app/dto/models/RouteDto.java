package app.dto.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RouteDto {
	private String kksName;
	private RouteTypeDto routeType;
	private Double length;
	private Double height;
	private Integer shelvesCount;
	private JoinPointDto firstEnd;
	private JoinPointDto secondEnd;
	private List<CableDto> cablesList = new ArrayList<>();

	public RouteDto() {	}

	//TODO delete?
	public RouteDto(String kksName, RouteTypeDto routeType, Double length, Double height, Integer shelvesCount, JoinPointDto firstEnd, JoinPointDto secondEnd) {
		this.kksName = kksName;
		this.routeType = routeType;
		this.length = length;
		this.height = height;
		this.shelvesCount = shelvesCount;
		this.firstEnd = firstEnd;
		this.secondEnd = secondEnd;
	}

	public String getKksName() {
		return kksName;
	}

	public void setKksName(String kksName) {
		this.kksName = kksName;
	}

	public RouteTypeDto getRouteType() {
		return routeType;
	}

	public void setRouteType(RouteTypeDto routeType) {
		this.routeType = routeType;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Integer getShelvesCount() {
		return shelvesCount;
	}

	public void setShelvesCount(Integer shelvesCount) {
		this.shelvesCount = shelvesCount;
	}

	public JoinPointDto getFirstEnd() {
		return firstEnd;
	}

	public void setFirstEnd(JoinPointDto firstEnd) {
		this.firstEnd = firstEnd;
	}

	public JoinPointDto getSecondEnd() {
		return secondEnd;
	}

	public void setSecondEnd(JoinPointDto secondEnd) {
		this.secondEnd = secondEnd;
	}

	public List<CableDto> getCablesList() {
		return cablesList;
	}

	public void setCablesList(List<CableDto> cablesList) {
		this.cablesList = cablesList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RouteDto)) return false;

		RouteDto route = (RouteDto) o;

		if (getKksName() != null ? !getKksName().equals(route.getKksName()) : route.getKksName() != null) return false;
		if (getRouteType() != null ? !getRouteType().equals(route.getRouteType()) : route.getRouteType() != null)
			return false;
		if (getLength() != null ? !getLength().equals(route.getLength()) : route.getLength() != null) return false;
		if (getHeight() != null ? !getHeight().equals(route.getHeight()) : route.getHeight() != null) return false;
		if (getShelvesCount() != null ? !getShelvesCount().equals(route.getShelvesCount()) : route.getShelvesCount() != null)
			return false;
		if (getFirstEnd() != null ? !getFirstEnd().equals(route.getFirstEnd()) : route.getFirstEnd() != null)
			return false;
		if (getSecondEnd() != null ? !getSecondEnd().equals(route.getSecondEnd()) : route.getSecondEnd() != null)
			return false;
		return getCablesList() != null ? getCablesList().equals(route.getCablesList()) : route.getCablesList() == null;

	}

	@Override
	public int hashCode() {
		int result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + (getRouteType() != null ? getRouteType().hashCode() : 0);
		result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
		result = 31 * result + (getHeight() != null ? getHeight().hashCode() : 0);
		result = 31 * result + (getShelvesCount() != null ? getShelvesCount().hashCode() : 0);
		result = 31 * result + (getFirstEnd() != null ? getFirstEnd().hashCode() : 0);
		result = 31 * result + (getSecondEnd() != null ? getSecondEnd().hashCode() : 0);
		result = 31 * result + (getCablesList() != null ? getCablesList().hashCode() : 0);
		return result;
	}
}
