package tabi.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    private Label kigou;


    @FXML
    private TextField thkw;  // ← ここに演算子を入れる

    @FXML
    private Button keisanButoon;

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

        keisanButoon.setOnAction((e) -> {
            int numA = Integer.parseInt(TextFieldA.getText());
            int numB = Integer.parseInt(TextFieldB.getText());
            String op = thkw.getText();   // ← 演算子取得

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
        });
    }
}
