package com.examples.vestel.speedy_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


public class MainActivity extends AppCompatActivity {

    private EditText edt_mail, edt_password;
    private Button btn_login;
    private TextView tv_createac;
    private String email, password;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, ControlService.class));
        startService(new Intent(getApplicationContext(), ScreenLockService.class));

        edt_mail = (EditText)findViewById(R.id.edt_mail);
        edt_password = (EditText)findViewById(R.id.edt_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_createac = (TextView)findViewById(R.id.tv_createac);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        tv_createac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CreateAcActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email    = edt_mail.getText().toString();
                password = edt_password.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fill in required fields.", Toast.LENGTH_LONG);
                }
                else {
                    LoginFunction();
                }
            }
        });
        if (firebaseUser != null) { // daha önce kullanıcı girişi yapılmışsa tekrar giriş yapmadan anasayfadan devam et.
            for(UserInfo profile : firebaseUser.getProviderData()){
                String mail = profile.getEmail();
            }
            startActivity(new Intent(MainActivity.this, EmergencyContactActivity.class));
        } else {
            Toast.makeText(MainActivity.this, "Kullanıcı girişi yapınız!..", Toast.LENGTH_LONG).show();
        }
    }

    private void LoginFunction(){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, EmergencyContactActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
