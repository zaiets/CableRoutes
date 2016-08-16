package app.service.functionalityTODO.service;


import app.service.functionalityTODO.excel.IOExcelForAnalyser;
import app.service.functionalityTODO.properties.PropertiesHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.dao.business.IDao;
import app.repository.entities.business.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public final class Analyser {

	@Autowired
	private PropertiesHolder propertiesHolder;
	//models.excel writer
	@Autowired
	IOExcelForAnalyser ioExcelForAnalyser;
	@Autowired
	private IDao<JoinPoint> joinPointDao;
	@Autowired
	private IDao<Equipment> equipmentDao;
	@Autowired
	private IDao<Cable> cableDao;
	@Autowired
	private IDao<Journal> journalDao;
	@Autowired
	private IDao<Route> routeDao;


	public void analyseEnglishLettersInEquiments() {
		//TODO
	}

	public void analyseEnglishLettersInJournals() {
		//TODO
	}

	public void analyseEnglishLettersInPoints() {
		//TODO
	}

	public void analyseEquipmentsActual() {
		//TODO
	}

	public void analysePointsActual() {
		//TODO
	}

	public boolean defineEquipmentsClosestPoints(String projectName, File equipmentsFile, File targetPath) {
		return ioExcelForAnalyser.analyseAndDefineClosestPointsOfEquipment(projectName, equipmentsFile, targetPath, joinPointDao);
	}

	public boolean findNewEquipmentsInJournals(String projectName, List<File> journals, File targetPath) {
		File journalsPath;
		if (journals == null || journals.isEmpty()) {
			String journalsPathName = propertiesHolder.get("input.journalsPath").concat(projectName).concat("/");
			journalsPath = new File(journalsPathName);
			File[] journalMassive = journalsPath.listFiles();
			if (journalMassive != null) {
				journals = Arrays.asList(journalMassive);
			} else return false;
		}
		List<String[]> addEquip = new ArrayList<>();
		for (File journal : journals) {
			addEquip = ioExcelForAnalyser.analyseNewEquipmentsFromJournal(addEquip, journal, equipmentDao.getAll(), targetPath);
		}
		return ioExcelForAnalyser.writeToFileAllAdditionalEquipment (projectName, addEquip, targetPath);
	}
}
