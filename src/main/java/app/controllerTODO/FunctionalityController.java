package app.controllerTODO;

import app.service.functionalityTODO.IFunctionalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/functionality")
public class FunctionalityController {
    @Autowired
    IFunctionalityService functionalityService;

    //TODO else
}
