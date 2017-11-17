package jeyanthchandru.logarithmcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    TextView valueRes;
    TextView logBs;
    EditText numberTxt;
    EditText baseTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valueRes = (TextView) findViewById(R.id.valueResult);
        numberTxt = (EditText) findViewById(R.id.numberText);
        baseTxt = (EditText) findViewById(R.id.baseText);
        logBs = (TextView) findViewById(R.id.logBase);
        Button CalcBtn = (Button) findViewById(R.id.logBtn);
        CalcBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                double num = Double.parseDouble(numberTxt.getText().toString());
                double base = Double.parseDouble(baseTxt.getText().toString());
                double res = Math.log(num)/Math.log(base);
                valueRes.setText(Double.toString(res));
                logBs.setText("log(base "+base+") "+num);
            }
        });
    }

}
