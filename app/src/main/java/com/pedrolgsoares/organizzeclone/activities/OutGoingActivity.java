package com.pedrolgsoares.organizzeclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.helper.DataCustom;
import com.pedrolgsoares.organizzeclone.model.Movimentacao;
import com.pedrolgsoares.organizzeclone.model.Usuario;

public class OutGoingActivity extends AppCompatActivity {
    private EditText eTTotalValue;
    private TextInputEditText tiedtData,tiedtCategoria,tiedtDescricao;
    private FloatingActionButton fabSalvar;
    private Movimentacao movimentacao;
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

        // clique para incluir despesas
        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movimentacao = new Movimentacao();
                movimentacao.setValor(Double.parseDouble(eTTotalValue.getText().toString()));

                String data = tiedtData.getText().toString();
                movimentacao.setDescricao(data);

                movimentacao.setCategoria(tiedtCategoria.getText().toString());
                movimentacao.setDescricao(tiedtDescricao.getText().toString());
                movimentacao.setTipo("d");

                movimentacao.salvar(data);
            }
        });
    }
}