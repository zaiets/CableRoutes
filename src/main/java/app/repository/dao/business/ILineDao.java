package app.repository.dao.business;

import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;

import java.util.List;


public interface ILineDao {

	boolean create(Line line);

	boolean createOrUpdate(Line line);

	Line read (int id);

	boolean change(Line line);

	boolean erase(Line line);

	List<Line> getAll();

	List<Line> readAllByJoinPoint(JoinPoint joinPoint);

	List<Line> readAllByTwoJoinPoints(JoinPoint pointOne, JoinPoint pointTwo);

}

