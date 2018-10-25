package com.kumarsunil17.tinstudent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameText, passwordText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.login_username);
        passwordText = findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(final View view) {
        String user = usernameText.getText().toString().trim();
        String pass = passwordText.getText().toString().trim();
        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Snackbar.make(view, "Fields can't be left blank", Snackbar.LENGTH_LONG).show();
        }else {
            Pattern pa = Pattern.compile("^[a-zA-Z1-9 ]+$");
            Matcher m = pa.matcher(user);
            if(!m.matches()){
                Snackbar.make(view, "Invalid User format", Snackbar.LENGTH_LONG).show();
            }else{
                final ProgressDialog p= new ProgressDialog(this);
                p.setMessage("Please wait while we are logging you in");
                p.setCancelable(false);
                p.show();
                mAuth.signInWithEmailAndPassword(user+"@gmail.com",pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        p.dismiss();
                        String name = authResult.getUser().getDisplayName();
                        if (name.equals("student")) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            mAuth.signOut();
                            Snackbar.make(view, "You aren't a student, get lost and login in your respective app", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.dismiss();
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
    }
}
