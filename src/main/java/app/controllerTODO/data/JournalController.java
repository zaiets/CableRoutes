package app.controllerTODO.data;

import app.service.entities.IJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/journal")
public class JournalController {
    @Autowired
    IJournalService journalService;

    //TODO

}
