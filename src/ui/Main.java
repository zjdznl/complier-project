package ui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import latex.MyLatex;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FlowPane root = new FlowPane();
        root.setHgap(10);
        root.setVgap(20);
        root.setPadding(new Insets(15,15,15,15));
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        final TextArea text = new TextArea("int i = 0;\ni = 1 + 5.12e+15");
        text.setStyle("-fx-highlight-fill: #d3ca47; -fx-highlight-text-fill: firebrick; -fx-font-size: 20px;");
        Button btn=new Button("Start");
        root.getChildren().add(btn);
        root.getChildren().add(text);
        text.selectRange(4,5);
//        text.setEditable(false);
//        text.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
//            @Override public void handle(MouseEvent t) { t.consume(); }
//        });
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str=text.getText();
                //动画线程
                Task<Void> progressTask = new Task<Void>(){
                    @Override
                    protected Void call() throws Exception {
                        MyLatex analyze=new MyLatex(str);
                        analyze.LetexAnalyze();
                        analyze.GetAnimatePos();
                        for (int i = 0; i <= analyze.animatePos.size(); i++) {
                            int tempX=analyze.animatePos.get(i).x;
                            int tempY=analyze.animatePos.get(i).y;
                            System.out.println(tempX+"-"+tempY);
                            text.selectRange(tempX-1,tempY);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        updateMessage("Finish");
                        return null;
                    }
                };
                new Thread(progressTask).start();
            }
        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root,500,500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}