package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ClassesActivity extends AppCompatActivity {
    private School school;
    final String FILE_NAME_CLASSES = "class";

    private Dialog dialogClass;
    private TextView className;
    private TextView classTeacherName;
    private TextView classTeacherId;
    private TextView classLearners;
    private Button buttonClassCancel;

    private Dialog dialogNewClass;
    private EditText newClassTeacherID;
    private EditText newClassName;
    private Button buttonCancelNewClass;
    private Button buttonOkNewClass;
    private Button buttonNewClass;
    private EditText newClassLearnerId;
    private Button buttonClassAddLearner;
    private PeopleDAO peopleDAO;
    private ClassesDAO classesDAO;
    private String[] classesInListView;
    private ListView lvMain;

    private EditText searchBoardClasses;
    private ImageButton searchButton;


    private Class checkIfClassExistAndReturnIt(String nameClass){
        for(Class currClass: peopleDAO.school.listClasses){
            if(currClass.number.equals(nameClass)) return currClass;
        }
        return null;
    }
    void findClassByName(String className) {
        Class currentClass = checkIfClassExistAndReturnIt(className);
        if (currentClass != null) {
            showClass(currentClass);
        } else {
            informThereIsNoSuchClass();
        }
    }
    private void informThereIsNoSuchClass() {
        searchBoardClasses.setText("");
        searchBoardClasses.setHint("There is no such class");
        searchBoardClasses.setHintTextColor(Color.RED);
    }

    @SuppressLint("SetTextI18n")
    private void defineDialogClassFields(Class currentClass){
        className.setText("Class name: " + currentClass.number);
        classTeacherId.setText("Class teacher's ID: " + currentClass.classTeacher.getCardID());
        classTeacherName.setText("Class teacher's name: " + currentClass.classTeacher.getFullName());
    }
    @SuppressLint("SetTextI18n")
    void showClass(Class currentClass) {
        defineDialogClassFields(currentClass);

        dialogClass.show();
    }



    private void defineSearchSystem() {
        searchBoardClasses = (EditText) findViewById(R.id.searchBoardClasses);
        searchButton = (ImageButton) findViewById(R.id.searchButtonClasses);
        defineSearchListener();
    }

    private void defineSearchListener() {
        searchButton.setOnClickListener(v -> findClassByName(searchBoardClasses.getText().toString()));
    }





    private void defineNewClassDialog() {
        this.dialogNewClass = new Dialog(ClassesActivity.this);
        dialogNewClass.setContentView(R.layout.new_class);
        this.newClassTeacherID = (EditText) dialogNewClass.findViewById(R.id.newClassTeacherId);
        this.newClassName = (EditText) dialogNewClass.findViewById(R.id.newClassName);
        this.buttonCancelNewClass = (Button) dialogNewClass.findViewById(R.id.buttonCancelNewClass);
        this.buttonOkNewClass = (Button) dialogNewClass.findViewById(R.id.buttonOkNewClass);
    }
    private boolean isCorrectTeacherIDInput(String input){
        return input.matches("[0-9]+") && Integer.parseInt(input) <= peopleDAO.PEOPLE_COUNT &&
                Integer.parseInt(input) > 0;
    }
    private void informWrongTeacherIDInput(){
        newClassTeacherID.setText("");
        newClassTeacherID.setHint("wrong input");
        newClassTeacherID.setHintTextColor(Color.RED);
    }
    private void informNoTeacherWithID(){
        newClassTeacherID.setText("");
        newClassTeacherID.setHint("no teacher with this ID");
        newClassTeacherID.setHintTextColor(Color.RED);
    }
    private void informTeacherIsNotFree(){
        newClassTeacherID.setText("");
        newClassTeacherID.setHint("Teacher isn't free" );
        newClassTeacherID.setHintTextColor(Color.RED);
    }


    private void informWrongLearnerInput(){
        newClassLearnerId.setText("");
        newClassLearnerId.setHint("wrong input");
        newClassLearnerId.setHintTextColor(Color.RED);
    }
    private void informNoLearnerWithID() {
        newClassLearnerId.setText("");
        newClassLearnerId.setHint("there is no learner with this ID");
        newClassLearnerId.setHintTextColor(Color.RED);
    }
    private boolean isLearnerFree(int learnerID) {
        for(Class currentClass : peopleDAO.school.listClasses){
            for (int i = 0; i < currentClass.learnersList.size(); i ++) {
                if(currentClass.learnersList.get(i).getCardID() == learnerID) return false;
            }
        }
        return true;
    }
    private void informLearnerIsNotFree(){
        newClassLearnerId.setText("");
        newClassLearnerId.setHint(
                "the student is already in another class");
        newClassLearnerId.setHintTextColor(Color.RED);
    }
    private void startMainActivity() {
        Intent b=new Intent(ClassesActivity.this,MainActivity.class);
        setResult(RESULT_CANCELED,b);
        startActivity(b);
    }

    private boolean isTeacherFree(int teacherID){
        for (Class currentClass : peopleDAO.school.listClasses) {
            if( currentClass.classTeacher.getCardID() == teacherID ) return false;
        }
        return true;
    }
    private void saveNewClassInDataBase(){
        classesDAO.database.insert(DBHelperClasses.TABLE_CLASSES, null ,
                classesDAO.makeContentValueForClass(newClassName.getText().toString(),
                        Integer.parseInt(newClassTeacherID.getText().toString())));
    }


    private String getLearnersStringForDialog(ArrayList<Learner> learnersList){
        int count = 0;
        StringBuilder result = new StringBuilder(new String("Learners: "));
        if (learnersList.isEmpty()) result.append("none");
        else {
            for (Learner learner : learnersList) {
                result.append(++count).append(")").append(" Name: ").append(learner.getFullName()).append(" ID: ").append(learner.getCardID());
                result.append(" | ");
            }
        }
        return result.toString();
    }
    @SuppressLint("SetTextI18n")
    private void defineListViewListener() {
        lvMain.setOnItemClickListener((parent, view, position, id) -> {
            className.setText("Class name: " + peopleDAO.school.listClasses.get(position).number);
            classTeacherId.setText("Class teacher's ID: " + peopleDAO.school.listClasses.get(position).classTeacher.getCardID());
            classTeacherName.setText("Class teacher's name: " + peopleDAO.school.listClasses.get(position).classTeacher.getFullName());
            classLearners.setText(getLearnersStringForDialog(peopleDAO.school.listClasses.get(position).learnersList));
            dialogClass.show();
        });
    }
    private String [] getClassesInListView() {
        String[] classesInListView = new String[peopleDAO.school.listClasses.size()];
        int i = 0;
        for (Class currentClass : peopleDAO.school.listClasses) {
            classesInListView[i] = collectStringForListView(currentClass);
            i++;
        }
        return classesInListView;
    }
    private void defineListView() {
        ArrayAdapter<String> adapterForListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getClassesInListView());
        lvMain = (ListView) findViewById(R.id.listViewClasses);
        lvMain.setAdapter(adapterForListView);
        defineListViewListener();
    }
    private void defineButtonOkNewClassListener(){
        buttonOkNewClass.setOnClickListener(v -> {
            if (isCorrectTeacherIDInput(newClassTeacherID.getText().toString())) {
                Teacher currentTeacher = peopleDAO.findTeacherByID(Integer.parseInt(newClassTeacherID.getText().toString()));
                if (currentTeacher == null) informNoTeacherWithID();
                else {
                    if(isTeacherFree(currentTeacher.getCardID())){
                        saveNewClassInDataBase();
                    }else{
                        informTeacherIsNotFree();
                    }
                }
            } else {
                informWrongTeacherIDInput();
            }
        });
    }
    private void defineElementsAndContentView() {
        setContentView(R.layout.activity_classes);
        this.buttonNewClass = (Button) findViewById(R.id.buttonNewClass);
        defineDialogElements();
        defineSearchSystem();
        defineListView();
        defineButtonsListeners();
    }
    private void putNewLearnerToClass(Learner learner,String nameClass){
        for(Class currentClass :peopleDAO.school.listClasses){
            if(currentClass.number.equals(nameClass)) {
                currentClass.learnersList.add(learner);
                break;
            }
        }
    }
    private void findClassToAddLeanerByNameAndAddLearner(Learner currentLearner){
        String classToAdd = className.getText().toString().substring(12);
        classesDAO.addLearnerToClass(classToAdd, currentLearner.getCardID());
        putNewLearnerToClass(currentLearner,classToAdd);
    }
    private void defineButtonClassAddButton(){
        buttonClassAddLearner.setOnClickListener(v -> {
            if(isCorrectTeacherIDInput(newClassLearnerId.getText().toString())){
                Learner currentLearner = peopleDAO.findLearnerByID(Integer.parseInt(newClassLearnerId.getText().toString()));
                if(currentLearner == null) informNoLearnerWithID();
                else {
                    if (isLearnerFree(currentLearner.getCardID())) {
                        findClassToAddLeanerByNameAndAddLearner(currentLearner);
                        Toast.makeText(this, "Learner added", Toast.LENGTH_SHORT).show();
                    }
                    else informLearnerIsNotFree();
                }
            } else informWrongLearnerInput();
        });
    }

    private void defineNewClassDialogButtonListeners(){
        buttonNewClass.setOnClickListener(v -> dialogNewClass.show());
        buttonCancelNewClass.setOnClickListener(v -> dialogNewClass.dismiss());
        defineButtonOkNewClassListener();
    }
    private void defineClassAddLearnerDialogListeners(){
        buttonClassCancel.setOnClickListener(v -> dialogClass.dismiss());
        defineButtonClassAddButton();
    }
    private void defineButtonsListeners() {
        defineNewClassDialogButtonListeners();
        defineClassAddLearnerDialogListeners();
    }
    private void defineDialogElements() {
        defineClassDialog();
        defineNewClassDialog();
    }
    private void defineClassDialog() {
        this.dialogClass = new Dialog(ClassesActivity.this);
        dialogClass.setContentView(R.layout.info_class);
        this.className = (TextView) dialogClass.findViewById(R.id.className);
        this.classTeacherName = (TextView) dialogClass.findViewById(R.id.classTeachersName);
        this.classTeacherId = (TextView) dialogClass.findViewById(R.id.classTeachersID);
        this.classLearners = (TextView) dialogClass.findViewById(R.id.classLearners);
        this.newClassLearnerId = (EditText) dialogClass.findViewById(R.id.newClassLearnerId);
        this.buttonClassAddLearner = (Button) dialogClass.findViewById(R.id.buttonClassAddLearner);
        this.buttonClassCancel = (Button) dialogClass.findViewById(R.id.buttonCancelClass);
    }
    private void getPeopleDao(){
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        peopleDAO.setDbHelper(new DBHelper(this));
        peopleDAO.createDatabase();
    }
    private String collectStringForListView(Class currentClass) {
        return ("Class: " + currentClass.number + " | Teacher's name: " + currentClass.classTeacher.getFullName() +
                "| Teacher's ID: " + currentClass.classTeacher.getCardID());
    }
    private void getClassesDAO(){
        classesDAO = new ClassesDAO();
        classesDAO.setDbHelper(new DBHelperClasses(this));
        classesDAO.createDatabase();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPeopleDao();
        getClassesDAO();
//        classesDAO.dbHelperClasses.onCreate(classesDAO.database);
        classesDAO.getClasses(peopleDAO);
        defineElementsAndContentView();

    }
}
