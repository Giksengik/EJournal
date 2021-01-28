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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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

    private String[] classesInListView;
    private ListView lvMain;

    private EditText searchBoardClasses;
    private ImageButton searchButton;

    void makeNewClass(Teacher classTeacher, String className) {
        School.num_of_classes++;
        String file_name = FILE_NAME_CLASSES + School.num_of_classes);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(file_name, MODE_PRIVATE);
            fos.write((classTeacher.CardID + "").getBytes());
            fos.write("\n".getBytes());
            fos.write(className.getBytes());
            fos.write("\n".getBytes());
            Toast.makeText(this, "Class added", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        for (int i = 0; i < school.classes.length; i++) {
            if (school.classes[i] == null) {
                Learner[] learners = new Learner[100];
                school.classes[i] = new Class(className, classTeacher, learners);
                break;
            }
        }
        Intent i = new Intent(ClassesActivity.this, MainActivity.class);
        i.putExtra("school", school);
        setResult(RESULT_CANCELED, i);
        startActivity(i);
    }
    private String getClassFileName (Class curClass) {
        String file_name=null;
        try {
            String str;
            int teacherID = curClass.classTeacher.CardID;
            for (int i = 0; i < School.num_of_classes; i++) {
                file_name = FILE_NAME_CLASSES + (i + 1);
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(file_name)));
                str = br.readLine();
                if (Integer.parseInt(str) == teacherID) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name;
    }
    private void writeLearnerToFile(Learner learner, String file_name){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(file_name, MODE_APPEND);
            fos.write((learner.CardID + "").getBytes());
            fos.write("\n".getBytes());
            Toast.makeText(this, "Learner added", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    void writeNewLearner(Learner learner, Class curClass) throws IOException {
        String file_name = getClassFileName(curClass);
        if (file_name == null) throw new IOException("NOT FOUND FILE FOR THIS CLASS!!!!");
        writeLearnerToFile(learner,file_name);
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

    Class checkIfClassExistAndReturnIt(String className) {
        for (int i = 0; i < School.num_of_classes; i++) {
            if (school.classes[i].number.equals(className)) return school.classes[i];
        }
        return null;
    }
    private String collectClassLearners(Class currentClass) {
        StringBuilder learners = new StringBuilder();
        for (int i = 0; i < currentClass.learners.length; i++) {
            if (currentClass.learners[i] == null) {
                break;
            } else {
                if (i != 0) {
                    learners.append(", ").append(i + 1).append(")").append(" Name: ").append(currentClass.learners[i].fullName).append(" ID: ").append(currentClass.learners[i].CardID);
                } else {
                    learners.append("Learners: 1) Name: ").append(currentClass.learners[i].fullName).append(" ID: ").append(currentClass.learners[i].CardID);
                }
            }
        }
        return learners.toString();
    }
    @SuppressLint("SetTextI18n")
    private void setDialogClassLearners(String learners){
        if (learners.equals("")) classLearners.setText("Learners: none");
         else classLearners.setText(learners);
    }
    private void defineDialogClassLearners(Class currentClass) {
        String learners = collectClassLearners(currentClass);
        setDialogClassLearners(learners);
    }
    @SuppressLint("SetTextI18n")
    private void defineDialogClassFields(Class currentClass){
        className.setText("Class name: " + currentClass.number);
        classTeacherId.setText("Class teacher's ID: " + currentClass.classTeacher.CardID);
        classTeacherName.setText("Class teacher's name: " + currentClass.classTeacher.fullName);
        defineDialogClassLearners(currentClass);
    }
    @SuppressLint("SetTextI18n")
    void showClass(Class currentClass) {
        defineDialogClassFields(currentClass);

        dialogClass.show();
    }

    private void defineDialogElements() {
        defineClassDialog();
        defineNewClassDialog();
    }

    private void defineSearchSystem() {
        searchBoardClasses = (EditText) findViewById(R.id.searchBoardClasses);
        searchButton = (ImageButton) findViewById(R.id.searchButtonClasses);
        defineSearchListener();
    }

    private void defineSearchListener() {
        searchButton.setOnClickListener(v -> findClassByName(searchBoardClasses.getText().toString()));
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

    private void defineNewClassDialog() {
        this.dialogNewClass = new Dialog(ClassesActivity.this);
        dialogNewClass.setContentView(R.layout.new_class);
        this.newClassTeacherID = (EditText) dialogNewClass.findViewById(R.id.newClassTeacherId);
        this.newClassName = (EditText) dialogNewClass.findViewById(R.id.newClassName);
        this.buttonCancelNewClass = (Button) dialogNewClass.findViewById(R.id.buttonCancelNewClass);
        this.buttonOkNewClass = (Button) dialogNewClass.findViewById(R.id.buttonOkNewClass);
    }

    private void defineElementsAndContentView() {
        setContentView(R.layout.activity_classes);
        this.buttonNewClass = (Button) findViewById(R.id.buttonNewClass);
        defineDialogElements();
        defineSearchSystem();
        defineSchool();
        defineListView();
        defineButtonsListeners();
    }
    private boolean isCorrectTeacherIDInput(String input){
        return input.matches("[0-9]+") && Integer.parseInt(input) < School.num_of_cards &&
                Integer.parseInt(input) > 0;
    }
    private void informWrongTeacherIDInput(){
        newClassTeacherID.setText("");
        newClassTeacherID.setHint("wrong input");
        newClassTeacherID.setHintTextColor(Color.RED);
    }
    private Teacher findTeacherByID(int id) {
        for (int i = 0; i < school.teachers.length; i++) {
            if (school.teachers[i] == null) return null;
            if (school.teachers[i].CardID == id) {
                return school.teachers[i];
            }
        }
        return null;
    }
    private void informNoTeacherWithID(){
        newClassTeacherID.setText("");
        newClassTeacherID.setHint("no teacher with this ID");
        newClassTeacherID.setHintTextColor(Color.RED);
    }
    private boolean isTeacherFree(int teacherID){
        for (int i = 0; i < school.classes.length - 1; i++){
            if (school.classes[i] == null) return true;
            if (school.classes[i].classTeacher.CardID == teacherID) return false;
        }
        return true;
    }
    private void informTeacherIsNotFree(){
        newClassTeacherID.setText("");
        newClassTeacherID.setHint("Teacher isn't free" );
        newClassTeacherID.setHintTextColor(Color.RED);
    }
    private void defineButtonOkNewClassListener(){
        buttonOkNewClass.setOnClickListener(v -> {
            if (isCorrectTeacherIDInput(newClassTeacherID.getText().toString())) {
                Teacher currentTeacher = findTeacherByID(Integer.parseInt(newClassTeacherID.getText().toString()));
                if (currentTeacher == null) informNoTeacherWithID();
                else {
                    if(isTeacherFree(currentTeacher.CardID)){
                        makeNewClass(currentTeacher, newClassName.getText().toString());
                    }else{
                        informTeacherIsNotFree();
                    }
                }
            } else {
                informWrongTeacherIDInput();
            }
        });
    }
    private void defineNewClassDialogButtonListeners(){
        buttonNewClass.setOnClickListener(v -> dialogNewClass.show());
        buttonCancelNewClass.setOnClickListener(v -> dialogNewClass.dismiss());
        defineButtonOkNewClassListener();
    }
    private void informWrongLearnerInput(){
        newClassLearnerId.setText("");
        newClassLearnerId.setHint("wrong input");
        newClassLearnerId.setHintTextColor(Color.RED);
    }
    private Learner findLearnerByID(int learnerId) {
        for (int i = 0; i < school.learners.length; i++) {
            if (school.learners[i] == null) return null;
            if (school.learners[i].CardID == learnerId) return school.learners[i];
        }
        return null;
    }
    private void informNoLearnerWithID() {
        newClassLearnerId.setText("");
        newClassLearnerId.setHint("there is no learner with this ID");
        newClassLearnerId.setHintTextColor(Color.RED);
    }
    private boolean isLearnerFree(int learnerID) {
        for (int i = 0; i < school.classes.length; i++) {
            if (school.classes[i] == null) return true;
            for (int j = 0; j < school.classes[i].learners.length; j++) {
                if (school.classes[i].learners[j] == null) break;
                if (school.classes[i].learners[j].CardID == learnerID) return false;
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
        b.putExtra("school",school);
        setResult(RESULT_CANCELED,b);
        startActivity(b);
    }
    private void findClassToAddLeanerByNameAndAddLearner(Learner learner){
        Class classToAdd;
        for(int i=0;i<school.classes.length;i++){
            String toCheck=className.getText().toString().substring(12);
            if(school.classes[i].number.equals(toCheck)){
                classToAdd=school.classes[i];
                for(int j=0;j<school.classes[i].learners.length;j++){
                    if(school.classes[i].learners[j]==null){
                        school.classes[i].learners[j]=learner;
                        break;
                    }
                }
                try {
                    writeNewLearner(learner,classToAdd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startMainActivity();
                break;
            }
        }
    }
    private void defineButtonClassAddButton(){
        buttonClassAddLearner.setOnClickListener(v -> {
            if(isCorrectTeacherIDInput(newClassLearnerId.getText().toString())){
                Learner currentLearner = findLearnerByID(Integer.parseInt(newClassLearnerId.getText().toString()));
                if(currentLearner == null) informNoLearnerWithID();
                else {
                    if (isLearnerFree(currentLearner.CardID)) {
                       findClassToAddLeanerByNameAndAddLearner(currentLearner);

                    }
                    else informLearnerIsNotFree();
                }
                } else informWrongLearnerInput();
        });
    }
    private void defineClassAddLearnerDialogListeners(){
        buttonClassCancel.setOnClickListener(v -> dialogClass.dismiss());
        defineButtonClassAddButton();
    }
    private void defineButtonsListeners() {
        defineNewClassDialogButtonListeners();
        defineClassAddLearnerDialogListeners();
    }

    private void defineSchool() {
        Intent mIntent = getIntent();
        school = (School) mIntent.getSerializableExtra("school");
    }

    private void defineClassesInListView() {
        classesInListView = new String[School.num_of_classes];
        for (int i = 0; i < School.num_of_classes; i++) {
            classesInListView[i] = collectStringForListView(school.classes[i]);
        }
    }

    private String collectStringForListView(Class currentClass) {
        return ("Class: " + currentClass.number + " | Teacher's name: " + currentClass.classTeacher.fullName +
                "| Teacher's ID: " + currentClass.classTeacher.CardID);
    }

    private void defineListView() {
        defineClassesInListView();
        ArrayAdapter<String> adapterForListView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                classesInListView);
        lvMain = (ListView) findViewById(R.id.listViewClasses);
        lvMain.setAdapter(adapterForListView);
        defineListViewListener();
    }

    @SuppressLint("SetTextI18n")
    private void defineListViewListener() {
        lvMain.setOnItemClickListener((parent, view, position, id) -> {
            className.setText("Class name: " + school.classes[position].number);
            classTeacherId.setText("Class teacher's ID: " + school.classes[position].classTeacher.CardID);
            classTeacherName.setText("Class teacher's name: " + school.classes[position].classTeacher.fullName);
            String learners = "";
            String b;
            for (int i = 0; i < school.classes[position].learners.length; i++) {
                if (school.classes[position].learners[i] == null) {
                    break;
                } else {
                    if (i != 0) {
                        b = learners + ", " + (i + 1) + ")" + " Name: " + school.classes[position].learners[i].fullName + " ID: " +
                                school.classes[position].learners[i].CardID;
                    } else {
                        b = "Learners: 1) Name: " + school.classes[position].learners[i].fullName + " ID: " +
                                school.classes[position].learners[i].CardID;
                    }
                    learners = b;
                }
            }
            if (learners.equals("")) {
                classLearners.setText("Learners: none");
            } else {
                classLearners.setText(learners);
            }
            dialogClass.show();
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defineElementsAndContentView();

    }
}
