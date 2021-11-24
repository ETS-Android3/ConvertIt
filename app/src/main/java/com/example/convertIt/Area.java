package com.example.convertIt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DecimalFormat;

public class Area extends AppCompatActivity {


    private EditText editSquareMeter;
    private EditText editSquareFeet;
    private EditText editSquareYards;
    private EditText editAcres;
    private EditText editHectares;
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
        setContentView(R.layout.activity_area);

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

        editSquareMeter = findViewById(R.id.squareMeter);
        editSquareFeet = findViewById(R.id.squareFeet);
        editSquareYards = findViewById(R.id.squareYard);
        editAcres = findViewById(R.id.acres);
        editHectares = findViewById(R.id.hectares);

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
                if (focusedViewId == R.id.squareMeter) {
                    v.setBackgroundResource(R.drawable.field);
                }
            } else {

                ((EditText) findViewById(focusedViewId)).removeTextChangedListener(textWatcher);
                if (focusedViewId != R.id.squareMeter) {
                    v.setBackgroundResource(R.drawable.field);
                }
            }
            v.setBackgroundResource(R.drawable.field);
        };

        editSquareMeter.setOnFocusChangeListener(onFocusChangeListener);
        editSquareFeet.setOnFocusChangeListener(onFocusChangeListener);
        editSquareYards.setOnFocusChangeListener(onFocusChangeListener);
        editAcres.setOnFocusChangeListener(onFocusChangeListener);
        editHectares.setOnFocusChangeListener(onFocusChangeListener);

    }

    private void clearFields(){
        editSquareMeter.setText("");
        editSquareFeet.setText("");
        editSquareYards.setText("");
        editAcres.setText("");
        editHectares.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    private void convert() {
        try {
            double num;
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###.###########");

            switch (focusedViewId){

                case R.id.squareMeter:
                    num = Double.parseDouble(value);

                    editSquareFeet.setText(decimalFormat.format((num*10.764)));
                    editSquareYards.setText(decimalFormat.format((num*1.196)));
                    editAcres.setText(decimalFormat.format((num*0.000247)));
                    editHectares.setText(decimalFormat.format((num/10000)));
                    break;

                case R.id.squareFeet:
                    num = Double.parseDouble(value);

                    editSquareMeter.setText(decimalFormat.format((num/10.764)));
                    editSquareYards.setText(decimalFormat.format((num/9)));
                    editAcres.setText(decimalFormat.format((num/43560)));
                    editHectares.setText(decimalFormat.format((num/107639)));
                    break;

                case R.id.squareYard:
                    num = Double.parseDouble(value);

                    editSquareMeter.setText(decimalFormat.format((num* 0.836127)));
                    editSquareFeet.setText(decimalFormat.format((num*9)));
                    editAcres.setText(decimalFormat.format((num/4840)));
                    editHectares.setText(decimalFormat.format((num/11960)));
                    break;

                case R.id.acres:
                    num = Double.parseDouble(value);

                    editSquareMeter.setText(decimalFormat.format((num*4046.856422)));
                    editSquareFeet.setText(decimalFormat.format((num*43560)));
                    editSquareYards.setText(decimalFormat.format((num*4840)));
                    editHectares.setText(decimalFormat.format((num*0.404686)));
                    break;

                case R.id.hectares:
                    num = Double.parseDouble(value);

                    editSquareMeter.setText(decimalFormat.format((num* 10000)));
                    editSquareFeet.setText(decimalFormat.format((num*107639)));
                    editSquareYards.setText(decimalFormat.format((num*11960)));
                    editAcres.setText(decimalFormat.format((num*2.471)));
                    break;
            }

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}