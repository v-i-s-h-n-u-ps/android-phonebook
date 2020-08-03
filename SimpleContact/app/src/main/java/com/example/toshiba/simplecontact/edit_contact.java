package com.example.toshiba.simplecontact;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class edit_contact extends AppCompatActivity {

    EditText contact_num, contact_email, contact_homepage, contact_name;
    DatabaseHandler db;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle("Edit");
        setContentView(R.layout.activity_edit_contact);
        db = new DatabaseHandler(getApplicationContext());
        Button btn_cancel = findViewById(R.id.button_cancel);
        Button btn_confirm = findViewById(R.id.button_confirm);
        contact_name = findViewById(R.id.input_contact_name);
        contact_num = findViewById(R.id.input_contact_number);
        contact_email = findViewById(R.id.input_email);
        contact_homepage = findViewById(R.id.input_homepage);
        /* get id from display contact activity*/
        final int id_to_edit = getIntent().getIntExtra("id_to_edit", 0);
        contact_info obj=new contact_info();
        obj=db.getContactInformation(id_to_edit);
        contact_name.setText(obj.getName());
        contact_num.setText(obj.getPhoneNumber());
        contact_email.setText(obj.getEmail());
        contact_homepage.setText(obj.getHome_page());
        /*Action for confirm button in edit contact activity*/
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = contact_name.getText().toString();
                String number = contact_num.getText().toString();
                String email = contact_email.getText().toString();
                String home_page = contact_homepage.getText().toString();
                db.editContact(id_to_edit, name, number, email, home_page);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "Successfully Edited ", duration);
                toast.show();
                finish();
            }
        });

        /*Action for cancel button in edit contact activity*/
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
