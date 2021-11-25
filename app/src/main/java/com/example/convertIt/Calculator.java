package com.example.convertIt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Calculator extends AppCompatActivity {


    Button number0, number1, number2, number3, number4, number5, number6, number7, number8, number9, clear, percent, divide, multiply, minus, plus,  dot, equals, delete, parenthesis;
    TextView inputText, outputText;
    String process;
    boolean checkP = false;


    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        View decor_View = getWindow().getDecorView();
        int ui_Options = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decor_View.setSystemUiVisibility(ui_Options);
        setContentView(R.layout.activity_calculator);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.nav_calculator);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {

                case R.id.nav_home:
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_calculator:
                    return true;
            }
            return false;
        });

        ImageButton backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(v -> onBackPressed());


        number0 = findViewById(R.id.number0);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        number6 = findViewById(R.id.number6);
        number7 = findViewById(R.id.number7);
        number8 = findViewById(R.id.number8);
        number9 = findViewById(R.id.number9);

        clear = findViewById(R.id.clear);
        parenthesis = findViewById(R.id.parenthesis);
        percent = findViewById(R.id.percent);
        divide = findViewById(R.id.divide);
        multiply = findViewById(R.id.multiply);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        delete = findViewById(R.id.delete);
        dot = findViewById(R.id.dot);
        equals = findViewById(R.id.equals);

        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);

        clear.setOnClickListener(view -> {
            inputText.setText("");
            outputText.setText("");
        });

        number0.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "0");
        });

        number1.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "1");
        });

        number2.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "2");
        });

        number3.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "3");
        });

        number4.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "4");
        });

        number5.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "5");
        });

        number6.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "6");
        });

        number7.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "7");
        });

        number8.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "8");
        });

        number9.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "9");
        });

        divide.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "÷");
        });

        multiply.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "×");
        });

        minus.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "-");
        });

        plus.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "+");
        });

        dot.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + ".");
        });

        percent.setOnClickListener(view -> {
            process = inputText.getText().toString();
            inputText.setText(process + "%");
        });

        equals.setOnClickListener(view -> {
            process = inputText.getText().toString();

            process = process.replaceAll("×", "*");
            process = process.replaceAll("%", "/100");
            process = process.replaceAll("÷", "/");

            Context rhino = Context.enter();
            rhino.setOptimizationLevel(-1);

            String finalResult;

            try {
                Scriptable scriptable = rhino.initStandardObjects();
                finalResult = rhino.evaluateString(scriptable, process, "javascript", 1, null).toString();
            } catch (Exception e) {
                finalResult = "0";
            }
            outputText.setText(finalResult);

        });

        delete.setOnClickListener(view -> {
            String value = inputText.getText().toString();
            int input = value.length();
            if (input > 0) {
                inputText.setText(value.substring(0, input - 1));
            }
           else{
                outputText.setText("");
            }
        });

        parenthesis.setOnClickListener(view -> {
            if (checkP) {
                process = inputText.getText().toString();
                inputText.setText(process + ")");
                checkP = false;
            } else {
                process = inputText.getText().toString();
                inputText.setText(process + "(");
                checkP = true;
            }
        });


    }
}