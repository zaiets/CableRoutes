package app.service.business;

import app.dto.models.EquipmentDto;
import app.dto.models.JoinPointDto;

import java.util.List;


public interface IEquipmentService extends IService<EquipmentDto, String> {

	List<EquipmentDto> readAllByKks(String kks);

	List<EquipmentDto> readAllByXyz(Double x, Double y, Double z, boolean includeNullValues);

	List<EquipmentDto> readAllByJoinPoint(JoinPointDto point);

}

