/*
CHEN JIN SHEN
22ACB02076
UCCD3223 Mobile Applications Development
(June 2024 Trimester)
*/

package com.example.loancalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void loanSelection(View v) {
        EditText ageEditText = findViewById(R.id.ageText);
        String ageText = ageEditText.getText().toString();

        //Check the age is fulfilling the requirement or not
        try {
            int age = Integer.parseInt(ageText);
            if (age < 21) {
                showAlertDialog();
            } else {
                Intent i = new Intent(this, SelectionActivity.class);
                i.putExtra("AGE", age);
                startActivity(i);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter Your Age", Toast.LENGTH_SHORT).show();
        }
    }

    //Display an Alert if the age is below 21 years old
    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Age Restriction")
                .setMessage("You must be at least 21 years old.")
                .setPositiveButton("OK", null)
                .show();
    }

}