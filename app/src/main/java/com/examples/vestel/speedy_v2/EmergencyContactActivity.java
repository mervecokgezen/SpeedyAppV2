package com.examples.vestel.speedy_v2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmergencyContactActivity extends AppCompatActivity {

    Button btn_goaddcontact;
    private RecyclerView recycler_view;
    private List<Contact> contact_list;

    private DatabaseReference databaseReference ;
    private FirebaseDatabase firebaseDatabase ;
    private String ContactIDFromServer;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    Contact c;

    Button btn_gocontactadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        firebaseAuth      = FirebaseAuth.getInstance();
        firebaseUser      = firebaseAuth.getCurrentUser();
        String userid     = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase    = FirebaseDatabase.getInstance();
        databaseReference   = firebaseDatabase.getReference().child("Contacts").child(userid);

        btn_gocontactadd = (Button)findViewById(R.id.btn_gocontactadd);

        btn_gocontactadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmergencyContactActivity.this, ContactAddActivity.class));
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_list = new ArrayList<Contact>();
                for(DataSnapshot ds :dataSnapshot.getChildren())
                {
                    c = new Contact(ds.child("contact_name").getValue().toString(),ds.child("contact_phone").getValue().toString(),ds.child("contact_mail").getValue().toString());
                    contact_list.add(c);


                }
                recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EmergencyContactActivity.this);

                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManager.scrollToPosition(0);

                recycler_view.setLayoutManager(linearLayoutManager);

                SimpleReceyclerAdapter adapter_items = new SimpleReceyclerAdapter(contact_list, new Contact.CustomItemClickListener(){

                    @Override
                    public void onItemClick(View v, int position) {
                        //Log.d("position", "Tıklanan Pozisyon:" + position);
                        Contact y = contact_list.get(position);
                    }
                });
                recycler_view.setAdapter(adapter_items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
