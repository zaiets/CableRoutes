package app.controller.data;

import app.dto.models.JoinPointDto;
import app.service.entities.IJoinPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/joinPoint")
public class JoinPointController {
    @Autowired
    IJoinPointService service;

    //CREATE ONE JOINPOINT
    @RequestMapping(method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid JoinPointDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET JOINPOINT
    @RequestMapping(value = "/{kks}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JoinPointDto> read (@PathVariable("kks") String kks) {
        JoinPointDto currentDto = service.read(kks);
        if (currentDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(currentDto, HttpStatus.OK);
    }

    //CREATE OR UPDATE JOINPOINT
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrUpdate(@RequestBody @Valid JoinPointDto entityDto) {
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //UPDATE JOINPOINT
    @RequestMapping(value = "/{kks}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable("kks") String kks,
                                            @RequestBody @Valid JoinPointDto entityDto) {
        if (service.update(kks, entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    //DELETE JOINPOINT
    @RequestMapping(value = "/{kks}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("kks") String kks) {
        if (service.delete(kks)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //GET ALL JOINPOINTS
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JoinPointDto>> listAll() {
        List<JoinPointDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entityDtoList, HttpStatus.OK);
    }

    //else TODO?

}
