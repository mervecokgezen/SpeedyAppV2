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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAcActivity extends AppCompatActivity {

    private Button btn_register;
    private TextView tv_backlogin;
    private EditText edt_namesurname, edt_mail, edt_password, edt_phone;
    private String name, mail, phone, password;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ac);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        btn_register    = (Button)findViewById(R.id.btn_register);
        tv_backlogin    = (TextView)findViewById(R.id.tv_gologin);
        edt_namesurname = (EditText)findViewById(R.id.edt_namesurname);
        edt_mail        = (EditText)findViewById(R.id.edt_mail);
        edt_password    =(EditText)findViewById(R.id.edt_password);
        edt_phone       = (EditText)findViewById(R.id.edt_phone);

        tv_backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAcActivity.this, MainActivity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edt_namesurname.getText().toString();
                mail = edt_mail.getText().toString();
                phone = edt_phone.getText().toString();
                password = edt_password.getText().toString();

                User user = new User(name, mail, phone, password);

                if(name.isEmpty() || password.isEmpty() || mail.isEmpty()|| phone.isEmpty()){
                    Toast.makeText(CreateAcActivity.this, "Required fill", Toast.LENGTH_LONG).show();
                }else {
                    UserRegister();
                    AddUserDatabase(user);
                }
            }
        });
    }

    public void UserRegister(){
        firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(CreateAcActivity.this, EmergencyContactActivity.class));
                    Toast.makeText(CreateAcActivity.this, "Success", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void AddUserDatabase(User user){

        databaseReference = FirebaseDatabase.getInstance().getReference();
        String ContactsIDFromServer = databaseReference.push().getKey();
        databaseReference.child("Users").child(ContactsIDFromServer).setValue(user);

    }
}
