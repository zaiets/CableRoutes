package app.controller.data;

import app.dto.models.CableDto;
import app.service.entities.ICableService;
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
    @Autowired
    ICableService service;

    //CREATE ONE CABLE
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody HttpHeaders headers, @Valid CableDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //GET CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CableDto> read (@RequestBody HttpHeaders headers, @PathVariable("kks") String kks) {
        CableDto currentCableDto = service.read(kks);
        if (currentCableDto == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentCableDto, headers, HttpStatus.OK);
    }

    //CREATE OR UPDATE CABLE
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrUpdate(@RequestBody HttpHeaders headers, @RequestBody @Valid CableDto entityDto) {
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCable(@RequestBody HttpHeaders headers, @PathVariable("kks") String kks, @RequestBody @Valid CableDto entityDto) {
        if (service.update(kks, entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //DELETE CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestBody HttpHeaders headers, @PathVariable("kks") String kks) {
        if (service.delete(kks)) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL CABLES
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CableDto>> listAll(@RequestBody HttpHeaders headers) {
        List<CableDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityDtoList, headers, HttpStatus.OK);
    }


//    boolean createOrUpdate(T t);
//
//

//    List<CableDto> readAllByTwoEquipments(EquipmentDto eq1, EquipmentDto eq2);
//
//    List<CableDto> readAllByEquipment(EquipmentDto eq);
//
//    List<CableDto> readAllByJournal(String journalKks);
//
//    List<CableDto> readAllByJoinPoint(JoinPointDto point);
//
//    List<CableDto> readAllByTwoJoinPoints(JoinPointDto start, JoinPointDto end);
//
// TODO?
}