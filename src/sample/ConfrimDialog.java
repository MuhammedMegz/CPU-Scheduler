package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfrimDialog {

    static boolean result;


    static  public boolean ConfirmDialog (String title ,String message){

        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        window.setMinHeight(125);
        window.setMaxHeight(125);
        window.setMaxWidth(250);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(8);
        layout.setHgap(10);


        Button button = new Button("Yes");
        GridPane.setConstraints(button , 0 ,1);
        button.setOnAction(e -> {
            result = true;
            window.close();
        });

        Button button1 = new Button("No");
        GridPane.setConstraints(button1 , 1 ,1 );
        button1.setOnAction(e -> {
            result = false;
            window.close();
        });

        Label label = new Label(message);
        GridPane.setConstraints(label , 0,0);

        layout.getChildren().addAll(button ,button1 ,label);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}
