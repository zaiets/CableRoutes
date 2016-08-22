package app.controllerTODO.data;

import app.dto.models.CableDto;
import app.repository.entities.common.User;
import app.service.entities.ICableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cable")
public class CableController {
    @Autowired
    ICableService cableService;

    //CREATE ONE CABLE
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCable(@RequestBody HttpHeaders headers, @Valid CableDto cableDto) {
        boolean isCreated = cableService.create(cableDto);
        if (!isCreated) {
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //GET CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CableDto> readCable (@RequestBody HttpHeaders headers, @PathVariable("kks") String kks) {
        CableDto currentCableDto = cableService.read(kks);
        if (currentCableDto == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentCableDto, headers, HttpStatus.OK);
    }

    //CREATE OR UPDATE CABLE
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateOrUpdateCable(@RequestBody HttpHeaders headers, @RequestBody @Valid CableDto cableDto) {
        if (cableService.createOrUpdate(cableDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateCable(@RequestBody HttpHeaders headers, @PathVariable("kks") String kks, @RequestBody @Valid CableDto cableDto) {
        if (cableService.update(kks, cableDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //DELETE CABLE
    @RequestMapping(value = "/{kks}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteCable(@RequestBody HttpHeaders headers, @PathVariable("kks") String kks) {
        if (cableService.delete(kks)) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL CABLES
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CableDto>> listAllCables(@RequestBody HttpHeaders headers) {
        List<CableDto> cableDtoList = cableService.getAll();
        if(cableDtoList.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cableDtoList, headers, HttpStatus.OK);
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
