package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class NewLearnerActivity extends AppCompatActivity {
    private School school;
    private EditText parentsName1;
    private EditText parentsPhone1;
    private EditText parentsName2;
    private EditText parentsPhone2;
    private EditText learnersName;
    private EditText learnersPhone;
    private Button confirmButton;
    final String FILE_LEARNERS= "learners";
    private void defineElements(){
        parentsName1=(EditText) findViewById(R.id.editTextNewLearner1);
        parentsPhone1=(EditText) findViewById(R.id.editTextNewLearner2);
        parentsName2=(EditText) findViewById(R.id.editTextNewLearner3);
        parentsPhone2=(EditText) findViewById(R.id.editTextNewLearner4);learnersName=(EditText) findViewById(R.id.editTextNewLearner5);
        learnersPhone=(EditText) findViewById(R.id.editTextNewLearner6);
        confirmButton=(Button)findViewById(R.id.newLearnerConfirm);
        defineConfirmButtonListener();
    }
    private void informWrongInputParentName1(){
        parentsName1.setText("");
        parentsName1.setHint("wrong input");
        parentsName1.setHintTextColor(Color.RED);
    }
    private void informWrongInputParentName2(){
        parentsName2.setText("");
        parentsName2.setHint("wrong input");
        parentsName2.setHintTextColor(Color.RED);
    }
    private void informWrongInputParentPhone1(){
        parentsPhone1.setText("");
        parentsPhone1.setHint("wrong input, don't write + ");
        parentsPhone1.setHintTextColor(Color.RED);
    }
    private void informWrongInputParentPhone2(){
        parentsPhone2.setText("");
        parentsPhone2.setHint("wrong input, don't write + ");
        parentsPhone2.setHintTextColor(Color.RED);
    }
    private void informWrongInputLearnerName(){
        learnersName.setText("");
        learnersName.setHint("wrong input");
        learnersName.setHintTextColor(Color.RED);
    }
    private void informWrongInputLearnerPhone(){
        learnersPhone.setText("");
        learnersPhone.setHint("wrong input, don't write + ");
        learnersPhone.setHintTextColor(Color.RED);
    }
    private void startMainActivityWithResult(){
        Intent i=new Intent(NewLearnerActivity.this,MainActivity.class);
        i.putExtra("school",school);
        setResult(RESULT_CANCELED,i);
        startActivity(i);
    }
    private boolean isCorrectInput(){
        boolean isCorrectInput = true;
        if (!StringValidation.isCorrectString(parentsName1.getText().toString())){
            informWrongInputParentName1();
            isCorrectInput = false;
        }
        if (!StringValidation.isCorrectString(parentsName2.getText().toString())) {
            informWrongInputParentName2();
            isCorrectInput = false;
        }
        if (!StringValidation.isCorrectPhoneNumber(parentsPhone1.getText().toString())) {
            informWrongInputParentPhone1();
            isCorrectInput = false;
        }
        if (!StringValidation.isCorrectPhoneNumber(parentsPhone2.getText().toString())) {
            informWrongInputParentPhone2();
            isCorrectInput = false;
        }
        if (!StringValidation.isCorrectString(learnersName.getText().toString())){
            informWrongInputLearnerName();
            isCorrectInput = false;
        }
        if (!StringValidation.isCorrectPhoneNumber(learnersPhone.getText().toString())) {
            informWrongInputLearnerPhone();
            isCorrectInput = false;
        }
        return isCorrectInput;
    }
    private void defineConfirmButtonListener(){
        confirmButton.setOnClickListener(v -> {
            if(isCorrectInput()){
                makeLearner();
                startMainActivityWithResult();
            }
        });
    }
    private void writeNewLearnerToFile(){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_LEARNERS, MODE_APPEND);
            fos.write((School.num_of_cards+"").getBytes());
            fos.write("\n".getBytes());
            fos.write(parentsName1.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(parentsPhone1.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(parentsName2.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(parentsPhone2.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(learnersName.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(learnersPhone.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write("----------".getBytes());
            fos.write("\n".getBytes());
            Toast.makeText(this, "Learner added", Toast.LENGTH_SHORT).show();
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
    private void putNewLearnerToSchool(){
        Parent parent1=new Parent(parentsName1.getText().toString(),parentsPhone1.getText().toString());
        Parent parent2=new Parent(parentsName2.getText().toString(),parentsPhone2.getText().toString());
        Parent [] parents ={parent1,parent2};
        Learner learner = new Learner(learnersName.getText().toString(),learnersPhone.getText().toString(),School.num_of_cards,parents);
        School.num_of_cards++;
        for(int i=0;i<school.learners.length;i++){
            if(school.learners[i]==null){
                school.learners[i]=learner;
                break;
            }
        }
    }
    private void makeLearner(){
        writeNewLearnerToFile();
        putNewLearnerToSchool();
    }
    private void defineSchool() {
        Intent mIntent = getIntent();
        school=(School)mIntent.getSerializableExtra("school");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_learner_activity);
        defineSchool();
        defineElements();

}

}

