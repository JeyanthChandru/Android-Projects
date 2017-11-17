package jeyanthchandru.flamesforfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    String displayString,displayRslt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView displayRes = (TextView) findViewById(R.id.displayResult);
        TextView displayQuot = (TextView) findViewById(R.id.displayQuote);
        Button bckBtn = (Button) findViewById(R.id.backBtn);
        Intent i = getIntent();
        displayString = i.getStringExtra("e1");
        displayRslt = i.getStringExtra("e2");
        displayRes.setText(displayString);
        displayQuot.setText(displayRslt);
        Toast.makeText(getApplicationContext(),displayString,Toast.LENGTH_LONG).show();
        bckBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
