package sample;



import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.sun.webkit.perf.PerfLogger.resetAll;


public class Main extends Application {

    Stage window;
    Scene scene;
    Button addProcess;
    GridPane layout;
    ComboBox<String> SchedulersList;
    RadioButton Preemptive;
    RadioButton nonPreemptive;
    TextField pName ,pDuration ,pArrTime;
    TextField pBurst , pPriority;
    ListView<String> nameList ,durationList ,ArrTimeList;
    ListView<String> priorityList ,burstList;
    Label nameLabel ,durationLabel ,ArrTimeLabel;
    Label priorityLabel ,burstLabel;
    int nextStart =0;
    int processCounter=0;
    XYChart.Series series1;
    Button startButton;
    ToggleGroup group;

    String Process;
    Button clearAll;

    ArrayList<process> processList = new ArrayList<>();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        window.setTitle("CPU Scheduler");
        window.setMinHeight(620);
        window.setMinWidth(900);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(8);
        layout.setHgap(10);

        group = new ToggleGroup();
        Preemptive = new RadioButton("Preemptive");
        Preemptive.setSelected(true);
        Preemptive.setToggleGroup(group);
        Preemptive.setUserData("preemptive");
        nonPreemptive = new RadioButton("Non Preemptive");
        nonPreemptive.setToggleGroup(group);
        nonPreemptive.setUserData("nonPreemptive");
        Preemptive.setVisible(false);
        nonPreemptive.setVisible(false);
        GridPane.setConstraints(Preemptive ,1 ,0);
        GridPane.setConstraints(nonPreemptive ,2 ,0);

        nameLabel = new Label("Process Name:");
        durationLabel = new Label("Process Duration:");
        ArrTimeLabel = new Label("Arrival Time:");
        priorityLabel = new Label("Priority Level");
        burstLabel = new Label("Bust Time");
        burstLabel.setVisible(false);
        priorityLabel.setVisible(false);
        GridPane.setConstraints(nameLabel ,0 ,1);
        GridPane.setConstraints(durationLabel ,1 ,1);
        GridPane.setConstraints(ArrTimeLabel ,2 ,1);
        GridPane.setConstraints(priorityLabel ,3 ,1);
        GridPane.setConstraints(burstLabel ,3 ,1);

        nameList = new ListView<>();
        nameList.setEditable(false);
        nameList.maxWidth(50);
        durationList = new ListView<>();
        durationList.setEditable(false);
        durationList.maxWidth(50);
        ArrTimeList = new ListView<>();
        ArrTimeList.setEditable(false);
        ArrTimeList.maxWidth(50);
        priorityList = new ListView<>();
        priorityList.setEditable(false);
        priorityList.maxWidth(50);
        priorityList.setVisible(false);
        burstList = new ListView<>();
        burstList.setEditable(false);
        burstList.maxWidth(50);
        burstList.setVisible(false);
        GridPane.setConstraints(nameList ,0 ,2);
        GridPane.setConstraints(durationList ,1 ,2);
        GridPane.setConstraints(ArrTimeList ,2 ,2);
        GridPane.setConstraints(priorityList ,3 ,2);
        GridPane.setConstraints(burstList ,3 ,2);

        pName = new TextField();
        pName.setPromptText("Process Name");
        pDuration = new TextField();
        pDuration.setPromptText("Process Duration");
        pArrTime = new TextField();
        pArrTime.setPromptText("Arrival Time");
        pBurst = new TextField();
        pBurst.setPromptText("Burst Time");
        pBurst.setVisible(false);
        pPriority = new TextField();
        pPriority.setVisible(false);
        pPriority.setPromptText("Priority Level");
        GridPane.setConstraints(pName ,0 ,3);
        GridPane.setConstraints(pDuration ,1 ,3);
        GridPane.setConstraints(pArrTime ,2 ,3);
        GridPane.setConstraints(pPriority ,3 ,3);
        GridPane.setConstraints(pBurst ,3 ,3);

        pDuration.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pDuration.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        pArrTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    pArrTime.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        SchedulersList = new ComboBox<>();
        SchedulersList.getItems().addAll(
                "FCFS",
                "SJF",
                "Priority",
                "Round Robin"
        );

