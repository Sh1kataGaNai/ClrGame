package com.example.a.clrgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.CountDownTimer;
import android.app.AlertDialog;

import java.util.HashMap;

import android.widget.ProgressBar;
import android.widget.TextView;






public class MainActivity extends AppCompatActivity  {

    private GameManager gm;
    private ProgressBar time;
    private TextView answers;
    private TextView first_color;
    private TextView second_color;
    private CountDownTimer timer;
    private boolean isRunning;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first_color = (TextView) findViewById(R.id.textColorName);
        second_color = (TextView) findViewById(R.id.textColorHex);
        answers = (TextView) findViewById(R.id.textViewTrueAnswers);
        time = (ProgressBar) findViewById(R.id.progressBarTimeLeft);

        //Check email for pass into ResultsActivity

        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            email = arguments.get("email").toString();

        timer = null;
        gm = new GameManager();

        if (savedInstanceState != null) {

            gm.restoreSavedState(savedInstanceState.getInt("deadline"),
                    savedInstanceState.getInt("current_time"),
                    (HashMap<String, String>) savedInstanceState.getSerializable("colors"),
                    savedInstanceState.getInt("answers"));
            isRunning = savedInstanceState.getBoolean("is_running");
            email = savedInstanceState.getString("email");
            greetingsAndStart();
        }
        else {
            isRunning = false;
            greetingsAndStart();

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("current_time", gm.getCurrentGameTime());
        bundle.putInt("deadline", gm.getDeadlineSeconds());
        bundle.putInt("answers", gm.getTrueAnswers());
        bundle.putSerializable("colors", gm.getAllColors());
        bundle.putBoolean("is_running", isRunning);
        bundle.putString("email", email);
        if(timer != null)
            timer.cancel();
        gm = null;
    }



    protected void greetingsAndStart() {

        if(!isRunning) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("ClrGame");
            alertDialog.setMessage("Готовы?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            gamingStage();


                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Выход",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }
            );
            alertDialog.show();
        }
        else {
            gamingStage();
        }

        }


    protected void gamingStage(){

        if(!isRunning)
            gm.newGame();

        gm.generatePairs();

        first_color.setText(gm.getRandomColorNameFirstField());
        first_color.setTextColor(Color.parseColor(gm.getRandomColorHexFirstField()));

        second_color.setText(gm.getRandomColorNameSecondField());
        second_color.setTextColor(Color.parseColor(gm.getRandomColorHexSecondField()));


        startGame();
    }
    protected void showResultsGameAlertBox() {
        if (isRunning && gm.getCurrentGameTime() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("ClrGame");
            alertDialog.setMessage("Игра завершена! Ваш счет: " + gm.getTrueAnswers() + "\nПопробовать снова?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            isRunning = false;
                            gamingStage();

                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Выход",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }
            );
            alertDialog.show();

        }
    }
    protected void showResultsGameActivity(){

        if(isRunning && gm.getCurrentGameTime() == 0)
        {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("results", gm.getTrueAnswers());
            intent.putExtra("email", email);
            finish();
            startActivity(intent);
        }
    }
    protected void startGame() {
        isRunning = true;
        time.setMax(gm.getDeadlineSeconds());
        timer = new CountDownTimer(gm.getCurrentGameTime() * 1000, 1000) {


            public void onTick(long millisUntilFinished) {
                int current_time = (int)millisUntilFinished / 1000;
                time.setProgress(current_time);
                gm.setCurrentGameTime(current_time);
                answers.setText("" + gm.getTrueAnswers());
            }

            public void onFinish() {

                    time.setProgress(0);
                    gm.setCurrentGameTime(0);
                    answers.setText("" + gm.getTrueAnswers());
                    //showResultsGameAlertBox();
                    showResultsGameActivity();
                }

        }
                .start();
    }




    public void submitYes(View view) {
        if(answers != null && gm != null){
            gm.compareUserAnswer(true);
            gm.generatePairs();

            first_color.setText(gm.getRandomColorNameFirstField());
            first_color.setTextColor(Color.parseColor(gm.getRandomColorHexFirstField()));

            second_color.setText(gm.getRandomColorNameSecondField());
            second_color.setTextColor(Color.parseColor(gm.getRandomColorHexSecondField()));
        }
    }
    public void submitNo(View view) {
        if(answers != null && gm != null){
            gm.compareUserAnswer(false);
            gm.generatePairs();

            first_color.setText(gm.getRandomColorNameFirstField());
            first_color.setTextColor(Color.parseColor(gm.getRandomColorHexFirstField()));

            second_color.setText(gm.getRandomColorNameSecondField());
            second_color.setTextColor(Color.parseColor(gm.getRandomColorHexSecondField()));
        }
    }

    }

