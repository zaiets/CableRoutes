package model.entities;

import java.util.Arrays;
import java.util.List;

public class Cable extends Line implements INamedByUniqueKKS {
    private String kksName;
    private String journalName;
    private int numberInJournal;
    private String[] cableType;
    private Equipment start;
    private Equipment end;
    private String reserving; // резервирующий кабель (KKS код)
    private int length;

    public Cable(String kksName, String journalName, int numberInJournal, String[] cableType, String reserving, Equipment startEquip,
                 Equipment endEquip, List<Route> routes, int length) {
        super(startEquip.getJoinPoint(), endEquip.getJoinPoint());
        if (routes != null) {
            routes.forEach(r -> r.getCablesList().add(this));
            super.setRoutesList(routes);
        }
        setKksName(kksName);
        setJournalName(journalName);
        setNumberInJournal(numberInJournal);
        setCableType(cableType);
        setStart(startEquip);
        setEnd(endEquip);
        setReserving(reserving);
        setLength(length);
    }

    @Override
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

    public String[] getCableType() {
        return cableType;
    }

    public void setCableType(String[] cableType) {
        this.cableType = cableType;
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

    //For route's cable list print
    public String getBasicInfo() {
        return getKksName() + Arrays.toString(getCableType());
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
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(getCableType(), cable.getCableType())) return false;
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
        result = 31 * result + Arrays.hashCode(getCableType());
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getReserving() != null ? getReserving().hashCode() : 0);
        result = 31 * result + getLength();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cable{");
        sb.append("basicInfo='").append(getBasicInfo()).append('\'');
        sb.append(", kksName='").append(kksName).append('\'');
        sb.append(", journalName='").append(journalName).append('\'');
        sb.append(", numberInJournal=").append(numberInJournal);
        sb.append(", cableType=").append(Arrays.toString(cableType));
        sb.append(", start=").append(start.getEquipmentName());
        sb.append(", end=").append(end.getEquipmentName());
        sb.append(", reserving='").append(reserving).append('\'');
        sb.append(", length=").append(length);
        sb.append(", journal=").append(journalName);
        sb.append('}');
        return sb.toString();
    }
}
