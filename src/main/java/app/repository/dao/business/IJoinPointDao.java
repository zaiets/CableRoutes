package app.repository.dao.business;

import app.repository.entities.business.JoinPoint;


public interface IJoinPointDao extends IDao<JoinPoint> {

	JoinPoint readByXyz(double[] xyz);

}

