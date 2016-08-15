package app.service.business.impl;

import app.dto.models.CableDto;
import app.dto.models.EquipmentDto;
import app.dto.models.JoinPointDto;
import app.repository.dao.business.ICableDao;
import app.repository.entities.business.Cable;
import app.service.business.ICableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static app.converter.ModelVsDtoConverter.*;

@Service
public class CableServiceImpl implements ICableService {

    static final Logger logger = LoggerFactory.getLogger(CableServiceImpl.class);

    @Autowired
    private ICableDao cableDao;

    @Override
    public boolean create(CableDto cableDto) {
        logger.info("joinPointService is creating new cable: {}", cableDto.getKksName());
        return cableDao.create(transformCableDto(cableDto));
    }

    @Override
    public boolean createOrUpdate(CableDto cableDto) {
        logger.info("Cables service is creating new cable: {}", cableDto.getKksName());
        return cableDao.createOrUpdate(transformCableDto(cableDto));
    }

    @Override
    public CableDto read(String kks) {
        logger.info("joinPointService is reading cable by kks: {}", kks);
        return transformCable(cableDao.read(kks));
    }

    @Override
    public boolean update(String kks, CableDto cableDto) {
        logger.info("joinPointService is updating cable: {}", cableDto.getKksName());
        return cableDao.update(kks, transformCableDto(cableDto));
    }

    @Override
    public boolean delete(String kks) {
        return cableDao.delete(kks);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CableDto> getAll(){
        logger.info("joinPointService is reading all cables");
        List<CableDto> cableDtoList = new ArrayList<>();
        cableDao.getAll().forEach(o -> cableDtoList.add(transformCable(o)));
        return cableDtoList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<CableDto> readAllByTwoEquipments(EquipmentDto equipOne, EquipmentDto equipTwo) {
        logger.info("joinPointService is reading all cables by equipmentDtos {} and {}", equipOne.getFullName(), equipTwo.getFullName());
        List<Cable> cables = cableDao.readAllByTwoEquipments(transformEquipmentDto(equipOne), transformEquipmentDto(equipTwo));
        List<CableDto> cableDtoList = new ArrayList<>();
        cables.forEach(o -> cableDtoList.add(transformCable(o)));
        return cableDtoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CableDto> readAllByEquipment(EquipmentDto equipmentDto) {
        logger.info("joinPointService is reading all cables by equipmentDto {}", equipmentDto.getFullName());
        List<Cable> cables = cableDao.readAllByEquipment(transformEquipmentDto(equipmentDto));
        List<CableDto> cableDtoList = new ArrayList<>();
        cables.forEach(o -> cableDtoList.add(transformCable(o)));
        return cableDtoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CableDto> readAllByJoinPoint(JoinPointDto joinPointDto) {
        logger.info("joinPointService is reading all cables by joinPointDto {}", joinPointDto.getKksName());
        List<Cable> cables = cableDao.readAllByJoinPoint(transformJoinPointDto(joinPointDto));
        List<CableDto> cableDtoList = new ArrayList<>();
        cables.forEach(o -> cableDtoList.add(transformCable(o)));
        return cableDtoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CableDto> readAllByTwoJoinPoints(JoinPointDto pointOne, JoinPointDto pointTwo) {
        logger.info("joinPointService is reading all cables by joinPointDtos {} and {}", pointOne.getKksName(), pointTwo.getKksName());
        List<Cable> cables = cableDao.readAllByTwoJoinPoints(transformJoinPointDto(pointOne), transformJoinPointDto(pointTwo));
        List<CableDto> cableDtoList = new ArrayList<>();
        cables.forEach(o -> cableDtoList.add(transformCable(o)));
        return cableDtoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CableDto> readAllByJournal(String journalKks) {
        logger.info("joinPointService is reading all cables by journalKks {}", journalKks);
        List<Cable> cables = cableDao.readAllByJournal(journalKks);
        List<CableDto> cableDtoList = new ArrayList<>();
        cables.forEach(o -> cableDtoList.add(transformCable(o)));
        return cableDtoList;
    }

}
