package app.controller.data;

import app.dto.models.EquipmentDto;
import app.dto.models.JoinPointDto;
import app.service.entities.IEquipmentService;
import app.service.entities.IJoinPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/equipment")
public class EquipmentController {
    static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    IEquipmentService service;
    @Autowired
    IJoinPointService joinPointService;

    //CREATE ONE EQUIPMENT
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid EquipmentDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET EQUIPMENT
    @RequestMapping(value = "/{kks}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentDto> read (@PathVariable("kks") String kks) {
        EquipmentDto currentDto = service.read(kks);
        if (currentDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(currentDto, HttpStatus.OK);
    }

    //CREATE OR UPDATE EQUIPMENT
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrUpdate(@RequestBody @Valid EquipmentDto entityDto) {
        logger.info("updateOrUpdate -> {}" + entityDto.getFullName());
        if (entityDto.getJoinPointKks() != null) {
            JoinPointDto currentJoinPoint = joinPointService.read(entityDto.getJoinPointKks());
            if (currentJoinPoint == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            entityDto.setJoinPoint(currentJoinPoint);
        }
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //UPDATE EQUIPMENT
    @RequestMapping(value = "/{kks}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable("kks") String kks,
                                            @RequestBody @Valid EquipmentDto entityDto) {
        if (service.update(kks, entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //DELETE EQUIPMENT
    @RequestMapping(value = "/{kks}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("kks") String kks) {
        if (service.delete(kks)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL EQUIPMENTS
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDto>> listAll() {
        List<EquipmentDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entityDtoList, HttpStatus.OK);
    }
}
