package app.dto.models;

import org.springframework.stereotype.Component;

@Component
public class JoinPointDto {
	private String kksName;
	private double x;
	private double y;
	private double z;

	public JoinPointDto(){}

	public JoinPointDto(String kksName, double x, double y, double z) {
		setKksName(kksName);
		setX(x);
		setY(y);
		setZ(z);
	}

	public void setKksName(String kksName) {
		this.kksName = kksName;
	}

	public String getKksName() {
		return kksName;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JoinPointDto)) return false;

		JoinPointDto joinPoint = (JoinPointDto) o;

		if (Double.compare(joinPoint.getX(), getX()) != 0) return false;
		if (Double.compare(joinPoint.getY(), getY()) != 0) return false;
		if (Double.compare(joinPoint.getZ(), getZ()) != 0) return false;
		return getKksName() != null ? getKksName().equals(joinPoint.getKksName()) : joinPoint.getKksName() == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = getKksName() != null ? getKksName().hashCode() : 0;
		temp = Double.doubleToLongBits(getX());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getY());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getZ());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("JoinPointDto{");
		sb.append("kksName='").append(kksName).append('\'');
		sb.append(", x=").append(x);
		sb.append(", y=").append(y);
		sb.append(", z=").append(z);
		sb.append('}');
		return sb.toString();
	}
}
