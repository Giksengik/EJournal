package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ElectivesActivity extends AppCompatActivity {
        private Button addNewElectiveButton;
        private Button searchButton;
        private EditText searchBoard;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_electives);
        }
}
