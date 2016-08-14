package app.repository.entities.business;

import javax.persistence.*;

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
	@Column(name = "X")
	private Double x;
	@Basic
	@Column(name = "Y")
	private Double y;
	@Basic
	@Column(name = "Z")
	private Double z;
	@JoinColumn(name = "JOIN_POINT_KKS")
	private JoinPoint joinPoint;

	public Equipment (){}

	//TODO delete
	public Equipment(String commonKks, String fullName, int cableConnectionAddLength, Double[] xyz, JoinPoint joinPoint) {
		setCommonKks(commonKks);
		setFullName(fullName);
		setCableConnectionAddLength(cableConnectionAddLength);
		setX(xyz[0]);
		setY(xyz[1]);
		setZ(xyz[2]);
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

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	public JoinPoint getJoinPoint() {
		return joinPoint;
	}

	public void setJoinPoint(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}

	@Override
	public String getUniqueName() {
		return getFullName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Equipment)) return false;

		Equipment equipment = (Equipment) o;

		if (getCableConnectionAddLength() != equipment.getCableConnectionAddLength()) return false;
		if (getFullName() != null ? !getFullName().equals(equipment.getFullName()) : equipment.getFullName() != null)
			return false;
		if (getCommonKks() != null ? !getCommonKks().equals(equipment.getCommonKks()) : equipment.getCommonKks() != null)
			return false;
		if (getX() != null ? !getX().equals(equipment.getX()) : equipment.getX() != null) return false;
		if (getY() != null ? !getY().equals(equipment.getY()) : equipment.getY() != null) return false;
		if (getZ() != null ? !getZ().equals(equipment.getZ()) : equipment.getZ() != null) return false;
		return getJoinPoint() != null ? getJoinPoint().equals(equipment.getJoinPoint()) : equipment.getJoinPoint() == null;

	}

	@Override
	public int hashCode() {
		int result = getFullName() != null ? getFullName().hashCode() : 0;
		result = 31 * result + (getCommonKks() != null ? getCommonKks().hashCode() : 0);
		result = 31 * result + getCableConnectionAddLength();
		result = 31 * result + (getX() != null ? getX().hashCode() : 0);
		result = 31 * result + (getY() != null ? getY().hashCode() : 0);
		result = 31 * result + (getZ() != null ? getZ().hashCode() : 0);
		result = 31 * result + (getJoinPoint() != null ? getJoinPoint().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Equipment{");
		sb.append("fullName='").append(fullName).append('\'');
		sb.append(", commonKks='").append(commonKks).append('\'');
		sb.append(", cableConnectionAddLength=").append(cableConnectionAddLength);
		sb.append(", x=").append(x);
		sb.append(", y=").append(y);
		sb.append(", z=").append(z);
		sb.append(", joinPoint=").append(joinPoint);
		sb.append(", uniqueName='").append(getUniqueName()).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
