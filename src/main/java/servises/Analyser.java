package servises;


import model.db.IDao;
import model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import properties.PropertiesHolder;
import repository.IOExcelForAnalyser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = "singleton")
public final class Analyser {

	@Autowired
	private PropertiesHolder propertiesHolder;
	//excel writer
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

	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

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

	public void defineEquipmentsClosestPoints(String projectName, File equipmentsFile, File targetPath) {
		ioExcelForAnalyser.analyseAndDefineClosestPointsOfEquipment(projectName, equipmentsFile, targetPath, joinPointDao);
	}

	public void findNewEquipmentsInJournals(List<File> journals, File targetPath) {
		List<String[]> addEquip = new ArrayList<>();
		for (File journal : journals) {
			addEquip.addAll(ioExcelForAnalyser.analyseEquipmentsInJournal(journal, equipmentDao.getAll(), targetPath));
		}
		ioExcelForAnalyser.writeToFileAllAdditionalEquipment (projectName, addEquip, targetPath);
	}
}
