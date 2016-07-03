package config;

import app.MainApp;
import controllers.ScreenController;
import model.db.InMemoryDB;
import model.db.impl.*;
import org.springframework.context.annotation.*;
import properties.PropertiesHolder;
import repository.IOExcelForAnalyser;
import repository.IOExcelForTracer;
import servises.Analyser;
import servises.DBManager;
import servises.Tracer;
import servises.tracerlogic.TracingHelper;


@Configuration
@Import({ScreenController.class, PropertiesHolder.class})
@ComponentScan(basePackages = "java.*")
public class AppConfig {
	@Bean
	MainApp appView() {
		return new MainApp();
	}
	@Bean
	PropertiesHolder propertiesHolder () {
		return new PropertiesHolder();
	}

	@Bean
	InMemoryDB getInMemoryDB() {
		return InMemoryDB.INSTANCE;
	}

	@Bean
	CableDao cableDao () {
		return new CableDao();
	}
	@Bean
	EquipmentDao equipmentDao() {
		return new EquipmentDao();
	}
	@Bean
	JoinPointDao joinPointDao() {
		return new JoinPointDao();
	}
	@Bean
	JournalDao journalDao() {
		return new JournalDao();
	}
	@Bean
	RouteDao routeDao() {
		return new RouteDao();
	}
	@Bean
	Tracer tracer() {
		return new Tracer();
	}
	@Bean
	Analyser analyser() {
		return new Analyser();
	}
	@Bean
	DBManager dbInitManager () {
		return new DBManager();
	}
	@Bean
	TracingHelper tracingHelper () {
		return new TracingHelper();
	}
	@Bean
	IOExcelForTracer ioExcelForTracer () {
		return new IOExcelForTracer();
	}
	@Bean
	IOExcelForAnalyser ioExcelForAnalyser () {
		return new IOExcelForAnalyser();
	}
}

