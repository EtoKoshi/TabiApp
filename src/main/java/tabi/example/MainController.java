package tabi.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class MainController {

    @FXML
    private WebView Map;

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

        // ---- 追加部分：入力するたびに自動計算 ----
        TextFieldA.textProperty().addListener((obs, oldV, newV) -> calc());
        TextFieldB.textProperty().addListener((obs, oldV, newV) -> calc());
        thkw.textProperty().addListener((obs, oldV, newV) -> calc());
        String url = getClass().getResource("/tabi/example/index.html").toExternalForm();
        Map.getEngine().load(url);
    }

    // ---- 追加：自動計算メソッド ----
    private void calc() {
        try {
            int numA = Integer.parseInt(TextFieldA.getText());
            int numB = Integer.parseInt(TextFieldB.getText());
            String op = thkw.getText(); // ← 演算子取得

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
            // 数字未入力・不正入力のときは結果を消す
            Answer.setText("");
        }
    }
}
