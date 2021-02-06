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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PersonsActivity extends AppCompatActivity {
    private transient PeopleDAO peopleDAO;
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
    private RecyclerView recyclerView;
    private ParticipantAdapter.OnParticipantClickListener participantClickListener;
    ArrayList<Participant> persons;
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
                return Integer.parseInt(id) <= peopleDAO.PEOPLE_COUNT && Integer.parseInt(id) > 0;
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
    private void findParticipantByID(int id){
        switch(peopleDAO.findParticipantsStatusByID(id)){
            case "EMPLOYEE":
                setDialogEmployeeAttributes(peopleDAO.findEmployeeByID(id));
                dialogEmployee.show();
                break;
            case "LEARNER":
                setDialogLearnerAttributes(peopleDAO.findLearnerByID(id));
                dialogLearner.show();
                break;
            case "TEACHER":
                setDialogTeacherAttributes(peopleDAO.findTeacherByID(id));
                dialogTeacher.show();
                break;
        }
    }
    private void defineSearchSystemButtonListener(){
        searchButton.setOnClickListener(v -> {
            if(isCorrectInput(searchBoard.getText().toString())){
               findParticipantByID(Integer.parseInt(searchBoard.getText().toString()));
            } else informWrongInput();
        });
    }
    private void defineSearchSystem(){
        searchButton =(ImageButton)findViewById(R.id.searchButton);
        searchBoard=(EditText)findViewById(R.id.searchBoardPersons);
        defineSearchSystemButtonListener();
    }



    @SuppressLint("SetTextI18n")
    private void setDialogEmployeeAttributes(Employee employeeToSee) {
        nameEmployee.setText("Full name: "+employeeToSee.getFullName());
        cardIdEmployee.setText("Card ID: "+employeeToSee.getCardID());
        phoneEmployee.setText("Phone: "+employeeToSee.getPhone());
        positionEmployee.setText("Position: "+employeeToSee.getPosition());
    }



    @SuppressLint("SetTextI18n")
    private void setDialogLearnerAttributes(Learner learnerToSee) {
        nameLearner.setText("Learner's full name: "+learnerToSee.getFullName());
        cardIdLearner.setText("Learner's Card ID: "+learnerToSee.getCardID());
        phoneLearner.setText("Learner's phone: "+learnerToSee.getPhone());
        firstParentName.setText("First parent's name: "+learnerToSee.getParents()[0].getFullName());
        firstParentPhone.setText("First parent's phone: "+learnerToSee.getParents()[0].getPhone());
        secondParentName.setText("Second parent's name: "+learnerToSee.getParents()[1].getFullName());
        secondParentPhone.setText("Second parent's phone: " +learnerToSee.getParents()[0].getPhone());
    }

    @SuppressLint("SetTextI18n")
    private void setDialogTeacherAttributes(Teacher teacherToSee){
        teacherCardId.setText("Card ID: "+teacherToSee.getCardID());
        teacherName.setText("Full name: "+teacherToSee.getFullName());
        teacherPhone.setText("Phone: "+teacherToSee.getPhone());
        teacherPosition.setText("Position: "+teacherToSee.getPosition());
        teacherQualifications.setText("Qualifications: "+teacherToSee.getQualifications());
    }

    private void startAddPersonActivity(){
        Intent i;
        i = new Intent(PersonsActivity.this, AddPersonActivity.class);
        i.putExtra("peopleDAO", peopleDAO);
        startActivity(i);
    }
    private void  defineButtonAddPersonListener () {
        buttonAddPerson.setOnClickListener(v -> startAddPersonActivity());
    }
    private void defineButtonNewPerson () {
        buttonAddPerson = (Button) findViewById(R.id.buttonAddPerson);
        defineButtonAddPersonListener();
    }
    private void defineButtons(){
        defineButtonNewPerson();
        defineSearchSystem();
        defineDialogs();
        }

    private void defineListView(){
        persons = peopleDAO.getAllPeople();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPeople);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setRecyclerViewListener();
        ParticipantAdapter adapter =  new ParticipantAdapter(this, persons, participantClickListener);
        recyclerView.setAdapter(adapter);

    }
        private void setRecyclerViewListener(){
            participantClickListener = (participant, position) -> {
                switch (participant.status){
                    case "LEARNER":
                        showLearnerDialog(position);
                        break;
                    case "TEACHER":
                        showTeacherDialog(position);
                        break;
                    case "EMPLOYEE":
                        showEmployeeDialog(position);
                        break;
                }
            };
    }
        private void showEmployeeDialog(int position){
        Employee employeeToSee = findEmployeeByPosition(position);
        if(employeeToSee !=  null) setDialogEmployeeAttributes(employeeToSee);
        dialogEmployee.show();
    }
        private void showLearnerDialog(int position){
            Learner learnerToSee = findLearnerByPosition(position);
        if (learnerToSee != null) setDialogLearnerAttributes(learnerToSee);
        dialogLearner.show();
    }
        private void showTeacherDialog(int position){
        Teacher teacherToSee = findTeacherByPosition(position);
        if(teacherToSee != null) setDialogTeacherAttributes(teacherToSee);
        dialogTeacher.show();
    }
        private Employee findEmployeeByPosition(int position) {
        for(int i = 0; i < peopleDAO.getEmployeeListLength(); i++){
            if(peopleDAO.getSchool().listEmployees.get(i).getCardID() == position + 1){
                return peopleDAO.getSchool().listEmployees.get(i);
            }
        }
        return null;
    }
        private Learner findLearnerByPosition(int position){
        for(int i = 0; i < peopleDAO.getLearnerListLength(); i ++) {
            if (peopleDAO.getSchool().listLearners.get(i).getCardID() == position + 1) {
                return peopleDAO.getSchool().listLearners.get(i);
            }
        }
        return null;
    }
        private Teacher findTeacherByPosition(int position){
        for(int i = 0; i < peopleDAO.getTeacherListLength(); i ++){
            int id = peopleDAO.getSchool().listTeachers.get(i).getCardID();
            if(peopleDAO.getSchool().listTeachers.get(i).getCardID() == position + 1){
                return peopleDAO.getSchool().listTeachers.get(i);
            }
        }
        return null;
    }
    private void getPeopleDao(){
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        peopleDAO.setDbHelper(new DBHelper(this));
        peopleDAO.createDatabase();
    }
    @SuppressLint("SetTextI18n")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_persons);
            getPeopleDao();
            defineListView();
            defineButtons();
        }
    }


