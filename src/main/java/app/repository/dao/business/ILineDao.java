package app.repository.dao.business;

import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;
import app.repository.entities.business.Route;

import java.util.List;


public interface ILineDao extends IDao<Line> {

	boolean erase(Line line);

	boolean change(Line line);

	List<Line> readAllByJoinPoint(JoinPoint joinPoint);

	List<Line> readAllByStartAndEnd(JoinPoint start, JoinPoint end);

	List<Line> readAllByRoute (Route route);

}

