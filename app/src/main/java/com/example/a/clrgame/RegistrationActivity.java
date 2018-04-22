package com.example.a.clrgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class RegistrationActivity extends AppCompatActivity {

    private TextView textViewEmail;
    private Button buttonCompleteAndStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        textViewEmail = (TextView) findViewById(R.id.editTextEmail);
        buttonCompleteAndStart = (Button) findViewById(R.id.buttonCompleteAndStart);

        if(savedInstanceState != null){
            buttonCompleteAndStart.setText(savedInstanceState.getString("email"));
        }

        textViewEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(isValidEmail(textViewEmail.getText())){

                    buttonCompleteAndStart.setEnabled(true);
                }
                else{

                    buttonCompleteAndStart.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isValidEmail(textViewEmail.getText())){

                    buttonCompleteAndStart.setEnabled(true);
                }
                else{

                    buttonCompleteAndStart.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isValidEmail(textViewEmail.getText())){

                    buttonCompleteAndStart.setEnabled(true);
                }
                else{

                    buttonCompleteAndStart.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putString("email", buttonCompleteAndStart.getText().toString());

    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void submitCompleteAndStart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", textViewEmail.getText().toString());
        finish();
        startActivity(intent);

    }

}
