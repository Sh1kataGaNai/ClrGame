package com.example.a.clrgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.CountDownTimer;
import android.app.AlertDialog;

import java.util.HashMap;

import android.widget.Button;
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
    private Menu main_menu;
    private Button buttonSubmitYes;
    private Button buttonSubmitNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first_color = (TextView) findViewById(R.id.textColorName);
        second_color = (TextView) findViewById(R.id.textColorHex);
        answers = (TextView) findViewById(R.id.textViewTrueAnswers);
        time = (ProgressBar) findViewById(R.id.progressBarTimeLeft);
        buttonSubmitYes = (Button) findViewById(R.id.buttonYes);
        buttonSubmitNo = (Button) findViewById(R.id.buttonNo);
        buttonSubmitYes.setEnabled(false);
        buttonSubmitNo.setEnabled(false);

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
            buttonSubmitYes.setEnabled(true);
            buttonSubmitNo.setEnabled(true);
            greetingsAndStart();
        }
        else {
            isRunning = false;
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        main_menu = menu;
        if(isRunning){
            main_menu.getItem(0).setIcon(getResources()
                    .getDrawable(R.drawable.stopw));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_game:
                if (isRunning) {
                    main_menu.getItem(0).setIcon(getResources()
                            .getDrawable(R.drawable.new_gamew));
                    timer.cancel();
                    time.setProgress(0);
                    isRunning = false;
                    buttonSubmitYes.setEnabled(false);
                    buttonSubmitNo.setEnabled(false);
                    answers.setText("0");
                    first_color.setText("####");
                    second_color.setText("####");
                }
                else{
                    if (gm == null)
                        gm = new GameManager();
                    greetingsAndStart();

                }
                return true;

            case R.id.helperExitApp:
                finish();
                return true;

            default:
                return true;

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
                            main_menu.getItem(0).setIcon(getResources()
                                    .getDrawable(R.drawable.stopw));


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

        buttonSubmitNo.setEnabled(true);
        buttonSubmitYes.setEnabled(true);

        if(!isRunning)
            gm.newGame();

        gm.generatePairs();

        first_color.setText(gm.getRandomColorNameFirstField());
        first_color.setTextColor(Color.parseColor(gm.getRandomColorHexFirstField()));

        second_color.setText(gm.getRandomColorNameSecondField());
        second_color.setTextColor(Color.parseColor(gm.getRandomColorHexSecondField()));


        startGame();
    }
    /* Old code

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
    */
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

