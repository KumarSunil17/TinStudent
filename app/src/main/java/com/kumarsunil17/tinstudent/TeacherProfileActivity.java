package com.kumarsunil17.tinstudent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfileActivity extends AppCompatActivity {
    private DatabaseReference userRef;
    private StorageReference dpRef;
    private String userID;

    private TextView nameText, addressText, emailText, phoneText;
    private CircleImageView dpImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        nameText = findViewById(R.id.teacher_profile_name);
        addressText = findViewById(R.id.teacher_address);
        emailText = findViewById(R.id.teacher_email);
        dpImage = findViewById(R.id.teacher_profile_dp);
        phoneText = findViewById(R.id.teacher_phone);

        userID = getIntent().getExtras().getString("uid");
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child("teacher");
        dpRef = FirebaseStorage.getInstance().getReference().child("dp");

        final ProgressDialog pg = new ProgressDialog(this);
        pg.setMessage("Please wait while we are fetching data");
        pg.setTitle("Please wait");
        pg.setCancelable(false);
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        userRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameText.setText(dataSnapshot.child("name").getValue().toString());
                addressText.setText(dataSnapshot.child("address").getValue().toString());
                phoneText.setText(dataSnapshot.child("number").getValue().toString());
                emailText.setText(dataSnapshot.child("email").getValue().toString());
                final String dpURL = dataSnapshot.child("image").getValue().toString();
                Picasso.with(TeacherProfileActivity.this).load(dpURL).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.no_profile)
                        .into(dpImage, new Callback() {
                            @Override
                            public void onSuccess() {
                            }
                            @Override
                            public void onError() {
                                Picasso.with(TeacherProfileActivity.this).load(dpURL).placeholder(R.drawable.no_profile).into(dpImage);
                            }
                        });
                pg.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pg.dismiss();
                Snackbar snackbar = Snackbar
                        .make(Objects.requireNonNull(getCurrentFocus()), databaseError.getMessage(), Snackbar.LENGTH_SHORT)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });

                snackbar.setActionTextColor(Color.WHITE);
                View sbView = snackbar.getView();
                TextView textView =  sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);

                snackbar.show();
            }
        });
    }

    public void doEmailTeacher(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailText.getText().toString()});
        i.setType("message/rfc9822");
        startActivity(Intent.createChooser(i, "Send Email"));
    }

    public void doCallTeacher(View view) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + phoneText.getText()));
        try {
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(TeacherProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void doMessageTeacher(View view) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:"+phoneText.getText().toString()));
            try{
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }
}
