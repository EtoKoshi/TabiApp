package tabi.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import com.gluonhq.maps.MapView;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapLayer;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

/**
 * MainController — MapView を使うバージョン
 */
public class MainController {

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

        // ---- 自動計算リスナ ----
        TextFieldA.textProperty().addListener((obs, oldV, newV) -> calc());
        TextFieldB.textProperty().addListener((obs, oldV, newV) -> calc());
        thkw.textProperty().addListener((obs, oldV, newV) -> calc());

        // ---- MapView の初期化（例：京都駅を中心に） ----
         MapPoint kyoto = new MapPoint(34.985849, 135.758766);
        mapView.setCenter(kyoto);
        mapView.setZoom(12); 

        // 簡単なマーカーレイヤーを追加
        mapView.addLayer(new MarkerLayer(kyoto));
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

    // 簡易マーカーレイヤー（MapLayer を継承）
    private static class MarkerLayer extends MapLayer {
        private final MapPoint point;
        private final Circle circle;

        public MarkerLayer(MapPoint point) {
            this.point = point;
            this.circle = new Circle(6); // サイズだけ指定（色は CSS でも可）
            // 任意で色を指定したければ circle.setFill(Color.RED);
            getChildren().add(circle);
        }

        @Override
        protected void layoutLayer() {
            // <-- 修正済み: MapPoint を直接渡すのではなく緯度・経度を渡す
            Point2D p = getMapPoint(point.getLatitude(), point.getLongitude());
            circle.setTranslateX(p.getX());
            circle.setTranslateY(p.getY());
        }
    }
}
