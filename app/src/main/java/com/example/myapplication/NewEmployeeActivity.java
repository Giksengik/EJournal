package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class NewEmployeeActivity extends AppCompatActivity {
    final String FILE_EMPLOYEES = "employees";
    private Button buttonConfirm;
    private EditText employeeName;
    private EditText employeePhone;
    private EditText employeePosition;
    private PeopleDAO peopleDAO;
    private void informWrongInputName() {
        employeeName.setText("");
        employeeName.setHint("wrong input");
        employeeName.setHintTextColor(Color.RED);
    }

    private void informWrongInputPhone() {
        employeePhone.setText("");
        employeePhone.setHint("wrong input, don't write + ");
        employeePhone.setHintTextColor(Color.RED);
    }

    private void informWrongInputPosition() {
        employeePosition.setHint("wrong input");
        employeePosition.setHintTextColor(Color.RED);
        employeePosition.setText("");
    }
    private void startPersonsActivityWithResult() {
        Intent i = new Intent(NewEmployeeActivity.this, PersonsActivity.class);
        i.putExtra("peopleDAO", peopleDAO);
        setResult(RESULT_CANCELED, i);
        startActivity(i);
    }
    private void putNewEmployeeToSchool() {
        peopleDAO.school.listEmployees.add(new Employee(employeeName.getText().toString(), employeePhone.getText().toString(),
                peopleDAO.PEOPLE_COUNT, employeePosition.getText().toString()));
    }

    private void defineButtonConfirmListener() {
        buttonConfirm.setOnClickListener(v -> {
            String name = employeeName.getText().toString();
            String phone = employeePhone.getText().toString();
            String position = employeePosition.getText().toString();
            if (StringValidation.isCorrectString(name)) {
                if (StringValidation.isCorrectPhoneNumber(phone)) {
                    if (StringValidation.isCorrectString(position)) {
                        saveNewEmployeeInDataBase();
                        putNewEmployeeToSchool();
                        Toast.makeText(this, "Employee is created", Toast.LENGTH_SHORT).show();
                        startPersonsActivityWithResult();
                    } else informWrongInputPosition();
                } else informWrongInputPhone();
            } else informWrongInputName();
        });
    }

    private void defineElements() {
        buttonConfirm = findViewById(R.id.newEmployeeConfirm);
        employeeName = findViewById(R.id.editTextNewEmployee1);
        employeePhone = findViewById(R.id.editTextNewEmployee2);
        employeePosition = findViewById(R.id.editTextNewEmployee3);
        defineButtonConfirmListener();
    }
    private void saveNewEmployeeInDataBase(){
        peopleDAO.createDatabase();
        peopleDAO.database.insert(DBHelper.TABLE_PARTICIPANTS, null ,
                peopleDAO.makeContentValueForEmployee(employeeName.getText().toString()
                , employeePhone.getText().toString(), employeePosition.getText().toString()));
    }
    private void getPeopleDao(){
        Intent mIntent = getIntent();
        peopleDAO = (PeopleDAO) mIntent.getSerializableExtra("peopleDAO");
        peopleDAO.setDbHelper(new DBHelper(this));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee_activity);
        getPeopleDao();
        defineElements();


    }
}
