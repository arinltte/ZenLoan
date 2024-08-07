/*
CHEN JIN SHEN
22ACB02076
UCCD3223 Mobile Applications Development
(June 2024 Trimester)
*/

package com.example.loancalculator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PersonalActivity extends AppCompatActivity {

    private EditText editTextLoanAmount, editTextInterestRate, editTextNumRepayments, editTextStartDate;
    private TextView textViewMonthlyInstalment, textViewLastPaymentDate, textViewTotalRepaid;
    private Button buttonCalculate, buttonShowAmortization;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal);

        Intent intent = getIntent();
        age = intent.getIntExtra("AGE", 0);

        editTextLoanAmount = findViewById(R.id.editTextLoanAmount);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);
        editTextNumRepayments = findViewById(R.id.editTextNumRepayments);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        textViewMonthlyInstalment = findViewById(R.id.textViewMonthlyInstalment);
        textViewLastPaymentDate = findViewById(R.id.textViewLastPaymentDate);
        textViewTotalRepaid = findViewById(R.id.textViewTotalRepaid);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonShowAmortization = findViewById(R.id.buttonShowAmortization);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalDetails();
            }
        });

        buttonShowAmortization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAmortizationSchedule();
            }
        });
    }

    private void personalDetails() {
        double principal = Double.parseDouble(editTextLoanAmount.getText().toString());
        double annualInterestRate = Double.parseDouble(editTextInterestRate.getText().toString());
        int numRepayments = Integer.parseInt(editTextNumRepayments.getText().toString());

        // Calculate maximum tenure based on age
        if (numRepayments > 120) { // 120 months = 10 years
            Toast.makeText(this, "Maximum loan tenure is 10 years", Toast.LENGTH_SHORT).show();
            return;
        } else if (age + (numRepayments / 12.0) > 60) {
            Toast.makeText(this, "Maximum loan tenure is 60 years of age", Toast.LENGTH_LONG).show();
            return;
        }

        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double monthlyInstalment = (principal * (1 + monthlyInterestRate * numRepayments)) / numRepayments;

        // Calculate the last payment date
        String startDateStr = editTextStartDate.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate;
        try {
            startDate = dateFormat.parse(startDateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, numRepayments);
        Date lastPaymentDate = calendar.getTime();

        // Calculate total amount repaid
        double totalRepaid = monthlyInstalment * numRepayments;

        // Display the calculation
        DecimalFormat df = new DecimalFormat("#.##");
        textViewMonthlyInstalment.setText("Monthly Instalment: " + df.format(monthlyInstalment));
        textViewLastPaymentDate.setText("Last Payment Date: " + dateFormat.format(lastPaymentDate));
        textViewTotalRepaid.setText("Total Amount Repaid: " + df.format(totalRepaid));
    }

    private void showAmortizationSchedule() {
        double principal = Double.parseDouble(editTextLoanAmount.getText().toString());
        double annualInterestRate = Double.parseDouble(editTextInterestRate.getText().toString());
        int numRepayments = Integer.parseInt(editTextNumRepayments.getText().toString());

        Intent intent = new Intent(this, AmortizationActivity.class);
        intent.putExtra("PRINCIPAL", principal);
        intent.putExtra("INTEREST_RATE", annualInterestRate);
        intent.putExtra("NUM_REPAYMENTS", numRepayments);
        startActivity(intent);
    }
}
