package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private final String informationSectionAlreadyExist = "Section already exist";
    private SectionsDAO sectionsDAO;
    private DisciplinesAdapter disciplineAdapter;
    private Button buttonAddDiscipline;
    private Button buttonAddSection;
    private Button buttonMainMenu;
    private EditText searchBoard;
    private ImageButton buttonSearch;
    private ArrayList<Section> sectionsInList;

    private RecyclerView sectionsRecyclerView;
    private SectionsAdapter.OnSectionClickListener onSectionClickListener;

    private Dialog dialogSection;
    private TextView sectionDiscipline;
    private TextView sectionTeacherID;
    private TextView sectionNumOfLearners;
    private Button sectionCloseButton;
    private Button sectionAddLearnerButton;
    private EditText sectionNewLearnerID;

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
        dialogSection = new Dialog(this);
        dialogSection.setContentView(R.layout.info_section);

    }
    private void defineElements () {
        buttonAddDiscipline = findViewById(R.id.buttonAddDiscipline);
       buttonAddSection = findViewById(R.id.buttonAddSection);
       buttonMainMenu = findViewById(R.id.buttonMainMenuSections);
       searchBoard = findViewById(R.id.searchBoardSections);
       buttonSearch = findViewById(R.id.searchButtonSections);

       newDisciplineName = dialogNewDiscipline.findViewById(R.id.newDisciplineName);
       addNewDisciplineButton = dialogNewDiscipline.findViewById(R.id.newDisciplineAddButton);
       newDisciplineCloseButton =  dialogNewDiscipline.findViewById(R.id.newDisciplineCloseButton);

        newSectionTeacherID = dialogNewSection.findViewById(R.id.newSectionTeacherID);
        addNewSectionButton = dialogNewSection.findViewById(R.id.buttonAddNewSection);
        closeNewSectionButton = dialogNewSection.findViewById(R.id.buttonCloseNewSection);
        disciplinesRecyclerView = dialogNewSection.findViewById(R.id.recyclerViewDisciplines);
        pickedDiscipline = dialogNewSection.findViewById(R.id.pickedDiscipline);

        sectionsRecyclerView = findViewById(R.id.recyclerViewSections);

        sectionDiscipline = dialogSection.findViewById(R.id.sectionDiscipline);
        sectionTeacherID = dialogSection.findViewById(R.id.sectionTeacherID);;
        sectionNumOfLearners = dialogSection.findViewById(R.id.sectionNumOfLearners);
        sectionCloseButton = dialogSection.findViewById(R.id.buttonCloseSection);
        sectionAddLearnerButton = dialogSection.findViewById(R.id.buttonAddLearnerToSection);;
        sectionNewLearnerID = dialogSection.findViewById(R.id.sectionNewLearnerID);
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
            if (!pickedDiscipline.getText().toString().equals("") &&
                    !pickedDiscipline.getText().toString().equals(informationDisciplineNotPicked) &&
                    !pickedDiscipline.getText().toString().equals(informationSectionAlreadyExist)
            ) {
                if (!sectionsDAO.isSectionAlreadyExist(pickedDiscipline.getText().toString())) {
                    if (StringValidation.isCorrectID(newSectionTeacherID.getText().toString(), peopleDAO.PEOPLE_COUNT)) {
                        if (peopleDAO.findParticipantsStatusByID(Integer.parseInt(newSectionTeacherID.getText().toString()))
                                .equals("TEACHER")) {
                            Teacher currentTeacher = peopleDAO.findTeacherByID(Integer.parseInt(newSectionTeacherID.getText().toString()));
                            if (isTeacherFree(currentTeacher.getCardID())) {
                                addNewSection(currentTeacher, pickedDiscipline.getText().toString());
                                updateSectionList();
                                dialogNewSection.dismiss();
                            } else informTeacherNotFree();
                        } else informWrongInputTeacher();
                    } else informWrongInputTeacher();
                } else informSectionAlreadyExist();
            } else informDisciplineNotSelected();
        });
        closeNewSectionButton.setOnClickListener(v -> {
            dialogNewSection.dismiss();
        });
        sectionCloseButton.setOnClickListener(v -> { dialogSection.dismiss();});
        sectionAddLearnerButton.setOnClickListener(v -> {
            if(StringValidation.isCorrectID(sectionNewLearnerID.getText().toString(), peopleDAO.PEOPLE_COUNT)
            && peopleDAO.findParticipantsStatusByID(Integer.parseInt(sectionNewLearnerID.getText().toString())).equals("LEARNER")){
                if(isLearnerFree(Integer.parseInt((sectionNewLearnerID.getText().toString())))){
                    addLearnerToSection(Integer.parseInt((sectionNewLearnerID.getText().toString())),
                            sectionsDAO.getSectionByDiscipline(sectionDiscipline.getText().toString(),peopleDAO));
                    updateSectionList();
                }else informLearnerNotFree();
            }else informWrongInputNewLearner();
        });
        buttonSearch.setOnClickListener(v -> {
            if(StringValidation.isCorrectString(searchBoard.getText().toString())){
                if ( sectionsDAO.getSectionByDiscipline(searchBoard.getText().toString(),peopleDAO) != null){
                    showSection(sectionsDAO.getSectionByDiscipline(searchBoard.getText().toString(),peopleDAO));
                }else informNoSuchSection();
            }
        });
        buttonMainMenu.setOnClickListener(v -> {
            Intent i;
                i = new Intent(SectionsActivity.this, MainActivity.class);
            i.putExtra("peopleDAO", peopleDAO);
            startActivityForResult(i,1);
        });
    }
    private void updateSectionList() {
        sectionsInList.clear();
        sectionsInList.addAll(peopleDAO.school.listSections);
        disciplineAdapter.notifyDataSetChanged();
    }
    private void informNoSuchSection(){
        searchBoard.setText("");
        searchBoard.setHintTextColor(Color.RED);
        searchBoard.setHint("No such section");
    }
    private void informLearnerNotFree(){
        sectionNewLearnerID.setText("");
        sectionNewLearnerID.setHintTextColor(Color.RED);
        sectionNewLearnerID.setHint("Learner not free");
    }
    private void informWrongInputNewLearner(){
        sectionNewLearnerID.setText("");
        sectionNewLearnerID.setHintTextColor(Color.RED);
        sectionNewLearnerID.setHint("Wrong input");
    }
    private void informSectionAlreadyExist(){
        pickedDiscipline.setText(informationSectionAlreadyExist);
        pickedDiscipline.setTextColor(Color.RED);
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
        sectionsInList = sectionsDAO.getSections(peopleDAO);
        sectionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        onSectionClickListener = (section, position) -> showSection(section);
        SectionsAdapter adapter =  new SectionsAdapter(this,sectionsInList,onSectionClickListener);
        sectionsRecyclerView.setAdapter(adapter);
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
    @SuppressLint("SetTextI18n")
    private void setSectionDialogFields (Section section) {
        sectionDiscipline.setText(section.name);
        sectionTeacherID.setText(section.classTeacher.getCardID()+"");
        sectionNumOfLearners.setText(section.learners.size()+"");
    }
    private void showSection(Section section){
        setSectionDialogFields(section);
        dialogSection.show();

    }
    private boolean isLearnerFree(int learnerID) {
        for(Section section : peopleDAO.school.listSections) {
            for(Learner learner : section.learners){
                if( learner.getCardID() == learnerID) return false;
            }
        }
        return true;
    }
    private void addLearnerToSection(int learnerID, Section section) {
        if(isLearnerFree(learnerID)) {
            sectionsDAO.addLearnerToSection(learnerID, section.name);
        }
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

