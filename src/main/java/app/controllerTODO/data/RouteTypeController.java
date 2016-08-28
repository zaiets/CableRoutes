package app.controllerTODO.data;

import app.dto.models.RouteTypeDto;
import app.service.entities.IRouteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/routetype")
public class RouteTypeController {
    @Autowired
    IRouteTypeService service;

    //CREATE ONE ROUTETYPE
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody HttpHeaders headers, @Valid RouteTypeDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //GET ROUTE
    @RequestMapping(value = "/{marker}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteTypeDto> read (@RequestBody HttpHeaders headers, @PathVariable("marker") String marker) {
        RouteTypeDto currentDto = service.read(marker);
        if (currentDto == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentDto, headers, HttpStatus.OK);
    }

    //CREATE OR UPDATE ROUTETYPE
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrUpdate(@RequestBody HttpHeaders headers,
                                                    @RequestBody @Valid RouteTypeDto entityDto) {
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE ROUTETYPE
    @RequestMapping(value = "/{marker}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody HttpHeaders headers, @PathVariable("marker") String marker,
                                            @RequestBody @Valid RouteTypeDto entityDto) {
        if (service.update(marker, entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //DELETE ROUTETYPE
    @RequestMapping(value = "/{marker}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestBody HttpHeaders headers, @PathVariable("marker") String marker) {
        if (service.delete(marker)) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL ROUTETYPES
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RouteTypeDto>> listAll(@RequestBody HttpHeaders headers) {
        List<RouteTypeDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityDtoList, headers, HttpStatus.OK);
    }

    //else TODO?

}