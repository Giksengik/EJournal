package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class ClassesActivity extends AppCompatActivity {
    School school;
    final String FILE_NAME_CLASSES="class";
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        Intent mIntent = getIntent();
        school=(School)mIntent.getSerializableExtra("school");
        Dialog dialogNewClass =  new Dialog(ClassesActivity.this);
        dialogNewClass.setContentView(R.layout.new_class);
        Dialog dialogClass= new Dialog(ClassesActivity.this);
        dialogClass.setContentView(R.layout.info_class);
        EditText newClassTeacherID= (EditText)dialogNewClass.findViewById(R.id.newClassTeacherId);
        EditText newClassName = (EditText)dialogNewClass.findViewById(R.id.newClassName);
        Button buttonCancelNewClass = (Button)dialogNewClass.findViewById(R.id.buttonCancelNewClass);
        Button buttonOkNewClass = (Button)dialogNewClass.findViewById(R.id.buttonOkNewClass);
        Button buttonNewClass = (Button)findViewById(R.id.buttonNewClass);
        TextView className=(TextView)dialogClass.findViewById(R.id.className);
        TextView classTeacherName=(TextView)dialogClass.findViewById(R.id.classTeachersName);
        TextView classTeacherId = (TextView)dialogClass.findViewById(R.id.classTeachersID);
        TextView classLearners= (TextView)dialogClass.findViewById(R.id.classLearners);
        EditText newClassLearnerId = (EditText) dialogClass.findViewById(R.id.newClassLearnerId);
        Button buttonClassAddLearner = (Button)dialogClass.findViewById(R.id.buttonClassAddLearner);
        String [] classes= new String[School.num_of_classes];
        for(int i=0;i<School.num_of_classes;i++){
            classes[i]="Class: "+school.classes[i].number+" | Teacher's name: "+school.classes[i].classTeacher.fullName+
                    "| Teacher's ID: "+school.classes[i].classTeacher.CardID;
        }
        ListView lvMain = (ListView)findViewById(R.id.listViewClasses);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                classes);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener((parent, view, position, id) -> {
            className.setText("Class name: "+school.classes[position].number);
            classTeacherId.setText("Class teacher's ID: "+school.classes[position].classTeacher.CardID);
            classTeacherName.setText("Class teacher's name: "+school.classes[position].classTeacher.fullName);
            String learners="";
            String b;
            for(int i=0;i<school.classes[position].learners.length;i++){
                if(school.classes[position].learners[i]==null){
                    break;
                }else{
                    if(i != 0) {
                        b = learners + ", " + " Name: " + school.classes[position].learners[i].fullName + " ID: " +
                                school.classes[position].learners[i].CardID;
                    }else{
                        b = " Name: " + school.classes[position].learners[i].fullName + " ID: " +
                                school.classes[position].learners[i].CardID;
                    }
                    learners=b;
                }
            }
            if(learners.equals("")){
                classLearners.setText("Learners: none");
            }else{
                classLearners.setText(learners);
            }
            dialogClass.show();
        });
        buttonNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogClass.show();
            }
        });
        buttonClassAddLearner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newClassLearnerId.getText().toString().matches("[0-9]+")) {
                    if (Integer.parseInt(newClassLearnerId.getText().toString()) >= School.num_of_cards) {
                        newClassLearnerId.setText("");
                        newClassLearnerId.setHint("no learner with this ID");
                        newClassLearnerId.setHintTextColor(Color.RED);

                    } else {
                        Learner newLearner = null;
                        boolean flag1 = true;
                        for (int i = 0; i < school.learners.length; i++) {
                            if (school.learners[i] == null) {
                                newClassLearnerId.setText("");
                                newClassLearnerId.setHint("no learner with this ID");
                                newClassLearnerId.setHintTextColor(Color.RED);
                                flag1 = false;
                                break;
                            } else if (school.learners[i].CardID == Integer.parseInt(newClassLearnerId.getText().toString())) {
                                newLearner = school.learners[i];
                                break;
                            }
                        }
                        if (flag1) {
                            boolean flag = true;
                            for (int i = 0; i < school.classes.length; i++) {
                                if (school.classes[i] == null) {
                                    break;
                                } else {
                                    for (int j = 0; j < school.classes[i].learners.length; j++) {
                                        if (school.classes[i].learners[j] == null) {
                                            break;
                                        } else {
                                            if (school.classes[i].learners[j].CardID ==
                                                    Integer.parseInt(newClassLearnerId.getText().toString())) {
                                                newClassLearnerId.setText("");
                                                newClassLearnerId.setHint(
                                                        "the student is already in another class");
                                                newClassLearnerId.setHintTextColor(Color.RED);
                                                flag = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (flag){
                                for(int i=0;i<school.classes.length;i++){
                                    String a=className.getText().toString().substring(12);
                                    if(school.classes[i].number.equals(a)){
                                        for(int j=0;j<school.classes[i].learners.length;j++){
                                           if(school.classes[i].learners[j]==null){
                                               school.classes[i].learners[j]=newLearner;
                                               break;
                                           }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }else{
                    newClassLearnerId.setText("");
                    newClassLearnerId.setHint("wrong input");
                    newClassLearnerId.setHintTextColor(Color.RED);
                }
            }
        });
        buttonOkNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                Teacher classTeacher = null;
                if (newClassTeacherID.getText().toString().matches("[0-9]+")) {
                    if (Integer.parseInt(newClassTeacherID.getText().toString()) > School.num_of_cards) {
                        newClassTeacherID.setText("");
                        newClassTeacherID.setHint("no teacher with this ID");
                        newClassTeacherID.setHintTextColor(Color.RED);
                    } else {
                        for (int i = 0; i < school.teachers.length; i++) {
                            if (school.teachers[i] == null) {
                                newClassTeacherID.setText("");
                                newClassTeacherID.setHint("no teacher with this ID");
                                newClassTeacherID.setHintTextColor(Color.RED);
                                break;
                            } else if (school.teachers[i].CardID == Integer.parseInt(newClassTeacherID.getText().toString())) {
                                flag = true;
                                classTeacher = school.teachers[i];
                                break;
                            }
                        }
                    }
                } else {
                    newClassTeacherID.setText("");
                    newClassTeacherID.setHint("wrong input");
                    newClassTeacherID.setHintTextColor(Color.RED);
                }
                if (newClassName.getText().toString().length() == 0 ||
                        newClassName.getText().toString().matches("[ ]*")) {
                    flag = false;
                    newClassName.setText("");
                    newClassName.setHint("wrong input");
                    newClassName.setHintTextColor(Color.RED);
                }
                if(flag){
                    makeNewClass(classTeacher,newClassName.getText().toString());
                }
            }
        });
    }
    void makeNewClass(Teacher classTeacher,String className){
        School.num_of_classes++;
        String file_name=FILE_NAME_CLASSES+School.num_of_classes;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(file_name, MODE_PRIVATE);
            fos.write((classTeacher.CardID+"").getBytes());
            fos.write("\n".getBytes());
            fos.write(className.getBytes());
            fos.write("\n".getBytes());
            Toast.makeText(this, "Class added", Toast.LENGTH_SHORT).show();
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
        for(int i=0;i<school.classes.length;i++){
            if(school.classes[i]==null){
                school.classes[i]=new Class(className,classTeacher,null);
                break;
            }
        }
        Intent i=new Intent(ClassesActivity.this,MainActivity.class);
        i.putExtra("school",school);
        setResult(RESULT_CANCELED,i);
        startActivity(i);
    }
}
