package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private String [] qualifications = new String[12];
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
    private PeopleDAO peopleDAO;
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
            isCorrect=false;
            informWrongPhone();
        }
        return checkBoxInput(isCorrect);
    }
    private void startPersonsActivityWithResult() {
        Intent i=new Intent(NewTeacherActivity.this,PersonsActivity.class);
        setResult(RESULT_CANCELED,i);
        i.putExtra("peopleDAO", peopleDAO);
        startActivity(i);
    }

    private void putNewTeacherToSchool(){
        peopleDAO.school.listTeachers.add(new Teacher(teacherName.getText().toString(), teacherPhone.getText().toString(),
                peopleDAO.PEOPLE_COUNT,teacherPosition.getText().toString(),getQualificationsInOneLine()));
    }
    private void defineConfirmButtonListener(){
        buttonConfirm.setOnClickListener(v -> {
            if(isCorrectInput()){
                saveNewTeacherInDataBase();
                Toast.makeText(this, "Teacher is created", Toast.LENGTH_SHORT).show();
                putNewTeacherToSchool();
                startPersonsActivityWithResult();
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
    private void saveNewTeacherInDataBase(){
        peopleDAO.createDatabase();
        peopleDAO.database.insert(DBHelper.TABLE_PARTICIPANTS, null ,
                peopleDAO.makeContentValueForTeacher(teacherName.getText().toString(), teacherPhone.getText().toString(),
                        teacherPosition.getText().toString(),getQualificationsInOneLine()));
    }
    private void getPeopleDao(){
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        peopleDAO.setDbHelper(new DBHelper(this));
    }
    private String getQualificationsInOneLine(){
        StringBuilder qualificationsInOneLine = new StringBuilder();
        for (int i = 0; i < 12 ; i++) {
            qualificationsInOneLine.append(qualifications[i]);
            if (i == 11) return qualificationsInOneLine.toString() + ".";
            else if (qualifications[i + 1] == null) return qualificationsInOneLine.toString() + ".";
            else qualificationsInOneLine.append(", ");
        }
        return qualificationsInOneLine.toString();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_teacher_activity);
        getPeopleDao();

        defineElements();
    }
}
