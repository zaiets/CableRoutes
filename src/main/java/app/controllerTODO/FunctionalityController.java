package app.controllerTODO;

import app.service.functionality.IFunctionalityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/functionality")
public class FunctionalityController {
    static final Logger logger = LoggerFactory.getLogger(FunctionalityController.class);

    @Autowired
    IFunctionalityService functionalityService;

    //TODO else

}
