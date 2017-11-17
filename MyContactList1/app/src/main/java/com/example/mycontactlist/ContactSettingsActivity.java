package com.example.mycontactlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

public class ContactSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_settings);
        initListButton();
        initMapButton();
        initSettingsButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
        initColorChoice();
    }
    private void initListButton(){
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactSettingsActivity.this,ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initMapButton(){
        ImageButton ibMap = (ImageButton) findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactSettingsActivity.this,ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettings() {
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield","contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder","ASC");
        String colorChoice = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("colorchoice","white");

        RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
        RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
        RadioButton rbBirthDay = (RadioButton) findViewById(R.id.radioBirthday);
        if (sortBy.equalsIgnoreCase("contactname")) {
            rbName.setChecked(true);
        }
        else if (sortBy.equalsIgnoreCase("city")) {
            rbCity.setChecked(true);
        }
        else {
            rbBirthDay.setChecked(true);
        }

        RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
        RadioButton rbDescending = (RadioButton) findViewById(R.id.radioDescending);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }

        RadioButton rbGray = (RadioButton) findViewById(R.id.radioGray);
        RadioButton rbYellow = (RadioButton) findViewById(R.id.radioYellow);
        RadioButton rbWhite = (RadioButton) findViewById(R.id.radioWhite);
        RadioButton rbBlue = (RadioButton) findViewById(R.id.radioBlue);
        ScrollView scrollColorChoice = (ScrollView) findViewById(R.id.ScrollViewColorChoice);


        if(colorChoice.equalsIgnoreCase("white")){
            rbWhite.setChecked(true);
            scrollColorChoice.setBackgroundResource(R.color.radio_background_white);
        }
        else if(colorChoice.equalsIgnoreCase("gray")){
            rbGray.setChecked(true);
            scrollColorChoice.setBackgroundResource(R.color.radio_background_gray);
        }
        else if (colorChoice.equalsIgnoreCase("yellow")){
            rbYellow.setChecked(true);
            scrollColorChoice.setBackgroundResource(R.color.radio_background_yellow);
        }
        else{
            rbBlue.setChecked(true);
            scrollColorChoice.setBackgroundResource(R.color.radio_background_blue);
        }

    }
    private void initSettingsButton(){
        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setBackgroundColor(Color.parseColor("#1A1A48"));
        ibSettings.setEnabled(false);
    }
    private void initSortByClick() {
        RadioGroup rgSortBy = (RadioGroup) findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
                RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
                if (rbName.isChecked()) {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit() .putString("sortfield", "contactname").commit();
                }
                else if (rbCity.isChecked()) {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "city").commit();
                }
                else {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "birthday").commit();
                }
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = (RadioGroup) findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").commit();
                }
                else {
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").commit();
                }
            }
        });
    }

    private void initColorChoice(){
        RadioGroup rgColorChoice = (RadioGroup) findViewById(R.id.radioGroupColorChoice);
        rgColorChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1){
                RadioButton rbGray = (RadioButton) findViewById(R.id.radioGray);
                RadioButton rbYellow = (RadioButton) findViewById(R.id.radioYellow);
                RadioButton rbWhite = (RadioButton) findViewById(R.id.radioWhite);
                ScrollView scrollColorChoice = (ScrollView) findViewById(R.id.ScrollViewColorChoice);



                if(rbGray.isChecked()){
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("colorchoice","gray").commit();
                    scrollColorChoice.setBackgroundResource(R.color.radio_background_gray);

                }
                else if(rbYellow.isChecked()){
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("colorchoice","yellow").commit();
                    scrollColorChoice.setBackgroundResource(R.color.radio_background_yellow);

                }
                else if(rbWhite.isChecked()){
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("colorchoice","white").commit();
                    scrollColorChoice.setBackgroundResource(R.color.radio_background_white);

                }
                else{
                    getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("colorchoice","blue").commit();
                    scrollColorChoice.setBackgroundResource(R.color.radio_background_blue);

                }
            }
        });
    }
}

