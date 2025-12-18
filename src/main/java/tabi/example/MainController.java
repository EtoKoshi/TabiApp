package tabi.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import com.gluonhq.maps.MapView;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapLayer;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.stage.FileChooser;

/**
 * MainController — MapView を使うバージョン
 */
public class MainController {

    @FXML
    private Button hozon;

    @FXML
    private Button risetto;

    @FXML
    private MapView mapView;

    @FXML
    private Label kigou;

    @FXML
    private TextField thkw;

    @FXML
    private TextField TextFieldA;

    @FXML
    private TextField TextFieldB;

    @FXML
    private Label Answer;

    @FXML
    private TextArea memo;

    @FXML
    private void initialize() {

        // ---- 自動計算 ----
        TextFieldA.textProperty().addListener((obs, o, n) -> calc());
        TextFieldB.textProperty().addListener((obs, o, n) -> calc());
        thkw.textProperty().addListener((obs, o, n) -> calc());

        // ---- MapView 初期化（京都駅） ----
        MapPoint kyoto = new MapPoint(34.985849, 135.758766);
        mapView.setCenter(kyoto);
        mapView.setZoom(12);
        mapView.addLayer(new MarkerLayer(kyoto));

        // ---- 保存（ファイル保存）----
        hozon.setOnAction(e -> saveMemoToFile());

        // ---- リセット ----
        risetto.setOnAction(e -> memo.clear());
    }

    // memo をファイルに保存
    private void saveMemoToFile() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("メモを保存");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("テキストファイル", "*.txt")
        );

        File file = chooser.showSaveDialog(memo.getScene().getWindow());
        if (file == null) return;

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(memo.getText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void calc() {
        try {
            int numA = Integer.parseInt(TextFieldA.getText());
            int numB = Integer.parseInt(TextFieldB.getText());
            String op = thkw.getText();

            int result;

            switch (op) {
                case "+":
                    result = numA + numB;
                    break;
                case "-":
                    result = numA - numB;
                    break;
                case "*":
                case "×":
                    result = numA * numB;
                    break;
                case "/":
                case "÷":
                    result = numA / numB;
                    break;
                default:
                    Answer.setText("不明な記号: " + op);
                    return;
            }

            Answer.setText(String.valueOf(result));
        } catch (Exception e) {
            Answer.setText("");
        }
    }

    // 簡易マーカーレイヤー
    private static class MarkerLayer extends MapLayer {

        private final Circle circle;

        public MarkerLayer(MapPoint point) {
            this.circle = new Circle(6);
            getChildren().add(circle);
        }

        @Override
        protected void layoutLayer() {
            // 今回は未使用
        }
    }
}
