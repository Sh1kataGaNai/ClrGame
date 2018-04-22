package com.example.a.clrgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    protected TextView textViewResultsGame;
    private String email;
    private String results;
    protected TextView textViewCongrats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        textViewResultsGame = (TextView) findViewById(R.id.textViewResultsGame);
        textViewCongrats = (TextView) findViewById(R.id.textViewCongrats);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            results = arguments.get("results").toString();
            email = arguments.get("email").toString();

            textViewCongrats.setText(getString(R.string.textViewCongrats) + ", " + email);
            textViewResultsGame.setText(getString(R.string.textViewResultsGame) + results);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Выйти?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();

                        }
                    })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    })
                    .show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void submitRestart(View view){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void submitSendEmail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email , null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ClrGame");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Congrats. Your result: " + results);
        startActivity(Intent.createChooser(emailIntent, "Send email with results..."));
    }
}
