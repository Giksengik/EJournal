package com.example.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class SectionsActivity extends AppCompatActivity {
    SectionsDAO sectionsDAO;

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

    private void defineDialogs() {
        dialogNewDiscipline = new Dialog(this);
        dialogNewDiscipline.setContentView(R.layout.new_discipline);
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
            defineButtonListeners();
        }
    }

