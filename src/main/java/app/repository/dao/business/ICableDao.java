package app.repository.dao.business;

import app.repository.entities.business.*;

import java.util.List;


public interface ICableDao extends IDao<Cable> {

	boolean update(String kks, Cable cable);

	Cable read(String kks);

	List<Cable> readAllByTwoEquipments(Equipment eq1, Equipment eq2);

	List<Cable> readAllByEquipment(Equipment equipment);

	List<Cable> readAllByJournal(String journalKks);

	List<Cable> readAllByJoinPoint(JoinPoint joinPoint);

	List<Cable> readAllByTwoJoinPoints(JoinPoint start, JoinPoint end);

}

