package app.dto.models;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CableDto extends LineDto {
    private String kksName;
    private String journal;
    private Integer numberInJournal;
    private String cableType;
    private String cableDimensions;
    private EquipmentDto startEquipment;
    private EquipmentDto endEquipment;
    private String reserving;
    private Integer length;

    public CableDto() {
    }

    //TODO delete?


    public CableDto(JoinPointDto startPoint, JoinPointDto endPoint, List<RouteDto> routesList, boolean traced, String kksName, String journal, Integer numberInJournal, String cableType, String cableDimensions, EquipmentDto startEquipment, EquipmentDto endEquipment, String reserving, Integer length) {
        super(startPoint, endPoint, routesList, traced);
        this.kksName = kksName;
        this.journal = journal;
        this.numberInJournal = numberInJournal;
        this.cableType = cableType;
        this.cableDimensions = cableDimensions;
        this.startEquipment = startEquipment;
        this.endEquipment = endEquipment;
        this.reserving = reserving;
        this.length = length;
    }

    public String getKksName() {
        return kksName;
    }

    public void setKksName(String kksName) {
        this.kksName = kksName;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Integer getNumberInJournal() {
        return numberInJournal;
    }

    public void setNumberInJournal(Integer numberInJournal) {
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

    public EquipmentDto getStartEquipment() {
        return startEquipment;
    }

    public void setStartEquipment(EquipmentDto startEquipment) {
        this.startEquipment = startEquipment;
    }

    public EquipmentDto getEndEquipment() {
        return endEquipment;
    }

    public void setEndEquipment(EquipmentDto endEquipment) {
        this.endEquipment = endEquipment;
    }

    public String getReserving() {
        return reserving;
    }

    public void setReserving(String reserving) {
        this.reserving = reserving;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CableDto)) return false;
        if (!super.equals(o)) return false;

        CableDto cableDto = (CableDto) o;

        if (getKksName() != null ? !getKksName().equals(cableDto.getKksName()) : cableDto.getKksName() != null)
            return false;
        if (getJournal() != null ? !getJournal().equals(cableDto.getJournal()) : cableDto.getJournal() != null)
            return false;
        if (getNumberInJournal() != null ? !getNumberInJournal().equals(cableDto.getNumberInJournal()) : cableDto.getNumberInJournal() != null)
            return false;
        if (getCableType() != null ? !getCableType().equals(cableDto.getCableType()) : cableDto.getCableType() != null)
            return false;
        if (getCableDimensions() != null ? !getCableDimensions().equals(cableDto.getCableDimensions()) : cableDto.getCableDimensions() != null)
            return false;
        if (getStartEquipment() != null ? !getStartEquipment().equals(cableDto.getStartEquipment()) : cableDto.getStartEquipment() != null)
            return false;
        if (getEndEquipment() != null ? !getEndEquipment().equals(cableDto.getEndEquipment()) : cableDto.getEndEquipment() != null)
            return false;
        if (getReserving() != null ? !getReserving().equals(cableDto.getReserving()) : cableDto.getReserving() != null)
            return false;
        return getLength() != null ? getLength().equals(cableDto.getLength()) : cableDto.getLength() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getKksName() != null ? getKksName().hashCode() : 0);
        result = 31 * result + (getJournal() != null ? getJournal().hashCode() : 0);
        result = 31 * result + (getNumberInJournal() != null ? getNumberInJournal().hashCode() : 0);
        result = 31 * result + (getCableType() != null ? getCableType().hashCode() : 0);
        result = 31 * result + (getCableDimensions() != null ? getCableDimensions().hashCode() : 0);
        result = 31 * result + (getStartEquipment() != null ? getStartEquipment().hashCode() : 0);
        result = 31 * result + (getEndEquipment() != null ? getEndEquipment().hashCode() : 0);
        result = 31 * result + (getReserving() != null ? getReserving().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        return result;
    }
}
