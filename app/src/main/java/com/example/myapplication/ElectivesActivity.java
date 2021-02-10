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
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ElectivesActivity extends AppCompatActivity {
        private Button addNewElectiveButton;
        private ImageButton searchButton;
        private EditText searchBoard;
        private ElectivesAdapter.OnElectivesClickListener electiveClickListener;

        private TextView dialogElectiveSubject;
        private TextView dialogElectiveTeacherID;
        private TextView dialogElectiveNumOfLearners;
        private EditText dialogElectiveAddNewLearner;
        private Button dialogElectiveCancelButton;
        private Button dialogElectiveAddLearnerButton;
        private Dialog dialogElective;


        private RadioGroup newElectiveDialogRadioGroupRight;
        private RadioGroup newElectiveDialogRadioGroupLeft;
        private RadioButton newElectiveDialogPhysicalTrainingRadioButton;
        private RadioButton newElectiveDialogMathematicsRadioButton;
        private RadioButton newElectiveDialogComputerScienceRadioButton;
        private RadioButton newElectiveDialogBiologyRadioButton;
        private RadioButton newElectiveDialogChemistryRadioButton;
        private RadioButton newElectiveDialogSocialStudiesRadioButton;
        private RadioButton newElectiveDialogHistoryRadioButton;
        private RadioButton newElectiveDialogLiteratureRadioButton;
        private RadioButton newElectiveDialogNativeLanguageRadioButton;
        private RadioButton newElectiveDialogForeignLanguageRadioButton;
        private RadioButton newElectiveDialogGeographyRadioButton;
        private RadioButton newElectiveDialogPhysicsRadioButton;
        private EditText newElectiveDialogTeacherID;
        private Button newElectiveDialogAddButton;
        private Button newElectiveDialogCancelButton;
        private PeopleDAO peopleDAO;
        private RecyclerView electivesList;
        private Dialog newElectiveDialog;
        private TextView newElectiveDialogWrongSubject;
        private boolean notCheckedYet = true;
    private void getPeopleDao(){
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        peopleDAO.setDbHelper(new DBHelper(this));
        peopleDAO.createDatabase();
    }
    private void findViews(){
        dialogElective = new Dialog(this);
        dialogElective.setContentView(R.layout.info_elective);
        newElectiveDialog = new Dialog(this);
        newElectiveDialog.setContentView(R.layout.new_elective);
        addNewElectiveButton = findViewById(R.id.addElectiveButton);
        searchButton = findViewById(R.id.searchButtonElectives);
        searchBoard = findViewById(R.id.searchBoardElectives);;
        dialogElectiveSubject = dialogElective.findViewById(R.id.dialogElectiveSubject);;
        dialogElectiveTeacherID = dialogElective.findViewById(R.id.dialogElectiveTeacherID);
        dialogElectiveNumOfLearners = dialogElective.findViewById(R.id.dialogElectiveNumOfLearners);
        dialogElectiveCancelButton = dialogElective.findViewById(R.id.dialogElectiveCancel);
        dialogElectiveAddLearnerButton =  dialogElective.findViewById(R.id.dialogElectiveAddLearnerButton);
        dialogElectiveAddNewLearner = dialogElective.findViewById(R.id.dialogElectiveNewLearnerID);
        newElectiveDialogRadioGroupRight = newElectiveDialog.findViewById(R.id.newElectiveDialogRadioGroupRight);
        newElectiveDialogRadioGroupLeft = newElectiveDialog.findViewById(R.id.newElectiveDialogRadioGroupLeft);
        newElectiveDialogPhysicalTrainingRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogPhysicalTrainingRadioButton);
        newElectiveDialogMathematicsRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogMathematicsRadioButton);
        newElectiveDialogComputerScienceRadioButton = newElectiveDialog.findViewById(R.id. newElectiveDialogComputerScienceRadioButton);
        newElectiveDialogBiologyRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogBiologyRadioButton);
        newElectiveDialogChemistryRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogChemistryRadioButton);
        newElectiveDialogSocialStudiesRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogSocialStudiesRadioButton);
        newElectiveDialogHistoryRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogHistoryRadioButton);
        newElectiveDialogLiteratureRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogLiteratureRadioButton);
        newElectiveDialogNativeLanguageRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogNativeLanguageRadioButton);
        newElectiveDialogForeignLanguageRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogForeignLanguageRadioButton);
        newElectiveDialogGeographyRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogGeographyRadioButton);
        newElectiveDialogPhysicsRadioButton = newElectiveDialog.findViewById(R.id.newElectiveDialogPhysicsRadioButton);
        newElectiveDialogTeacherID = newElectiveDialog.findViewById(R.id.newElectiveDialogTeacherID);
        newElectiveDialogAddButton = newElectiveDialog.findViewById(R.id.newElectiveDialogAddButton);
        newElectiveDialogCancelButton = newElectiveDialog.findViewById(R.id.newElectiveDialogCancelButton);
        newElectiveDialogWrongSubject = newElectiveDialog.findViewById(R.id.newElectiveDialogWrongSibject);
    }
    public void informWrongNewLearnerInput(){
        dialogElectiveAddNewLearner.setText("");
        dialogElectiveAddNewLearner.setHint("Wrong input" );
        dialogElectiveAddNewLearner.setHintTextColor(Color.RED);
    }
    @SuppressLint("SetTextI18n")
    private void informWrongSubjectInput(){
           newElectiveDialogWrongSubject.setText("Wrong input!");
    }
    private void checkAndAddNewElective(){
        String subject = null;
        if (newElectiveDialogBiologyRadioButton.isChecked()) {
            subject = "Biology";
        } else if (newElectiveDialogChemistryRadioButton.isChecked()) {
            subject = "Chemistry";
        } else if (newElectiveDialogComputerScienceRadioButton.isChecked()) {
            subject = "Computer science";
        } else if (newElectiveDialogNativeLanguageRadioButton.isChecked()) {
            subject = "Native language";
        } else if (newElectiveDialogForeignLanguageRadioButton.isChecked()) {
            subject = "Foreign Language";
        } else if (newElectiveDialogGeographyRadioButton.isChecked()) {
            subject = "Geography";
        } else if (newElectiveDialogHistoryRadioButton.isChecked()) {
            subject = "History";
        } else if (newElectiveDialogLiteratureRadioButton.isChecked()) {
            subject = "Literature";
        } else if (newElectiveDialogPhysicalTrainingRadioButton.isChecked()) {
            subject = "Physical Training";
        } else if (newElectiveDialogMathematicsRadioButton.isChecked()) {
            subject = "Mathematics";
        } else if (newElectiveDialogSocialStudiesRadioButton.isChecked()) {
            subject = "Social studies";
        } else if (newElectiveDialogPhysicsRadioButton.isChecked()) {
            subject = "Physics";
        }else informWrongSubjectInput();
    }
    public void defineNewElectiveDialogButtons(){
        newElectiveDialogRadioGroupRight.setOnCheckedChangeListener((group, checkedId) -> {
            if(newElectiveDialogRadioGroupLeft.isEnabled() && notCheckedYet){
                notCheckedYet = false;
                newElectiveDialogRadioGroupLeft.clearCheck();
                newElectiveDialogRadioGroupRight.check(checkedId);
                notCheckedYet = true;
            }
            else if(newElectiveDialogRadioGroupRight.isEnabled() && notCheckedYet){
                notCheckedYet = false;
                newElectiveDialogRadioGroupRight.clearCheck();
                newElectiveDialogRadioGroupRight.check(checkedId);
                notCheckedYet = true;
            }
        });
        newElectiveDialogRadioGroupLeft.setOnCheckedChangeListener((group, checkedId) -> {
            if(newElectiveDialogRadioGroupRight.isEnabled() && notCheckedYet){
                notCheckedYet = false;
                newElectiveDialogRadioGroupRight.clearCheck();
                newElectiveDialogRadioGroupLeft.check(checkedId);
                notCheckedYet = true;
            }
            else if(newElectiveDialogRadioGroupLeft.isEnabled() && notCheckedYet){
                notCheckedYet = false;
                newElectiveDialogRadioGroupLeft.clearCheck();
                newElectiveDialogRadioGroupLeft.check(checkedId);
                notCheckedYet = true;
            }
        });
        newElectiveDialogCancelButton.setOnClickListener(v -> {
            newElectiveDialog.dismiss();
        });
        newElectiveDialogAddButton.setOnClickListener(v -> {
            if(StringValidation.isCorrectID(newElectiveDialogTeacherID.getText().toString(), peopleDAO.PEOPLE_COUNT))
                checkAndAddNewElective();
            else informWrongNewTeacherInput();
        });
    }
    private void informWrongNewTeacherInput(){
        newElectiveDialogTeacherID.setText("");
        newElectiveDialogTeacherID.setHint("Wrong input");
        newElectiveDialogTeacherID.setHintTextColor(Color.RED);
    }
    private void defineDialogElectiveButtons(){
        dialogElectiveCancelButton.setOnClickListener(v -> {dialogElective.dismiss();});
        dialogElectiveAddLearnerButton.setOnClickListener(v -> {
            if(StringValidation.isCorrectID(dialogElectiveAddNewLearner.getText().toString(), peopleDAO.PEOPLE_COUNT));
//                checkAndAddNewLearner();
            else informWrongNewLearnerInput();
        });
    }
    private void defineButtonListeners(){
        defineDialogElectiveButtons();
        defineNewElectiveDialogButtons();
        addNewElectiveButton.setOnClickListener(v -> {
            newElectiveDialog.show();
        });
    }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_electives);
            getPeopleDao();
            findViews();
            defineButtonListeners();
            electiveClickListener = (currentElective, position) -> newElectiveDialog.show();
            ElectivesAdapter electivesAdapter =  new ElectivesAdapter(this,peopleDAO.school.listElectives,electiveClickListener);
            electivesList = new RecyclerView(this);
            electivesList.setAdapter(electivesAdapter);
        }
}
