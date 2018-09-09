//
// Michal Bochnak, Netid: mbochn2
// CS 478 - Project #1, Phone Dialer
// Professor: Ugo Buy
// UIC, Feb 9, 2018
//
// GetPhoneNumberActivity.java
//


package com.phonedialer.cs478_project01_phonedialer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.EditText;


//
// GetPhoneNumberActivity class
//
// Allows the user to enter the phone number into the text field
//
public class GetPhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);

        final EditText editText = findViewById(R.id.phoneNumberEditText);

        // add action listener to the edit field
        editText.setOnEditorActionListener
                (new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // done / return key pressed
                        if (actionId == EditorInfo.IME_ACTION_DONE
                                || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                            // get phone number from text field
                            String phoneNumber = editText.getText().toString().trim();
                            // verify if phone number has desired format ans set result code
                            if (isCorrectFormat(phoneNumber)) {
                                setResultCodeAndIntent(RESULT_OK, phoneNumber);
                            }
                            else {
                                setResultCodeAndIntent(RESULT_CANCELED, phoneNumber);
                            }

                            finish();
                        }
                        return false;
                    }
                });
    }

    // returns true if phone number has correct format: (XXX) XXX-XXXX
    private boolean isCorrectFormat(String phoneNumber) {

        // wrong format, not enough characters
        if (phoneNumber.length()  != 14) {
            return false;
        }

        // check if parenthesis, space, and dash are correct
        if ( !((phoneNumber.charAt(0) == '(') && (phoneNumber.charAt(4) == ')')
                && (phoneNumber.charAt(5) == ' ') && (phoneNumber.charAt(9) == '-')) ) {
            return false;
        }
        // check if all 'X' is a digit
        else if ( !(Character.isDigit(phoneNumber.charAt(1)))
                && (Character.isDigit(phoneNumber.charAt(2)))
                && (Character.isDigit(phoneNumber.charAt(3)))
                && (Character.isDigit(phoneNumber.charAt(6)))
                && (Character.isDigit(phoneNumber.charAt(7)))
                && (Character.isDigit(phoneNumber.charAt(8)))
                && (Character.isDigit(phoneNumber.charAt(10)))
                && (Character.isDigit(phoneNumber.charAt(11)))
                && (Character.isDigit(phoneNumber.charAt(12)))
                && (Character.isDigit(phoneNumber.charAt(13)))
                ) {

            return false;
        }
        // phone number format is correct
        else {
            return true;
        }
    }

    // sets result code and intent field with phone number
    private void setResultCodeAndIntent(int resultCode, String phoneNumber) {

        // nothing was entered into the field
        if (phoneNumber.equals("")) {
            phoneNumber = "none";
        }

        Intent intent = getIntent();
        intent.putExtra("phoneNumber", phoneNumber);
        setResult(resultCode, intent);
    }

    //
    // Back button pressed, do not apply any changes
    //
    @Override
    public void onBackPressed() {
        setResultCodeAndIntent(RESULT_CANCELED, "none");
        finish();
    }
}
