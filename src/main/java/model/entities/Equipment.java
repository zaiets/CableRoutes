package model.entities;

import java.util.Arrays;

public class Equipment implements INamedByUniqueName {
	private String kksName;
	private String equipmentName; // наименование оборудования (название как написано в журнале)
	private int cableConnectionAddLength = 0; //запас кабеля для подключения
	private Double[] xyz;
	private JoinPoint joinPoint;

	public Equipment(String kksName, String equipmentName, int cableConnectionAddLength, Double[] xyz, JoinPoint joinPoint) {
		setKksName(kksName);
		setEquipmentName(equipmentName);
		setCableConnectionAddLength(cableConnectionAddLength);
		setXyz(xyz);
		setJoinPoint(joinPoint);
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public int getCableConnectionAddLength() {
		return cableConnectionAddLength;
	}

	public void setCableConnectionAddLength(int cableConnectionAddLength) {
		this.cableConnectionAddLength = cableConnectionAddLength;
	}

	@Override
	public String getKksName() {
		return kksName;
	}

	public void setKksName(String kksName) {
		this.kksName = kksName;
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
		if (getKksName() != null ? !getKksName().equals(equipment.getKksName()) : equipment.getKksName() != null)
			return false;
		if (getEquipmentName() != null ? !getEquipmentName().equals(equipment.getEquipmentName()) : equipment.getEquipmentName() != null)
			return false;
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		if (!Arrays.equals(getXyz(), equipment.getXyz())) return false;
		return getJoinPoint() != null ? getJoinPoint().equals(equipment.getJoinPoint()) : equipment.getJoinPoint() == null;

	}

	@Override
	public int hashCode() {
		int result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + (getEquipmentName() != null ? getEquipmentName().hashCode() : 0);
		result = 31 * result + getCableConnectionAddLength();
		result = 31 * result + Arrays.hashCode(getXyz());
		result = 31 * result + (getJoinPoint() != null ? getJoinPoint().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Equipment{");
		sb.append("kksName='").append(getKksName()).append('\'');
		sb.append(", equipmentName='").append(getEquipmentName()).append('\'');
		sb.append(", cableConnectionAddLength=").append(getCableConnectionAddLength());
		sb.append(", xyz=").append(Arrays.toString(getXyz()));
		sb.append(", joinPoint=").append(getJoinPoint());
		sb.append('}');
		return sb.toString();
	}
}
