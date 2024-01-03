package com.pedrolgsoares.organizzeclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pedrolgsoares.organizzeclone.MainActivity;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.adapter.MovimentacaoAdapter;
import com.pedrolgsoares.organizzeclone.config.ConfiguracaoFirebase;
import com.pedrolgsoares.organizzeclone.helper.Base64Custom;
import com.pedrolgsoares.organizzeclone.model.Movimentacao;
import com.pedrolgsoares.organizzeclone.model.Usuario;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.EventListener;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    private FloatingActionButton morefab, incomefab,outgoingfab;
    private TextView despesastext,receitastext,eTSaudacao,eTValorGeral;
    private Boolean isAllFabsVisible;
    // calendario
    private MaterialCalendarView calendarView;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
    private DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebaseDatabase();
    // mudando os eventlistiner
    private DatabaseReference dbrUsuario;
    private ValueEventListener valueEventListenerUsuario;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double valorGeral = 0.00;

    private RecyclerView recyclerView;
    private MovimentacaoAdapter movimentacaoAdapter;
    private List<Movimentacao> movimentacaoList = new ArrayList<>();
    private String mes;
    private DatabaseReference dbrMovimentacao;
    private ValueEventListener valueEventListenerMovimentacao;
    private Movimentacao movimentacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        morefab = findViewById(R.id.morefab);
        incomefab = findViewById(R.id.incomefab);
        outgoingfab = findViewById(R.id.outgoingfab);

        despesastext = findViewById(R.id.despesastext);
        receitastext = findViewById(R.id.receitastext);


        incomefab.setVisibility(View.GONE);
        outgoingfab.setVisibility(View.GONE);

        despesastext.setVisibility(View.GONE);
        receitastext.setVisibility(View.GONE);

        isAllFabsVisible = false;
        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Calendario
        calendarView = findViewById(R.id.calendarView);
        setupCalendario();

        //Campos das informações
        eTSaudacao = findViewById(R.id.eTSaudacao);
        eTValorGeral = findViewById(R.id.eTValorGeral);

        //Configurando o adapter e o recyclerview
        recyclerView = findViewById(R.id.recyclerViewMovi);
        //Configurar Adapter
        movimentacaoAdapter = new MovimentacaoAdapter(movimentacaoList,this);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(movimentacaoAdapter);

        swipe();

        morefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAllFabsVisible) {
                    incomefab.show();
                    outgoingfab.show();
                    despesastext.setVisibility(View.VISIBLE);
                    receitastext.setVisibility(View.VISIBLE);
                    isAllFabsVisible = true;
                } else {
                    incomefab.hide();
                    outgoingfab.hide();
                    despesastext.setVisibility(View.GONE);
                    receitastext.setVisibility(View.GONE);
                    isAllFabsVisible = false;
                }
            }
        });
    }

    public void abreReceitas(View view){
        startActivity(new Intent(this, IncomeActivity.class));
    }

    public void abreDespesas(View view){
        startActivity(new Intent(this, OutGoingActivity.class));
    }

    public void setupCalendario(){
        CharSequence charSequence[] = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};  // Exibir automaticamente o array contendo os valores
        calendarView.setTitleMonths(charSequence);
        CalendarDay calendarDay = calendarView.getCurrentDate(); // atual
        String mesEscolhido =  String.format("%02d",(calendarDay.getMonth() + 1));

        mes = String.valueOf(mesEscolhido +"" + calendarDay.getYear()); // converte o atual em string
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mesEscolhido =  String.format("%02d",(date.getMonth() + 1));
                mes = String.valueOf(mesEscolhido +"" + date.getYear()); // converte o atual em string

                Log.i("MES SELECIONADO","mes: "+mes);
                dbrMovimentacao.removeEventListener(valueEventListenerMovimentacao);
                getDadosValores();
            }
        });
    }

    public void getInfo(){
        // acessa email do usuário para converter e entrar no nó seguindo a regra do firebase
        String email = firebaseAuth.getCurrentUser().getEmail();
        String idEmail = Base64Custom.codificarBase64(email);

        // acessa nó
        dbrUsuario = databaseReference.child("usuarios").child(idEmail);

        valueEventListenerUsuario = dbrUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue(Usuario.class); // tenho acesso ao json do objeto usuario

                eTSaudacao.setText("Olá, "+usuario.getNome());
                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                valorGeral = receitaTotal - despesaTotal;
                // Formato decimal de texto
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String resultado = decimalFormat.format(valorGeral);
                eTValorGeral.setText(resultado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getDadosValores(){
        // acessa email do usuário para converter e entrar no nó seguindo a regra do firebase
        String email = firebaseAuth.getCurrentUser().getEmail();
        String idEmail = Base64Custom.codificarBase64(email);
        dbrMovimentacao = databaseReference.child("movimentacao").child(idEmail).child(mes);

        valueEventListenerMovimentacao = dbrMovimentacao.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                movimentacaoList.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren() ){

                    Movimentacao movimentacao = dados.getValue( Movimentacao.class );
                    movimentacao.setKeyValue(dados.getKey());
                    Log.i("LOGO DO JSON","AQUI: "+dados.getValue( Movimentacao.class ).getDescricao());
                    Log.i("LOGO DO JSON","KEYVALUE: "+movimentacao.getKeyValue());
                    movimentacaoList.add( movimentacao );

                }

                movimentacaoAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void swipe(){
        int dragDirs = ItemTouchHelper.ACTION_STATE_IDLE;
        int swipeDirs = ItemTouchHelper.START | ItemTouchHelper.END;
       ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(dragDirs,swipeDirs) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.i("DESLIZAR: ","FOI");
               deleteMovi(viewHolder);
           }
       };
       new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

    }
    public void deleteMovi(RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("TESTE").setTitle("TESTE2");

        alertDialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // captura a posição do item
                int posicacoItem = viewHolder.getAdapterPosition();
                movimentacao = movimentacaoList.get(posicacoItem);
                Log.i("PEGUEI AQUI",movimentacao.getKeyValue());

                // acessa email do usuário para converter e entrar no nó seguindo a regra do firebase
                String email = firebaseAuth.getCurrentUser().getEmail();
                String idEmail = Base64Custom.codificarBase64(email);
                dbrMovimentacao = databaseReference.child("movimentacao").child(idEmail).child(mes);

                dbrMovimentacao.child(movimentacao.getKeyValue()).removeValue();
                movimentacaoAdapter.notifyItemRemoved(posicacoItem);

            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                movimentacaoAdapter.notifyDataSetChanged();

            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSair){
            firebaseAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getInfo();
        getDadosValores();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbrUsuario.removeEventListener(valueEventListenerUsuario);
        dbrMovimentacao.removeEventListener(valueEventListenerMovimentacao);
    }
}
