package app.repository.entities.business;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Table(name="JOURNAL")
public class Journal implements INamedByUniqueName {
	@Column(name = "KKS", unique = true)
	private String kksName;
	@Basic
	@Column(name = "FULL_NAME")
	private File file;
	@OneToMany(mappedBy = "cable")
	private List<Cable> cables;

	public Journal () {}

	public File getFile() {
		return file;
	}

	public String getKksName() {
		return kksName;
	}

	public List<Cable> getCables() {
		return cables;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void setKksName(String kksName) {
		this.kksName = kksName;
	}
	
	public void setCables(List<Cable> cables) {
		this.cables = cables;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Journal)) return false;

		Journal journal = (Journal) o;

		if (getKksName() != null ? !getKksName().equals(journal.getKksName()) : journal.getKksName() != null)
			return false;
		if (getFile() != null ? !getFile().equals(journal.getFile()) : journal.getFile() != null) return false;
		return getCables() != null ? getCables().equals(journal.getCables()) : journal.getCables() == null;

	}

	@Override
	public int hashCode() {
		int result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + (getFile() != null ? getFile().hashCode() : 0);
		result = 31 * result + (getCables() != null ? getCables().hashCode() : 0);
		return result;
	}

	@Override
	public String getUniqueName() {
		return getKksName();
	}
}
