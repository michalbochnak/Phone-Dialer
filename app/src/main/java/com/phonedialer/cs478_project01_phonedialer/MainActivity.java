//
// Michal Bochnak, Netid: mbochn2
// CS 478 - Project #1, Phone Dialer
// UIC, Feb 9, 2018
// Professor: Ugo Buy
//
// MainActivity.java
//


package com.phonedialer.cs478_project01_phonedialer;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


//
// MainActivity class
//
// Allows the user to open new activity to enter phone number
//  and to open Dialer to make a call with the provided number
//
public class MainActivity extends   AppCompatActivity {

    // id for return code
    public final int PHONE_NUMBER_CORRECT = 1;
    private String phoneNumber = "none";
    private boolean phoneNumberFormatCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add Enter Phone Number button listener
        findViewById(R.id.enterPhoneNumber).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchGetPhoneNumberActivity();
            }
        });

        // add Open Dialer button listener
        findViewById(R.id.openDialer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumberFormatCorrect) {
                    startActivity(new Intent(Intent.ACTION_DIAL).setData
                            (Uri.parse("tel:" + phoneNumber)));
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Entered phone number:\n" + phoneNumber
                                    + "\n\nCorrect number format:\n(XXX) XXX-XXXX",
                            Toast.LENGTH_LONG).show();

                    // change button text to gray
                    ((Button)(findViewById(R.id.openDialer))).setTextColor(Color.GRAY);
                }
            }
        });
    }

    private void LaunchGetPhoneNumberActivity() {
        Intent intent = new Intent(this, GetPhoneNumberActivity.class);
        startActivityForResult(intent, PHONE_NUMBER_CORRECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the phone number correctness
        if (requestCode == PHONE_NUMBER_CORRECT) {
            TextView phoneNumberLabel = findViewById(R.id.phoneNumberLabel);

            // number returned from second activity is formatted correctly
            if (resultCode == RESULT_OK) {
                phoneNumberFormatCorrect = true;
                phoneNumber = data.getStringExtra("phoneNumber");
                phoneNumberLabel.setTextColor(Color.GREEN);
                phoneNumberLabel.setText("Phone #: " + phoneNumber);
            }
            // number returned from second activity is NOT formatted correctly
            else {
                if (!data.getStringExtra("phoneNumber").toString().equals("none")) {
                    phoneNumber = data.getStringExtra("phoneNumber");
                    phoneNumberFormatCorrect = false;
                    phoneNumberLabel.setTextColor(Color.RED);
                    phoneNumberLabel.setText("Phone #: " + phoneNumber);
                }
            }

            // change button text to black
            ((Button)(findViewById(R.id.openDialer))).setTextColor(Color.BLACK);
        }
    }


}   // end of MainActivity class
