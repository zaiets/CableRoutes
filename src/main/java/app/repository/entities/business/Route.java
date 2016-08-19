package app.repository.entities.business;

import javax.persistence.*;

@Entity
@Table(name="ROUTE")
public class Route implements INamedByUniqueName {
	@Id
	@Column(name = "KKS", unique = true)
	private String kksName;
	@JoinColumn(name = "ROUTE_TYPE_MARKER")
	private RouteType routeType;
	@Basic
	@Column(name = "LENGTH")
	private double length;
	@Basic
	@Column(name = "HEIGHT")
	private Double height;
	@Basic
	@Column(name = "SHELVES_COUNT")
	private int shelvesCount;
	@JoinColumn(name = "FIRST_JOIN_POINT_KKS", nullable=false)
	private JoinPoint firstEnd;
	@JoinColumn(name = "SECOND_JOIN_POINT_KKS", nullable=false)
	private JoinPoint secondEnd;

	public Route() {	}

	public String getKksName() {
		return kksName;
	}

	public void setKksName(String kksName) {
		this.kksName = kksName;
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public void setRouteType(RouteType routeType) {
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
		return getSecondEnd() != null ? getSecondEnd().equals(route.getSecondEnd()) : route.getSecondEnd() == null;

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
		return result;
	}

	@Override
	public String getUniqueName() {
		return getKksName();
	}
}
