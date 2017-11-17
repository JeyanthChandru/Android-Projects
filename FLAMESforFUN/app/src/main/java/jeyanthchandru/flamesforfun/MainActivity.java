package jeyanthchandru.flamesforfun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText Name1, Name2;
    String flames_array = "FLAMES";
    String displayString;
    String displayRslt;
    int mod_v=0;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name1 = (EditText) findViewById(R.id.name1);
        Name2 = (EditText) findViewById(R.id.name2);
        Button Calc = (Button) findViewById(R.id.calcBtn);

        Calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String firstName = Name1.getText().toString();
                firstName = firstName.toUpperCase();
                String secondName = Name2.getText().toString();
                secondName = secondName.toUpperCase();
                if (firstName.equals("") || secondName.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter the names to calculate FLAMES", Toast.LENGTH_LONG).show();
                }
                else if(firstName.equals("JEYANTH") && (secondName.equals("ARPITHA")) || firstName.equals("ARPITHA") && (secondName.equals("JEYANTH")))
                {
                        displayString = "Marriage";
                        displayRslt = "Jeyanth and Arpitha are born to be married to each other. No matter what, It will happen.";
                        Intent i = new Intent(MainActivity.this, Main2Activity.class);
                        i.putExtra("e1", displayString);
                        i.putExtra("e2", displayRslt);
                        startActivity(i);
                }
                else if(firstName.equals("SRI") && (secondName.equals("ARPITHA")) || firstName.equals("ARPITHA") && (secondName.equals("SRI")))
                {
                    displayString = "Friends Forever";
                    displayRslt = "You are lucky to have a friend like SRI.";
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra("e1", displayString);
                    i.putExtra("e2", displayRslt);
                    startActivity(i);
                }
                else if(firstName.equals("SRINATH") && (secondName.equals("ARPITHA")) || firstName.equals("ARPITHA") && (secondName.equals("SRINATH")))
                {
                    displayString = "Friends Forever";
                    displayRslt = "You are lucky to have a friend like SRI.";
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra("e1", displayString);
                    i.putExtra("e2", displayRslt);
                    startActivity(i);
                }
                else if(firstName.equals("SARAVANAN") && (secondName.equals("LAKSHMI")) || firstName.equals("LAKSHMI") && (secondName.equals("SARAVANAN")))
                {
                    displayString = "Aasa Dosa Appala Vada..";
                    displayRslt = "Message for Charna : Dont even think about it... Aporam aapuku naa poruppu illa.., Message for Manni: Ungaluku thaan yerkanave kalyanam Ayiduchu la.. Aporam ethuku testingu..";
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra("e1", displayString);
                    i.putExtra("e2", displayRslt);
                    startActivity(i);
                }

                else {
                    for (int i=0;i<firstName.length();i++){
                        for (int j=0;j<secondName.length();j++){
                            if(firstName.charAt(i) == secondName.charAt(j))
                            {
                                StringBuilder sb1 = new StringBuilder(firstName);
                                sb1.deleteCharAt(i);
                                firstName=sb1.toString();
                                StringBuilder sb2 = new StringBuilder(secondName);
                                sb2.deleteCharAt(j);
                                secondName=sb2.toString();                            }
                        }
                    }
                    String finalString = firstName + secondName;
                    int lengthOfFinal = finalString.length();
                    String Text;
                    if(lengthOfFinal > 5)
                    Text = flames_cal(flames_array, lengthOfFinal, mod_v);
                    else
                    Text = Integer.toString(lengthOfFinal);
                    switch (Text) {
                        case "F":
                        case "0":
                            displayString = "FRIENDS";
                            displayRslt = "Friendship is always a sweet responsibility, never an opportunity";
                            break;
                        case "L":
                        case "1":
                            displayString = "LOVE";
                            displayRslt = "Love is when the other person's happiness is more important than your own.";
                            break;
                        case "A":
                        case "2":
                            displayString = "AFFECTION";
                            displayRslt = "Every gift which is given, even though it be small, is in reality great, if it is given with affection.";
                            break;
                        case "M":
                        case "3":
                            displayString = "MARRIAGE";
                            displayRslt = "Marriage is the most natural state of man, and... the state in which you will find solid happiness.";
                            break;
                        case "E":
                        case "4":
                            displayString = "ENEMY";
                            displayRslt = "It is easier to forgive an enemy than to forgive a friend.";
                            break;
                        case "S":
                        case "5":
                            displayString = "SISTER";
                            displayRslt = "Sister is probably the most competitive relationship within the family, but once the sisters are grown, it becomes the strongest relationship.";
                            break;
                        default:
                            displayString = "Not an Option";
                            displayRslt = "No Quotes";
                    }
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    i.putExtra("e1", displayString);
                    i.putExtra("e2", displayRslt);
                    startActivity(i);
                }
            }


        });

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                NetworkInfo info = (NetworkInfo) extras.getParcelable("networkInfo");
                NetworkInfo.State state = info.getState();
                Log.d("TEST Internet", info.toString() + " " + state.toString());

                if (state == NetworkInfo.State.CONNECTED) {
                    Toast.makeText(MainActivity.this, "Internet connection is on", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "Internet connection is Off", Toast.LENGTH_LONG).show();
                }

            }
        };

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver((BroadcastReceiver) br, intentFilter);
    }
    public static boolean contains(char[] in, int index, char t) {
        for (int i = 0; i < index; i++) {
            if (in[i] == t) return true;
        }
        return false;
    }

    public String flames_cal(String flames_array,int index,int mod_v){
        int len = flames_array.length();
        while(len > 1){
            int mod = (mod_v + index) % len;
            StringBuilder fla = new StringBuilder(flames_array);
            if(mod == 0)
                mod_v = flames_array.length() - 1;
            else
                mod_v = mod - 1;
            fla.deleteCharAt(mod_v);
            flames_array = fla.toString();
            len = flames_array.length();
        }
        return flames_array;
    }
}
