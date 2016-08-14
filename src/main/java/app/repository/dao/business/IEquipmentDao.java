package app.repository.dao.business;

import app.repository.entities.business.Equipment;
import app.repository.entities.business.JoinPoint;

import java.util.List;


public interface IEquipmentDao extends IDao<Equipment> {

	List<Equipment> readAllByKks(String kks);

	List<Equipment> readAllByXyz(Double x, Double y, Double z, boolean includeNull);

	List<Equipment> readAllByJoinPoint(JoinPoint joinPoint);

}

