package com.examples.vestel.speedy_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactAddActivity extends AppCompatActivity {

    private Button btn_add;
    private EditText edt_contactname, edt_contactphone, edt_contactemail;
    private String contactname, contactphone, contactemail;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts");
        firebaseAuth      = FirebaseAuth.getInstance();
        firebaseUser      = firebaseAuth.getCurrentUser();

        btn_add          = (Button)findViewById(R.id.btn_contactadd);
        edt_contactname  = (EditText)findViewById(R.id.edt_contactname);
        edt_contactphone = (EditText)findViewById(R.id.edt_contactphone);
        edt_contactemail = (EditText)findViewById(R.id.edt_contactemail);

       btn_add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               contactname  = edt_contactname.getText().toString();
               contactphone = edt_contactphone.getText().toString();
               contactemail = edt_contactemail.getText().toString();

               Contact contact = new Contact(contactname, contactphone, contactemail);

               if (contactname.isEmpty() || contactphone.isEmpty()||contactemail.isEmpty()){
                   Toast.makeText(ContactAddActivity.this, "Please fill in the required fields!", Toast.LENGTH_LONG).show();
               }
               else {
                   AddNewContact(contact);

                   Toast.makeText(ContactAddActivity.this,"Add Succes!", Toast.LENGTH_LONG).show();
                   startActivity(new Intent(ContactAddActivity.this, EmergencyContactActivity.class));
               }
           }
       });

    }
    private void AddNewContact(Contact c){

        String ContactsIDFromServer = databaseReference.push().getKey();
        String userid = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(userid).child(ContactsIDFromServer).setValue(c);

    }
}
