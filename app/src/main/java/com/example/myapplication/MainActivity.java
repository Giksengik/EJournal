package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    final String FILE_NAME_PERSONS = "filePersons";
    final String FILE_NAME_SCHOOL = "fileSchool";
    final String SCHOOL_NAME = "RTU MIREA";
    final String SCHOOL_ADDRESS = "Vernadsky prospect, 78, Moscow";
    final String FILE_NAME_EMPLOYEE = "employees";
    final String FILE_NAME_LEARNERS = "learners";
    final String FILE_NAME_TEACHERS = "teachers";
    final String FILE_NAME_CLASSES = "class";
    private School school;
    private Intent result;
    private PeopleDAO peopleDAO;

    private Button buttonClasses;
    private Button buttonElectives;
    private Button buttonPersons;
    private Button buttonSections;
    private TextView textSchoolAddress;
    private TextView textSchoolName;
    private boolean isThereResult(){
        return !(result.getSerializableExtra("peopleDAO") == null);
    }
    private void defineButtonElectivesListener() {
        buttonElectives.setOnClickListener(v -> {
            Intent i;
            i = new Intent(MainActivity.this, ElectivesActivity.class);
            i.putExtra("peopleDAO", peopleDAO);
            startActivity(i);
        });
    }
    private void defineButtonClassesListener() {
        buttonClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity.this, ClassesActivity.class);
                i.putExtra("peopleDAO", peopleDAO);
                startActivity(i);
            }
        });
    }
    private void defineButtonPersonsListener() {
        buttonPersons.setOnClickListener(v -> {
            Intent i;
            i = new Intent(MainActivity.this, PersonsActivity.class);
            i.putExtra("peopleDAO", peopleDAO);
            startActivity(i);
        });
    }
    private void defineButtonSectionsListener() {
        buttonSections.setOnClickListener(v -> {
            Intent i;
            i = new Intent(MainActivity.this, SectionsActivity.class);
            i.putExtra("peopleDAO", peopleDAO);
            startActivity(i);
        });
    }
    private void defineButtonsListeners(){
        defineButtonElectivesListener();
        defineButtonClassesListener();
        defineButtonPersonsListener();
        defineButtonSectionsListener();
    }
    private void defineElements(){
        setContentView(R.layout.activity_main);
        buttonClasses = (Button) findViewById(R.id.buttonClasses);
        buttonElectives = (Button) findViewById(R.id.buttonElectives);
        buttonPersons = (Button) findViewById(R.id.buttonPersons);
        buttonSections = (Button) findViewById(R.id.buttonSections);
        textSchoolAddress = (TextView) findViewById(R.id.textSchoolAddress);
        textSchoolName = (TextView) findViewById(R.id.textSchoolName);
        defineButtonsListeners();
    }
    @SuppressLint("SetTextI18n")
    private void setSchoolName() {
        try {
            FileInputStream inputStream;
            inputStream = openFileInput(FILE_NAME_SCHOOL);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader buffreader = new BufferedReader(isr);
            String readString = buffreader.readLine();
            textSchoolName.setText("Name: " + readString);
            readString = buffreader.readLine();
            textSchoolAddress.setText("Address: " + readString);
            isr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void makeSchoolNameFile() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME_SCHOOL, MODE_PRIVATE);
            fos.write(SCHOOL_NAME.getBytes());
            fos.write("\n".getBytes());
            fos.write(SCHOOL_ADDRESS.getBytes());
            fos.write("\n".getBytes());
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean isSchoolNameExists() {
        try {
            FileInputStream inputStream = null;
            inputStream = openFileInput(FILE_NAME_SCHOOL);
            int data = inputStream.read();
            return data != 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    private void defineSchool(){
        result = getIntent();
        if(isThereResult()){
            peopleDAO = (PeopleDAO) result.getSerializableExtra("peopleDAO");
        } else {
            peopleDAO.setDbHelper(new DBHelper(this));
            peopleDAO.getPeople();
        }
    }
    private void createPeopleDAO(){
        peopleDAO = new PeopleDAO();
        peopleDAO.setDbHelper(new DBHelper(this));
        peopleDAO.createDatabase();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPeopleDAO();
        defineSchool();
        defineElements();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        school = (School) data.getSerializableExtra("school");
    }

}