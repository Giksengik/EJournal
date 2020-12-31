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
    final String FILE_EMPLOYEES= "employees";
    School school;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee_activity);
        Button buttonConfirm= findViewById(R.id.newEmployeeConfirm);
        EditText employeeName= findViewById(R.id.editTextNewEmployee1);
        EditText employeePhone= findViewById(R.id.editTextNewEmployee2);
        EditText employeePosition= findViewById(R.id.editTextNewEmployee3);
        Intent mIntent = getIntent();
        school=(School)mIntent.getSerializableExtra("school");
        buttonConfirm.setOnClickListener(v -> {
            String name = employeeName.getText().toString();
            String phone = employeePhone.getText().toString();
            String position = employeePosition.getText().toString();
            boolean flag=false;
            if ("".equals(name) ||
                    !name.matches("[a-zA-Z| ]+")||
                    name.matches("[ ]*")) {
                employeeName.setText("");
                employeeName.setHint("wrong input");
                employeeName.setHintTextColor(Color.RED);
                flag=true;
            }
            if (phone.length() != 11 ||
                    !phone.matches("[0-9]+")) {
                employeePhone.setText("");
                employeePhone.setHint("wrong input, don't write + ");
                employeePhone.setHintTextColor(Color.RED);
                flag=true;
            }
            if(position.equals("")||
                    position.matches("[ ]*")) {
                employeePosition.setHint("wrong input");
                employeePosition.setHintTextColor(Color.RED);
                employeePosition.setText("");
                flag = true;
            }if(!flag){
                makeEmployee(name,phone,position,school);
                Intent i=new Intent(NewEmployeeActivity.this,MainActivity.class);
                i.putExtra("school",school);
                setResult(RESULT_CANCELED,i);
                startActivity(i);
            }
        });
    }
    void makeEmployee(String name,String phone,String position,School school){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_EMPLOYEES, MODE_APPEND);
            fos.write((School.num_of_cards+"").getBytes());
            fos.write("\n".getBytes());
            fos.write(name.getBytes());
            fos.write("\n".getBytes());
            fos.write(phone.getBytes());
            fos.write("\n".getBytes());
            fos.write(position.getBytes());
            fos.write("\n".getBytes());
            fos.write("----------".getBytes());
            fos.write("\n".getBytes());
            Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show();
            for(int i=0;i<school.employees.length;i++){
               if(school.employees[i]==null){
                   school.employees[i]=new Employee(name,phone,School.num_of_cards,position);
                   School.num_of_cards++;
                   break;
               }
            }
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
}
