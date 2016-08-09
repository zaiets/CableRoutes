package app.repository.entities.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;

@Entity
@Table(name="JOIN_POINT")
public class JoinPoint implements INamedByUniqueName {
	@Column(name = "KKS", unique = true)
	private String kksName;
	@Column(name = "XYZ")
	private double [] xyz;

	public JoinPoint(){}

	//TODO delete
	public JoinPoint(String kksName, double [] xyz) {
		setKksName(kksName);
		setXyz(xyz);
	}

	public String getCommonKks() {
		return kksName;
	}

	public void setKksName(String kksName) {
		this.kksName = kksName;
	}

	public void setXyz(double [] xyz) {
		this.xyz = xyz;
	}

	public double[] getXyz() {
		return xyz;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JoinPoint)) return false;

		JoinPoint that = (JoinPoint) o;

		if (getCommonKks() != null ? !getCommonKks().equals(that.getCommonKks()) : that.getCommonKks() != null) return false;
		return Arrays.equals(getXyz(), that.getXyz());

	}

	@Override
	public int hashCode() {
		int result = getCommonKks() != null ? getCommonKks().hashCode() : 0;
		result = 31 * result + Arrays.hashCode(getXyz());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nJoinPoint [Name: ");
		builder.append(getCommonKks());
		builder.append(", X-Y-Z: ");
		builder.append(Arrays.toString(getXyz()));
		builder.append("]");
		return builder.toString();
	}
}
