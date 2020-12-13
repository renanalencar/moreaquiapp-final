package br.com.renanalencar.moreaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnNovo;
    private Button btnVisualizar;
    private Button btnGravar;
    private Button btnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovo = findViewById(R.id.btnNovo);
        btnVisualizar = findViewById(R.id.btnVisualizar);
        btnGravar = findViewById(R.id.btnGravar);
        btnMapa = findViewById(R.id.btnMapa);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

        btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });

        // configura um listener para invocar uma conexão com o servidor
        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get singleton instance of database
                EstateData estateData = EstateData.getInstance(MainActivity.this);

                // Retorna todos os imoveis do banco de dados SQLite para
                // uma lista
                List<LocationEstate> estates = estateData.getAllEstates();
                // instancia um RecordData para executar a adição de objetos
                // no servidor passando como parametro a lista de imoveis
                // recuperada do SQLite
                new RecordData().execute(estates);

            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAddressesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}