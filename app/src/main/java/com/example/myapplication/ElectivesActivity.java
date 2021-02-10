package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ElectivesActivity extends AppCompatActivity {
        private Button addNewElectiveButton;
        private Button searchButton;
        private EditText searchBoard;
        private ElectivesAdapter.OnElectivesClickListener electiveClickListener;

        private TextView dialogElectiveSubject;
        private TextView dialogElectiveTeacherID;
        private TextView dialogElectiveNumOfLearners;
        private EditText dialogElectiveAddNewEmployee;
        private Button dialogElectiveCancelButton;
        private Button getDialogElectiveAddLearnerButton;

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
    private void getPeopleDao(){
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        peopleDAO.setDbHelper(new DBHelper(this));
        peopleDAO.createDatabase();
    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_electives);
            getPeopleDao();
            newElectiveDialog = new Dialog(ElectivesActivity.this);
            newElectiveDialog.setContentView(R.layout.new_elective);
            newElectiveDialog.show();
            electiveClickListener = (currentElective, position) -> newElectiveDialog.show();
            ElectivesAdapter electivesAdapter =  new ElectivesAdapter(this,peopleDAO.school.listElectives,electiveClickListener);
            electivesList = new RecyclerView(this);
            electivesList.setAdapter(electivesAdapter);
        }
}
