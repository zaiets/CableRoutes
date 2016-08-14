package app.service.business;

import app.dto.models.JoinPointDto;


public interface IJoinPointService extends IService<JoinPointDto, String> {

	JoinPointDto readByXyz(double x, double y, double z);

}

