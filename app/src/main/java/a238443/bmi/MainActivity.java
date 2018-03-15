package a238443.bmi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity {
    private boolean b_isImperial = false;
    static boolean b_callStart = true;
    private EditText et_weightInput,et_heightInput;
    private Button bt_unitChange;
    private Button bt_calculate;
    private SharedPreferences sp_shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_weightInput = findViewById(R.id.weight_input);
        et_heightInput = findViewById(R.id.height_input);
        bt_unitChange = findViewById(R.id.units_changing_button);
        bt_calculate = findViewById(R.id.calculate_button);
        sp_shared = getSharedPreferences(getString(R.string.saved_data), Activity.MODE_PRIVATE);

        if(b_callStart) {
            v_readUsersData();
            b_callStart = false;
        }
        else
            onRestoreInstanceState(savedInstanceState);

        Toolbar tl_mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(tl_mainToolbar);
        v_listeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about_author_dropdown) {
            startActivity(new Intent(this, AboutAuthorActivity.class));
        }
        if(item.getItemId() == R.id.action_save) {
            v_saveUsersData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("weight",et_weightInput.getText().toString());
        savedInstanceState.putString("height",et_heightInput.getText().toString());
        savedInstanceState.putBoolean("isImperial",b_isImperial);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String s_weight = savedInstanceState.getString("weight");
        String s_height = savedInstanceState.getString("height");
        b_isImperial = savedInstanceState.getBoolean("isImperial");

        if(b_isImperial)
            v_makeImperialUI();

        et_heightInput.setText(s_height);
        et_weightInput.setText(s_weight);
    }

    private void v_listeners(){
        bt_unitChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence cs_oppositeUnit, cs_weightHint, cs_heightHint;

                if(!b_isImperial){
                    cs_oppositeUnit = getResources().getText(R.string.metric);
                    cs_weightHint = getResources().getText(R.string.hint_pounds);
                    cs_heightHint = getResources().getText(R.string.hint_inches);
                    b_isImperial = true;
                }
                else{
                    cs_oppositeUnit = getResources().getText(R.string.imperial);
                    cs_weightHint = getResources().getText(R.string.hint_kilograms);
                    cs_heightHint = getResources().getText(R.string.hint_meters);
                    b_isImperial = false;
                }

                bt_unitChange.setText(cs_oppositeUnit);
                et_weightInput.setHint(cs_weightHint);
                et_heightInput.setHint(cs_heightHint);
                et_heightInput.setText("");
                et_weightInput.setText("");
                et_heightInput.setError(null);
                et_weightInput.setError(null);
            }
        });

        bt_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in_calculation = new Intent(getApplicationContext(), ResultActivity.class);

                double[] dt_inputValidation = dt_inputValid(et_weightInput.getText().toString(),et_heightInput.getText().toString());

                if(dt_inputValidation[0]>0){
                    in_calculation.putExtra("height",dt_inputValidation[0]);
                    in_calculation.putExtra("weight",dt_inputValidation[1]);
                    in_calculation.putExtra("isImperial", b_isImperial);
                    startActivity(in_calculation);
                }
            }
        });
    }

    private void v_prompt(CharSequence csToShow){
        Toast tst_toPrompt = Toast.makeText(getApplicationContext(),csToShow,Toast.LENGTH_SHORT);
        tst_toPrompt.show();
    }

    private double[] dt_inputValid(String sWeightInput, String sHeightInput){
        boolean b_correctInput = false;
        double d_weight = 0,d_height = 0;
        BMI bmi_toValidate;

        if(sWeightInput.isEmpty())
            et_weightInput.setError(getResources().getString(R.string.weight_input_empty));
        else {
            d_weight = parseDouble(sWeightInput);
            b_correctInput = true;
        }

        if(sHeightInput.isEmpty()){
            et_heightInput.setError(getResources().getString(R.string.height_input_empty));
            b_correctInput = false;
        }
        else{
            if(b_correctInput) {
                d_height = parseDouble(sHeightInput);

                if (b_isImperial)
                    bmi_toValidate = new BMIImperial(d_weight, d_height);
                else
                    bmi_toValidate = new BMIMetric(d_weight, d_height);

                b_correctInput = b_promptingValidator(bmi_toValidate);
            }
        }

        if(b_correctInput)
            return new double[]{d_height,d_weight};
        else
            return new double[]{0,0};
    }

    private boolean b_promptingValidator(BMI bmiToValidate) {
        int[] it_messageID = bmiToValidate.it_validatingMessage();

        if(it_messageID[0] != R.string.data_valid) {
            if(it_messageID[1] == R.string.weight_input_low)
                et_weightInput.setError(getText(it_messageID[1]));
            if(it_messageID[2] == R.string.height_input_big || it_messageID[2] == R.string.height_input_low)
                et_heightInput.setError(getText(it_messageID[2]));
        }

        return it_messageID[0] == R.string.data_valid;
    }

    private void v_saveUsersData() {
        String s_weight = et_weightInput.getText().toString();
        String s_height = et_heightInput.getText().toString();

        SharedPreferences.Editor sp_editor = sp_shared.edit();
        sp_editor.clear();
        v_checkValuesToSave(s_height, s_weight);

        if (!s_weight.isEmpty()) {
            sp_editor.putString(getString(R.string.saved_weight), s_weight);
            sp_editor.apply();
        }

        if (!s_height.isEmpty()) {
            sp_editor.putString(getString(R.string.saved_height), s_height);
            sp_editor.apply();
        }

        sp_editor.putBoolean(getString(R.string.saved_unit_system), b_isImperial);
        sp_editor.apply();
    }

    private void v_readUsersData() {
        if(sp_shared.contains(getString(R.string.saved_weight)))
            et_weightInput.setText(sp_shared.getString(getString(R.string.saved_weight),""));
        if(sp_shared.contains(getString(R.string.saved_height)))
            et_heightInput.setText(sp_shared.getString(getString(R.string.saved_height),""));
        if(sp_shared.contains(getString(R.string.saved_unit_system)))
            b_isImperial = sp_shared.getBoolean(getString(R.string.saved_unit_system),false);

        if(b_isImperial)
            v_makeImperialUI();
    }

    private void v_checkValuesToSave(String sHeight,String sWeight) {
        if(sHeight.isEmpty() && sWeight.isEmpty())
            v_prompt(getResources().getString(R.string.no_values_to_save));
        else{
            if(sHeight.isEmpty())
                v_prompt(getResources().getString(R.string.no_height_save));
            else{
                if(sWeight.isEmpty())
                    v_prompt(getResources().getString(R.string.no_weight_save));
                else
                    v_prompt(getResources().getString(R.string.successful_save));
            }
        }
    }

    private void v_makeImperialUI() {
        bt_unitChange.setText(getResources().getText(R.string.metric));
        et_weightInput.setHint(getResources().getText(R.string.hint_inches));
        et_heightInput.setHint(getResources().getText(R.string.hint_pounds));
    }
}


