package app.dto.models;

import org.springframework.stereotype.Component;

@Component
public class RouteDto {
	private String kksName;
	private RouteTypeDto routeType;
	private Double length;
	private Double height;
	private Integer shelvesCount;
	private JoinPointDto firstEnd;
	private JoinPointDto secondEnd;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RouteDto)) return false;

		RouteDto routeDto = (RouteDto) o;

		if (getKksName() != null ? !getKksName().equals(routeDto.getKksName()) : routeDto.getKksName() != null)
			return false;
		if (getRouteType() != null ? !getRouteType().equals(routeDto.getRouteType()) : routeDto.getRouteType() != null)
			return false;
		if (getLength() != null ? !getLength().equals(routeDto.getLength()) : routeDto.getLength() != null)
			return false;
		if (getHeight() != null ? !getHeight().equals(routeDto.getHeight()) : routeDto.getHeight() != null)
			return false;
		if (getShelvesCount() != null ? !getShelvesCount().equals(routeDto.getShelvesCount()) : routeDto.getShelvesCount() != null)
			return false;
		if (getFirstEnd() != null ? !getFirstEnd().equals(routeDto.getFirstEnd()) : routeDto.getFirstEnd() != null)
			return false;
		return getSecondEnd() != null ? getSecondEnd().equals(routeDto.getSecondEnd()) : routeDto.getSecondEnd() == null;

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
		return result;
	}
}
