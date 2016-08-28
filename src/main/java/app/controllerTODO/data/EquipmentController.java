package app.controllerTODO.data;

import app.dto.models.EquipmentDto;
import app.service.entities.IEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/equipment")
public class EquipmentController {
    @Autowired
    IEquipmentService service;

    //CREATE ONE EQUIPMENT
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody HttpHeaders headers, @Valid EquipmentDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //GET EQUIPMENT
    @RequestMapping(value = "/{kks}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentDto> read (@RequestBody HttpHeaders headers, @PathVariable("kks") String kks) {
        EquipmentDto currentDto = service.read(kks);
        if (currentDto == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentDto, headers, HttpStatus.OK);
    }

    //CREATE OR UPDATE EQUIPMENT
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrUpdate(@RequestBody HttpHeaders headers,
                                                    @RequestBody @Valid EquipmentDto entityDto) {
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE EQUIPMENT
    @RequestMapping(value = "/{kks}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody HttpHeaders headers, @PathVariable("kks") String kks,
                                            @RequestBody @Valid EquipmentDto entityDto) {
        if (service.update(kks, entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //DELETE EQUIPMENT
    @RequestMapping(value = "/{kks}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestBody HttpHeaders headers, @PathVariable("kks") String kks) {
        if (service.delete(kks)) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL EQUIPMENTS
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDto>> listAll(@RequestBody HttpHeaders headers) {
        List<EquipmentDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityDtoList, headers, HttpStatus.OK);
    }

    //else TODO?

}
