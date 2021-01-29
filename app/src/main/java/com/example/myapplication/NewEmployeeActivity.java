package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
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
    private School school;
    private Button buttonConfirm;
    private EditText employeeName;
    private EditText employeePhone;
    private EditText employeePosition;

    private void defineSchool() {
        Intent mIntent = getIntent();
        school = (School) mIntent.getSerializableExtra("school");
        setContentView(R.layout.add_employee_activity);
    }

    private boolean isCorrectString(String string) {
        return !("".equals(string) || !string.matches("[a-zA-Z| ]+") || string.matches("[ ]*"));
    }

    private boolean isCorrectPhoneNumber(String phone) {
        return !(phone.length() != 11 || !phone.matches("[0-9]+"));
    }

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

    ;

    private void startMainActivityWithResult() {
        Intent i = new Intent(NewEmployeeActivity.this, MainActivity.class);
        i.putExtra("school", school);
        setResult(RESULT_CANCELED, i);
        startActivity(i);
    }
    private void writeNewLearnerToFile(){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_EMPLOYEES, MODE_APPEND);
            fos.write((School.num_of_cards+"").getBytes());
            fos.write("\n".getBytes());
            fos.write(employeeName.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(employeePhone.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write(employeePosition.getText().toString().getBytes());
            fos.write("\n".getBytes());
            fos.write("----------".getBytes());
            fos.write("\n".getBytes());
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
    private void putNewLearnerToSchool() {
        for (int i = 0; i < school.employees.length; i++) {
            if (school.employees[i] == null) {
                school.employees[i] = new Employee
                        (employeeName.getText().toString(), employeePhone.getText().toString(), School.num_of_cards,
                                employeePosition.getText().toString());
                School.num_of_cards++;
                break;
            }
        }
    }
    private void makeEmployee() {
        writeNewLearnerToFile();
        putNewLearnerToSchool();


    }

    private void defineButtonConfirmListener() {
        buttonConfirm.setOnClickListener(v -> {
            String name = employeeName.getText().toString();
            String phone = employeePhone.getText().toString();
            String position = employeePosition.getText().toString();
            if (isCorrectString(name)) {
                if (isCorrectPhoneNumber(phone)) {
                    if (isCorrectString(position)) {
                        makeEmployee();
                        startMainActivityWithResult();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defineSchool();
        defineElements();


    }
}
