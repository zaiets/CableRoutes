package app.repository.entities.business;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name="EQUIPMENT")
public class Equipment implements INamedByUniqueName {
	/**
	 * This field <b>must</b> contain correct equipment fullName from current one cable journal.
	 * This field is unique identification of current equipment in current project (instead of commonKks)
	 */
	@Column(name = "FULL_NAME", unique = true)
	private String fullName;
	/**
	 * You can have dozens of equipments in one project named by the same commonKks
	 */
	@Column(name = "KKS", unique = false)
	private String commonKks;
	@Basic
	@Column(name = "ADD_LENGTH")
	private int cableConnectionAddLength = 0;
	@Basic
	@Column(name = "XYZ")
	private Double[] xyz;
	@ManyToOne
	@JoinColumn(name = "JOIN_POINT_KKS")
	private JoinPoint joinPoint;

	public Equipment (){}

	//TODO delete
	public Equipment(String commonKks, String fullName, int cableConnectionAddLength, Double[] xyz, JoinPoint joinPoint) {
		setCommonKks(commonKks);
		setFullName(fullName);
		setCableConnectionAddLength(cableConnectionAddLength);
		setXyz(xyz);
		setJoinPoint(joinPoint);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getCableConnectionAddLength() {
		return cableConnectionAddLength;
	}

	public void setCableConnectionAddLength(int cableConnectionAddLength) {
		this.cableConnectionAddLength = cableConnectionAddLength;
	}

	public String getCommonKks() {
		return commonKks;
	}

	public void setCommonKks(String commonKks) {
		this.commonKks = commonKks;
	}

	public Double[] getXyz() {
		return xyz;
	}

	public void setXyz(Double[] xyz) {
		this.xyz = xyz;
	}

	public JoinPoint getJoinPoint() {
		return joinPoint;
	}

	public void setJoinPoint(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Equipment)) return false;

		Equipment equipment = (Equipment) o;

		if (getCableConnectionAddLength() != equipment.getCableConnectionAddLength()) return false;
		if (getCommonKks() != null ? !getCommonKks().equals(equipment.getCommonKks()) : equipment.getCommonKks() != null)
			return false;
		if (getFullName() != null ? !getFullName().equals(equipment.getFullName()) : equipment.getFullName() != null)
			return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		if (!Arrays.equals(getXyz(), equipment.getXyz())) return false;
		return getJoinPoint() != null ? getJoinPoint().equals(equipment.getJoinPoint()) : equipment.getJoinPoint() == null;

	}

	@Override
	public int hashCode() {
		int result = getCommonKks() != null ? getCommonKks().hashCode() : 0;
		result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
		result = 31 * result + getCableConnectionAddLength();
		result = 31 * result + Arrays.hashCode(getXyz());
		result = 31 * result + (getJoinPoint() != null ? getJoinPoint().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Equipment{");
		sb.append("commonKks='").append(getCommonKks()).append('\'');
		sb.append(", fullName='").append(getFullName()).append('\'');
		sb.append(", cableConnectionAddLength=").append(getCableConnectionAddLength());
		sb.append(", xyz=").append(Arrays.toString(getXyz()));
		sb.append(", joinPoint=").append(getJoinPoint());
		sb.append('}');
		return sb.toString();
	}
}
