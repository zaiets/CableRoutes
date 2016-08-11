package app.repository.dao.business;

import app.repository.entities.business.Cable;
import app.repository.entities.business.Journal;

import java.util.List;


interface JournalDao extends IDao<Journal> {

	List<Journal> readAllByCable(Cable cable);

}

