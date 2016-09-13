package app.controller.data;

import app.dto.models.CableDto;
import app.service.entities.ICableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@RequestMapping(value = "/cable")
public class CableController {
    static final Logger logger = LoggerFactory.getLogger(CableController.class);

    @Autowired
    ICableService service;

    //CREATE ONE CABLE
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid CableDto entityDto) {
        logger.info("create {}", entityDto);
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CableDto> read(@PathVariable("kks") String kks) {
        CableDto currentDto = service.read(kks);
        logger.info("found entity: ", currentDto);
        if (currentDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(currentDto, HttpStatus.OK);
    }

    //CREATE OR UPDATE CABLE
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrUpdate(@RequestBody @Valid CableDto entityDto) {
        logger.info("createOrUpdate: {}", entityDto);
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //UPDATE CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCable(@PathVariable("kks") String kks, @RequestBody @Valid CableDto entityDto) {
        logger.info("update: {}", kks);
        if (service.update(kks, entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //DELETE CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("kks") String kks) {
        logger.info("delete: {}", kks);
        if (service.delete(kks)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //GET ALL CABLES
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CableDto>> listAll(@RequestBody HttpHeaders headers) {
        List<CableDto> entityDtoList = service.getAll();
        logger.info("get all items");
        if (entityDtoList.isEmpty()) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entityDtoList, headers, HttpStatus.OK);
    }

    /*
    List of already available service methods:
    List<CableDto> readAllByTwoEquipments(EquipmentDto eq1, EquipmentDto eq2);
    List<CableDto> readAllByEquipment(EquipmentDto eq);
    List<CableDto> readAllByJournal(String journalKks);
    List<CableDto> readAllByJoinPoint(JoinPointDto point);
    List<CableDto> readAllByTwoJoinPoints(JoinPointDto start, JoinPointDto end);
    */
}
