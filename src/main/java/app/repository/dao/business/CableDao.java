package app.repository.dao.business;

import app.repository.entities.business.*;

import java.util.List;


interface CableDao extends IDao<Cable> {

	List<Cable> readAllByTwoEquipments(Equipment eq1, Equipment eq2);

	List<Cable> readAllByEquipment(Equipment equipment);

	List<Cable> readAllByRoute(Route route);

	List<Cable> readAllByJournal(Route route);

	List<Cable> readAllByType(Route route);

}

