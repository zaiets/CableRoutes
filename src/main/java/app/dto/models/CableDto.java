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
    private EquipmentDto start;
    private EquipmentDto end;
    private String reserving;
    private Integer length;

    public CableDto() {
    }

    public CableDto(String kksName, String journal, Integer numberInJournal, String cableType, String cableDimensions,
                    String reserving, EquipmentDto start, EquipmentDto end, Integer length,
                    List<RouteDto> routesList, boolean traced) {
        super(start.getJoinPoint(), end.getJoinPoint(), routesList, traced);
        this.kksName = kksName;
        this.journal = journal;
        this.numberInJournal = numberInJournal;
        this.cableType = cableType;
        this.cableDimensions = cableDimensions;
        this.start = start;
        this.end = end;
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

    public EquipmentDto getStart() {
        return start;
    }

    public void setStart(EquipmentDto start) {
        this.start = start;
    }

    public EquipmentDto getEnd() {
        return end;
    }

    public void setEnd(EquipmentDto end) {
        this.end = end;
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
        if (getStart() != null ? !getStart().equals(cableDto.getStart()) : cableDto.getStart() != null)
            return false;
        if (getEnd() != null ? !getEnd().equals(cableDto.getEnd()) : cableDto.getEnd() != null)
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
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getReserving() != null ? getReserving().hashCode() : 0);
        result = 31 * result + (getLength() != null ? getLength().hashCode() : 0);
        return result;
    }
}
