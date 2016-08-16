package app.service.business.impl;

import app.dto.models.EquipmentDto;
import app.dto.models.JoinPointDto;
import app.repository.dao.business.IEquipmentDao;
import app.repository.entities.business.Equipment;
import app.service.business.IEquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static app.converter.ModelVsDtoConverter.*;

@Service
public class EquipmentServiceImpl implements IEquipmentService {

    static final Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Autowired
    private IEquipmentDao equipmentDao;

    @Override
    public boolean create(EquipmentDto equipmentDto) {
        logger.info("EquipmentService is creating new equipment: {}", equipmentDto.getFullName());
        return equipmentDao.create(transformEquipmentDto(equipmentDto));
    }

    @Override
    public boolean createOrUpdate(EquipmentDto equipmentDto) {
        logger.info("EquipmentService is creating new equipment: {}", equipmentDto.getFullName());
        return equipmentDao.createOrUpdate(transformEquipmentDto(equipmentDto));
    }

    @Override
    public EquipmentDto read(String kks) {
        logger.info("EquipmentService is reading equipment by kks: {}", kks);
        return transformEquipment(equipmentDao.read(kks));
    }

    @Override
    public boolean update(String kks, EquipmentDto equipmentDto) {
        logger.info("EquipmentService is updating equipment: {}", equipmentDto.getFullName());
        return equipmentDao.update(kks, transformEquipmentDto(equipmentDto));
    }

    @Override
    public boolean delete(String kks) {
        return equipmentDao.delete(kks);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EquipmentDto> getAll(){
        logger.info("EquipmentService is reading all equipments");
        List<EquipmentDto> equipmentDtoList = new ArrayList<>();
        equipmentDao.getAll().forEach(o -> equipmentDtoList.add(transformEquipment(o)));
        return equipmentDtoList;
    }

    @Override
    public List<EquipmentDto> readAllByXyz (Double x, Double y, Double z, boolean includeNullValues) {
        logger.info("EquipmentService is reading equipments by xyz: {}, {}, {} (including null values = {})", x, y, z, includeNullValues);
        List<EquipmentDto> equipmentDtoList = new ArrayList<>();
        List<Equipment> equipmentList = equipmentDao.readAllByXyz(x, y, z, includeNullValues);
        if (equipmentList != null && !equipmentList.isEmpty())
            equipmentList.forEach(o -> equipmentDtoList.add(transformEquipment(o)));
        return equipmentDtoList;
    }

    @Override
    public List<EquipmentDto> readAllByJoinPoint (JoinPointDto pointDto) {
        logger.info("EquipmentService is reading equipments by joinPoint: {}", pointDto.getKksName());
        List<EquipmentDto> equipmentDtoList = new ArrayList<>();
        List<Equipment> equipmentList = equipmentDao.readAllByJoinPoint(transformJoinPointDto(pointDto));
        if (equipmentList != null && !equipmentList.isEmpty())
            equipmentList.forEach(o -> equipmentDtoList.add(transformEquipment(o)));
        return equipmentDtoList;
    }

    @Override
    public List<EquipmentDto> readAllByKks (String kks) {
        logger.info("EquipmentService is reading equipments by kks: {}", kks);
        List<EquipmentDto> equipmentDtoList = new ArrayList<>();
        List<Equipment> equipmentList = equipmentDao.readAllByKks(kks);
        if (equipmentList != null && !equipmentList.isEmpty())
            equipmentList.forEach(o -> equipmentDtoList.add(transformEquipment(o)));
        return equipmentDtoList;
    }


}
