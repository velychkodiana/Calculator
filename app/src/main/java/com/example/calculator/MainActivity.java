package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import java.util.Locale;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.Expression;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CalculatorApp"; // Статичний тег для логів
    private TextView display;
    private String currentInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Запущено головну активність"); // Повідомлення про запуск

        display = findViewById(R.id.display);

        // Визначення кнопок
        Button acButton = findViewById(R.id.button_ac);
        Button cButton = findViewById(R.id.button_c);
        Button equalButton = findViewById(R.id.button_equal);
        Button exitButton = findViewById(R.id.button_exit);
        Button changelanguageButton = findViewById(R.id.button_change_language);

        // Налаштування тексту для кнопок із ресурсів
        acButton.setText(getString(R.string.button_ac));
        cButton.setText(getString(R.string.button_c));
        equalButton.setText(getString(R.string.button_equal));
        exitButton.setText(getString(R.string.button_exit));
        changelanguageButton.setText(getString(R.string.button_change_language));
        display.setText(getString(R.string.default_display));

        changelanguageButton.setOnClickListener(v -> {
            String currentLanguage = Locale.getDefault().getLanguage();
            Log.d(TAG, "Зміна мови: поточна мова - " + currentLanguage);
            if (currentLanguage.equals("uk")) {
                setLanguage("en");
            } else if (currentLanguage.equals("en")) {
                setLanguage("uk");
            }
            Log.i(TAG, "Мова змінена на: " + Locale.getDefault().getLanguage());
            recreate();
        });

        // Кнопка AC (очистити)
        acButton.setOnClickListener(v -> {
            currentInput = "";
            display.setText(getString(R.string.default_display));
            Log.d(TAG, "Кнопка AC: дисплей очищено");
        });

        // Кнопка C (стерти останній символ)
        cButton.setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(currentInput.isEmpty() ? getString(R.string.default_display) : currentInput);
                Log.d(TAG, "Кнопка C: останній символ стерто, поточний ввід - " + currentInput);
            } else {
                Log.w(TAG, "Кнопка C: нічого видаляти");
            }
        });

        // Кнопка "="
        equalButton.setOnClickListener(v -> {
            try {
                double result = evaluateExpression(currentInput);
                display.setText(formatResult(result));
                currentInput = formatResult(result);
                Log.i(TAG, "Обчислення успішне: " + result);
            } catch (Exception e) {
                display.setText(getString(R.string.error_message));
                Log.e(TAG, "Помилка під час обчислення: " + e.getMessage(), e);
            }
        });

        // Кнопка Exit (вихід)
        exitButton.setOnClickListener(v -> {
            Log.d(TAG, "Кнопка Exit: вихід із застосунку");
            finish();
        });

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
        setButtonListener(R.id.button_multiply, "×");
        setButtonListener(R.id.button_divide, "÷");
        setButtonListener(R.id.button_pi, "π");
        setButtonListener(R.id.button_sin, "sin(");
        setButtonListener(R.id.button_cos, "cos(");
        setButtonListener(R.id.button_tan, "tan(");
        setButtonListener(R.id.button_log, "log(");
        setButtonListener(R.id.button_factorial, "!");
        setButtonListener(R.id.button_square, "^2");
        setButtonListener(R.id.button_reciprocal, "1/");
        setButtonListener(R.id.button_sqrt, "√(");
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
            display.setText(currentInput);
            Log.d(TAG, "Натиснуто кнопку: " + value + ", поточний ввід - " + currentInput);
        });
    }

    private void setLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        Log.i(TAG, "Мова змінена на: " + languageCode);
    }

    private double evaluateExpression(String expression) {
        expression = expression.replace("π", String.valueOf(Math.PI));
        Expression expr = new Expression(expression);
        if (!expr.checkSyntax()) {
            Log.e(TAG, "Невірний синтаксис виразу: " + expression);
            throw new IllegalArgumentException("Invalid expression");
        }
        Log.d(TAG, "Обчислення виразу: " + expression);
        return expr.calculate();
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return String.valueOf(result);
        }
    }
}