        SchedulersList.setPromptText("Select Scehduler Type ");
        SchedulersList.setOnAction(e -> {
            if(SchedulersList.getSelectionModel().getSelectedItem().equals("FCFS")){

            }
            getSelectedScheduler();
        });
        GridPane.setConstraints(SchedulersList, 0, 0);


        addProcess = new Button("Add");
        addProcess.setOnAction(e -> {

            if(  SchedulersList.getSelectionModel().isEmpty()) {

                AlertDialog.display("Empty Scheduler Type","Please Select Scheduler Type");

            } else if(SchedulersList.getSelectionModel().getSelectedItem().equals("FCFS")) {

                addNewProcess();
                pName.setText("");
                pDuration.setText("");
                pArrTime.setText("");
            }else if(SchedulersList.getSelectionModel().getSelectedItem().equals("SJF") && group.getSelectedToggle().getUserData().toString().equals("nonPreemptive")){
                addNewProcess();
                pName.setText("");
                pDuration.setText("");
                pArrTime.setText("");
            }
        });
        GridPane.setConstraints(addProcess ,0 ,4);


        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> ganttChart = new GanttChart<>(xAxis,yAxis);
        String[] process = new String[]{"Process"};

        ganttChart.setLayoutX(20);
        ganttChart.setLayoutY(80);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(javafx.scene.paint.Color.CHOCOLATE);
        xAxis.setMinorTickCount(1);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(5);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(process)));

        ganttChart.setTitle("CPU Scheduler Monitoring");
        ganttChart.setLegendVisible(false);
        ganttChart.setBlockHeight(50);


        Process = process[0];
        series1 = new XYChart.Series();

        ganttChart.getData().addAll(series1);
        ganttChart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        GridPane.setConstraints(ganttChart , 0,5);

        startButton = new Button("Start");
        startButton.setOnAction(e -> {
            if(SchedulersList.getSelectionModel().getSelectedItem().equals("FCFS")) {
                Collections.sort(processList, new Comparator<process>() {
                    @Override
                    public int compare(process lhs, process rhs) {
                        return lhs.getArrTime() > rhs.getArrTime() ? -1 : (lhs.getArrTime() < rhs.getArrTime()) ? 1 : 0;
                    }
                });
                FCFS_Schedule();
            }else if(SchedulersList.getSelectionModel().getSelectedItem().equals("SJF") && group.getSelectedToggle().getUserData().toString().equals("nonPreemptive")){
                SJF_nonPreemptive_Schedule();
            }
        });
        GridPane.setConstraints(startButton ,1 ,4);

        clearAll = new Button("reset all");
        clearAll.setOnAction(e-> {
            processCounter = 0;
            nextStart = 0;
            nameList.getItems().clear();
            durationList.getItems().clear();
            ArrTimeList.getItems().clear();
            series1.getData().clear();
            processList.clear();
            pName.setText("");
            pArrTime.setText("");
            pDuration.setText("");
        });
        GridPane.setConstraints(clearAll ,2 ,4);

        layout.getChildren().addAll(SchedulersList ,Preemptive ,nonPreemptive ,
                nameList ,durationList ,ArrTimeList ,
                nameLabel ,durationLabel ,ArrTimeLabel ,
                pName ,pDuration ,pArrTime ,
                addProcess ,ganttChart ,clearAll ,startButton ,
                priorityList ,burstList ,priorityLabel ,burstLabel ,
                pBurst ,pPriority
        );


        BorderPane border = new BorderPane();
        border.setCenter(layout);
        border.setBottom(ganttChart);

        scene = new Scene( border , 900 ,620);
        scene.getStylesheets().add("vipe.css");
        window.setScene(scene);
        window.show();
    }

    private void closeProgram(){
        boolean result = ConfrimDialog.ConfirmDialog("Exit" ,"are you sure to exit program?");
        if(result)
            window.close();
    }

    private void getSelectedScheduler(){
        String selected = SchedulersList.getValue();
        if(selected.trim().equals("FCFS")){

            Preemptive.setVisible(false);
            nonPreemptive.setVisible(false);
            priorityList.setVisible(false);
            burstList.setVisible(false);
            priorityLabel.setVisible(false);
            burstLabel.setVisible(false);
            pBurst.setVisible(false);
            pPriority.setVisible(false);

        }else if(selected.trim().equals("SJF")){

            Preemptive.setVisible(true);
            nonPreemptive.setVisible(true);
            ArrTimeList.setVisible(true);
            priorityList.setVisible(false);
            burstList.setVisible(false);
            priorityLabel.setVisible(false);
            burstLabel.setVisible(false);
            pBurst.setVisible(false);
            pPriority.setVisible(false);

        }else if(selected.trim().equals("Round Robin")){

            Preemptive.setVisible(false);
            nonPreemptive.setVisible(false);
            priorityList.setVisible(false);
            burstList.setVisible(true);
            priorityLabel.setVisible(false);
            burstLabel.setVisible(true);
            pBurst.setVisible(true);
            pPriority.setVisible(false);

        }else if(selected.trim().equals("Priority")){

            Preemptive.setVisible(true);
            nonPreemptive.setVisible(true);
            priorityList.setVisible(true);
            burstList.setVisible(false);
            priorityLabel.setVisible(true);
            burstLabel.setVisible(false);
            pBurst.setVisible(false);
            pPriority.setVisible(true);

        }
    }

    private void addNewProcess() {
            if (pName.getText().trim().toString().isEmpty() || pDuration.getText().trim().isEmpty() || pArrTime.getText().trim().isEmpty()) {

                AlertDialog.display("Empty Text Fields", "Please fill all fields");
            }
            else if (SchedulersList.getSelectionModel().isEmpty()) {

                AlertDialog.display("Empty Scheduler Type", "Please Select Scheduler Type");

            } else if (SchedulersList.getSelectionModel().getSelectedItem().equals("FCFS")) {
                processCounter++;
                nameList.getItems().add(pName.getText().trim());
                durationList.getItems().add(pDuration.getText().trim());
                ArrTimeList.getItems().add(pArrTime.getText().trim());

                process temp = new process(pName.getText(),Integer.parseInt(pDuration.getText()));
                processList.add(temp);
            }else if(SchedulersList.getSelectionModel().getSelectedItem().equals("SJF") && group.getSelectedToggle().getUserData().toString().equals("nonPreemptive")){
                processCounter++;
                nameList.getItems().add(pName.getText().trim());
                durationList.getItems().add(pDuration.getText().trim());
                ArrTimeList.getItems().add(pArrTime.getText().trim());
                process temp = new process(pName.getText(),Integer.parseInt(pDuration.getText()));
                processList.add(temp);
            }
    }

    private void FCFS_Schedule(){

        String color = "";
        nextStart =0;
        for(int i = 0 ; i < processCounter ; i++) {
            if (((processCounter - 1) % 2) == 0) {
                color = "status-red";
            } else if (((processCounter - 1) % 3) == 0) {
                color = "status-blue";
            } else {
                color = "status-green";
            }
            series1.getData().add(new XYChart.Data(nextStart, Process, new GanttChart.ExtraData(processList.get(i).getDuration(), color)));
            nextStart += processList.get(i).getDuration();
        }
    }

    private void SJF_nonPreemptive_Schedule() {

        Collections.sort(processList, new Comparator<process>() {
            @Override
            public int compare(process lhs, process rhs) {
                return lhs.getDuration() < rhs.getDuration() ? -1 : (lhs.getDuration() > rhs.getDuration()) ? 1 : 0;
            }
        });

//        processList.sort(Comparator
//                .comparing(processList.get(i))
//                .thenComparing(process::getDuration));

        nextStart = 0;
        String color = "status-red";
        for (int i = 0; i < processCounter; i++) {
            if (((processCounter - 1) % 2) == 0) {
                color = "status-red";
            } else if (((processCounter - 1) % 3) == 0) {
                color = "status-blue";
            } else {
                color = "status-green";
            }

            series1.getData().add(new XYChart.Data(nextStart, Process, new GanttChart.ExtraData(processList.get(i).getDuration(), color)));
            nextStart += processList.get(i).getDuration();

        }
    }
}







