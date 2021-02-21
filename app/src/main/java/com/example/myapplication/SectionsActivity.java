package com.example.myapplication;

import android.app.Dialog;
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
    }
    private void defineRecyclerViews() {
        disciplinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        disciplinesClickListener = (discipline, position) -> {
                pickedDiscipline.setText(discipline);
            };

        }

    private void getSectionsDAO(){
        sectionsDAO = new SectionsDAO();
        sectionsDAO.setDbHelpers(new DBHelperDisciplines(this));
        sectionsDAO.createDatabases();
    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sections);
            getSectionsDAO();
            defineDialogs();
            defineElements();
            defineRecyclerViews();
            defineButtonListeners();
        }
    }

