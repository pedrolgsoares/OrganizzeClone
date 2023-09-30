package com.pedrolgsoares.organizzeclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.helper.DataCustom;

public class OutGoingActivity extends AppCompatActivity {
    private EditText eTTotalValue;
    private TextInputEditText tiedtData,tiedtCategoria,tiedtDescricao;
    private FloatingActionButton fabSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going);

        eTTotalValue   = findViewById(R.id.eTTotalValue);
        tiedtData      = findViewById(R.id.tiedtData);
        tiedtCategoria = findViewById(R.id.tiedtCategoria);
        tiedtDescricao = findViewById(R.id.tiedtDescricao);
        fabSalvar      = findViewById(R.id.fabSalvar);

        // configurando a data atual
        tiedtData.setText(DataCustom.getDataAtual());

    }
}