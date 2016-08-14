package app.repository.dao.business;

import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;

import java.util.List;


public interface ILineDao {

	List<Line> getAll();

	boolean create(Line line);

	boolean createOrUpdate(Line line);

	boolean erase(Line line);

	boolean change(Line line);

	Line read (int id);

	List<Line> readAllByJoinPoint(JoinPoint joinPoint);

	List<Line> readAllByTwoJoinPoints(JoinPoint pointOne, JoinPoint pointTwo);

}

