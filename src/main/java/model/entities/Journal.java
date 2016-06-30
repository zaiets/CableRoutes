package model.entities;

import java.util.List;

public class Journal implements INamedByUniqueKKS {
	private String fileName;
	private String kksName;
	private List<Cable> cables;
	
	public Journal (String fullFileName, String journalName, List<Cable> cables) {
		setFileName(fullFileName);
		setKksName(journalName);
		setCables(cables);		
	}

	public String getFileName() {
		return fileName;
	}

	public String getKksName() {
		return kksName;
	}

	public List<Cable> getCables() {
		return cables;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setKksName(String kksName) {
		this.kksName = kksName;
	}
	
	public void setCables(List<Cable> cables) {
		this.cables = cables;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nJournal [File:");
		builder.append(getFileName());
		builder.append(", Journal kksName: ");
		builder.append(getKksName());
		if (getCables() != null) {
			builder.append(", cables in it: {");
			for (Cable cab : getCables()) {
				builder.append(cab.toString());
			}
			builder.append("}");
		} else {
			builder.append(", No cables");
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Journal)) return false;

		Journal journal = (Journal) o;

		if (getFileName() != null ? !getFileName().equals(journal.getFileName()) : journal.getFileName() != null)
			return false;
		if (getKksName() != null ? !getKksName().equals(journal.getKksName()) : journal.getKksName() != null) return false;
		return getCables() != null ? getCables().equals(journal.getCables()) : journal.getCables() == null;

	}

	@Override
	public int hashCode() {
		int result = getFileName() != null ? getFileName().hashCode() : 0;
		result = 31 * result + (getKksName() != null ? getKksName().hashCode() : 0);
		result = 31 * result + (getCables() != null ? getCables().hashCode() : 0);
		return result;
	}
}
