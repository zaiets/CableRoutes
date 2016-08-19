package app.repository.entities.business;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name="JOURNAL")
public class Journal implements INamedByUniqueName {
	@Id
	@Column(name = "KKS", unique = true)
	private String kksName;
	@Basic
	@Column(name = "FILE")
	private File file;

	public Journal () {}

	public File getFile() {
		return file;
	}

	public String getKksName() {
		return kksName;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void setKksName(String kksName) {
		this.kksName = kksName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Journal)) return false;

		Journal journal = (Journal) o;

		if (getKksName() != null ? !getKksName().equals(journal.getKksName()) : journal.getKksName() != null)
			return false;
		return getFile() != null ? getFile().equals(journal.getFile()) : journal.getFile() == null;

	}

	@Override
	public int hashCode() {
		int result = getKksName() != null ? getKksName().hashCode() : 0;
		result = 31 * result + (getFile() != null ? getFile().hashCode() : 0);
		return result;
	}

	@Override
	public String getUniqueName() {
		return getKksName();
	}
}
