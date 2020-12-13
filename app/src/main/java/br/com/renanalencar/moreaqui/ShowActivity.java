package br.com.renanalencar.moreaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private ListView lstImoveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Get singleton instance of database
        EstateData estateData = EstateData.getInstance(ShowActivity.this);

        // Get all posts from database
        List<LocationEstate> estates = estateData.getAllEstates();

        lstImoveis = findViewById(R.id.lstImoveis);

        ArrayAdapter<LocationEstate> adapter = new ArrayAdapter<>(ShowActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, estates);

        lstImoveis.setAdapter(adapter);

        lstImoveis.setOnTouchListener(new OnSwipeTouchListener(ShowActivity.this) {
            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(ShowActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShowActivity.this, MainActivity.class);
        startActivity(intent);
    }
}