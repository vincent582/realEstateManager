package com.openclassrooms.realestatemanager.UI.Fragment.LoanSimulator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanSimulatorFragment extends Fragment {

    @BindView(R.id.seekBar) SeekBar mSeekBar;
    @BindView(R.id.simulator_nbr_of_years_tv) TextView mDisplayNbrOfYears;
    @BindView(R.id.simulator_interest_tv) TextView mDisplayRateFees;
    @BindView(R.id.simulator_loan_amount_ed) EditText mLoanAmountEditText;
    @BindView(R.id.simulator_financial_contribution_ed) EditText mFinancialContributionEditText;
    @BindView(R.id.simulator_monthly_payment_tv) TextView mMonthlyPayment;
    @BindView(R.id.simulator_total_payment_tv) TextView mTotalPayment;
    @BindView(R.id.simulator_reset_fab) FloatingActionButton mResetFab;

    private int mLoanAmount = 0;
    private int mFinancialContribution = 0;
    private float mAmountOfInterestPerYears = 0;
    private float mAmountOfInterestFromAmountValues = 0;
    private int mNbrOfYears = 0;

    public LoanSimulatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_simulator, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,view);
        setDefaultValues();
        setUpSeekBar();
        setUpLoanAmountEditText();
        setUpFinancialContributionEditText();

        mResetFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultValues();
            }
        });
        return view;
    }

    private void setUpLoanAmountEditText() {
        mLoanAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try
                {
                    mLoanAmount = Integer.parseInt(String.valueOf(s));
                    calculateInterestFromAmount();
                }
                catch(NumberFormatException nfe) {
                    return;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setUpFinancialContributionEditText() {
        mFinancialContributionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try
                {
                    mFinancialContribution = Integer.parseInt(String.valueOf(s));
                    calculateInterestFromAmount();
                }
                catch(NumberFormatException nfe) {
                    return;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setUpSeekBar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mNbrOfYears = progress + 1;
                calculateInterestWhenChangeNbrOfYears();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void calculateInterestWhenChangeNbrOfYears(){
            mAmountOfInterestPerYears = (float) (0.29 * mNbrOfYears);
            totalLoanAmount();
    }

    private void calculateInterestFromAmount(){
        int percentOfAmount = (100 * mFinancialContribution) / mLoanAmount;
        if (percentOfAmount <= 10){
            mAmountOfInterestFromAmountValues = (float) 1.30;
        }else if(percentOfAmount > 10 && percentOfAmount <= 30){
            mAmountOfInterestFromAmountValues = (float) 1.1;
        }else if(percentOfAmount > 30 && percentOfAmount <= 50){
            mAmountOfInterestFromAmountValues = (float) 0.80;
        }else if(percentOfAmount > 50 && percentOfAmount <= 70){
            mAmountOfInterestFromAmountValues = (float) 0.50;
        }else if(percentOfAmount > 70 && percentOfAmount <= 90){
            mAmountOfInterestFromAmountValues = (float) 0.20;
        }else {
            mAmountOfInterestFromAmountValues = 0;
        }
        totalLoanAmount();
    }

    private void totalLoanAmount() {
        mDisplayNbrOfYears.setText(String.valueOf(mNbrOfYears)+ " year(s)");
        int totalAmountWithoutFees = mLoanAmount - mFinancialContribution;
        float mTotalInterest = mAmountOfInterestFromAmountValues + mAmountOfInterestPerYears;
        mDisplayRateFees.setText(String.format("%.2f", mTotalInterest) + " %");
        float totalAmountOfInterest = totalAmountWithoutFees * mTotalInterest / 100;
        float totalAmountWithFees = totalAmountWithoutFees + totalAmountOfInterest;
        mTotalPayment.setText((String.format("%,.2f",totalAmountWithFees) + " $"));
        if (mNbrOfYears > 0){
            float totalPerMonth = totalAmountWithFees / mNbrOfYears / 12;
            mMonthlyPayment.setText(String.format("%,.2f",totalPerMonth) + " $");
        }
    }

    private void setDefaultValues() {
        mLoanAmountEditText.setText("");
        mLoanAmount = 0;
        mFinancialContributionEditText.setText("");
        mFinancialContribution = 0;
        mSeekBar.setProgress(0);
        mNbrOfYears = 0;
        mAmountOfInterestFromAmountValues = 0;
        mAmountOfInterestPerYears = 0;
        totalLoanAmount();
    }

}
