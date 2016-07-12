package app.repository.entities.business;

import java.util.ArrayList;
import java.util.List;

public class Route implements INamedByUniqueName {
	private String kksName;
	private String routeType;
	private double length;
	private Double height;
	private int shelvesCount;
	private JoinPoint firstEnd;
	private JoinPoint secondEnd;
	private List<Cable> cablesList = new ArrayList<>();

	public Route(String kksName, String routeType, double length, Double height, int shelvesCount, JoinPoint firstEnd, JoinPoint secondEnd) {
		this.kksName = kksName;
		this.routeType = routeType;
		this.length = length;
		this.height = height;
		this.shelvesCount = shelvesCount;
		this.firstEnd = firstEnd;
		this.secondEnd = secondEnd;
	}

	@Override
	public String getKksName() {
		return kksName;
	}

	public void setKksName(String kksName) {
		this.kksName = kksName;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public int getShelvesCount() {
		return shelvesCount;
	}

	public void setShelvesCount(int shelvesCount) {
		this.shelvesCount = shelvesCount;
	}

	public JoinPoint getFirstEnd() {
		return firstEnd;
	}

	public void setFirstEnd(JoinPoint firstEnd) {
		this.firstEnd = firstEnd;
	}

	public JoinPoint getSecondEnd() {
		return secondEnd;
	}

	public void setSecondEnd(JoinPoint secondEnd) {
		this.secondEnd = secondEnd;
	}

	public List<Cable> getCablesList() {
		return cablesList;
	}

	public void setCablesList(List<Cable> cablesList) {
		this.cablesList = cablesList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Route)) return false;

		Route route = (Route) o;

		if (Double.compare(route.getLength(), getLength()) != 0) return false;
		if (getShelvesCount() != route.getShelvesCount()) return false;
		if (getKksName() != null ? !getKksName().equals(route.getKksName()) : route.getKksName() != null) return false;
		if (getRouteType() != null ? !getRouteType().equals(route.getRouteType()) : route.getRouteType() != null)
			return false;
		if (getHeight() != null ? !getHeight().equals(route.getHeight()) : route.getHeight() != null) return false;
		if (getFirstEnd() != null ? !getFirstEnd().equals(route.getFirstEnd()) : route.getFirstEnd() != null)
			return false;
		if (getSecondEnd() != null ? !getSecondEnd().equals(route.getSecondEnd()) : route.getSecondEnd() != null)
			return false;
		return getCablesList() != null ? getCablesList().equals(route.getCablesList()) : route.getCablesList() == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + (getRouteType() != null ? getRouteType().hashCode() : 0);
		temp = Double.doubleToLongBits(getLength());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (getHeight() != null ? getHeight().hashCode() : 0);
		result = 31 * result + getShelvesCount();
		result = 31 * result + (getFirstEnd() != null ? getFirstEnd().hashCode() : 0);
		result = 31 * result + (getSecondEnd() != null ? getSecondEnd().hashCode() : 0);
		result = 31 * result + (getCablesList() != null ? getCablesList().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Route{");
		sb.append("kksName='").append(kksName).append('\'');
		sb.append(", routeType='").append(routeType).append('\'');
		sb.append(", length=").append(length);
		sb.append(", height=").append(height);
		sb.append(", shelvesCount=").append(shelvesCount);
		sb.append(", firstEnd=").append(firstEnd);
		sb.append(", secondEnd=").append(secondEnd);
		sb.append(", cablesList=").append(cablesList);
		sb.append('}');
		return sb.toString();
	}
}