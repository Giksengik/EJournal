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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PersonsActivity extends AppCompatActivity {
    School school;
        @SuppressLint("SetTextI18n")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_persons);
            Button buttonAddPerson= (Button)findViewById(R.id.buttonAddPerson);
            Intent mIntent = getIntent();
            school=(School)mIntent.getSerializableExtra("school");
            ImageButton searchButton =(ImageButton)findViewById(R.id.searchButton);
            EditText searchBoard=(EditText)findViewById(R.id.searchBoardPersons);
            //Initialize dialogs
            Dialog dialogEmployee = new Dialog(PersonsActivity.this);
            dialogEmployee.setContentView(R.layout.info_employee);

            Dialog dialogLearner = new Dialog(PersonsActivity.this);
            dialogLearner.setContentView(R.layout.info_learner);

            Dialog dialogTeacher = new Dialog(PersonsActivity.this);
            dialogTeacher.setContentView(R.layout.info_teacher);

            TextView cardIdEmployee=(TextView)dialogEmployee.findViewById(R.id.employeeId);
            TextView nameEmployee = (TextView)dialogEmployee.findViewById(R.id.employeeName);
            TextView phoneEmployee = (TextView)dialogEmployee.findViewById(R.id.employeePhone);
            TextView positionEmployee = (TextView)dialogEmployee.findViewById(R.id.employeePosition);
            Button cancelInfoEmployee = (Button) dialogEmployee.findViewById(R.id.cancelInfoEmployee);

            TextView cardIdLearner =(TextView)dialogLearner.findViewById(R.id.learnerId);
            TextView nameLearner =(TextView)dialogLearner.findViewById(R.id.learnerName);
            TextView phoneLearner =(TextView)dialogLearner.findViewById(R.id.learnerPhone);
            TextView firstParentName =(TextView)dialogLearner.findViewById(R.id.firstParentName);
            TextView firstParentPhone =(TextView)dialogLearner.findViewById(R.id.firstParentPhone);
            TextView secondParentName = (TextView)dialogLearner.findViewById(R.id.secondParentName);
            TextView secondParentPhone = (TextView)dialogLearner.findViewById(R.id.secondParentPhone);
            Button cancelInfoLearner = (Button)dialogLearner.findViewById(R.id.cancelInfoLearner);

            TextView teacherCardId=(TextView)dialogTeacher.findViewById(R.id.teacherCardId);
            TextView teacherName =(TextView)dialogTeacher.findViewById(R.id.teacherName);
            TextView teacherPhone =(TextView)dialogTeacher.findViewById(R.id.teacherPhone);
            TextView teacherQualifications = (TextView)dialogTeacher.findViewById(R.id.teacherQualifications);
            TextView teacherPosition = (TextView)dialogTeacher.findViewById(R.id.teacherPosition);
            Button cancelInfoTeacher = (Button)dialogTeacher.findViewById(R.id.cancelInfoTeacher);


            //Making list
            String [] persons= new String[School.num_of_cards-1];
            int numEmployees=0;
            int numTeachers=0;
            int numLearners=0;
            for(int i=0;i<school.employees.length;i++){
                if(school.employees[i]==null){
                    break;
                }else{
                    numEmployees++;
                }
            }
            for(int i=0;i<school.teachers.length;i++){
                if(school.teachers[i]==null){
                    break;
                }else{
                    numTeachers++;
                }
            }
            for(int i=0;i<school.learners.length;i++){
                if(school.learners[i]==null){
                    break;
                }else{
                    numLearners++;
                }
            }
            for(int i=0;i<School.num_of_cards-1;i++){
                for(int j=0;j<numEmployees;j++){
                    if(school.employees[j].CardID==i+1){
                        persons[i]="Employee  | Card ID:"+(i+1)+" | Full Name:"+school.employees[j].fullName;
                    }
                }
                for(int j=0;j<numLearners;j++){
                    if(school.learners[j].CardID==i+1){
                        persons[i]="Learner      | Card ID:"+(i+1)+" | Full Name:"+school.learners[j].fullName;
                    }
                }
                for(int j=0;j<numTeachers;j++){
                    if(school.teachers[j].CardID==i+1){
                        persons[i]="Teacher     | Card ID:"+(i+1)+" | Full Name:"+school.teachers[j].fullName;
                    }
                }
            }
            ListView lvMain = (ListView)findViewById(R.id.listViewPersons);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    persons);
            lvMain.setAdapter(adapter);
            lvMain.setOnItemClickListener((parent, view, position, id) -> {
                if(persons[position].charAt(0)=='E'){
                    Employee employeeToSee = null;
                    for(int i=0;i<school.employees.length;i++){
                        if(school.employees[i].CardID==position+1){
                            employeeToSee=school.employees[i];
                            break;
                        }
                    }
                    nameEmployee.setText("Full name: "+employeeToSee.fullName);
                    cardIdEmployee.setText("Card ID: "+employeeToSee.CardID);
                    phoneEmployee.setText("Phone: "+employeeToSee.phone);
                    positionEmployee.setText("Position: "+employeeToSee.position);
                    dialogEmployee.show();

                }else if(persons[position].charAt(0)=='L'){
                    Learner learnerToSee=null;
                    for(int i=0;i<school.learners.length;i++) {
                        if (school.learners[i].CardID == position + 1) {
                            learnerToSee = school.learners[i];
                            break;
                        }
                    }
                        nameLearner.setText("Learner's full name: "+learnerToSee.CardID);
                        cardIdLearner.setText("Learner's Card ID: "+learnerToSee.CardID);
                        phoneLearner.setText("Learner's phone: "+learnerToSee.phone);
                        firstParentName.setText("First parent's name: "+learnerToSee.parents[0].fullName);
                        firstParentPhone.setText("First parent's phone: "+learnerToSee.parents[0].phone);
                        secondParentName.setText("Second parent's name: "+learnerToSee.parents[1].fullName);
                        secondParentPhone.setText("Second parent's phone: " +learnerToSee.parents[1].phone);
                        dialogLearner.show();
                }
                else if(persons[position].charAt(0)=='T'){
                    Teacher teacherToSee=null;
                    for(int i=0;i<school.teachers.length;i++){
                        if(school.teachers[i].CardID == position +1){
                            teacherToSee= school.teachers[i];
                            break;
                        }
                    }
                    teacherCardId.setText("Card ID: "+teacherToSee.CardID);
                    teacherName.setText("Full name: "+teacherToSee.fullName);
                    teacherPhone.setText("Phone: "+teacherToSee.phone);
                    teacherPosition.setText("Position: "+teacherToSee.position);
                    String qualifications= "";
                    String s=null;
                    String b=null;
                    for(int i=0;i<teacherToSee.qualifications.length;i++){
                        s=teacherToSee.qualifications[i];
                        if(i!=teacherToSee.qualifications.length-1) {
                            b=qualifications+s+", ";
                        }else{
                            b=qualifications+s;
                        }
                        qualifications=b;
                    }
                    teacherQualifications.setText("Qualifications: "+qualifications);
                    dialogTeacher.show();
                }
            });
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(searchBoard.getText().toString().matches("[0-9]+")){
                        if(Integer.parseInt(searchBoard.getText().toString())>=School.num_of_cards){
                            searchBoard.setText("");
                            searchBoard.setHint(
                                    "no person with this id");
                            searchBoard.setHintTextColor(Color.RED);
                        }else {
                            boolean flag = false;
                            for (int i = 0; i < school.employees.length; i++) {
                                if (school.employees[i] == null) {
                                    break;
                                } else if (school.employees[i].CardID == Integer.parseInt(searchBoard.getText().toString())) {
                                    flag = true;
                                    nameEmployee.setText("Full name: " + school.employees[i].fullName);
                                    cardIdEmployee.setText("Card ID: " + school.employees[i].CardID);
                                    phoneEmployee.setText("Phone: " + school.employees[i].phone);
                                    positionEmployee.setText("Position: " + school.employees[i].position);
                                    dialogEmployee.show();
                                    break;
                                }
                            }
                            for (int i = 0; i < school.teachers.length; i++) {
                                if (!flag) {
                                    if (school.teachers[i] == null) {
                                        break;
                                    } else if (school.teachers[i].CardID == Integer.parseInt(searchBoard.getText().toString())) {
                                        flag = true;
                                        teacherCardId.setText("Card ID: " + school.teachers[i].CardID);
                                        teacherName.setText("Full name: " + school.teachers[i].fullName);
                                        teacherPhone.setText("Phone: " + school.teachers[i].phone);
                                        teacherPosition.setText("Position: " + school.teachers[i].position);
                                        String qualifications = "";
                                        String s = null;
                                        String b = null;
                                        for (int j = 0; j < school.teachers[i].qualifications.length; j++) {
                                            s = school.teachers[i].qualifications[j];
                                            if (j != school.teachers[i].qualifications.length - 1) {
                                                b = qualifications + s + ", ";
                                            } else {
                                                b = qualifications + s;
                                            }
                                            qualifications = b;
                                        }
                                        teacherQualifications.setText("Qualifications: " + qualifications);
                                        dialogTeacher.show();
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                            for (int i = 0; i < school.learners.length; i++) {
                                if (!flag) {
                                    if (school.learners[i] == null) {
                                        break;
                                    }else if (school.learners[i].CardID == Integer.parseInt(searchBoard.getText().toString())){
                                        nameLearner.setText("Learner's full name: "+school.learners[i].fullName);
                                        cardIdLearner.setText("Learner's Card ID: "+school.learners[i].CardID);
                                        phoneLearner.setText("Learner's phone: "+school.learners[i].phone);
                                        firstParentName.setText("First parent's name: "+school.learners[i].parents[0].fullName);
                                        firstParentPhone.setText("First parent's phone: "+school.learners[i].parents[0].phone);
                                        secondParentName.setText("Second parent's name: "+school.learners[i].parents[1].fullName);
                                        secondParentPhone.setText("Second parent's phone: " +school.learners[i].parents[1].phone);
                                        dialogLearner.show();
                                        break;
                                    }
                                }else{
                                    break;
                                }
                            }
                        }
                    }else{
                        searchBoard.setText("");
                        searchBoard.setHint("wrong input");
                        searchBoard.setHintTextColor(Color.RED);
                    }
                }
            });

            buttonAddPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i;
                    i = new Intent(PersonsActivity.this, AddPersonActivity.class);
                    i.putExtra("school",school);
                    startActivity(i);
                }
            });
            cancelInfoEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogEmployee.dismiss();
                }
            });
            cancelInfoLearner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLearner.dismiss();
                }
            });
            cancelInfoTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogTeacher.dismiss();
                }
            });

        }
    }


