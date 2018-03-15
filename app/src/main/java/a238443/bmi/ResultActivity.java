package a238443.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar tl_resultToolbar = findViewById(R.id.result_toolbar);
        tl_resultToolbar.setTitle("");
        setSupportActionBar(tl_resultToolbar);
        tl_resultToolbar.setNavigationIcon(R.drawable.ic_action_back);

        tl_resultToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent in_fromMain = getIntent();
        BMI bmi_calc;

        if(in_fromMain.getBooleanExtra("isImperial",false)){
            bmi_calc = new BMIImperial(in_fromMain.getDoubleExtra("weight",10),in_fromMain.getDoubleExtra("height",1));
        }
        else{
            bmi_calc = new BMIMetric(in_fromMain.getDoubleExtra("weight",10),in_fromMain.getDoubleExtra("height",1));
        }

        v_printAndFormat(bmi_calc);
    }

    private void v_printAndFormat(BMI bmiToPrint){
        TextView tv_result = findViewById(R.id.BMI_result);
        TextView tv_description = findViewById(R.id.BMI_description);

        double d_result = bmiToPrint.d_calculateBMI();
        tv_result.setText(String.format(Locale.ENGLISH,"%.2f",d_result));

        CharSequence cs_description="";
        ConstraintLayout cl_current = findViewById(R.id.result_layout);

        if(d_result<=15) {
            cs_description = getResources().getString(R.string.very_severely_underweight);
            cl_current.setBackgroundColor(getResources().getColor(R.color.very_severely_underweight));
        }
        if(b_intervalContains(15,16,d_result)) {
            cs_description = getResources().getString(R.string.severely_underweight);
            cl_current.setBackgroundColor(getResources().getColor(R.color.severely_underweight));
        }
        if(b_intervalContains(16,18.5,d_result)) {
            cs_description = getResources().getString(R.string.underweight);
            cl_current.setBackgroundColor(getResources().getColor(R.color.underweight));
        }
        if(b_intervalContains(18.5,25,d_result)) {
            cs_description = getResources().getString(R.string.normal);
            cl_current.setBackgroundColor(getResources().getColor(R.color.normal));
        }
        if(b_intervalContains(25,30,d_result)) {
            cs_description = getResources().getString(R.string.overweight);
            cl_current.setBackgroundColor(getResources().getColor(R.color.overweight));
        }
        if(b_intervalContains(30,35,d_result)) {
            cs_description = getResources().getString(R.string.moderately_obese);
            cl_current.setBackgroundColor(getResources().getColor(R.color.moderately_obese));
        }
        if(b_intervalContains(35,40,d_result)) {
            cs_description = getResources().getString(R.string.severely_obese);
            cl_current.setBackgroundColor(getResources().getColor(R.color.severely_obese));
        }
        if(d_result>40) {
            cs_description = getResources().getString(R.string.very_severely_obese);
            cl_current.setBackgroundColor(getResources().getColor(R.color.very_severely_obese));
        }

        tv_description.setText(cs_description);
    }

    private boolean b_intervalContains(double dLow, double dHigh, double dNumber) {
        return dNumber > dLow && dNumber <= dHigh;
    }

}
