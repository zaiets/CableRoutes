package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import servises.Analyser;
import servises.DBManager;
import servises.Tracer;

import java.io.File;
import java.util.List;

@Controller
public class ScreenController {
    public static final String DEFAULT_PATH = "files/journals";
    public static final String DEFAULT_PROJECT = "MAYAK";
    public static final String MESSAGE_SMTHN_WRONG = "Ошибка в программе!";
    @Autowired
    private Analyser analyser;
    @Autowired
    private Tracer tracer;
    @Autowired
    private DBManager dbManager;

    private String projectName;
    private Stage stage;

    //Common elements
    @FXML
    private TextField projectNameArea;
    @FXML
    private Text textArea0, textAreaAnalyser0, textAreaTracer0, textAreaCalculator0;
    @FXML
    private ProgressBar progressBar, progressBarAnalyser, progressBarTracer, progressBarCalculator;
    @FXML
    private Button buttonActions, buttonActionsAnalyser, buttonActionsTracer, buttonActionsCalculator;


    //DBinit page
    @FXML
    private Text textArea1, textArea2, textArea3, textArea4;
    @FXML
    private Button buttonChooseFile1, buttonChooseFile2, buttonChooseFile3, buttonChooseFiles4;
    @FXML
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private File equipments, joinPoints, routes = null;
    private File[] journals = null;

    //Analyser page
    @FXML
    private Text textAreaAnalyser1, textAreaAnalyser2, textAreaAnalyser3;
    @FXML
    private Button buttonChooseFilesAnalyser1, buttonChooseFileAnalyser2;
    @FXML
    private RadioButton radioAnalyser1, radioAnalyser2, radioAnalyser3;
    private File equipmentsForAnalyse = null;
    private File[] journalsForAnalyse = null;

    //Tracer page
    @FXML
    private Text textAreaTracer1, textAreaTracer2;
    @FXML
    private Button buttonChooseFilesTracer1;
    @FXML
    private RadioButton radioTracer1, radioTracer2;
    private File[] journalsForTracing = null;

    //Calculator page
    @FXML
    private Text textAreaCalculator1, textAreaCalculator2;
    @FXML
    private Button buttonChooseFilesCalculator1;
    @FXML
    private RadioButton radioCalculator1, radioCalculator2;
    private File[] journalsForCalculating = null;


    @FXML
    public void setNewProjectName(ActionEvent event) {
        projectName = projectNameArea.getText();
        textArea0.setText(projectName);
        textAreaAnalyser0.setText(projectName);
        textAreaTracer0.setText(projectName);
        textAreaCalculator0.setText(projectName);
    }

    @FXML
    protected void press_ButtonActions(ActionEvent event) {
        progressBar.setProgress(0.2);
        if (checkBox1.isSelected()) textArea1.setText("Done...");
        progressBar.setProgress(0.4);
        if (checkBox2.isSelected()) textArea2.setText("Done...");
        progressBar.setProgress(0.6);
        if (checkBox3.isSelected()) textArea3.setText("Done...");
        progressBar.setProgress(0.8);
        if (checkBox4.isSelected()) textArea4.setText("Done...");
        progressBar.setProgress(1.0);

    }

    @FXML
    protected void setProgressBarsNull() {
        progressBar.setProgress(0.0);
        progressBarAnalyser.setProgress(0.0);
        progressBarCalculator.setProgress(0.0);
        progressBarTracer.setProgress(0.0);
    }

    private File inputFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы excel (*.xlsx)", "*.xlsx"));
        fileChooser.setTitle(title);
        return fileChooser.showOpenDialog(stage);
    }

    private List<File> inputFiles(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы excel (*.xlsx)", "*.xlsx"));
        fileChooser.setTitle(title);
        return fileChooser.showOpenMultipleDialog(stage);
    }

    private Float[] getProgressValues(int step) {
        Float[] progressValues = new Float[step + 1];
        float f;
        progressValues[0] = 0f;
        progressValues[step] = 1f;
        for (int i = 1; i < step; i++) {
            f = Math.round(100f / (step - i)) * 0.01f;
            progressValues[i] = f;
        }
        return progressValues;
    }

    public void setAnalyser(Analyser analyser) {
        this.analyser = analyser;
    }

    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void setTracer(Tracer tracer) {
        this.tracer = tracer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}