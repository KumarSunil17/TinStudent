package com.kumarsunil17.tinstudent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {
private EditText emailText;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        emailText = findViewById(R.id.forgot_email);
        mAuth = FirebaseAuth.getInstance();
    }

    public void doResetPassword(final View view) {
        final ProgressDialog p = new ProgressDialog(this);
        String email = emailText.getText().toString();
        p.setMessage("Please Wait while we are Verifying");
        p.setTitle("Hold On ... ");
        p.setCancelable(false);
        p.setCanceledOnTouchOutside(false);
        if(TextUtils.isEmpty(email)){
            Snackbar.make(view, "Email Field Cannot be left blank ..", Snackbar.LENGTH_LONG).show();
        }else{
            p.show();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Snackbar.make(view, "Email Sent Successfully", Snackbar.LENGTH_LONG).show();
                        finish();
                    }else{
                        p.dismiss();
                        Snackbar.make(view, task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
