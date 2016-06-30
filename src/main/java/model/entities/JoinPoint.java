package model.entities;

import java.util.Arrays;

public class JoinPoint implements INamedByUniqueKKS {
	private String kksName;
	private double [] xyz;
	
	public JoinPoint(String kksName, double [] xyz) {
		setKksName(kksName);
		setXyz(xyz);
	}

	public String getKksName() {
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

		if (getKksName() != null ? !getKksName().equals(that.getKksName()) : that.getKksName() != null) return false;
		return Arrays.equals(getXyz(), that.getXyz());

	}

	@Override
	public int hashCode() {
		int result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + Arrays.hashCode(getXyz());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nJoinPoint [Name: ");
		builder.append(getKksName());
		builder.append(", X-Y-Z: ");
		builder.append(Arrays.toString(getXyz()));
		builder.append("]");
		return builder.toString();
	}
}
