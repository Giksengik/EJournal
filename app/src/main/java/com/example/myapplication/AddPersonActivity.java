package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddPersonActivity extends AppCompatActivity {
    School school;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_person_activity);
        Button buttonNewLearner= (Button)findViewById(R.id.buttonNewLearner);
        Button buttonNewEmployee= (Button)findViewById(R.id.buttonNewEmployee);
        Button buttonNewTeacher= (Button)findViewById(R.id.buttonNewTeacher);
        buttonNewEmployee.setOnClickListener(listener);
        buttonNewLearner.setOnClickListener(listener);
        buttonNewTeacher.setOnClickListener(listener);
        Intent mIntent = getIntent();
        school=(School)mIntent.getSerializableExtra("school");
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        @SuppressLint("NonConstantResourceId")
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.buttonNewEmployee:
                    i = new Intent(AddPersonActivity.this, NewEmployeeActivity.class);
                    i.putExtra("school",school);
                    startActivity(i);
                    break;
                case R.id.buttonNewLearner:
                    i = new Intent(AddPersonActivity.this, NewLearnerActivity.class);
                    i.putExtra("school",school);
                    startActivity(i);
                    break;
                case R.id.buttonNewTeacher:
                    i = new Intent(AddPersonActivity.this, NewTeacherActivity.class);
                    i.putExtra("school",school);
                    startActivity(i);
                    break;
            }
        }
    };
}

