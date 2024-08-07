/*
CHEN JIN SHEN
22ACB02076
UCCD3223 Mobile Applications Development
(June 2024 Trimester)
*/

package com.example.loancalculator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import java.text.DecimalFormat;

public class AmortizationHousingActivity extends AppCompatActivity {

    private TextView textViewAmortizationSchedule;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_amortization_housing);

        scrollView = findViewById(R.id.scrollView);
        textViewAmortizationSchedule = findViewById(R.id.textViewAmortizationSchedule);

        // Assuming you pass the loan details through the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double principal = extras.getDouble("PRINCIPAL");
            double annualInterestRate = extras.getDouble("INTEREST_RATE");
            int numRepayments = extras.getInt("NUM_REPAYMENTS");

            calculateAmortizationHousing(principal, annualInterestRate, numRepayments);
        }
    }

    private void calculateAmortizationHousing(double principal, double annualInterestRate, int numRepayments) {
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double numerator = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numRepayments);
        double denominator = Math.pow(1 + monthlyInterestRate, numRepayments) - 1;
        double monthlyInstalment = numerator / denominator;

        StringBuilder amortizationSchedule = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.##");

        double remainingBalance = principal;
        for (int i = 0; i <= numRepayments; i++) {
            double interestPaid = remainingBalance * monthlyInterestRate;
            double principalPaid = monthlyInstalment - interestPaid;
            amortizationSchedule.append("Payment number: ").append(i+1).append("\n")
                    .append("Beginning balance: RM ").append(df.format(remainingBalance)).append("\n")
                    .append("Monthly repayment: RM ").append(df.format(monthlyInstalment)).append("\n")
                    .append("Interest paid: RM ").append(df.format(interestPaid)).append("\n")
                    .append("Principal paid: RM ").append(df.format(principalPaid)).append("\n")
                    .append("Remaining balance: RM ").append(df.format(remainingBalance -= principalPaid)).append("\n\n");
        }

        textViewAmortizationSchedule.setText(amortizationSchedule.toString());
    }
}
