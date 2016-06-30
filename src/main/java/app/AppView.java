package app;


import config.AppConfig;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import servises.Analyser;
import servises.DBInitManager;
import servises.Tracer;

import java.io.File;
import java.util.Arrays;

public class AppView extends Application {
    final static String DEFAULT_OBJECT = "MAYAK";

    @Autowired
    private Tracer tracer;
    @Autowired
    private Analyser analyser;
    @Autowired
    private DBInitManager dbInitManager;

    @Override
    public void start(Stage primaryStage) {

        final Text actiontarget = new Text();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(30, 30, 30, 30));

        Scene scene = new Scene(grid, 500, 350);
        Text scenetitle = new Text("Для начала работы скопируйте данные \nв папки согласно указаниям README:");
        scenetitle.setFont(Font.font("Times new roman", FontWeight.NORMAL, 13));
        grid.add(scenetitle, 0, 0, 2, 1);

        try {
            Label objLabel1 = new Label("1. Укажите название объекта:");
            objLabel1.setAlignment(Pos.CENTER_LEFT);
            grid.add(objLabel1, 0, 1);
            TextField inpObject1 = new TextField();
            inpObject1.setAlignment(Pos.CENTER_RIGHT);
            inpObject1.setMinWidth(220);
            inpObject1.setMaxWidth(220);
            inpObject1.setText(DEFAULT_OBJECT);
            grid.add(inpObject1, 1, 1);

            Label objLabel2 = new Label("2. Выберите действие:");
            objLabel2.setAlignment(Pos.CENTER_LEFT);
            grid.add(objLabel2, 0, 3);

            Button btn_analyse_EquipsInJournals = new Button();
            btn_analyse_EquipsInJournals.setText("Прошерстить журнал");
            btn_analyse_EquipsInJournals.setTextFill(Color.DARKGREEN);
            btn_analyse_EquipsInJournals.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        analyser.setProjectName(DEFAULT_OBJECT);
                        String pathName = inpObject1.getText();
                        File path;
                        if (pathName != null) {
                            path = new File(pathName);
                            if (path.isDirectory() && path.listFiles() != null) {
                                analyser.findNewEquipmentsInJournals(Arrays.asList(path.listFiles()), path);
                            }
                        }
                        actiontarget.setFill(Color.DARKGREEN);
                        actiontarget.setFont(Font.font("Courier", FontWeight.BOLD, 11));
                        actiontarget.setText("Оборудование в журнале проверено!");
                    } catch (Exception ex) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Ошибка в программе!" + ex.getLocalizedMessage());
                    }
                }
            });

            HBox hbBtn_analyse_EquipsInJournals = new HBox(10);
            hbBtn_analyse_EquipsInJournals.setAlignment(Pos.BOTTOM_LEFT);
            hbBtn_analyse_EquipsInJournals.getChildren().add(btn_analyse_EquipsInJournals);
            grid.add(hbBtn_analyse_EquipsInJournals, 0, 4);


            Button btn_tracer = new Button();
            btn_tracer.setText("Протрассировать");
            btn_tracer.setTextFill(Color.DARKBLUE);
            btn_tracer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        analyser.setProjectName(DEFAULT_OBJECT);
                        String pathName = inpObject1.getText();
                        File path;
                        if (pathName != null) {
                            path = new File(pathName);
                            if (tracer.testModelIsReadyForTracing()) {
                                tracer.traceJournals(path);
                            }
                        }
                        actiontarget.setFill(Color.DARKBLUE);
                        actiontarget.setFont(Font.font("Courier", FontWeight.BOLD, 11));
                        actiontarget.setText("Трассировка выполнена!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Ошибка в программе!" + ex.getLocalizedMessage());
                    }
                }
            });

            HBox hbBtn_tracer = new HBox(10);
            hbBtn_tracer.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn_tracer.getChildren().add(btn_tracer);
            grid.add(hbBtn_tracer, 1, 4);


            Button btn_calc = new Button();
            btn_calc.setText("Расчет смет");
            btn_calc.setTextFill(Color.DARKMAGENTA);
            btn_calc.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
//                        String obj = inpObject1.getText();
//                        String path = "properties/"; //inpObject2.getText();
//                        Calculator.setNewObject(obj, path);
//                        Calculator.calculate();
                        actiontarget.setFill(Color.DARKMAGENTA);
                        actiontarget.setFont(Font.font("Courier", FontWeight.BOLD, 11));
                        actiontarget.setText("Расчет для смет выполнен успешно!");
                    } catch (Exception ex) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Ошибка в программе!" + ex.getLocalizedMessage());
                    }
                }
            });

            HBox hbBtn_calc = new HBox(10);
            hbBtn_calc.setAlignment(Pos.BOTTOM_LEFT);
            hbBtn_calc.getChildren().add(btn_calc);
            grid.add(hbBtn_calc, 0, 5);

            Button btn_defaults = new Button();
            btn_defaults.setText("> INIT DB");
            btn_defaults.setFont(Font.font("Courier", FontWeight.NORMAL, 11));
            btn_defaults.setTextFill(Color.RED);
            btn_defaults.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        dbInitManager.init();
                        inpObject1.setText(DEFAULT_OBJECT);
                        actiontarget.setText(" ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            HBox hbBtn_defaults = new HBox(10);
            hbBtn_defaults.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn_defaults.getChildren().add(btn_defaults);
            grid.add(hbBtn_defaults, 1, 7);

            Label resultLabel = new Label("3. Результат работы программы:");
            resultLabel.setAlignment(Pos.CENTER_LEFT);
            grid.add(resultLabel, 0, 6);
            grid.add(actiontarget, 1, 6);
        } catch (Throwable e) {
            e.printStackTrace();
            actiontarget.setText("Error! " + e.getLocalizedMessage());
        }
        primaryStage.setTitle("Cableroutes v.0.9.9 by Zaiets A.Y.");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, AppView.class);
        launch(args);
    }
}