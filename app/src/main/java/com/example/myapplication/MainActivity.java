package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    final String FILE_NAME_PERSONS = "filePersons";
    final String FILE_NAME_SCHOOL = "fileSchool";
    final String SCHOOL_NAME = "RTU MIREA";
    final String SCHOOL_ADDRESS = "Vernadsky prospect, 78, Moscow";
    final String FILE_NAME_EMPLOYEE = "employees";
    final String FILE_NAME_LEARNERS = "learners";
    final String FILE_NAME_TEACHERS = "teachers";
    final String FILE_NAME_CLASSES = "class";
    Employee[] employees = new Employee[100];
    Learner[] learners = new Learner[2000];
    Teacher[] teachers = new Teacher[200];
    Class[] classes = new Class[50];
    Elective[] electives = new Elective[100];
    Section[] sections = new Section[100];
    School school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent m = getIntent();
        Button buttonClasses = (Button) findViewById(R.id.buttonClasses);
        Button buttonElectives = (Button) findViewById(R.id.buttonElectives);
        Button buttonPersons = (Button) findViewById(R.id.buttonPersons);
        Button buttonSections = (Button) findViewById(R.id.buttonSections);

        TextView textSchoolAddress = (TextView) findViewById(R.id.textSchoolAddress);
        TextView textSchoolName = (TextView) findViewById(R.id.textSchoolName);

        if (m.getSerializableExtra("school") == null) {
            //clearFiles();
            //clearClasses();
            checkFile();
            setSchoolName(textSchoolName, textSchoolAddress);
            makeSchoolEntities(employees, learners, teachers, classes, electives, sections);
            school = new School(employees, teachers, learners, textSchoolAddress.getText().toString()
                    , textSchoolAddress.getText().toString(), classes, electives, sections); // creating school object
            //Toast.makeText(this, ("" + school.classes[4].number), Toast.LENGTH_SHORT).show();
            /*Toast.makeText(this, (""+school.employees[0].CardID), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, (school.teachers[0].fullName), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, (school.teachers[0].phone), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, (school.teachers[0].position), Toast.LENGTH_SHORT).show();
            for(int i=0;i<school.teachers[0].qualifications.length;i++){
                Toast.makeText(this, (school.teachers[0].qualifications[i]), Toast.LENGTH_SHORT).show();
            }
             */
        } else {
            school = (School) m.getSerializableExtra("school");
        }
        buttonClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity.this, ClassesActivity.class);
                i.putExtra("school", school);
                startActivity(i);
            }
        });
        buttonElectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity.this, ElectivesActivity.class);
                i.putExtra("school", school);
                startActivity(i);
            }
        });
        buttonPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity.this, PersonsActivity.class);
                i.putExtra("school", school);
                startActivity(i);
            }
        });
        buttonSections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity.this, SectionsActivity.class);
                i.putExtra("school", school);
                startActivity(i);
            }
        });
    }

    void checkFile() {
        try {
            FileInputStream inputStream = null;
            inputStream = openFileInput(FILE_NAME_SCHOOL);
            int data = inputStream.read();
            if (data == 0) {
                makeFileSchool();
            } else {
                Toast.makeText(this, "Файл уже имеется", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException ex) {
            makeFileSchool();
        }
    }

    void makeFileSchool() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME_SCHOOL, MODE_PRIVATE);
            fos.write(SCHOOL_NAME.getBytes());
            fos.write("\n".getBytes());
            fos.write(SCHOOL_ADDRESS.getBytes());
            fos.write("\n".getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
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

    void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILE_NAME_PERSONS)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    void setSchoolName(TextView schoolName, TextView schoolAddress) {
        try {
            FileInputStream inputStream = null;
            inputStream = openFileInput(FILE_NAME_SCHOOL);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader buffreader = new BufferedReader(isr);
            String readString = buffreader.readLine();
            schoolName.setText("Name: " + readString);
            readString = buffreader.readLine();
            schoolAddress.setText("Address: " + readString);
            isr.close();
        } catch (IOException ex) {
            makeFileSchool();
        }
    }

    void addEmloyee(Employee[] employees) {

    }

    void addLearner(Learner[] learners) {

    }

    void addTeacher(Teacher[] teachers) {

    }

    void addClass(Class[] classes) {

    }

    void addElective(Elective[] electives) {

    }

    void addSection(Section[] sections) {

    }

    void makeSchoolEntities(Employee[] employees, Learner[] learners, Teacher[] teachers,
                            Class[] classes, Elective[] electives, Section[] sections) {

        //Employees
        try {
            int id = 0;
            String name = null;
            String position = null;
            String phone = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILE_NAME_EMPLOYEE)));
            String str = "";
            while ((str = br.readLine()) != null) {
                for (int i = 0; i < 4; i++) {
                    if (i == 0) {
                        id = Integer.parseInt(str);
                        str = br.readLine();
                    }
                    if (i == 1) {
                        name = str;
                        str = br.readLine();
                    }
                    if (i == 2) {
                        phone = str;
                        str = br.readLine();
                    }
                    if (i == 3) {
                        position = str;
                        str = br.readLine();
                    }
                }
                for (int i = 0; i < employees.length; i++) {
                    if (employees[i] == null) {
                        employees[i] = new Employee(name, phone, id, position);
                        if (id >= School.num_of_cards) {
                            School.num_of_cards = id + 1;
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Learners
        try {
            int id = 0;
            String nameParent1 = null;
            String nameParent2 = null;
            String phoneParent1 = null;
            String phoneParent2 = null;
            String nameLearner = null;
            String phoneLearner = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILE_NAME_LEARNERS)));
            String str = "";
            while ((str = br.readLine()) != null) {
                for (int i = 0; i < 7; i++) {
                    if (i == 0) {
                        id = Integer.parseInt(str);
                        str = br.readLine();
                    } else if (i == 1) {
                        nameParent1 = str;
                        str = br.readLine();
                    } else if (i == 2) {
                        phoneParent1 = str;
                        str = br.readLine();
                    } else if (i == 3) {
                        nameParent2 = str;
                        str = br.readLine();
                    } else if (i == 4) {
                        phoneParent2 = str;

                        str = br.readLine();
                    } else if (i == 5) {
                        nameLearner = str;
                        str = br.readLine();
                    } else {
                        phoneLearner = str;
                        str = br.readLine();
                    }
                }
                Parent parent1 = new Parent(nameParent1, phoneParent1);
                Parent parent2 = new Parent(nameParent2, phoneParent2);
                Parent[] parents = {parent1, parent2};
                for (int i = 0; i < learners.length; i++) {
                    if (learners[i] == null) {
                        learners[i] = new Learner(nameLearner, phoneLearner, id, parents);
                        if (id >= School.num_of_cards) {
                            School.num_of_cards = id + 1;
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Teachers
        try {
            int id = 0;
            String name = null;
            String phone = null;
            String position = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILE_NAME_TEACHERS)));
            String str = "";
            while ((str = br.readLine()) != null) {
                for (int i = 0; i < 4; i++) {
                    if (i == 0) {
                        id = Integer.parseInt(str);
                        str = br.readLine();
                    } else if (i == 1) {
                        name = str;
                        str = br.readLine();
                    } else if (i == 2) {
                        phone = str;
                        str = br.readLine();
                    } else {
                        position = str;
                        str = br.readLine();
                    }
                }
                // j cannot be less than 1
                int j = 0;
                String[] qualificationsToCheck = new String[12];
                while (!str.equals("----------")) {
                    j++;
                    uploadArray(str, qualificationsToCheck);
                    str = br.readLine();
                }
                String[] qualifications = new String[j];
                for (int i = 0; i < j; i++) {
                    qualifications[i] = qualificationsToCheck[i];
                }
                for (int i = 0; i < teachers.length; i++) {
                    if (teachers[i] == null) {
                        teachers[i] = new Teacher(name, phone, id, position, qualifications);
                        if (id >= School.num_of_cards) {
                            School.num_of_cards = id + 1;
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Classes
        int numOfClasses = 0;
        try {
            String str = "";
            for (int i = 0; i < classes.length; i++) {
                String file_name = FILE_NAME_CLASSES + (i + 1);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        openFileInput(file_name)));
                if ((str = br.readLine()) != null) {
                    numOfClasses++;
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        School.num_of_classes = numOfClasses;
        try {
            for (int i = 0; i < numOfClasses; i++) {
                String str;
                String className = null;
                Teacher classTeacher = null;
                Learner[] classLearners = new Learner[100];
                int ID = 0;
                String file_name = FILE_NAME_CLASSES + (i + 1);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        openFileInput(file_name)));
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        str = br.readLine();
                        ID = Integer.parseInt(str);
                    } else if (j == 1) {
                        str = br.readLine();
                        className = str;
                    }
                }
                for (int k = 0; k < teachers.length; k++) {
                    if (teachers[k] == null) {
                        break;
                    }
                    if (teachers[k].CardID == ID) {
                        classTeacher = teachers[k];
                        break;
                    }
                }
                int count = 0;
                while ((str = br.readLine()) != null) {
                    int learnersId = Integer.parseInt(str);
                    for (int j = 0; j < learners.length; j++) {
                        if (learnersId == learners[j].CardID) {
                            classLearners[count] = learners[j];
                            count++;
                            break;
                        }
                    }
                }
                Class newClass = new Class(className, classTeacher, classLearners);
                for (int k = 0; k < classes.length; k++) {
                    if (classes[k] == null) {
                        classes[k] = newClass;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        school = (School) data.getSerializableExtra("school");
    }

    void uploadArray(String str, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = str;
                break;
            }
        }
    }

    void clearFiles() {
        FileOutputStream fos = null;
        //teachers
        try {
            fos = openFileOutput(FILE_NAME_TEACHERS, MODE_PRIVATE);
            fos.write("".getBytes());
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
        //employees
        try {
            fos = openFileOutput(FILE_NAME_EMPLOYEE, MODE_PRIVATE);
            fos.write("".getBytes());
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
        //learners
        try {
            fos = openFileOutput(FILE_NAME_LEARNERS, MODE_PRIVATE);
            fos.write("".getBytes());
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

    void clearClasses() {
        int numOfClasses = 0;
        try {
            String str = "";
            for (int i = 0; i < classes.length; i++) {
                String file_name = FILE_NAME_CLASSES + (i + 1);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        openFileInput(file_name)));
                if ((str = br.readLine()) != null) {
                    numOfClasses++;
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < numOfClasses; i++) {
                String file_name = FILE_NAME_CLASSES + (i + 1);
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(file_name, MODE_PRIVATE);
                    fos.write(("").getBytes());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}