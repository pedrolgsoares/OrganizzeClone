package com.pedrolgsoares.organizzeclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.pedrolgsoares.organizzeclone.MainActivity;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.config.ConfiguracaoFirebase;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class PrincipalActivity extends AppCompatActivity {
    private FloatingActionButton morefab, incomefab,outgoingfab;
    private TextView despesastext,receitastext;
    private Boolean isAllFabsVisible;
    // calendario
    private MaterialCalendarView calendarView;

    private Toolbar toolbar;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
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
        CharSequence charSequence[] = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
        calendarView.setTitleMonths(charSequence);

        // Exibir automaticamente o array contendo os valores
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSair){
            autenticacao.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}