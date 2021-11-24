package com.example.convertIt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DecimalFormat;


public class Length extends AppCompatActivity {


    private EditText editMeter;
    private EditText editKilometer;
    private EditText editInches;
    private EditText editFeet;
    private EditText editYard;
    private String value;
    private int focusedViewId;
    private TextWatcher textWatcher;


    @SuppressLint("NonConstantResourceId")
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
        setContentView(R.layout.activity_length);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {

                case R.id.nav_home:
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.nav_calculator:
                    startActivity(new Intent(getApplicationContext(), Calculator.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        editMeter = findViewById(R.id.meter);
        editKilometer = findViewById(R.id.kilometer);
        editInches = findViewById(R.id.inches);
        editFeet = findViewById(R.id.feet);
        editYard = findViewById(R.id.yard);


        ImageButton buttonClear = findViewById(R.id.clearBtn);
        buttonClear.setOnClickListener(v -> clearFields());

        ImageButton backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(v -> onBackPressed());

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                value = ((EditText) findViewById(focusedViewId)).getText().toString().trim();

                if (value.length() > 0) {
                    convert();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        View.OnFocusChangeListener onFocusChangeListener = (v, hasFocus) -> {
            if (hasFocus) {
                focusedViewId = v.getId();
                ((EditText) findViewById(focusedViewId)).addTextChangedListener(textWatcher);
                if (focusedViewId == R.id.meter) {
                    v.setBackgroundResource(R.drawable.field);
                }
            } else {

                ((EditText) findViewById(focusedViewId)).removeTextChangedListener(textWatcher);
                if (focusedViewId != R.id.meter) {
                    v.setBackgroundResource(R.drawable.field);
                }
            }
            v.setBackgroundResource(R.drawable.field);
        };

        editMeter.setOnFocusChangeListener(onFocusChangeListener);
        editKilometer.setOnFocusChangeListener(onFocusChangeListener);
        editInches.setOnFocusChangeListener(onFocusChangeListener);
        editFeet.setOnFocusChangeListener(onFocusChangeListener);
        editYard.setOnFocusChangeListener(onFocusChangeListener);

    }

    private void clearFields(){
        editMeter.setText("");
        editKilometer.setText("");
        editInches.setText("");
        editFeet.setText("");
        editYard.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    private void convert() {
        try {
            double num;
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###.#######");

            switch (focusedViewId){

                case R.id.meter:
                    num = Double.parseDouble(value);

                    editKilometer.setText(decimalFormat.format((num/1000)));
                    editInches.setText(decimalFormat.format((num*39.37)));
                    editFeet.setText(decimalFormat.format((num*3.281)));
                    editYard.setText(decimalFormat.format((num*1.094)));
                    break;

                case R.id.kilometer:
                    num = Double.parseDouble(value);

                    editMeter.setText(decimalFormat.format((num*1000.00)));
                    editInches.setText(decimalFormat.format(num*39370.00));
                    editFeet.setText(decimalFormat.format((num*3281.00)));
                    editYard.setText(decimalFormat.format((num*1094.00)));
                    break;

                case R.id.inches:
                    num = Double.parseDouble(value);
                    
                    editMeter.setText(decimalFormat.format((num/39.37)));
                    editKilometer.setText(decimalFormat.format((num* 0.0000254)));
                    editFeet.setText(decimalFormat.format((num/12)));
                    editYard.setText(decimalFormat.format((num/36)));
                    break;

                case R.id.feet:
                    num = Double.parseDouble(value);

                    editMeter.setText(decimalFormat.format((num/3.281)));
                    editKilometer.setText(decimalFormat.format((num*0.000305)));
                    editInches.setText(decimalFormat.format((num*12)));
                    editYard.setText(decimalFormat.format((num/3)));
                    break;

                case R.id.yard:
                    num = Double.parseDouble(value);

                    editMeter.setText(decimalFormat.format((num/1.0936)));
                    editKilometer.setText(decimalFormat.format((num*0.000914)));
                    editInches.setText(decimalFormat.format((num*36)));
                    editFeet.setText(decimalFormat.format((num*3)));
                    break;
            }

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}






