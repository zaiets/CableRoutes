package app.repository.dao.business;

import app.repository.entities.business.Equipment;
import app.repository.entities.business.JoinPoint;

import java.util.List;


interface EquipmentDao extends IDao<Equipment> {

	List<Equipment> readAllByFullNameFragment(String nameFragment);

	List<Equipment> readAllByKks(String kks);

	List<Equipment> readAllByXyz(Double[] xyz);

	List<Equipment> readAllByJoinPoint(JoinPoint joinPoint);

}

