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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ElectivesActivity extends AppCompatActivity {
        private Button addNewElectiveButton;
        private ImageButton searchButton;
        private EditText searchBoard;
        private ImageButton closeSearchButton;
        private TextView dialogElectiveSubject;
        private TextView dialogElectiveTeacherID;
        private TextView dialogElectiveNumOfLearners;
        private EditText dialogElectiveAddNewLearner;
        private Button dialogElectiveCancelButton;
        private Button dialogElectiveAddLearnerButton;


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
        private Button buttonMainMenuElectives;
        private ElectivesAdapter electivesAdapter;
        private ArrayList<Elective> electivesInList;
        private ElectivesAdapter.OnElectivesClickListener electiveClickListener;
        private PeopleDAO peopleDAO;
        private Dialog newElectiveDialog;
        private Dialog dialogElective;
        private TextView newElectiveDialogWrongSubject;
        private boolean notCheckedYet = true;
        private ElectivesDAO electivesDAO;

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
        closeSearchButton = findViewById(R.id.electiveCloseSearchButton);
        buttonMainMenuElectives = findViewById(R.id.buttonMainMenuElectives);
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
    private void informTeacherDoesNotFit(){
        newElectiveDialogTeacherID.setText("");
        newElectiveDialogTeacherID.setHint("Teacher doesn't fit");
        newElectiveDialogTeacherID.setHintTextColor(Color.RED);
    }
    private void createNewElective(Teacher teacher, String subject){
        electivesDAO.database.insert(DBHelperElectives.TABLE_ELECTIVES, null ,
                electivesDAO.makeContentValueForElective(subject, teacher.getCardID()));
    }
    private void putNewElectiveToSchool(Teacher teacher, String subject){
        peopleDAO.school.listElectives.add(new Elective(teacher,subject));
    }
    private boolean checkIsTeacherFree(Teacher teacher){
            for(Elective elective : peopleDAO.school.listElectives){
                if(elective.electiveTeacher == teacher) return false;
            }
            return true;
    }
    private void checkAndAddNewElective(int teacherID){
        String subject = null;
        if (newElectiveDialogBiologyRadioButton.isChecked()) {
            subject = "Biology";
        } else if (newElectiveDialogChemistryRadioButton.isChecked()) {
            subject = "Chemistry";
        } else if (newElectiveDialogComputerScienceRadioButton.isChecked()) {
            subject = "Computer Science";
        } else if (newElectiveDialogNativeLanguageRadioButton.isChecked()) {
            subject = "Native Language";
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
            subject = "Social Studies";
        } else if (newElectiveDialogPhysicsRadioButton.isChecked()) {
            subject = "Physics";
        }else informWrongSubjectInput();
        if (subject != null){
            if (peopleDAO.findParticipantsStatusByID(teacherID).equals("TEACHER")) {
                Teacher toCheck =  peopleDAO.findTeacherByID(teacherID);
                if(StringExercise.checkIsSubString(toCheck.getQualifications(), subject) && checkIsTeacherFree(toCheck)) {
                    createNewElective(toCheck, subject);
                    putNewElectiveToSchool(toCheck,subject);
                    newElectiveDialog.dismiss();
                }
                else informTeacherDoesNotFit();
            }else informWrongNewTeacherInput();
        }
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
                checkAndAddNewElective(Integer.parseInt(newElectiveDialogTeacherID.getText().toString()));
            else informWrongNewTeacherInput();
        });
    }
    private void informWrongNewTeacherInput(){
        newElectiveDialogTeacherID.setText("");
        newElectiveDialogTeacherID.setHint("Wrong input");
        newElectiveDialogTeacherID.setHintTextColor(Color.RED);
    }
    private boolean isLearnerWithIDExist(String learnerID){
            return peopleDAO.findParticipantsStatusByID(Integer.parseInt(learnerID)).equals("LEARNER");
    }
    private boolean isLearnerAlreadyInElective(int learnerID,int teacherID){
        for(Elective elective : peopleDAO.school.listElectives) {
            if(elective.electiveTeacher.getCardID() == teacherID){
                for(Learner learner : elective.listLearners){
                    if(learner.getCardID() == learnerID) return true;
                }
                return false;
            }
        }
        return true;
    }
    private void addLearnerToElective(int learnerID, int teacherID){
        for(Elective elective : peopleDAO.school.listElectives) {
            if(elective.electiveTeacher.getCardID() == teacherID){
                elective.listLearners.add(peopleDAO.findLearnerByID(learnerID));
            }
        }
    }
    private void informLearnerIsAlreadyInElective(){
        dialogElectiveAddNewLearner.setText("");
        dialogElectiveAddNewLearner.setHint("Learner is already int elective");
        dialogElectiveAddNewLearner.setHintTextColor(Color.RED);
    }
    private void informLearnerDoesNotExist(){
        dialogElectiveAddNewLearner.setText("");
        dialogElectiveAddNewLearner.setHint("Learner with this ID doesn't exist");
        dialogElectiveAddNewLearner.setHintTextColor(Color.RED);
    }

    private void checkAndAddNewLearner(String learnerID) {
            if(isLearnerWithIDExist(learnerID)){
                if(!isLearnerAlreadyInElective(Integer.parseInt(learnerID), Integer.parseInt(dialogElectiveTeacherID.getText().toString().substring(14)))){
                    electivesDAO.addLearnerToElectiveDB(Integer.parseInt(learnerID), Integer.parseInt(dialogElectiveTeacherID.getText().toString().substring(14)));

                    addLearnerToElective(Integer.parseInt(learnerID), Integer.parseInt(dialogElectiveTeacherID.getText().toString().substring(14)));
                }
                else informLearnerIsAlreadyInElective();
            } else informLearnerDoesNotExist();
    }
    private void defineDialogElectiveButtons(){
        dialogElectiveCancelButton.setOnClickListener(v -> {dialogElective.dismiss();});
        dialogElectiveAddLearnerButton.setOnClickListener(v -> {
            if(StringValidation.isCorrectID(dialogElectiveAddNewLearner.getText().toString(), peopleDAO.PEOPLE_COUNT))
              checkAndAddNewLearner(dialogElectiveAddNewLearner.getText().toString());
            else informWrongNewLearnerInput();
        });
    }
    private void defineButtonListeners(){
        defineDialogElectiveButtons();
        defineNewElectiveDialogButtons();
        defineMainMenuButtonListener();
        addNewElectiveButton.setOnClickListener(v -> {
            newElectiveDialog.show();
        });
        RecyclerView electivesList = (RecyclerView) findViewById(R.id.recyclerViewElectives);
        electivesList.setLayoutManager(new LinearLayoutManager(this));
        electiveClickListener = (currentElective, position) -> showElectiveDialog(currentElective);
        electivesInList = new ArrayList<>();
        electivesInList.addAll(peopleDAO.school.listElectives);
        electivesAdapter =  new ElectivesAdapter(this, electivesInList, electiveClickListener);
        electivesList.setAdapter(electivesAdapter);
    }
    @SuppressLint("SetTextI18n")
    private void showElectiveDialog(Elective elective){
        dialogElectiveSubject.setText("Subject: "+elective.academicSubject);
        dialogElectiveTeacherID .setText("Teacher's ID: "+elective.electiveTeacher.getCardID());
        dialogElectiveNumOfLearners.setText("Number of Learners: "+ elective.listLearners.size());
        dialogElective.show();
    }
    private void getElectiveDAO(){
        electivesDAO = new ElectivesDAO();
        electivesDAO.setDbHelper(new DBHelperElectives(this));
        electivesDAO.createDatabase();
        peopleDAO.school.listElectives = electivesDAO.getElectives(peopleDAO);

    }
    private void addElectivesBySubject(String subject, ArrayList<Elective> electivesToSee){
        for(Elective elective : peopleDAO.school.listElectives){
            if (elective.academicSubject.equals(subject)) electivesToSee.add(elective);
        }
    }
    private void defineSearchSystem(){
            searchButton.setOnClickListener(v -> {
                String subject = searchBoard.getText().toString();
                ArrayList <Elective> electivesToSee = new ArrayList<>();
                if(StringValidation.isCorrectString(subject)) {
                    if (subject.equals("Mathematics") || subject.equals("mathematics"))
                        addElectivesBySubject("Mathematics", electivesToSee);
                    if (subject.equals("Physics") || subject.equals("physics")) {
                        addElectivesBySubject("Physics", electivesToSee);
                    }
                    if (subject.equals("Computer Science") || subject.equals("Computer science") || subject.equals("computer science")
                            || subject.equals("computer Science"))
                        addElectivesBySubject("Computer Science", electivesToSee);
                    if (subject.equals("Foreign Language") || subject.equals("foreign language") || subject.equals("foreign Language")
                            || subject.equals("Foreign language"))
                        addElectivesBySubject("Foreign Language", electivesToSee);
                    if (subject.equals("Native Language") || subject.equals("Native language") || subject.equals("native language")
                            || subject.equals("native Language"))
                        addElectivesBySubject("Native Language", electivesToSee);
                    if (subject.equals("Literature") || subject.equals("literature"))
                        addElectivesBySubject("Literature", electivesToSee);
                    if (subject.equals("Geography") || subject.equals("geography"))
                        addElectivesBySubject("Geography", electivesToSee);
                    if (subject.equals("Physical Training") || subject.equals("physical Training") || subject.equals("physical training")
                            || subject.equals("Physical training"))
                        addElectivesBySubject("PhysicalTraining", electivesToSee);
                    if (subject.equals("Biology") || subject.equals("biology"))
                        addElectivesBySubject("Biology", electivesToSee);
                    if (subject.equals("Chemistry") || subject.equals("chemistry"))
                        addElectivesBySubject("Chemistry", electivesToSee);
                    if (subject.equals("Social Studies") || subject.equals("social Studies") || subject.equals("social studies")
                            || subject.equals("Social studies"))
                        addElectivesBySubject("Social Studies", electivesToSee);
                    if (subject.equals("History") || subject.equals("history"))
                        addElectivesBySubject("History", electivesToSee);
                }
                electivesInList.clear();
                electivesInList.addAll(electivesToSee);
                electivesAdapter.notifyDataSetChanged();
            });
            closeSearchButton.setOnClickListener(v -> {
                electivesInList.clear();
                electivesInList.addAll(peopleDAO.school.listElectives);
                electivesAdapter.notifyDataSetChanged();
            });
    }
    private void defineMainMenuButtonListener(){
        buttonMainMenuElectives.setOnClickListener(v -> {
            Intent i;
            i = new Intent(ElectivesActivity.this, MainActivity.class);
            i.putExtra("peopleDAO", peopleDAO);
            startActivityForResult(i,1);
        });
    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_electives);
            getPeopleDao();
            getElectiveDAO();
            findViews();
            defineButtonListeners();
            defineSearchSystem();
        }
}
