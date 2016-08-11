package app.repository.dao.business;

import app.repository.entities.business.JoinPoint;


interface JoinPointDao extends IDao<JoinPoint> {

	JoinPoint readByXyz(double[] xyz);

}

