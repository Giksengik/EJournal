package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PersonsActivity extends AppCompatActivity {
    School school;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_persons);
            Button buttonAddPerson= (Button)findViewById(R.id.buttonAddPerson);
            Intent mIntent = getIntent();
            school=(School)mIntent.getSerializableExtra("school");
            buttonAddPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i;
                    i = new Intent(PersonsActivity.this, AddPersonActivity.class);
                    i.putExtra("school",school);
                    startActivityForResult(i,1);
                    startActivity(i);
                }
            });
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        school =(School)data.getSerializableExtra("school");
        Intent i = new Intent();
        i.putExtra("school",school);
        setResult(RESULT_CANCELED,i);
        finish();
    }
    }


