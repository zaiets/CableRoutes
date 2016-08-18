package app.service.functionalityTODO.service;


import app.repository.dao.business.IEquipmentDao;
import app.repository.dao.business.IJoinPointDao;
import app.service.functionalityTODO.excel.IOExcelForAnalyser;
import app.service.functionalityTODO.properties.PropertiesManager;
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
	private PropertiesManager propertiesManager;

	@Autowired
	private IJoinPointDao joinPointDao;
	@Autowired
	private IEquipmentDao equipmentDao;

	public boolean defineEquipmentsClosestPoints(String projectName, File equipmentsFile, File targetPath) {
		return ioExcelForAnalyser.analyseAndDefineClosestPointsOfEquipment(projectName, equipmentsFile, targetPath, joinPointDao);
	}

	public boolean findNewEquipmentsInJournals(String projectName, List<File> journals, File targetPath) {
		File journalsPath;
		if (journals == null || journals.isEmpty()) {
			String journalsPathName = propertiesManager.get("input.journalsPath").concat(projectName).concat("/");
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
