package app.service.business.impl;

import app.dto.models.JoinPointDto;
import app.repository.dao.business.IJoinPointDao;
import app.service.business.IJoinPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static app.converter.ModelVsDtoConverter.transformJoinPoint;
import static app.converter.ModelVsDtoConverter.transformJoinPointDto;

@Service
public class JoinPointServiceImpl implements IJoinPointService {

    static final Logger logger = LoggerFactory.getLogger(JoinPointServiceImpl.class);

    @Autowired
    private IJoinPointDao joinPointDao;

    @Override
    public boolean create(JoinPointDto joinPointDto) {
        logger.info("joinPointService is creating new joinPoint: {}", joinPointDto.getKksName());
        return joinPointDao.create(transformJoinPointDto(joinPointDto));
    }

    @Override
    public boolean createOrUpdate(JoinPointDto joinPointDto) {
        logger.info("joinPointService is creating new joinPoint: {}", joinPointDto.getKksName());
        return joinPointDao.createOrUpdate(transformJoinPointDto(joinPointDto));
    }

    @Override
    public JoinPointDto read(String kks) {
        logger.info("joinPointService is reading joinPoint by kks: {}", kks);
        return transformJoinPoint(joinPointDao.read(kks));
    }

    @Override
    public boolean update(String kks, JoinPointDto joinPointDto) {
        logger.info("joinPointService is updating joinPoint: {}", joinPointDto.getKksName());
        return joinPointDao.update(kks, transformJoinPointDto(joinPointDto));
    }

    @Override
    public boolean delete(String kks) {
        return joinPointDao.delete(kks);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JoinPointDto> getAll(){
        logger.info("joinPointService is reading all joinPoints");
        List<JoinPointDto> joinPointDtos = new ArrayList<>();
        joinPointDao.getAll().forEach(o -> joinPointDtos.add(transformJoinPoint(o)));
        return joinPointDtos;
    }

    @Override
    public JoinPointDto readByXyz (double x, double y, double z) {
        logger.info("joinPointService is reading joinPoint by xyz: {}, {}, {}", x, y, z);
        return transformJoinPoint(joinPointDao.readByXyz(x, y, z));
    }

}
