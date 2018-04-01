import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.GanttChart;

public class GanttChartSample extends Application {

    @Override public void start(Stage stage) {

        stage.setTitle("Gantt Chart Sample");

        String[] process = new String[]{"Process"};

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<>(xAxis,yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(1);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(1);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(process)));

        chart.setTitle("CPU Scheduler Monitoring");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 20);
        String Process;

        Process = process[0];

        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data(0, Process, new GanttChart.ExtraData( 1, "status-red")));
        series1.getData().add(new XYChart.Data(1, Process, new GanttChart.ExtraData( 1, "status-green")));
        series1.getData().add(new XYChart.Data(2, Process, new GanttChart.ExtraData( 1, "status-red")));
        series1.getData().add(new XYChart.Data(3, Process, new GanttChart.ExtraData( 1, "status-green")));


        chart.getData().addAll(series1);

        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        Scene scene  = new Scene(chart,620,350);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}