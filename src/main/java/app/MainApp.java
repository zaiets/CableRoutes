package app;


import config.AppConfig;
import config.ScreenConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import servises.Analyser;
import servises.DBManager;
import servises.Tracer;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScreenConfig screens = context.getBean(ScreenConfig.class);
        DBManager dbManager = context.getBean(DBManager.class);
        Tracer tracer = context.getBean(Tracer.class);
        Analyser analyser = context.getBean(Analyser.class);

        screens.setPrimaryStage(primaryStage);
        screens.setTracer(tracer);
        screens.setAnalyser(analyser);
        screens.setDbManager(dbManager);
        screens.showMainScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

}