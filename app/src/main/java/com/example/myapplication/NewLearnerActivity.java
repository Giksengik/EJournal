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

public class NewLearnerActivity extends AppCompatActivity {
   School school;
    final String FILE_LEARNERS= "learners";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_learner_activity);
        Intent mIntent = getIntent();
        school=(School)mIntent.getSerializableExtra("school");
        EditText parentsName1=(EditText) findViewById(R.id.editTextNewLearner1);
        EditText parentsPhone1=(EditText) findViewById(R.id.editTextNewLearner2);
        EditText parentsName2=(EditText) findViewById(R.id.editTextNewLearner3);
        EditText parentsPhone2=(EditText) findViewById(R.id.editTextNewLearner4);
        EditText learnersName=(EditText) findViewById(R.id.editTextNewLearner5);
        EditText learnersPhone=(EditText) findViewById(R.id.editTextNewLearner6);
        Button confirmButton=(Button)findViewById(R.id.newLearnerConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=false;
                if ("".equals(parentsName1.getText().toString()) ||
                        !parentsName1.getText().toString().matches("[a-zA-Z| ]+")||
                        parentsName1.getText().toString().matches("[ ]*")) {
                    parentsName1.setText("");
                    parentsName1.setHint("wrong input");
                    parentsName1.setHintTextColor(Color.RED);
                    flag=true;
                }
                if ("".equals(parentsName2.getText().toString()) ||
                        !parentsName2.getText().toString().matches("[a-zA-Z| ]+")||
                parentsName2.getText().toString().matches("[ ]*")) {
                    parentsName2.setText("");
                    parentsName2.setHint("wrong input");
                    parentsName2.setHintTextColor(Color.RED);
                    flag=true;
                }
                if (parentsPhone1.getText().toString().length() != 11 ||
                        !parentsPhone1.getText().toString().matches("[0-9]+")) {
                    parentsPhone1.setText("");
                    parentsPhone1.setHint("wrong input, don't write + ");
                    parentsPhone1.setHintTextColor(Color.RED);
                    flag=true;
                }
                if(parentsPhone2.getText().toString().length() != 11 ||
                        !parentsPhone2.getText().toString().matches("[0-9]+")){
                    parentsPhone2.setText("");
                    parentsPhone2.setHint("wrong input, don't write + ");
                    parentsPhone2.setHintTextColor(Color.RED);
                    flag=true;
                }
                if(learnersName.getText().toString().equals("")||
                        !learnersName.getText().toString().matches("[a-zA-Z| ]+")||
                        learnersName.getText().toString().matches("[ ]*")){
                    learnersName.setText("");
                    learnersName.setHint("wrong input");
                    learnersName.setHintTextColor(Color.RED);
                    flag=true;
                }
                if(learnersPhone.getText().toString().length() != 11||
                        !learnersPhone.getText().toString().matches("[0-9]+")){
                    learnersPhone.setText("");
                    learnersPhone.setHint("wrong input, don't write + ");
                    learnersPhone.setHintTextColor(Color.RED);
                    flag=true;
                }
                if(!flag){
                    makeLearner(parentsName1.getText().toString(), parentsPhone1.getText().toString(),
                            parentsName2.getText().toString(),parentsPhone2.getText().toString(),
                            learnersName.getText().toString(),learnersPhone.getText().toString());
                    Intent i=new Intent(NewLearnerActivity.this,MainActivity.class);
                    i.putExtra("school",school);
                    setResult(RESULT_CANCELED,i);
                    startActivity(i);
                }
            }
        });
}
    void makeLearner(String nameParent1,String phoneParent1,String nameParent2,String phoneParent2,
                 String nameLearner,String phoneLearner){
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(FILE_LEARNERS, MODE_APPEND);
                fos.write((School.num_of_cards+"").getBytes());
                fos.write("\n".getBytes());
                fos.write(nameParent1.getBytes());
                fos.write("\n".getBytes());
                fos.write(phoneParent1.getBytes());
                fos.write("\n".getBytes());
                fos.write(nameParent2.getBytes());
                fos.write("\n".getBytes());
                fos.write(phoneParent2.getBytes());
                fos.write("\n".getBytes());
                fos.write(nameLearner.getBytes());
                fos.write("\n".getBytes());
                fos.write(phoneLearner.getBytes());
                fos.write("\n".getBytes());
                fos.write("----------".getBytes());
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
        Parent parent1=new Parent(nameParent1,phoneParent1);
        Parent parent2=new Parent(nameParent2,phoneParent2);
        Parent [] parents ={parent1,parent2};
        Learner learner = new Learner(nameLearner,phoneLearner,School.num_of_cards,parents);
        School.num_of_cards++;
        for(int i=0;i<school.learners.length;i++){
            if(school.learners[i]==null){
                school.learners[i]=learner;
                break;
            }
        }
    }
}

