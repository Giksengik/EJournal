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
        String file_name = FILE_NAME_CLASSES + School.num_of_classes;
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

    void writeNewLearner(Learner learner, Class curClass) {
        String file_name = null;
        try {
            String str;
            int teacherID = curClass.classTeacher.CardID;
            for (int i = 0; i < School.num_of_classes; i++) {
                file_name = FILE_NAME_CLASSES + (i + 1);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        openFileInput(file_name)));
                str = br.readLine();
                if (Integer.parseInt(str) == teacherID) {
                    break;
                }
            }
        } catch (FileNotFoundException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(file_name, MODE_APPEND);
            fos.write((learner.CardID + "").getBytes());
            fos.write("\n".getBytes());
            Toast.makeText(this, "Learner added", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("SetTextI18n")
    void showClass(Class currentClass) {
        className.setText("Class name: " + currentClass.number);
        classTeacherId.setText("Class teacher's ID: " + currentClass.classTeacher.CardID);
        classTeacherName.setText("Class teacher's name: " + currentClass.classTeacher.fullName);
        String learners = "";
        String b;
        for (int i = 0; i < currentClass.learners.length; i++) {
            if (currentClass.learners[i] == null) {
                break;
            } else {
                if (i != 0) {
                    b = learners + ", " + (i + 1) + ")" + " Name: " + currentClass.learners[i].fullName + " ID: " +
                            currentClass.learners[i].CardID;
                } else {
                    b = "Learners: 1) Name: " + currentClass.learners[i].fullName + " ID: " +
                            currentClass.learners[i].CardID;
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
        searchButton.setOnClickListener(v -> {
            findClassByName(searchBoardClasses.getText().toString());
        });
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
        defineButtons();
    }

    private void defineButtons() {
        buttonNewClass.setOnClickListener(v -> dialogNewClass.show());
        buttonCancelNewClass.setOnClickListener(v -> dialogNewClass.dismiss());
        buttonClassAddLearner.setOnClickListener(v -> {
            if(newClassLearnerId.getText().toString().matches("[0-9]+")) {
                if (Integer.parseInt(newClassLearnerId.getText().toString()) >= School.num_of_cards) {
                    newClassLearnerId.setText("");
                    newClassLearnerId.setHint("no learner with this ID");
                    newClassLearnerId.setHintTextColor(Color.RED);

                } else {
                    Learner newLearner = null;
                    boolean flag1 = true;
                    for (int i = 0; i < school.learners.length; i++) {
                        if (school.learners[i] == null) {
                            newClassLearnerId.setText("");
                            newClassLearnerId.setHint("no learner with this ID");
                            newClassLearnerId.setHintTextColor(Color.RED);
                            flag1 = false;
                            break;
                        } else if (school.learners[i].CardID == Integer.parseInt(newClassLearnerId.getText().toString())) {
                            newLearner = school.learners[i];
                            break;
                        }
                    }
                    if (flag1) {
                        boolean flag = true;
                        for (int i = 0; i < school.classes.length; i++) {
                            if (school.classes[i] == null) {
                                break;
                            } else {
                                for (int j = 0; j < school.classes[i].learners.length; j++) {
                                    if (school.classes[i].learners[j] == null) {
                                        break;
                                    } else {
                                        if (school.classes[i].learners[j].CardID ==
                                                Integer.parseInt(newClassLearnerId.getText().toString())) {
                                            newClassLearnerId.setText("");
                                            newClassLearnerId.setHint(
                                                    "the student is already in another class");
                                            newClassLearnerId.setHintTextColor(Color.RED);
                                            flag = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (flag){
                            Class classToAdd;
                            for(int i=0;i<school.classes.length;i++){
                                String a=className.getText().toString().substring(12);
                                if(school.classes[i].number.equals(a)){
                                    classToAdd=school.classes[i];
                                    for(int j=0;j<school.classes[i].learners.length;j++){
                                        if(school.classes[i].learners[j]==null){
                                            school.classes[i].learners[j]=newLearner;
                                            break;
                                        }
                                    }
                                    Intent b=new Intent(ClassesActivity.this,MainActivity.class);
                                    writeNewLearner(newLearner,classToAdd);
                                    b.putExtra("school",school);
                                    setResult(RESULT_CANCELED,b);
                                    startActivity(b);
                                    break;
                                }
                            }
                        }
                    }
                }
            }else{
                newClassLearnerId.setText("");
                newClassLearnerId.setHint("wrong input");
                newClassLearnerId.setHintTextColor(Color.RED);
            }
        });
        buttonOkNewClass.setOnClickListener(v -> {
            boolean flag = false;
            Teacher classTeacher = null;
            if (newClassTeacherID.getText().toString().matches("[0-9]+")) {
                if (Integer.parseInt(newClassTeacherID.getText().toString()) > School.num_of_cards) {
                    newClassTeacherID.setText("");
                    newClassTeacherID.setHint("no teacher with this ID");
                    newClassTeacherID.setHintTextColor(Color.RED);
                } else {
                    for (int i = 0; i < school.teachers.length; i++) {
                        if (school.teachers[i] == null) {
                            newClassTeacherID.setText("");
                            newClassTeacherID.setHint("no teacher with this ID");
                            newClassTeacherID.setHintTextColor(Color.RED);
                            break;
                        } else if (school.teachers[i].CardID == Integer.parseInt(newClassTeacherID.getText().toString())) {
                            flag = true;
                            classTeacher = school.teachers[i];
                            break;
                        }
                    }
                }
            } else {
                newClassTeacherID.setText("");
                newClassTeacherID.setHint("wrong input");
                newClassTeacherID.setHintTextColor(Color.RED);
            }
            if (newClassName.getText().toString().length() == 0 ||
                    newClassName.getText().toString().matches("[ ]*")) {
                flag = false;
                newClassName.setText("");
                newClassName.setHint("wrong input");
                newClassName.setHintTextColor(Color.RED);
            }
            if(flag){
                makeNewClass(classTeacher,newClassName.getText().toString());
            }
        });
        buttonClassCancel.setOnClickListener(v -> dialogClass.dismiss());
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
