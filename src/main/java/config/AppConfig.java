package config;

import controllers.ScreenController;
import model.db.InMemoryDB;
import model.db.implInMemory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import properties.PropertiesHolder;
import repository.IOExcelForAnalyser;
import repository.IOExcelForTracer;
import servises.Analyser;
import servises.DBManager;
import servises.Tracer;
import servises.tracerlogic.TracingHelper;


@Configuration
@ComponentScan(basePackages = "java.*")
@Import(PropertiesHolder.class)
public class AppConfig {

	@Bean
	PropertiesHolder propertiesHolder() {
		return new PropertiesHolder();
	}

	@Bean
	InMemoryDB inMemoryDB() {
		return InMemoryDB.INSTANCE;
	}

	@Bean
	CableDao cableDao() {
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
	DBManager dBManager() {
		return new DBManager();
	}

	@Bean
	TracingHelper tracingHelper() {
		return new TracingHelper();
	}

	@Bean
	IOExcelForTracer iOExcelForTracer() {
		return new IOExcelForTracer();
	}

	@Bean
	IOExcelForAnalyser iOExcelForAnalyser() {
		return new IOExcelForAnalyser();
	}

	@Bean
	ScreenController screenController() {
		return new ScreenController();
	}
}

