package com.example.toshiba.simplecontact;

import android.Manifest;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayContact extends AppCompatActivity{

    FloatingActionButton call_button, sms_button, email_button, web_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHandler db = new DatabaseHandler(this);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        /* get id from main activity*/
        int id_get = getIntent().getIntExtra("id_to_retrieve", 0);
        setContentView(R.layout.activity_display_contact);
        contact_info obj= new contact_info();
        obj = db.getContactInformation(id_get);
        final String phonenumber = obj.getPhoneNumber(); // Get phone number
        final String email = obj.getEmail(); // Get Email
        final String Home_Page_Address = obj.getHome_page(); // Get Home Page Address
        this.setTitle("Back");
        TextView contact_name = (TextView) findViewById (R.id.contact_name);
        contact_name.setText(obj.getName());
        ArrayList<String> contact_info_array = new ArrayList<String>();
        contact_info_array.add(phonenumber);
        TextView contact = (TextView) findViewById (R.id.contact);
        contact.setText(phonenumber);
        if(!(email == "")) {
            contact_info_array.add(email);
            TextView email_address = (TextView) findViewById (R.id.email_address);
            email_address.setText(email);
        }
        if(!(Home_Page_Address == "http://")) {
            contact_info_array.add(Home_Page_Address);
            TextView home_page = (TextView) findViewById (R.id.home_page);
            home_page.setText(Home_Page_Address);
        }
        call_button = findViewById(R.id.call_button);
        sms_button = findViewById(R.id.sms_button);

        /*Action for call button in display contact activity*/
        call_button.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View arg0) {
                                               Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
                                               callIntent.setData(Uri.parse("tel:" + phonenumber));    //this is the phone number calling
                                               //check permission
                                               //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
                                               //the system asks the user to grant approval.
                                               if (ActivityCompat.checkSelfPermission(DisplayContact.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                   //request permission from user if the app hasn't got the required permission
                                                   ActivityCompat.requestPermissions(DisplayContact.this,
                                                           new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                                                           10);
                                                   return;
                                               } else
                                                   try {
                                                       startActivity(callIntent);
                                                   } catch (android.content.ActivityNotFoundException ex) {
                                                       Toast.makeText(DisplayContact.this, "Invalid Number!", Toast.LENGTH_SHORT).show();
                                                   }
                                           }
                                       }
        );

        /*Action for email button in display contact activity*/
//        email_button.setOnClickListener(new View.OnClickListener() {
//                                            public void onClick(View arg0) {
//                                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                                                    emailIntent.setType("text/plain");
//                                                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
//                                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//                                                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//                                                try {
//                                                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                                                } catch (android.content.ActivityNotFoundException ex) {
//                                                    Toast.makeText(DisplayContact.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        }
//        );

        /*Action for sms button in display contact activity*/
        sms_button.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View arg0) {
                                              Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phonenumber));
                                              try {
                                                  startActivity(smsIntent);
                                              } catch (android.content.ActivityNotFoundException ex) {
                                                  Toast.makeText(DisplayContact.this, "There is error in sending the SMS", Toast.LENGTH_SHORT).show();
                                              }

                                          }
                                      }
        );

        /*Action for web button in display contact activity*/
//        web_button.setOnClickListener(new View.OnClickListener() {
//                                          public void onClick(View arg0) {
//                                              Intent launchweb = new Intent(android.content.Intent.ACTION_VIEW);
//                                              launchweb.setData(Uri.parse(Home_Page_Address));
//                                              try {
//                                                  startActivity(launchweb);
//                                              }catch (android.content.ActivityNotFoundException ex) {
//                                                  Toast.makeText(DisplayContact.this, "There is error in the home page address", Toast.LENGTH_SHORT).show();
//                                              }
//
//                                          }
//                                      }
//        );

        /*To set the visiblity of call,sms,email and visit home page
          if the String is empty or only whitepsace in the string, it will be invisible to the user and vice versa*/
        if(phonenumber.replaceAll(" ","").isEmpty())
        {
            call_button.setVisibility(View.GONE);
            sms_button.setVisibility(View.GONE);
        }
//        if(email.replaceAll(" ","").isEmpty())
//        {
//            email_button.setVisibility(View.GONE);
//        }
//        if(Home_Page_Address.replaceAll(" ","").isEmpty())
//        {
//            web_button.setVisibility(View.GONE);
//        }
    }

    @Override
    /*
    Menu item Selecter using ItemID
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id_get = getIntent().getIntExtra("id_to_retrieve", 0);
        DatabaseHandler db=new DatabaseHandler(this);
        switch (item.getItemId()) {
            case R.id.delete:
                db.deleteContact(id_get);
                finish();
                return true;
            case R.id.edit:
                Intent intent = new Intent(getApplicationContext(), edit_contact.class);
                intent.putExtra( "id_to_edit", id_get);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
}

