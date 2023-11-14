package com.pedrolgsoares.organizzeclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.config.ConfiguracaoFirebase;
import com.pedrolgsoares.organizzeclone.helper.Base64Custom;
import com.pedrolgsoares.organizzeclone.helper.DataCustom;
import com.pedrolgsoares.organizzeclone.model.Movimentacao;
import com.pedrolgsoares.organizzeclone.model.Usuario;

public class IncomeActivity extends AppCompatActivity {
    private EditText eTTotalValue;
    private TextInputEditText tiedtData,tiedtCategoria,tiedtDescricao;
    private FloatingActionButton fabSalvar;
    private Movimentacao movimentacao;
    // .getFirebaseDataBase => pode recuperar informações do banco
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
    // getAutenticacao() => pode recuperar informações do usuario logado no firebase
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
    // variavel da despesa total
    private Double receitaT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        eTTotalValue   = findViewById(R.id.eTTotalValue);
        tiedtData      = findViewById(R.id.tiedtData);
        tiedtCategoria = findViewById(R.id.tiedtCategoria);
        tiedtDescricao = findViewById(R.id.tiedtDescricao);
        fabSalvar      = findViewById(R.id.fabSalvar);

        // configurando a data atual
        tiedtData.setText(DataCustom.getDataAtual());
        recupeReceiTotal();

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
            Toast.makeText(IncomeActivity.this,"Campo  valor não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (dat.isEmpty()){
            Toast.makeText(IncomeActivity.this,"Campo  data não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (cate.isEmpty()){
            Toast.makeText(IncomeActivity.this,"Campo  categoria não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (descr.isEmpty()){
            Toast.makeText(IncomeActivity.this,"Campo  descrição não preenchido !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            movimentacao = new Movimentacao();
            Double valorEntrada = Double.parseDouble(valor);
            movimentacao.setValor(valorEntrada);
            movimentacao.setData(dat);
            movimentacao.setCategoria(cate);
            movimentacao.setDescricao(descr);
            movimentacao.setTipo("r");
            // valor para atualizacao da receita(apos) = despesatotal(atualmente) + valor
            Double valorParaAtualizar = receitaT + valorEntrada;
            atualizarReceitaTotal( valorParaAtualizar );
            movimentacao.salvar(dat);
            Toast.makeText(IncomeActivity.this,"Receita adicionada a lista!",Toast.LENGTH_SHORT).show();
            return true;

        }
    }

    // recupera receitaTotal
    public void recupeReceiTotal(){
        // acessa email do usuário para converter e entrar no nó seguindo a regra do firebase
        String email = firebaseAuth.getCurrentUser().getEmail();
        String idEmail = Base64Custom.codificarBase64(email);

        // acessa nó
        DatabaseReference dbrUsuario = databaseReference.child("usuarios").child(idEmail);

        dbrUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // transformar o objeto do firebase
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaT = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void atualizarReceitaTotal( Double valorParaAtt){
        // acessa email do usuário para converter e entrar no nó seguindo a regra do firebase
        String email = firebaseAuth.getCurrentUser().getEmail();
        String idEmail = Base64Custom.codificarBase64(email);

        // acessa nó
        DatabaseReference dbrUsuario = databaseReference.child("usuarios").child(idEmail);
        dbrUsuario.child("receitaTotal").setValue(valorParaAtt);
    }

}