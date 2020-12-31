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
    School school;
    String [] qualifications = new String[12];
    final String FILE_TEACHERS="teachers";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_teacher_activity);
        Intent mIntent = getIntent();
        school=(School)mIntent.getSerializableExtra("school");
        Button buttonConfirm= (Button)findViewById(R.id.newTeacherConfirm);
        CheckBox checkBoxMath = (CheckBox)findViewById(R.id.checkBoxMath);
        CheckBox checkBoxPhysics= (CheckBox)findViewById(R.id.checkBoxPhysics);
        CheckBox checkBoxHistory = (CheckBox)findViewById(R.id.checkBoxHistory);
        CheckBox checkBoxBiology = (CheckBox)findViewById(R.id.checkBoxBiology);
        CheckBox checkBoxChemistry = (CheckBox)findViewById(R.id.checkBoxChemistry);
        CheckBox checkBoxPhysTrain = (CheckBox)findViewById(R.id.checkBoxPhysicalTraining);
        CheckBox checkBoxComputerScience = (CheckBox)findViewById(R.id.checkBoxComputerScience);
        CheckBox checkBoxGeography = (CheckBox)findViewById(R.id.checkBoxGeography);
        CheckBox checkBoxForeignLanguage = (CheckBox)findViewById(R.id.checkBoxForeignLanguage);
        CheckBox checkBoxNativeLanguage = (CheckBox)findViewById(R.id.checkBoxNativeLanguage);
        CheckBox checkBoxLiterature = (CheckBox)findViewById(R.id.checkBoxLiterature);
        CheckBox checkBoxSocialStudies =(CheckBox)findViewById(R.id.checkBoxSocialStudies);
        TextView wrongInput = (TextView)findViewById(R.id.newTeacherWrongInput);
        EditText teacherName  = (EditText)findViewById(R.id.editTextNewTeacher1);
        EditText teacherPosition = (EditText)findViewById(R.id.editTextNewTeacher2);
        EditText teacherPhone = (EditText)findViewById(R.id.editTextNewTeacher3);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                boolean flag1=false;
                if(teacherName.getText().toString().matches("[a-zA-Z| ]+")&&
                        teacherName.getText().toString().length()!=1&&
                        !teacherName.getText().toString().matches("[ ]*")) {
                    flag1=true;
                }else{
                     teacherName.setText("");
                    teacherName.setHint("wrong input");
                    teacherName.setHintTextColor(Color.RED);
                }
                if(teacherPosition.getText().toString().length()!=0&&
                !teacherPosition.getText().toString().matches("[ ]*")){
                    flag1=true;
                }else{
                    teacherPosition.setText("");
                    teacherPosition.setHint("wrong input");
                    teacherPosition.setHintTextColor(Color.RED);
                }
                if(teacherPhone.getText().toString().length() == 11||
                        teacherPhone.getText().toString().matches("[0-9]+")){
                    flag1=true;
                }else{
                    teacherPhone.setText("");
                    teacherPhone.setHint("wrong input, don't write + ");
                    teacherPhone.setHintTextColor(Color.RED);
                }
                    boolean flag = false;
                if(checkBoxMath.isChecked()){
                    addQualification("Mathematics",qualifications);
                    flag=true;
                }
                if(checkBoxPhysics.isChecked()){
                    addQualification("Mathematics",qualifications);
                    addQualification("Physics",qualifications);
                    flag=true;
                }
                if(checkBoxBiology.isChecked()){
                    addQualification("Biology",qualifications);
                    flag=true;
                }
                if(checkBoxHistory.isChecked()){
                    addQualification("History",qualifications);
                    addQualification("Social Studies",qualifications);
                    flag=true;
                }
                if(checkBoxChemistry.isChecked()){
                    addQualification("Chemistry",qualifications);
                    flag=true;
                }
                if(checkBoxPhysTrain.isChecked()){
                    addQualification("Physical Training",qualifications);
                    flag=true;
                }
                if(checkBoxComputerScience.isChecked()){
                    addQualification("Computer Science",qualifications);
                    flag=true;
                }
                if(checkBoxGeography.isChecked()){
                    addQualification("Geography",qualifications);
                    flag=true;
                }
                if(checkBoxForeignLanguage.isChecked()){
                    addQualification("Foreign Language",qualifications);
                    flag=true;
                }
                if(checkBoxNativeLanguage.isChecked()){
                    addQualification("Native Language",qualifications);
                    addQualification("Literature",qualifications);
                    flag=true;
                }
                if(checkBoxLiterature.isChecked()){
                    addQualification("Native Language",qualifications);
                    addQualification("Literature",qualifications);
                    flag=true;
                }
                if(checkBoxSocialStudies.isChecked()){
                    addQualification("History",qualifications);
                    addQualification("Social Studies",qualifications);
                    flag=true;
                }
                if(!flag||!flag1){
                    wrongInput.setText("Wrong qualification, please choose at least one subject");
                }
                else{
                    makeTeacher(qualifications,teacherName.getText().toString(),teacherPhone.getText().toString()
                    ,teacherPosition.getText().toString());
                    Intent i=new Intent(NewTeacherActivity.this,MainActivity.class);
                    i.putExtra("school",school);
                    setResult(RESULT_CANCELED,i);
                    startActivity(i);
                }
            }
        });
    }
    void addQualification(String qualification,String [] qualifications){
        for(int i=0;i<qualifications.length;i++){
            if(qualifications[i]==null){
                qualifications[i]=qualification;
                break;
            }else if(qualifications[i].equals(qualification)){
                break;
            }
        }
    }
    void makeTeacher(String [] qualifications,String name,String phone,String position){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_TEACHERS, MODE_APPEND);
            fos.write((School.num_of_cards+"").getBytes());
            fos.write("\n".getBytes());
            fos.write(name.getBytes());
            fos.write("\n".getBytes());
            fos.write(phone.getBytes());
            fos.write("\n".getBytes());
            fos.write(position.getBytes());
            fos.write("\n".getBytes());
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
            Toast.makeText(this, "Teacher added", Toast.LENGTH_SHORT).show();
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
        Teacher teacher =new Teacher(name,phone,School.num_of_cards,position,qualifications);
        School.num_of_cards++;
        for(int i=0;i<school.teachers.length;i++){
            if(school.teachers[i]==null){
                school.teachers[i]=teacher;
                break;
            }
        }
    }

}
