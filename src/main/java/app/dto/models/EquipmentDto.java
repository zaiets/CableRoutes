package app.dto.models;

import org.springframework.stereotype.Component;

@Component
public class EquipmentDto {
	private String fullName;
	private String commonKks;
	private Integer cableConnectionAddLength = 0;
	private Double x;
	private Double y;
	private Double z;
	private JoinPointDto joinPoint;

	public EquipmentDto(){}

	public EquipmentDto(String fullName, String commonKks, Integer cableConnectionAddLength,
						Double x, Double y, Double z, JoinPointDto joinPoint) {
		this.fullName = fullName;
		this.commonKks = commonKks;
		this.cableConnectionAddLength = cableConnectionAddLength;
		this.x = x;
		this.y = y;
		this.z = z;
		this.joinPoint = joinPoint;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCommonKks() {
		return commonKks;
	}

	public void setCommonKks(String commonKks) {
		this.commonKks = commonKks;
	}

	public Integer getCableConnectionAddLength() {
		return cableConnectionAddLength;
	}

	public void setCableConnectionAddLength(Integer cableConnectionAddLength) {
		this.cableConnectionAddLength = cableConnectionAddLength;
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

	public JoinPointDto getJoinPoint() {
		return joinPoint;
	}

	public void setJoinPoint(JoinPointDto joinPoint) {
		this.joinPoint = joinPoint;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EquipmentDto)) return false;

		EquipmentDto that = (EquipmentDto) o;

		if (getFullName() != null ? !getFullName().equals(that.getFullName()) : that.getFullName() != null)
			return false;
		if (getCommonKks() != null ? !getCommonKks().equals(that.getCommonKks()) : that.getCommonKks() != null)
			return false;
		if (getCableConnectionAddLength() != null ? !getCableConnectionAddLength().equals(that.getCableConnectionAddLength()) : that.getCableConnectionAddLength() != null)
			return false;
		if (getX() != null ? !getX().equals(that.getX()) : that.getX() != null) return false;
		if (getY() != null ? !getY().equals(that.getY()) : that.getY() != null) return false;
		if (getZ() != null ? !getZ().equals(that.getZ()) : that.getZ() != null) return false;
		return getJoinPoint() != null ? getJoinPoint().equals(that.getJoinPoint()) : that.getJoinPoint() == null;

	}

	@Override
	public int hashCode() {
		int result = getFullName() != null ? getFullName().hashCode() : 0;
		result = 31 * result + (getCommonKks() != null ? getCommonKks().hashCode() : 0);
		result = 31 * result + (getCableConnectionAddLength() != null ? getCableConnectionAddLength().hashCode() : 0);
		result = 31 * result + (getX() != null ? getX().hashCode() : 0);
		result = 31 * result + (getY() != null ? getY().hashCode() : 0);
		result = 31 * result + (getZ() != null ? getZ().hashCode() : 0);
		result = 31 * result + (getJoinPoint() != null ? getJoinPoint().hashCode() : 0);
		return result;
	}
}
