package com.msujayappdev.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    Button button, switcher, clear;
    EditText editText;
    ImageView leftImg, rightImg;
    int[] setting = new int[]{0};
    long back_pressed;

    // Press back button twice to exit
    @Override
    public void onBackPressed() {
        if (back_pressed + 1000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Press again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.resultText); // output text box
        button = findViewById(R.id.button); // convert button
        switcher = findViewById(R.id.switcher); // conversion mode switcher button
        clear = findViewById(R.id.clear); // button to clear I/O fields
        editText = findViewById(R.id.editText); // input text box
        leftImg = (ImageView) findViewById(R.id.celsius); // image for input unit
        rightImg = (ImageView) findViewById(R.id.fahrenheit); // image for output unit

        // Button to clear I/O fields
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText.setText("");
                editText.setText("");
            }
        });

        // Button to switch between conversion modes
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch from Celsius->Fahrenheit to Fahrenheit->Celsius
                if(setting[0]==0) {
                    setting[0]=1;
                    leftImg.setImageResource(R.drawable.fahrenheit_vector);
                    rightImg.setImageResource(R.drawable.celsius_vector);
                }
                // Switch from Fahrenheit->Celsius to Celsius->Fahrenheit
                else if (setting[0]==1) {
                    setting[0]=0;
                    leftImg.setImageResource(R.drawable.celsius_vector);
                    rightImg.setImageResource(R.drawable.fahrenheit_vector);
                }
                // Clear I/O fields
                resultText.setText("");
                editText.setText("");
            }
        });
        }

        // Convert function attached to Convert button
        public void convert(View view) {
            double converted = 0.0;
            String unit="";
            // If input field is empty, show message
            if(TextUtils.isEmpty(editText.getText().toString())){
                resultText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                resultText.setText("Enter a value to convert!");
            }
            else{
                String s = editText.getText().toString();
                double temperature = Double.parseDouble(s);
                // Celsius to Fahrenheit mode
                if(setting[0]==0) {
                    converted = 1.8 * temperature + 32;
                    unit = "℉";
                }
                // Fahrenheit to Celsius mode
                else if(setting[0]==1){
                    converted = (5/9.0) * (temperature - 32) ;
                    unit = "℃";
                }
                converted = Math.round(converted*100)/100.0;
                resultText.setTypeface(Typeface.DEFAULT_BOLD);
                resultText.setText("Temperature in " + unit + ": " + converted);
//                Toast.makeText(this, "Converted", Toast.LENGTH_SHORT).show();
            }

            // Close keyboard on conversion and show toast if input field is empty
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                Toast.makeText(this, "Enter a value to convert!", Toast.LENGTH_SHORT).show();
            }
    }
}