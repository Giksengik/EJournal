package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class NewTeacherActivity extends AppCompatActivity {
    private School school;
    private String [] qualifications = new String[12];
    private final String FILE_TEACHERS="teachers";
    private Button buttonConfirm;
    private CheckBox checkBoxMath;
    private CheckBox checkBoxPhysics;
    private CheckBox checkBoxHistory;
    private CheckBox checkBoxBiology;
    private CheckBox checkBoxChemistry;
    private CheckBox checkBoxPhysTrain;
    private CheckBox checkBoxComputerScience;
    private CheckBox checkBoxGeography;
    private CheckBox checkBoxForeignLanguage;
    private CheckBox checkBoxNativeLanguage;
    private CheckBox checkBoxLiterature;
    private CheckBox checkBoxSocialStudies;
    private TextView wrongInput;
    private EditText teacherName;
    private EditText teacherPosition;
    private EditText teacherPhone;
    private void defineSchool(){
        Intent mIntent = getIntent();
        school =(School)mIntent.getSerializableExtra("school");

    }
    private void informWrongName(){
        teacherName.setText("");
        teacherName.setHint("wrong input");
        teacherName.setHintTextColor(Color.RED);
    }
    private void informWrongPhone(){
        teacherPhone.setText("");
        teacherPhone.setHint("wrong input, don't write + ");
        teacherPhone.setHintTextColor(Color.RED);
    }
    private void informWrongPosition(){
        teacherPosition.setText("");
        teacherPosition.setHint("wrong input");
        teacherPosition.setHintTextColor(Color.RED);
    }
    @SuppressLint("SetTextI18n")
    private boolean checkBoxInput(boolean isCorrect){
        if(checkBoxPhysics.isChecked()){
            addQualification("Mathematics",qualifications);
            addQualification("Physics",qualifications);
        }else if(checkBoxMath.isChecked()){
            addQualification("Mathematics",qualifications);
        }
        if(checkBoxBiology.isChecked()){
            addQualification("Biology",qualifications);
        }
        if(checkBoxHistory.isChecked() || checkBoxSocialStudies.isChecked()){
            addQualification("History",qualifications);
            addQualification("Social Studies",qualifications);
        }
        if(checkBoxChemistry.isChecked()){
            addQualification("Chemistry",qualifications);
        }
        if(checkBoxPhysTrain.isChecked()){
            addQualification("Physical Training",qualifications);
        }
        if(checkBoxComputerScience.isChecked()){
            addQualification("Computer Science",qualifications);
        }
        if(checkBoxGeography.isChecked()){
            addQualification("Geography",qualifications);
        }
        if(checkBoxForeignLanguage.isChecked()){
            addQualification("Foreign Language",qualifications);
        }
        if(checkBoxNativeLanguage.isChecked() || checkBoxLiterature.isChecked()){
            addQualification("Native Language",qualifications);
            addQualification("Literature",qualifications);
        }
        if(qualifications[0] == null){
            wrongInput.setText("Wrong qualification, please choose at least one subject");
            return false;
        }
        return isCorrect;
    }
    @SuppressLint("SetTextI18n")
    private boolean isCorrectInput(){
        boolean isCorrect=true;
        if(!StringValidation.isCorrectString(teacherName.getText().toString())){
            isCorrect=false;
            informWrongName();
        }
        if(!StringValidation.isCorrectString(teacherPosition.getText().toString())){
            isCorrect=false;
            informWrongPosition();
        }
        if(!StringValidation.isCorrectPhoneNumber(teacherPhone.getText().toString())){
            isCorrect=true;
            informWrongPhone();
        }
        return checkBoxInput(isCorrect);
    }
    private void startMainActivityWithResult() {
        Intent i=new Intent(NewTeacherActivity.this,MainActivity.class);
        i.putExtra("school",school);
        setResult(RESULT_CANCELED,i);
        startActivity(i);
    }
    private void writeNewTeacherQualificationsToFile(FileOutputStream fos) throws IOException {
        int i=0;
        while(i<12){
            if (qualifications[i]==null) {
                fos.write("----------".getBytes());
                fos.write("\n".getBytes());
                break;
            }
            fos.write(qualifications[i].getBytes());
            fos.write("\n".getBytes());
            if(i==11){
                fos.write("----------".getBytes());
                fos.write("\n".getBytes());
            }
            i++;
        }
    }
    private void writeNewTeacherToFile(){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_TEACHERS, MODE_APPEND);
            fos.write((School.num_of_cards+"").getBytes());
            fos.write("\n".getBytes());
            fos.write(teacherName.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(teacherPhone.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(teacherPosition.getText().toString().getBytes());
            fos.write("\n".getBytes());
            writeNewTeacherQualificationsToFile(fos);
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
    private void putNewTeacherToSchool(){
        Teacher teacher =new Teacher(teacherName.getText().toString(),teacherPhone.getText().toString(),School.num_of_cards,
                teacherPosition.getText().toString(),qualifications);
        School.num_of_cards++;
        for(int i=0;i<school.teachers.length;i++){
            if(school.teachers[i]==null){
                school.teachers[i]=teacher;
                break;
            }
        }
    }
    private void makeTeacher(){
        writeNewTeacherToFile();
        putNewTeacherToSchool();
        Toast.makeText(this, "Teacher added", Toast.LENGTH_SHORT).show();
    }
    private void defineConfirmButtonListener(){
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(isCorrectInput()){
                    makeTeacher();
                    startMainActivityWithResult();
                }
            }
        });
    }
    void addQualification(String qualification,String [] qualifications) {
        for (int i = 0; i < qualifications.length; i++) {
            if (qualifications[i] == null) {
                qualifications[i] = qualification;
                break;
            }
        }
    }
    private void defineElements() {
        buttonConfirm = (Button) findViewById(R.id.newTeacherConfirm);
        checkBoxMath = (CheckBox) findViewById(R.id.checkBoxMath);
        checkBoxPhysics = (CheckBox) findViewById(R.id.checkBoxPhysics);
        checkBoxHistory = (CheckBox) findViewById(R.id.checkBoxHistory);
        checkBoxBiology = (CheckBox) findViewById(R.id.checkBoxBiology);
        checkBoxChemistry = (CheckBox) findViewById(R.id.checkBoxChemistry);
        checkBoxPhysTrain = (CheckBox) findViewById(R.id.checkBoxPhysicalTraining);
        checkBoxComputerScience = (CheckBox) findViewById(R.id.checkBoxComputerScience);
        checkBoxGeography = (CheckBox) findViewById(R.id.checkBoxGeography);
        checkBoxForeignLanguage = (CheckBox) findViewById(R.id.checkBoxForeignLanguage);
        checkBoxNativeLanguage = (CheckBox) findViewById(R.id.checkBoxNativeLanguage);
        checkBoxLiterature = (CheckBox) findViewById(R.id.checkBoxLiterature);
        checkBoxSocialStudies = (CheckBox) findViewById(R.id.checkBoxSocialStudies);
        wrongInput = (TextView) findViewById(R.id.newTeacherWrongInput);
        teacherName = (EditText) findViewById(R.id.editTextNewTeacher1);
        teacherPosition = (EditText) findViewById(R.id.editTextNewTeacher2);
        teacherPhone = (EditText) findViewById(R.id.editTextNewTeacher3);
        defineConfirmButtonListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_teacher_activity);
        defineSchool();
        defineElements();
    }
}
