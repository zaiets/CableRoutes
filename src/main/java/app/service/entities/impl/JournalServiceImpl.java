package app.service.entities.impl;

import app.dto.models.CableDto;
import app.dto.models.JournalDto;
import app.repository.dao.business.ICableDao;
import app.repository.dao.business.IJournalDao;
import app.repository.entities.business.Cable;
import app.repository.entities.business.Journal;
import app.service.entities.IJournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static app.converter.ModelVsDtoConverter.transformCable;
import static app.converter.ModelVsDtoConverter.transformJournal;
import static app.converter.ModelVsDtoConverter.transformJournalDto;

@Service
public class JournalServiceImpl implements IJournalService {

    static final Logger logger = LoggerFactory.getLogger(JournalServiceImpl.class);

    @Autowired
    private IJournalDao journalDao;
    @Autowired
    private ICableDao cableDao;

    @Override
    public boolean create(JournalDto journalDto) {
        logger.info("journalService is creating new journal: {}", journalDto.getKksName());
        return journalDao.create(transformJournalDto(journalDto, cableDao.readAllByJournal(journalDto.getKksName())));
    }

    @Override
    public boolean createOrUpdate(JournalDto journalDto) {
        logger.info("journalService is creating new journal: {}", journalDto.getKksName());
        return journalDao.createOrUpdate(transformJournalDto(journalDto, cableDao.readAllByJournal(journalDto.getKksName())));
    }

    @Override
    public JournalDto read(String kks) {
        logger.info("journalService is reading journal by kks: {}", kks);
        List<CableDto> cableDtoList = new ArrayList<>();
        List<Cable> cableList = cableDao.readAllByJournal(kks);
        if (cableList != null && !cableList.isEmpty()) {
            cableList.forEach(o -> cableDtoList.add(transformCable(o)));
        }
        return transformJournal(journalDao.read(kks), cableDtoList);
    }

    @Override
    public boolean update(String kks, JournalDto journalDto) {
        logger.info("journalService is updating journal: {}", journalDto.getKksName());
        return journalDao.update(kks, transformJournalDto(journalDto, cableDao.readAllByJournal(journalDto.getKksName())));
    }

    @Override
    public boolean delete(String kks) {
        return journalDao.delete(kks);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<JournalDto> getAll(){
        logger.info("journalService is reading all journals");
        List<JournalDto> journalDtoList = new ArrayList<>();
        List<Journal> journalList = journalDao.getAll();
        if (journalList != null && !journalList.isEmpty()) {
            journalList.forEach(jou -> {
                List<CableDto> cableDtoList = new ArrayList<>();
                List<Cable> cableList = cableDao.readAllByJournal(jou.getKksName());
                if (cableList != null && !cableList.isEmpty()) {
                    cableList.forEach(cable -> cableDtoList.add(transformCable(cable)));
                }
                journalDtoList.add(transformJournal(jou, cableDtoList));
            });
        }
        return journalDtoList;
    }

}
