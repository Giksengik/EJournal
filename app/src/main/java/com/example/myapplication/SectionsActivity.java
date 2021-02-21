package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SectionsActivity extends AppCompatActivity {
    private PeopleDAO peopleDAO;
    private final String informationDisciplineNotPicked = "Discipline not picked";
    private SectionsDAO sectionsDAO;
    private DisciplinesAdapter disciplineAdapter;
    private Button buttonAddDiscipline;
    private Button buttonAddSection;
    private Button buttonMainMenu;
    private EditText searchBoard;
    private ImageButton buttonCloseSearch;
    private ImageButton buttonSearch;
    private RecyclerView recyclerView;

    private Dialog dialogNewDiscipline;
    private EditText newDisciplineName;
    private Button addNewDisciplineButton;
    private Button newDisciplineCloseButton;

    private Dialog dialogNewSection;
    private EditText newSectionTeacherID;
    private RecyclerView disciplinesRecyclerView;
    private Button addNewSectionButton;
    private Button closeNewSectionButton;
    private TextView pickedDiscipline;
    private DisciplinesAdapter.OnDisciplineClickListener disciplinesClickListener;
    private void defineDialogs() {
        dialogNewDiscipline = new Dialog(this);
        dialogNewDiscipline.setContentView(R.layout.new_discipline);
        dialogNewSection = new Dialog(this);
        dialogNewSection.setContentView(R.layout.new_section);

    }
    private void defineElements () {
        buttonAddDiscipline = findViewById(R.id.buttonAddDiscipline);
       buttonAddSection = findViewById(R.id.buttonAddSection);
       buttonMainMenu = findViewById(R.id.buttonMainMenuSections);
       searchBoard = findViewById(R.id.searchBoardSections);
       buttonCloseSearch = findViewById(R.id.closeSearchButtonSections);
       buttonSearch = findViewById(R.id.searchButtonSections);

       newDisciplineName = dialogNewDiscipline.findViewById(R.id.newDisciplineName);
       addNewDisciplineButton = dialogNewDiscipline.findViewById(R.id.newDisciplineAddButton);
       newDisciplineCloseButton =  dialogNewDiscipline.findViewById(R.id.newDisciplineCloseButton);

        newSectionTeacherID = dialogNewSection.findViewById(R.id.newSectionTeacherID);
        addNewSectionButton = dialogNewSection.findViewById(R.id.buttonAddNewSection);
        closeNewSectionButton = dialogNewSection.findViewById(R.id.buttonCloseNewSection);
        disciplinesRecyclerView = dialogNewSection.findViewById(R.id.recyclerViewDisciplines);
        pickedDiscipline = dialogNewSection.findViewById(R.id.pickedDiscipline);
    }
    private void informWrongDisciplineInput() {
        newDisciplineName.setText("");
        newDisciplineName.setHint("Wrong inout");
        newDisciplineName.setHintTextColor(Color.RED);
    }
    private void informDisciplineIsAlreadyExist () {
        newDisciplineName.setText("");
        newDisciplineName.setHint("Discipline is already exists");
        newDisciplineName.setHintTextColor(Color.RED);
    }
    private void defineButtonListeners () {
        buttonAddDiscipline.setOnClickListener(v -> {dialogNewDiscipline.show();});
        newDisciplineCloseButton.setOnClickListener(v -> { dialogNewDiscipline.dismiss();});
        addNewDisciplineButton.setOnClickListener(v -> {
            if(StringValidation.isCorrectString(newDisciplineName.getText().toString())){
                if( !sectionsDAO.checkIsDisciplineExist(newDisciplineName.getText().toString())) {
                sectionsDAO.addNewDiscipline(newDisciplineName.getText().toString());
                dialogNewDiscipline.dismiss();
            }else informDisciplineIsAlreadyExist();
            }else informWrongDisciplineInput();
        });
        buttonAddSection.setOnClickListener(v -> {
            ArrayList<String> disciplinesInList = sectionsDAO.getDiscipline();
            dialogNewSection.show();
            disciplineAdapter = new DisciplinesAdapter(this, disciplinesInList, disciplinesClickListener);
            disciplinesRecyclerView.setAdapter(disciplineAdapter);
        });
        addNewSectionButton.setOnClickListener(v -> {
            if(!pickedDiscipline.getText().toString().equals("") ||
                    !pickedDiscipline.getText().toString().equals(informationDisciplineNotPicked)){
                if(StringValidation.isCorrectID(newSectionTeacherID.getText().toString(),peopleDAO.PEOPLE_COUNT)){
                    if(peopleDAO.findParticipantsStatusByID(Integer.parseInt(newSectionTeacherID.getText().toString()))
                            .equals("TEACHER")){
                        Teacher currentTeacher = peopleDAO.findTeacherByID(Integer.parseInt(newSectionTeacherID.getText().toString()));
                        if(isTeacherFree(currentTeacher.getCardID())) {
                            addNewSection(currentTeacher,pickedDiscipline.getText().toString());
                        }else informTeacherNotFree();
                    }
                    else informWrongInputTeacher();
                }else informWrongInputTeacher();
            }else informDisciplineNotSelected();
        });
        closeNewSectionButton.setOnClickListener(v -> {
            dialogNewSection.dismiss();
        });
    }
    private void informTeacherNotFree(){
        newSectionTeacherID.setHintTextColor(Color.RED);
        newSectionTeacherID.setText("");
        newSectionTeacherID.setHint("Teacher is not free");
    }
    private void addNewSection(Teacher teacher, String discipline) {
        sectionsDAO.addNewSection(teacher, discipline);
        putNewSectionToSchool(teacher, discipline);
    }
    private void putNewSectionToSchool(Teacher teacher, String discipline) {
        peopleDAO.school.listSections.add(new Section(discipline,teacher,new ArrayList<Learner>()));
    }
    private boolean isTeacherFree(int id) {
        for(Section section : peopleDAO.school.listSections) {
            if(section.classTeacher.getCardID() == id) return false;
        }
        return true;
    }
    private void informWrongInputTeacher(){
        newSectionTeacherID.setHintTextColor(Color.RED);
        newSectionTeacherID.setText("");
        newSectionTeacherID.setHint("Wrong input");
    }
    @SuppressLint("SetTextI18n")
    private void informDisciplineNotSelected(){
        pickedDiscipline.setText(informationDisciplineNotPicked);
        pickedDiscipline.setTextColor(Color.RED);
    }
    private void defineRecyclerViews() {
        disciplinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        disciplinesClickListener = (discipline, position) -> {
                pickedDiscipline.setText(discipline);
            };
        }

    private void getSectionsDAO(){
        sectionsDAO = new SectionsDAO();
        sectionsDAO.setDbHelpers(new DBHelperDisciplines(this),
                new DBHelperSections(this));
        sectionsDAO.createDatabases();
//        sectionsDAO.databaseSections.execSQL("create table " + DBHelperSections.TABLE_SECTIONS + "(" + DBHelperSections.KEY_TEACHER_ID
//                + " integer primary key," + DBHelperSections.KEY_DISCIPLINE + " text," + DBHelperSections.KEY_LEARNERS + " text" + ")");
        peopleDAO.school.listSections = sectionsDAO.getSections(peopleDAO);
    }
    private void getPeopleDao(){
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        peopleDAO.setDbHelper(new DBHelper(this));
        peopleDAO.createDatabase();
    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sections);
            getPeopleDao();
            getSectionsDAO();
            defineDialogs();
            defineElements();
            defineRecyclerViews();
            defineButtonListeners();
        }
    }

