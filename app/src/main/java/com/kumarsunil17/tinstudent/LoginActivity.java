package com.kumarsunil17.tinstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText username, passsword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username.findViewById(R.id.login_username);
        passsword.findViewById(R.id.login_password);

    }

    public void doLogin(View view) {

    }

    public void doResetPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
    }
}
