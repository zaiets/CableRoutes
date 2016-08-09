package app.repository.entities.business;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cable extends Line implements INamedByUniqueName {
    @Column(name = "KKS", unique = true)
    private String kksName;
    @ManyToOne
    @JoinColumn(name = "JOURNAL_KKS", nullable = false)
    private Journal journal;
    @Basic
    @Column(name = "NUMBER_IN_JOURNAL")
    private int numberInJournal;
    @Basic
    @Column(name = "TYPE")
    private String cableType;
    @Basic
    @Column(name = "DIMENSIONS")
    private String cableDimensions;
    @JoinColumn(name = "EQUIPMENT_START_NAME")
    private Equipment start;
    @JoinColumn(name = "EQUIPMENT_START_NAME")
    private Equipment end;
    @Basic
    @Column(name = "RESERVING")
    private String reserving;
    @Basic
    @Column(name = "LENGTH")
    private int length;

    public Cable () {    }

    //TODO delete
    public Cable(String kksName, Journal journal, int numberInJournal, String[] cableType, String reserving, Equipment startEquip,
                 Equipment endEquip, List<Route> routes, int length) {
        super(startEquip.getJoinPoint(), endEquip.getJoinPoint());
        if (routes != null) {
            routes.forEach(r -> r.getCablesList().add(this));
            super.setRoutesList(routes);
        }
        setKksName(kksName);
        setJournal(journal);
        setNumberInJournal(numberInJournal);
        setCableType(cableType[0]);
        setCableDimensions(cableType[1]);
        setStart(startEquip);
        setEnd(endEquip);
        setReserving(reserving);
        setLength(length);
    }

    public String getCommonKks() {
        return kksName;
    }

    public void setKksName(String kksName) {
        this.kksName = kksName;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
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



}
