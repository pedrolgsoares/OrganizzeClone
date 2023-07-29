package com.pedrolgsoares.organizzeclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pedrolgsoares.organizzeclone.R;

public class PrincipalActivity extends AppCompatActivity {
    private FloatingActionButton morefab, incomefab,outgoingfab;
    private TextView despesastext,receitastext;
    private Boolean isAllFabsVisible;
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
}