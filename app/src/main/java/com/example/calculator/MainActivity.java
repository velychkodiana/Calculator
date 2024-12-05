package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Визначення кнопок
        Button acButton = findViewById(R.id.button_ac);
        Button cButton = findViewById(R.id.button_c);
        Button equalButton = findViewById(R.id.button_equal);
        Button exitButton = findViewById(R.id.button_exit);

        // Кнопка AC (очистити)
        acButton.setOnClickListener(v -> {
            currentInput = "";
            display.setText("0");
        });

        // Кнопка C (стерти останній символ)
        cButton.setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(currentInput.isEmpty() ? "0" : currentInput);
            }
        });

        // Кнопка "="
        equalButton.setOnClickListener(v -> {
            try {
                double result = evaluateExpression(currentInput);
                display.setText(formatResult(result));
                currentInput = formatResult(result); // Зберігаємо результат як поточний ввід
            } catch (Exception e) {
                display.setText("Error");
            }
        });

        // Кнопка Exit (вихід)
        exitButton.setOnClickListener(v -> finish());

        // Налаштування кнопок
        setButtonListener(R.id.button_1, "1");
        setButtonListener(R.id.button_2, "2");
        setButtonListener(R.id.button_3, "3");
        setButtonListener(R.id.button_4, "4");
        setButtonListener(R.id.button_5, "5");
        setButtonListener(R.id.button_6, "6");
        setButtonListener(R.id.button_7, "7");
        setButtonListener(R.id.button_8, "8");
        setButtonListener(R.id.button_9, "9");
        setButtonListener(R.id.button_0, "0");
        setButtonListener(R.id.button_dot, ".");
        setButtonListener(R.id.button_plus, "+");
        setButtonListener(R.id.button_minus, "-");
        setButtonListener(R.id.button_multiply, "*");
        setButtonListener(R.id.button_divide, "/");
        setButtonListener(R.id.button_pi, "π");
        setButtonListener(R.id.button_sin, "sin(");
        setButtonListener(R.id.button_cos, "cos(");
        setButtonListener(R.id.button_tan, "tan(");
        setButtonListener(R.id.button_log, "log(");
        setButtonListener(R.id.button_factorial, "!");
        setButtonListener(R.id.button_square, "^2");
        setButtonListener(R.id.button_reciprocal, "1/");
        setButtonListener(R.id.button_sqrt, "sqrt(");
        setButtonListener(R.id.button_left_paren, "(");
        setButtonListener(R.id.button_right_paren, ")");
    }

    private void setButtonListener(int buttonId, String value) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            if (value.equals("π")) {
                currentInput += Math.PI; // Додаємо значення π
            } else {
                currentInput += value; // Додаємо вибраний символ
            }
            display.setText(currentInput); // Оновлюємо текст на екрані
        });
    }

    private double evaluateExpression(String expression) {
        // Заміняємо функції на відповідні математичні вирази
        expression = expression.replace("π", String.valueOf(Math.PI));
        expression = expression.replace("sqrt", "sqrt");
        expression = expression.replace("sin", "sin");
        expression = expression.replace("cos", "cos");
        expression = expression.replace("tan", "tan");
        expression = expression.replace("log", "log10");

        // Обчислення через бібліотеку mxparser
        Expression expr = new Expression(expression);
        if (!expr.checkSyntax()) {
            throw new IllegalArgumentException("Invalid expression");
        }
        return expr.calculate();
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result); // Якщо ціле число, видаляємо дробову частину
        } else {
            return String.valueOf(result);
        }
    }
}
