package app.service.business;

import app.dto.models.JoinPointDto;
import app.dto.models.LineDto;

import java.util.List;


public interface ILineService extends IService<LineDto, Integer> {

	List<LineDto> readAllByJoinPoint(JoinPointDto point);

	List<LineDto> readAllByTwoJoinPoints(JoinPointDto pointOne, JoinPointDto pointTwo);

}

