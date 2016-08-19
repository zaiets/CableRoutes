package app.repository.entities.business;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Entity
@Table(name="CABLE")
public class Cable extends Line implements INamedByUniqueName {
    @Column(name = "KKS", unique = true)
    private String kksName;
    @Basic
    @Column(name = "JOURNAL_KKS", nullable = false)
    private String journalName;
    @Basic
    @Column(name = "NUMBER_IN_JOURNAL")
    private int numberInJournal;
    @Basic
    @Column(name = "TYPE")
    private String cableType;
    @Basic
    @Column(name = "DIMENSIONS")
    private String cableDimensions;
    @OneToOne
    @Lazy
    @JoinColumn(name = "EQUIPMENT_START_NAME")
    private Equipment start;
    @OneToOne
    @Lazy
    @JoinColumn(name = "EQUIPMENT_END_NAME")
    private Equipment end;
    @Basic
    @Column(name = "RESERVING")
    private String reserving;
    @Basic
    @Column(name = "LENGTH")
    private int length;

    public Cable () {    }

    public String getKksName() {
        return kksName;
    }

    public void setKksName(String kksName) {
        this.kksName = kksName;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public int getNumberInJournal() {
        return numberInJournal;
    }

    public void setNumberInJournal(int numberInJournal) {
        this.numberInJournal = numberInJournal;
    }

    public String getCableType() {
        return cableType;
    }

    public void setCableType(String cableType) {
        this.cableType = cableType;
    }

    public String getCableDimensions() {
        return cableDimensions;
    }

    public void setCableDimensions(String cableDimensions) {
        this.cableDimensions = cableDimensions;
    }

    public Equipment getStart() {
        return start;
    }

    public void setStart(Equipment start) {
        this.start = start;
    }

    public Equipment getEnd() {
        return end;
    }

    public void setEnd(Equipment end) {
        this.end = end;
    }

    public String getReserving() {
        return reserving;
    }

    public void setReserving(String reserving) {
        this.reserving = reserving;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cable)) return false;
        if (!super.equals(o)) return false;

        Cable cable = (Cable) o;

        if (getNumberInJournal() != cable.getNumberInJournal()) return false;
        if (getLength() != cable.getLength()) return false;
        if (getKksName() != null ? !getKksName().equals(cable.getKksName()) : cable.getKksName() != null) return false;
        if (getJournalName() != null ? !getJournalName().equals(cable.getJournalName()) : cable.getJournalName() != null)
            return false;
        if (getCableType() != null ? !getCableType().equals(cable.getCableType()) : cable.getCableType() != null)
            return false;
        if (getCableDimensions() != null ? !getCableDimensions().equals(cable.getCableDimensions()) : cable.getCableDimensions() != null)
            return false;
        if (getStart() != null ? !getStart().equals(cable.getStart()) : cable.getStart() != null) return false;
        if (getEnd() != null ? !getEnd().equals(cable.getEnd()) : cable.getEnd() != null) return false;
        return getReserving() != null ? getReserving().equals(cable.getReserving()) : cable.getReserving() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getKksName() != null ? getKksName().hashCode() : 0);
        result = 31 * result + (getJournalName() != null ? getJournalName().hashCode() : 0);
        result = 31 * result + getNumberInJournal();
        result = 31 * result + (getCableType() != null ? getCableType().hashCode() : 0);
        result = 31 * result + (getCableDimensions() != null ? getCableDimensions().hashCode() : 0);
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getReserving() != null ? getReserving().hashCode() : 0);
        result = 31 * result + getLength();
        return result;
    }

    @Override
    public String getUniqueName() {
        return getKksName();
    }
}
