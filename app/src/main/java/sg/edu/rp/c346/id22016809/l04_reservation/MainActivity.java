package sg.edu.rp.c346.id22016809.l04_reservation;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    EditText mobileNoInput;
    SeekBar groupSizeInput;
    TextView sizeDisplay;

    DatePicker datePicker;
    TimePicker timePicker;

    RadioGroup smokingAreaPicker;
    Button submit;
    Button reset;
    TextView finalDetailsDisplay;
    LinearLayout finalDetails;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        mobileNoInput = findViewById(R.id.phoneInput);
        groupSizeInput = findViewById(R.id.groupSizeInput);

        sizeDisplay = findViewById(R.id.sizeDisplay);

        datePicker = findViewById(R.id.date);
        timePicker = findViewById(R.id.time);

        smokingAreaPicker = findViewById(R.id.smokingRadio);

        submit = findViewById(R.id.submitButton);
        reset = findViewById(R.id.resetButton);
        finalDetailsDisplay = findViewById(R.id.finalDetailsDisplay);

        finalDetails = findViewById(R.id.finalDetails);


        groupSizeInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Progress Bar Changed", "Changing number");

                sizeDisplay.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {Log.i("onStartTrackingTouch", "touched it :3");}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {Log.i("onStopTrackingTouch", "stopped touching it :<");}
        });
        
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            Log.i("onTimeChangedListener", "hourOfTheDay: " + hourOfDay);
            if (!(8 <= hourOfDay && hourOfDay<= 20)){
                Toast.makeText(MainActivity.this, "Reservations ONLY from 0800 to 1600 (8 a.m. to 9.pm", Toast.LENGTH_LONG).show();

                timePicker.setCurrentMinute(0);
                timePicker.setCurrentHour(8);
            }
        });

        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            LocalDate today = LocalDate.now();
            Log.i("setOnDateChangedLstnr", "LocalDate.now()" + today);
            int toDaysofMonth = today.getDayOfMonth();
            int todaysMonth = today.getMonthValue();
            int todaysYear = today.getYear();

            if (!(year == todaysYear && monthOfYear == todaysMonth && dayOfMonth == toDaysofMonth+1)) {
                datePicker.updateDate(todaysYear,todaysMonth,toDaysofMonth+1);
                Toast.makeText(this, "Reservations for only Tommorrow", Toast.LENGTH_LONG).show();
            }


        });

        submit.setOnClickListener(v -> {
            //check if any input is empty
            //highlight empty inputs
            //toast

            Log.i("On Submit Click", "Check if fields are empty");

            if (nameInput.getText().length() == 0 || mobileNoInput.getText().length() == 0){
                Toast.makeText(this, "Invalid! Name and/or Mobile Number Missing", Toast.LENGTH_SHORT).show();
                return;
            }

            //resize
            if (finalDetails.getScaleX() == 0 && finalDetails.getScaleY() == 0){
                Log.i("On Submit Click", "Resized final detail layout");
                finalDetails.setScaleX(1);
                finalDetails.setScaleY(1);

                String sa = "";

                int smokingInt = smokingAreaPicker.getCheckedRadioButtonId();
                smokingAreaPicker.check(smokingInt);

                if(smokingInt == R.id.smokingArea){
                    sa = "Smoking Area";
                }else if (smokingInt == R.id.nonSmokingArea){
                    sa = "Non-Smoking Area";
                }



                finalDetailsDisplay.setText(String.format("-CHECK YOUR RESERVATION INFORMATION-\n" +
                                "(to confirm, submit again)\n\n" +
                                "Name: %s\n\n" +
                                "Mobile No.: %s\n\n" +
                                "Reservation for %d on %d/%d/%d at %d:%d, in a %s",
                        nameInput.getText(),
                        mobileNoInput.getText(),
                        groupSizeInput.getProgress(),
                        datePicker.getDayOfMonth(),datePicker.getMonth(),datePicker.getYear(),
                        timePicker.getCurrentHour(),timePicker.getCurrentMinute(), sa));
            } else{
                Log.i("On Submit Second Click", "Second Click");

                submit.setText(R.string.confirm);

                Toast.makeText(MainActivity.this, "Reservation Submitted!", Toast.LENGTH_LONG).show();
            }
            //collapse all layouts, show the text view final details
            //confirm button
        });

        reset.setOnClickListener(v -> {
            nameInput.setText("");
            mobileNoInput.setText("");
            groupSizeInput.setProgress(1);

            datePicker.updateDate(2020,5,1);

            timePicker.setCurrentHour(19);
            timePicker.setCurrentMinute(30);

            smokingAreaPicker.clearCheck();
        });
    }
}
