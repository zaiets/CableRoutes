package app.controllerTODO.data;

import app.service.entities.ICableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cable")
public class CableController {
    @Autowired
    ICableService cableService;

    //TODO

}
