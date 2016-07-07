package model.entities;

public class RouteType {
	
	String name;
	CharSequence type;

	public RouteType(String name, CharSequence type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CharSequence getType() {
		return type;
	}

	public void setType(CharSequence type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RouteType)) return false;

		RouteType routeType = (RouteType) o;

		if (getName() != null ? !getName().equals(routeType.getName()) : routeType.getName() != null) return false;
		return getType() != null ? getType().equals(routeType.getType()) : routeType.getType() == null;

	}

	@Override
	public int hashCode() {
		int result = getName() != null ? getName().hashCode() : 0;
		result = 31 * result + (getType() != null ? getType().hashCode() : 0);
		return result;
	}
}
