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

public class Time extends AppCompatActivity {


    private EditText editHours;
    private EditText editMinutes;
    private EditText editSeconds;
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
        setContentView(R.layout.activity_time);

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

        ImageButton buttonClear = findViewById(R.id.clearBtn);
        buttonClear.setOnClickListener(v -> clearFields());

        ImageButton backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(v -> onBackPressed());

        editHours = findViewById(R.id.hours);
        editMinutes = findViewById(R.id.minutes);
        editSeconds = findViewById(R.id.seconds);

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
                if (focusedViewId == R.id.hours) {
                    v.setBackgroundResource(R.drawable.field);
                }
            } else {

                ((EditText) findViewById(focusedViewId)).removeTextChangedListener(textWatcher);
                if (focusedViewId != R.id.hours) {
                    v.setBackgroundResource(R.drawable.field);
                }
            }
            v.setBackgroundResource(R.drawable.field);
        };

        editHours.setOnFocusChangeListener(onFocusChangeListener);
        editMinutes.setOnFocusChangeListener(onFocusChangeListener);
        editSeconds.setOnFocusChangeListener(onFocusChangeListener);

    }

    private void clearFields(){
        editHours.setText("");
        editMinutes.setText("");
        editSeconds.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    private void convert() {
        try {
            double num;
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###.#####");

            switch (focusedViewId){

                case R.id.hours:
                    num = Double.parseDouble(value);

                    editMinutes.setText(decimalFormat.format((num * 60)));
                    editSeconds.setText(decimalFormat.format((num * 3600)));
                    break;

                case R.id.minutes:
                    num = Double.parseDouble(value);

                    editHours.setText(decimalFormat.format((num / 60)));
                    editSeconds.setText(decimalFormat.format((num * 60)));
                    break;

                case R.id.seconds:
                    num = Double.parseDouble(value);

                    editHours.setText(decimalFormat.format((num / 3600)));
                    editMinutes.setText(decimalFormat.format((num / 60)));
                    break;
            }

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}