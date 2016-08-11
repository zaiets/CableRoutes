package app.repository.entities.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="JOIN_POINT")
public class JoinPoint implements INamedByUniqueName {
	@Column(name = "KKS", unique = true)
	private String kksName;
	@Column(name = "X")
	private double x;
	@Column(name = "Y")
	private double y;
	@Column(name = "Z")
	private double z;

	public JoinPoint(){}

	//TODO delete
	public JoinPoint(String kksName, double [] xyz) {
		setKksName(kksName);
		setX(xyz[0]);
		setY(xyz[1]);
		setZ(xyz[2]);
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
		if (!(o instanceof JoinPoint)) return false;

		JoinPoint joinPoint = (JoinPoint) o;

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
		final StringBuilder sb = new StringBuilder("JoinPoint{");
		sb.append("kksName='").append(kksName).append('\'');
		sb.append(", x=").append(x);
		sb.append(", y=").append(y);
		sb.append(", z=").append(z);
		sb.append('}');
		return sb.toString();
	}
}
