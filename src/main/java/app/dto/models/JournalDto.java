package app.dto.models;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JournalDto {
	private String kksName;
	private String fileName;
	private List<CableDto> cables;

	public JournalDto() {}

	//TODO delete?

	public JournalDto(String kksName, String fileName, List<CableDto> cables) {
		this.kksName = kksName;
		this.fileName = fileName;
		this.cables = cables;
	}

	public List<CableDto> getCables() {
		return cables;
	}

	public void setCables(List<CableDto> cables) {
		this.cables = cables;
	}

	public String getKksName() {
		return kksName;
	}

	public void setKksName(String kksName) {
		this.kksName = kksName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JournalDto)) return false;

		JournalDto that = (JournalDto) o;

		if (getKksName() != null ? !getKksName().equals(that.getKksName()) : that.getKksName() != null) return false;
		if (getFileName() != null ? !getFileName().equals(that.getFileName()) : that.getFileName() != null)
			return false;
		return getCables() != null ? getCables().equals(that.getCables()) : that.getCables() == null;

	}

	@Override
	public int hashCode() {
		int result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
		result = 31 * result + (getCables() != null ? getCables().hashCode() : 0);
		return result;
	}
}
