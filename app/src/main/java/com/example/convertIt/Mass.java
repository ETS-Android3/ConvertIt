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

public class Mass extends AppCompatActivity {


    private EditText editMilligrams;
    private EditText editGrams;
    private EditText editKilogram;
    private EditText editTons;
    private EditText editPounds;
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
        setContentView(R.layout.activity_mass);

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

        editMilligrams = findViewById(R.id.milligram);
        editGrams = findViewById(R.id.grams);
        editKilogram = findViewById(R.id.kilogram);
        editTons = findViewById(R.id.tons);
        editPounds = findViewById(R.id.pounds);

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
                if (focusedViewId == R.id.milligram) {
                    v.setBackgroundResource(R.drawable.field);
                }
            } else {

                ((EditText) findViewById(focusedViewId)).removeTextChangedListener(textWatcher);
                if (focusedViewId != R.id.milligram) {
                    v.setBackgroundResource(R.drawable.field);
                }
            }
            v.setBackgroundResource(R.drawable.field);
        };

        editMilligrams.setOnFocusChangeListener(onFocusChangeListener);
        editGrams.setOnFocusChangeListener(onFocusChangeListener);
        editKilogram.setOnFocusChangeListener(onFocusChangeListener);
        editTons.setOnFocusChangeListener(onFocusChangeListener);
        editPounds.setOnFocusChangeListener(onFocusChangeListener);

    }

    private void clearFields(){
        editMilligrams.setText("");
        editGrams.setText("");
        editKilogram.setText("");
        editTons.setText("");
        editPounds.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    private void convert() {
        try {
            double num;
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###.###########");

            switch (focusedViewId){

                case R.id.milligram:
                    num = Double.parseDouble(value);

                    editGrams.setText(decimalFormat.format((num/1000)));
                    editKilogram.setText(decimalFormat.format((num/1000000)));
                    editTons.setText(decimalFormat.format((num/1000000000)));
                    editPounds.setText(decimalFormat.format((num*0.0000022)));
                    break;

                case R.id.grams:
                    num = Double.parseDouble(value);

                    editMilligrams.setText(decimalFormat.format((num*1000)));
                    editKilogram.setText(decimalFormat.format((num/1000)));
                    editTons.setText(decimalFormat.format((num/1000000)));
                    editPounds.setText(decimalFormat.format((num*0.00220462)));
                    break;

                case R.id.kilogram:
                    num = Double.parseDouble(value);

                    editMilligrams.setText(decimalFormat.format((num*1000000)));
                    editGrams.setText(decimalFormat.format((num*1000)));
                    editTons.setText(decimalFormat.format((num/1000)));
                    editPounds.setText(decimalFormat.format((num*2.205)));
                    break;

                case R.id.tons:
                    num = Float.parseFloat(value);

                    editMilligrams.setText(decimalFormat.format((num*1000000000)));
                    editGrams.setText(decimalFormat.format((num*1000000)));
                    editKilogram.setText(decimalFormat.format((num*1000)));
                    editPounds.setText(decimalFormat.format((num*2205)));
                    break;

                case R.id.pounds:
                    num = Double.parseDouble(value);

                    editMilligrams.setText(decimalFormat.format((num*453592.37)));
                    editGrams.setText(decimalFormat.format((num*453.59237)));
                    editKilogram.setText(decimalFormat.format((num*0.453592)));
                    editTons.setText(decimalFormat.format((num*0.00045359237)));
                    break;
            }

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}