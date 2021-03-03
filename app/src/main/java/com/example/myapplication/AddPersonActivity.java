package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddPersonActivity extends AppCompatActivity {
    private View.OnClickListener listener;
    private Button buttonNewLearner;
    private Button buttonNewEmployee;
    private Button buttonNewTeacher;
    private PeopleDAO peopleDAO;
    private void startNewEmployeeActivity() {
        Intent i;
        i = new Intent(AddPersonActivity.this, NewEmployeeActivity.class);
        i.putExtra("peopleDAO", peopleDAO);
        startActivity(i);
    }

    private void startNewLearnerActivity() {
        Intent i;
        i = new Intent(AddPersonActivity.this, NewLearnerActivity.class);
        i.putExtra("peopleDAO", peopleDAO);
        startActivity(i);
    }

    private void startNewTeacherActivity() {
        Intent i;
        i = new Intent(AddPersonActivity.this, NewTeacherActivity.class);
        i.putExtra("peopleDAO", peopleDAO);
        startActivity(i);
    }

    @SuppressLint("NonConstantResourceId")
    private void defineButtonListener() {
        listener = v -> {
            switch (v.getId()) {
                case R.id.buttonNewEmployee:
                    startNewEmployeeActivity();
                    break;
                case R.id.buttonNewLearner:
                    startNewLearnerActivity();
                    break;
                case R.id.buttonNewTeacher:
                    startNewTeacherActivity();
                    break;
            }
        };
    }

    private void defineButtons() {
        buttonNewLearner = (Button) findViewById(R.id.buttonNewLearner);
        buttonNewEmployee = (Button) findViewById(R.id.buttonNewEmployee);
        buttonNewTeacher = (Button) findViewById(R.id.buttonNewTeacher);
        defineButtonListener();
        buttonNewEmployee.setOnClickListener(listener);
        buttonNewLearner.setOnClickListener(listener);
        buttonNewTeacher.setOnClickListener(listener);
    }
    private void getPeopleDao(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_person_activity);
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        defineButtons();
    }
}

