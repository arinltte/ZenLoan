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

public class HousingActivity extends AppCompatActivity {

    private EditText editTextLoanAmountHousing, editTextInterestRateHousing, editTextNumRepaymentsHousing, editTextStartDateHousing;
    private TextView textViewMonthlyInstalmentHousing, textViewLastPaymentDateHousing, textViewTotalRepaidHousing;
    private Button buttonCalculateHousing, buttonShowAmortizationHousing;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_housing);

        Intent intent = getIntent();
        age = intent.getIntExtra("AGE", 0);

        editTextLoanAmountHousing = findViewById(R.id.editTextLoanAmountHousing);
        editTextInterestRateHousing = findViewById(R.id.editTextInterestRateHousing);
        editTextNumRepaymentsHousing = findViewById(R.id.editTextNumRepaymentsHousing);
        editTextStartDateHousing = findViewById(R.id.editTextStartDateHousing);
        textViewMonthlyInstalmentHousing = findViewById(R.id.textViewMonthlyInstalmentHousing);
        textViewLastPaymentDateHousing = findViewById(R.id.textViewLastPaymentDateHousing);
        textViewTotalRepaidHousing = findViewById(R.id.textViewTotalRepaidHousing);
        buttonCalculateHousing = findViewById(R.id.buttonCalculateHousing);
        buttonShowAmortizationHousing = findViewById(R.id.buttonShowAmortizationHousing);

        buttonCalculateHousing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                housingDetails();
            }
        });

        buttonShowAmortizationHousing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAmortizationSchedule();
            }
        });
    }

    private void housingDetails() {
        double principal = Double.parseDouble(editTextLoanAmountHousing.getText().toString());
        double annualInterestRate = Double.parseDouble(editTextInterestRateHousing.getText().toString());
        int numRepayments = Integer.parseInt(editTextNumRepaymentsHousing.getText().toString());

        //Calculate maximum tenure based on age
        if (numRepayments > 420) { // 420 months = 35 years
            Toast.makeText(this, "Maximum loan tenure is 35 years", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (age + (numRepayments / 12.0) > 70) {
            Toast.makeText(this, "Maximum loan tenure is 70 years of age", Toast.LENGTH_LONG).show();
            return;
        }

        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double numerator = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numRepayments);
        double denominator = Math.pow(1 + monthlyInterestRate, numRepayments) - 1;
        double monthlyInstalment = numerator / denominator;

        //Calculate the last payment date
        String startDateStr = editTextStartDateHousing.getText().toString();
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

        //Calculate total amount repaid
        double totalRepaid = monthlyInstalment * numRepayments;

        //Display the calculation
        DecimalFormat df = new DecimalFormat("#.##");
        textViewMonthlyInstalmentHousing.setText("Monthly Instalment: " + df.format(monthlyInstalment));
        textViewLastPaymentDateHousing.setText("Last Payment Date: " + dateFormat.format(lastPaymentDate));
        textViewTotalRepaidHousing.setText("Total Amount Repaid: " + df.format(totalRepaid));
    }

    private void showAmortizationSchedule() {
        double principal = Double.parseDouble(editTextLoanAmountHousing.getText().toString());
        double annualInterestRate = Double.parseDouble(editTextInterestRateHousing.getText().toString());
        int numRepayments = Integer.parseInt(editTextNumRepaymentsHousing.getText().toString());

        Intent intent = new Intent(this, AmortizationHousingActivity.class);
        intent.putExtra("PRINCIPAL", principal);
        intent.putExtra("INTEREST_RATE", annualInterestRate);
        intent.putExtra("NUM_REPAYMENTS", numRepayments);
        startActivity(intent);
    }
}
