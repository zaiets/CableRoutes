package app.dto.models;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class JournalDto {
	private String kksName;
	private File file;
	private List<CableDto> cables;

	public JournalDto() {}

	public JournalDto(String kksName, File file, List<CableDto> cables) {
		this.kksName = kksName;
		this.file = file;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JournalDto)) return false;

		JournalDto that = (JournalDto) o;

		if (getKksName() != null ? !getKksName().equals(that.getKksName()) : that.getKksName() != null) return false;
		if (getFile() != null ? !getFile().equals(that.getFile()) : that.getFile() != null) return false;
		return getCables() != null ? getCables().equals(that.getCables()) : that.getCables() == null;

	}

	@Override
	public int hashCode() {
		int result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + (getFile() != null ? getFile().hashCode() : 0);
		result = 31 * result + (getCables() != null ? getCables().hashCode() : 0);
		return result;
	}
}
