package app.service.business;

import app.dto.models.CableDto;
import app.dto.models.EquipmentDto;
import app.dto.models.JoinPointDto;
import app.dto.models.JournalDto;

import java.util.List;


public interface ICableService extends IService<CableDto, String> {

	boolean update(String kks, CableDto cableDto);

	CableDto read(String kks);

	List<CableDto> readAllByTwoEquipments(EquipmentDto eq1, EquipmentDto eq2);

	List<CableDto> readAllByEquipment(EquipmentDto eq);

	List<CableDto> readAllByJournal(JournalDto jou);

	List<CableDto> readAllByJoinPoint(JoinPointDto point);

	List<CableDto> readAllByTwoJoinPoints(JoinPointDto start, JoinPointDto end);

}

