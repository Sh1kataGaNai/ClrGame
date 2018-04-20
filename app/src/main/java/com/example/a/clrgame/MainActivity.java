package com.example.a.clrgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.CountDownTimer;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DialogFragment;
import java.util.TimerTask;

import android.widget.ProgressBar;
import android.widget.TextView;






public class MainActivity extends AppCompatActivity  {

    private GameManager gm;
    private ProgressBar time;
    private TextView answers;
    private TextView first_color;
    private TextView second_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareGame();
    }

    protected void prepareGame() {
        first_color = (TextView) findViewById(R.id.textColorName);
        second_color = (TextView) findViewById(R.id.textColorHex);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("ClrGame");
        alertDialog.setMessage("Готовы?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        gm = new GameManager();
                        gm.generatePairs();

                        first_color.setText(gm.getRandomColorNameFirstField());
                        first_color.setTextColor(Color.parseColor(gm.getRandomColorHexFirstField()));

                        second_color.setText(gm.getRandomColorNameSecondField());
                        second_color.setTextColor(Color.parseColor(gm.getRandomColorHexSecondField()));


                        startGame();

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

    protected void showResultsGame() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("ClrGame");
        alertDialog.setMessage("Игра завершена! Ваш счет: " + gm.getTrueAnswers() + "\nПопробовать снова?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        gm = new GameManager();
                        gm.generatePairs();

                        first_color.setText(gm.getRandomColorNameFirstField());
                        first_color.setTextColor(Color.parseColor(gm.getRandomColorHexFirstField()));

                        second_color.setText(gm.getRandomColorNameSecondField());
                        second_color.setTextColor(Color.parseColor(gm.getRandomColorHexSecondField()));


                        startGame();

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
    protected void startGame() {


        answers = (TextView) findViewById(R.id.textViewTrueAnswers);
        time = (ProgressBar) findViewById(R.id.progressBarTimeLeft);
        time.setMax(gm.getDeadlineSeconds());
        new CountDownTimer(gm.getDeadlineSeconds() * 1000, 1000) {


            public void onTick(long millisUntilFinished) {
                time.setProgress((int)millisUntilFinished / 1000);

                answers.setText("" + gm.getTrueAnswers());
            }

            public void onFinish() {
                time.setProgress(0);
                answers.setText("" + gm.getTrueAnswers());
                showResultsGame();
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

