package com.pedrolgsoares.organizzeclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
                String valor = eTTotalValue.getText().toString();
                String data = tiedtData.getText().toString();
                String categoria = tiedtCategoria.getText().toString();
                String descricao = tiedtDescricao.getText().toString();
                validaCampo(valor,data,categoria,descricao);


            }
        });
    }

    public boolean validaCampo(String valor,String dat,String cate,String descr){
        if (valor.isEmpty()){
            Toast.makeText(OutGoingActivity.this,"Campo  valor não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (dat.isEmpty()){
            Toast.makeText(OutGoingActivity.this,"Campo  data não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (cate.isEmpty()){
            Toast.makeText(OutGoingActivity.this,"Campo  categoria não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (descr.isEmpty()){
            Toast.makeText(OutGoingActivity.this,"Campo  descrição não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            movimentacao = new Movimentacao();
            movimentacao.setValor(Double.parseDouble(valor));

            String data = tiedtData.getText().toString();
            movimentacao.setData(data);

            movimentacao.setCategoria(tiedtCategoria.getText().toString());
            movimentacao.setDescricao(tiedtDescricao.getText().toString());
            movimentacao.setTipo("d");
            movimentacao.salvar(data);
            Toast.makeText(OutGoingActivity.this,"Despesa adicionada a lista!",Toast.LENGTH_SHORT).show();
            return true;

        }
    }
}