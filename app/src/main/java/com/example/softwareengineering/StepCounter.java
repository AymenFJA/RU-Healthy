package com.example.softwareengineering;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DecimalFormat;


public class StepCounter extends AppCompatActivity implements SensorEventListener,StepListener {

    private TextView TvMiles;
    private TextView TvCalories;
    private CircularProgressBar circularProgressBar1;



    private  StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;

    private static final String TEXT_Miles_STEPS = "\nMile";
    private static final String TEXT_Miles_Calorie ="\nKcal";

    private int numSteps;
    private double miles;
    private double calories;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_count);

        //Get Sensor Values using the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);



        //TvSteps=(TextView)findViewById(R.id.tv_steps);
        TvMiles=(TextView) findViewById(R.id.tv_miles);
        TvCalories=(TextView) findViewById(R.id.tv_calories);


//For The ProgressBar
        circularProgressBar1 = (CircularProgressBar) findViewById(R.id.circularProgress);
        circularProgressBar1.setProgressColor(getResources().getColor(R.color.colorPrimaryDark));
        circularProgressBar1.setProgressWidth(30);


//THIS METHOD IS TO MAKE THE COUNTER RUN AUTO AND TO WORK IN THE BACKGROUND// BY AYMEN
        numSteps=0;
        sensorManager.registerListener(StepCounter.this, accel, SensorManager.SENSOR_DELAY_FASTEST);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void step(long timeNs) {

        numSteps++;
        miles=(numSteps*(0.001))*0.6;
        calories=numSteps*0.1;
        DecimalFormat mm = new DecimalFormat("#.##");
        //DecimalFormat ss = new DecimalFormat("#");
        DecimalFormat cc = new DecimalFormat("#");


        circularProgressBar1.setProgress(numSteps);
        TvMiles.setText((mm.format(miles))+TEXT_Miles_STEPS);
        TvCalories.setText((cc.format(calories))+TEXT_Miles_Calorie);

        //return numSteps;

    }




}
