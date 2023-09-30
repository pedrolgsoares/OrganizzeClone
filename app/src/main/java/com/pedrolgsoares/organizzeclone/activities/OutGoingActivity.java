package com.pedrolgsoares.organizzeclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.helper.DataCustom;

public class OutGoingActivity extends AppCompatActivity {
    private EditText ETTotalValue;
    private TextInputEditText TiedtData,TiedtCategoria,TiedtDescricao;
    private FloatingActionButton fabSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going);

        ETTotalValue = findViewById(R.id.ETTotalValue);
        TiedtData = findViewById(R.id.TiedtData);
        TiedtCategoria = findViewById(R.id.TiedtCategoria);
        TiedtDescricao = findViewById(R.id.TiedtDescricao);

        // configurando a data atual
        TiedtData.setText(DataCustom.getDataAtual());

    }
}