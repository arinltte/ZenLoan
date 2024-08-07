/*
CHEN JIN SHEN
22ACB02076
UCCD3223 Mobile Applications Development
(June 2024 Trimester)
*/

package com.example.loancalculator;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelectionActivity extends AppCompatActivity {

    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selection);

        //Passing Age Value from MainActivity to next activity page
        Intent intent = getIntent();
        age = intent.getIntExtra("AGE", 0);
    }

    //Direct users to Personal Loan Activity Page
    public void personalLoan(View v) {
        Intent i = new Intent(this, PersonalActivity.class);
        i.putExtra("AGE", age);
        startActivity(i);
    }

    //Direct users to Housing Loan Activity Page
    public void housingLoan(View v) {
        Intent i = new Intent(this, HousingActivity.class);
        i.putExtra("AGE", age);
        startActivity(i);
    }
}
