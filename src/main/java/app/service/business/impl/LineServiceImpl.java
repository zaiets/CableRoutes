package app.service.business.impl;

import app.dto.models.JoinPointDto;
import app.dto.models.LineDto;
import app.repository.dao.business.ILineDao;
import app.repository.entities.business.Line;
import app.service.business.ILineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static app.converter.ModelVsDtoConverter.*;

@Service
public class LineServiceImpl implements ILineService {

    static final Logger logger = LoggerFactory.getLogger(LineServiceImpl.class);

    @Autowired
    private ILineDao lineDao;

    @Override
    public boolean create(LineDto lineDto) {
        logger.info("LineService is creating new line: {}", lineDto.toString());
        return lineDao.create(transformLineDto(lineDto));
    }

    @Override
    public boolean createOrUpdate(LineDto lineDto) {
        logger.info("LineService is creating new line: {}", lineDto.toString());
        return lineDao.createOrUpdate(transformLineDto(lineDto));
    }

    @Override
    public LineDto read(Integer id) {
        logger.info("LineService is reading line by id: {}", id);
        return transformLine(lineDao.read(id));
    }

    @Override
    public boolean update(Integer id, LineDto lineDto) {
        logger.info("LineService is updating cable: {}", lineDto.toString());
        return lineDao.change(transformLineDto(lineDto));
    }

    @Override
    public boolean delete(Integer id) {
        logger.info("LineService is deleting cable: {}", id);
        return !(id == null || id < 0) && lineDao.erase(lineDao.read(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LineDto> getAll(){
        logger.info("LineService is reading all lines");
        List<LineDto> lineDtoList = new ArrayList<>();
        lineDao.getAll().forEach(o -> lineDtoList.add(transformLine(o)));
        return lineDtoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LineDto> readAllByJoinPoint(JoinPointDto joinPointDto) {
        logger.info("LineService is reading all lines by joinPointDto {}", joinPointDto.getKksName());
        List<Line> lines = lineDao.readAllByJoinPoint(transformJoinPointDto(joinPointDto));
        List<LineDto> lineDtoList = new ArrayList<>();
        lines.forEach(o -> lineDtoList.add(transformLine(o)));
        return lineDtoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LineDto> readAllByTwoJoinPoints(JoinPointDto pointOne, JoinPointDto pointTwo) {
        logger.info("LineService is reading all lines by joinPointDtos {} and {}", pointOne.getKksName(), pointTwo.getKksName());
        List<Line> lines = lineDao.readAllByTwoJoinPoints(transformJoinPointDto(pointOne), transformJoinPointDto(pointTwo));
        List<LineDto> lineDtoList = new ArrayList<>();
        lines.forEach(o -> lineDtoList.add(transformLine(o)));
        return lineDtoList;
    }

}
