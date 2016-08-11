package app.repository.dao.business;

import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;
import app.repository.entities.business.Route;

import java.util.List;


interface LineDao extends IDao<Line> {

	Line readById(int id);

	Line readByJoinPoint(JoinPoint joinPoint);

	Line readByStartAndEnd(JoinPoint start, JoinPoint end);

	List<Line> readAllByRoute (Route route);

}

