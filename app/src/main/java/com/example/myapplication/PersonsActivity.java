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
    private School school;
    private Dialog dialogEmployee;
    private Dialog dialogLearner;
    private Dialog dialogTeacher;
    private Button buttonAddPerson;
    private ImageButton searchButton;
    private EditText searchBoard;
    private TextView cardIdEmployee;
    private TextView nameEmployee;
    private TextView phoneEmployee;
    private TextView positionEmployee;
    private Button cancelInfoEmployee;

    private TextView cardIdLearner;
    private TextView nameLearner;
    private TextView phoneLearner;
    private TextView firstParentName;
    private TextView firstParentPhone;
    private TextView secondParentName;
    private TextView secondParentPhone;
    private Button cancelInfoLearner;

    private TextView teacherCardId;
    private TextView teacherName;
    private TextView teacherPhone;
    private TextView teacherQualifications ;
    private TextView teacherPosition;
    private Button cancelInfoTeacher;
    private int numEmployees=0;
    private int numTeachers=0;
    private int numLearners=0;
    private ListView lvMain;
    String [] persons;
    private void defineEmployeeDialog(){
        dialogEmployee = new Dialog(PersonsActivity.this);
        dialogEmployee.setContentView(R.layout.info_employee);
        cardIdEmployee = (TextView) dialogEmployee.findViewById(R.id.employeeId);
        nameEmployee = (TextView) dialogEmployee.findViewById(R.id.employeeName);
        phoneEmployee = (TextView) dialogEmployee.findViewById(R.id.employeePhone);
        positionEmployee = (TextView) dialogEmployee.findViewById(R.id.employeePosition);
        cancelInfoEmployee = (Button) dialogEmployee.findViewById(R.id.cancelInfoEmployee);
    }
    private void defineLearnerDialog(){
        dialogLearner = new Dialog(PersonsActivity.this);
        dialogLearner.setContentView(R.layout.info_learner);
        cardIdLearner =(TextView)dialogLearner.findViewById(R.id.learnerId);
        nameLearner =(TextView)dialogLearner.findViewById(R.id.learnerName);
        phoneLearner =(TextView)dialogLearner.findViewById(R.id.learnerPhone);
        firstParentName =(TextView)dialogLearner.findViewById(R.id.firstParentName);
        firstParentPhone =(TextView)dialogLearner.findViewById(R.id.firstParentPhone);
        secondParentName = (TextView)dialogLearner.findViewById(R.id.secondParentName);
        secondParentPhone = (TextView)dialogLearner.findViewById(R.id.secondParentPhone);
        cancelInfoLearner = (Button)dialogLearner.findViewById(R.id.cancelInfoLearner);
    }
    private void defineTeacherDialog(){
        dialogTeacher = new Dialog(PersonsActivity.this);
        dialogTeacher.setContentView(R.layout.info_teacher);
        teacherCardId=(TextView)dialogTeacher.findViewById(R.id.teacherCardId);
        teacherName =(TextView)dialogTeacher.findViewById(R.id.teacherName);
        teacherPhone =(TextView)dialogTeacher.findViewById(R.id.teacherPhone);
        teacherQualifications = (TextView)dialogTeacher.findViewById(R.id.teacherQualifications);
        teacherPosition = (TextView)dialogTeacher.findViewById(R.id.teacherPosition);
        cancelInfoTeacher = (Button)dialogTeacher.findViewById(R.id.cancelInfoTeacher);

    }
    private void defineDialogs(){
        defineEmployeeDialog();
        defineLearnerDialog();
        defineTeacherDialog();
        cancelInfoEmployee.setOnClickListener(v -> dialogEmployee.dismiss());
        cancelInfoLearner.setOnClickListener(v -> dialogLearner.dismiss());
        cancelInfoTeacher.setOnClickListener(v -> dialogTeacher.dismiss());
    }
    private boolean isCorrectInput(String id){
        if(id.matches("[0-9]+")) {
                return Integer.parseInt(id) >= School.num_of_cards ||
                    Integer.parseInt(id) > 0;
        }
        return false;
    }
    private void informWrongInput () {
        searchBoard.setText("");
        searchBoard.setHint("wrong input");
        searchBoard.setHintTextColor(Color.RED);
    }
    private void informThereIsNoPerson(){
        searchBoard.setText("");
        searchBoard.setHint("no person with this id");
        searchBoard.setHintTextColor(Color.RED);
    }
    private void checkTeacherWithID(int id) {
        for (int i = 0; i < school.teachers.length; i++) {
            if (school.teachers[i] == null) informThereIsNoPerson();
            else if (school.teachers[i].CardID == id){
                setDialogTeacherAttributes(school.teachers[i]);
                dialogTeacher.show();
                break;
            }

        }
    }
    private void checkLearnerWithID(int id) {
        for (int i = 0; i < school.learners.length; i++) {
                if (school.learners[i] == null) {
                    checkTeacherWithID(id);
                }else if (school.learners[i].CardID == id){
                    setDialogLearnerAttributes(school.learners[i]);
                    dialogLearner.show();
                    break;
                }

        }
    }
    private void checkEmployeeWithID(int id){
        for (int i = 0; i < school.employees.length; i++) {
            if (school.employees[i] == null) {
                checkLearnerWithID(id);
            } else if (school.employees[i].CardID == id) {
                setDialogEmployeeAttributes(school.employees[i]);
                dialogEmployee.show();
                break;
            }
        }
    }
    private void defineSearchSystemButtonListener(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrectInput(searchBoard.getText().toString())){
                    checkEmployeeWithID(Integer.parseInt(searchBoard.getText().toString()));
                } else informWrongInput();
            }
        });
    }
    private void defineSearchSystem(){
        searchButton =(ImageButton)findViewById(R.id.searchButton);
        searchBoard=(EditText)findViewById(R.id.searchBoardPersons);
        defineSearchSystemButtonListener();
    }
    private void defineSchool(){
        Intent mIntent = getIntent();
        school=(School)mIntent.getSerializableExtra("school");
    }
    private String [] getAllPeople(){
        persons = new String[School.num_of_cards-1];
        for(int i=0;i<School.num_of_cards-1;i++){
            for(int j=0;j<numEmployees;j++){
                if(school.employees[j].CardID == i+1){
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
        return persons;
    }
    private Employee findEmployeeByPosition(int position) {
        for(int i=0;i<school.employees.length;i++){
            if(school.employees[i].CardID==position+1){
                return school.employees[i];
            }
        }
        return null;
    }
    @SuppressLint("SetTextI18n")
    private void setDialogEmployeeAttributes(Employee employeeToSee) {
        nameEmployee.setText("Full name: "+employeeToSee.fullName);
        cardIdEmployee.setText("Card ID: "+employeeToSee.CardID);
        phoneEmployee.setText("Phone: "+employeeToSee.phone);
        positionEmployee.setText("Position: "+employeeToSee.position);
    }

    private void showEmployeeDialog(int position){
        Employee employeeToSee = findEmployeeByPosition(position);
        if(employeeToSee !=  null) setDialogEmployeeAttributes(employeeToSee);
        dialogEmployee.show();
    }
    private Learner findLearnerByPosition(int position){
        for(int i=0;i<school.learners.length;i++) {
            if (school.learners[i].CardID == position + 1) {
                return school.learners[i];
            }
        }
        return null;
    }
    @SuppressLint("SetTextI18n")
    private void setDialogLearnerAttributes(Learner learnerToSee) {
        nameLearner.setText("Learner's full name: "+learnerToSee.CardID);
        cardIdLearner.setText("Learner's Card ID: "+learnerToSee.CardID);
        phoneLearner.setText("Learner's phone: "+learnerToSee.phone);
        firstParentName.setText("First parent's name: "+learnerToSee.parents[0].fullName);
        firstParentPhone.setText("First parent's phone: "+learnerToSee.parents[0].phone);
        secondParentName.setText("Second parent's name: "+learnerToSee.parents[1].fullName);
        secondParentPhone.setText("Second parent's phone: " +learnerToSee.parents[1].phone);
    }
    private void showLearnerDialog(int position){
        Learner learnerToSee = findLearnerByPosition(position);
        if (learnerToSee != null) setDialogLearnerAttributes(learnerToSee);
        dialogLearner.show();
    }
    private Teacher findTeacherByPosition(int position){
        for(int i=0;i<school.teachers.length;i++){
            if(school.teachers[i].CardID == position + 1){
                return school.teachers[i];
            }
        }
        return null;
    }
    private String getQualifications(Teacher teacherToSee){
        StringBuilder qualifications = new StringBuilder();
        for(int i=0;i<teacherToSee.qualifications.length;i++){
            qualifications.append(teacherToSee.qualifications[i]);
            if(i!=teacherToSee.qualifications.length-1) {
                qualifications.append(", ");
            }else{
                qualifications.append(".");
            }
        }
        return qualifications.toString();
    }
    @SuppressLint("SetTextI18n")
    private void setDialogTeacherAttributes(Teacher teacherToSee){
        teacherCardId.setText("Card ID: "+teacherToSee.CardID);
        teacherName.setText("Full name: "+teacherToSee.fullName);
        teacherPhone.setText("Phone: "+teacherToSee.phone);
        teacherPosition.setText("Position: "+teacherToSee.position);
        teacherQualifications.setText("Qualifications: "+getQualifications(teacherToSee));
    }
    private void showTeacherDialog(int position){
        Teacher teacherToSee = findTeacherByPosition(position);
        if(teacherToSee != null) setDialogTeacherAttributes(teacherToSee);
        dialogTeacher.show();
    }
    private void setListViewListener(){
        lvMain.setOnItemClickListener((parent, view, position, id) -> {
            switch(persons[position].charAt(0)){
                case 'E':
                    showEmployeeDialog(position);
                    break;
                case 'L':
                    showLearnerDialog(position);
                    break;
                case 'T':
                    showTeacherDialog(position);
                    break;
            }
        });
    }
    private void defineListView(){
        persons = getAllPeople();
        lvMain = (ListView)findViewById(R.id.listViewPersons);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                persons);
        lvMain.setAdapter(adapter);
        setListViewListener();
    }
    private void defineAmountOfEmployees(){
        for(int i=0;i<school.employees.length;i++){
            if(school.employees[i]==null){
                break;
            }else{
                numEmployees++;
            }
        }
    }
    private void defineAmountOfTeachers(){
        for(int i=0;i<school.teachers.length;i++){
            if(school.teachers[i]==null){
                break;
            }else{
                numTeachers++;
            }
        }
    }
    private void defineAmountOfLearners(){
        for(int i=0;i<school.learners.length;i++){
            if(school.learners[i]==null){
                break;
            }else{
                numLearners++;
            }
        }
    }
    private void defineAmountOfPeople(){
        defineAmountOfEmployees();
        defineAmountOfTeachers();
        defineAmountOfLearners();
    }
    private void startAddPersonActivity(){
        Intent i;
        i = new Intent(PersonsActivity.this, AddPersonActivity.class);
        i.putExtra("school",school);
        startActivity(i);
    }
    private void  defineButtonAddPersonListener () {
        buttonAddPerson.setOnClickListener(v -> startAddPersonActivity());
    }
    private void defineButtonNewPerson () {
        buttonAddPerson= (Button)findViewById(R.id.buttonAddPerson);
        defineButtonAddPersonListener();
    }
    private void defineButtons(){
        defineButtonNewPerson();
    }
    private void defineElementsAndSetContentView(){
        setContentView(R.layout.activity_persons);
        defineSchool();
        defineAmountOfPeople();
        defineSearchSystem();
        defineDialogs();
        defineListView();
        defineButtons();
    }
    @SuppressLint("SetTextI18n")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            defineElementsAndSetContentView();
        }
    }


