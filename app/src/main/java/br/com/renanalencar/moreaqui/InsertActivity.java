package br.com.renanalencar.moreaqui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.AlertDialog;


public class InsertActivity extends AppCompatActivity {

    private EditText edtTelefone;
    private RadioGroup rgTamanho;
    private RadioGroup rgTipo;
    private CheckBox cbStatus;
    private Button btnInserir;

    String tamanho, tipo, status, telefone;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edtTelefone = findViewById(R.id.edtTelefone);
        rgTamanho = findViewById(R.id.rgTamanho);
        rgTipo = findViewById(R.id.rgTipo);
        cbStatus = findViewById(R.id.cbStatus);
        btnInserir = findViewById(R.id.btnInserir);

        edtTelefone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, edtTelefone));

        rgTamanho.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbPequeno:
                        tamanho = "Pequeno";
                        break;
                    case R.id.rbMedio:
                        tamanho = "Médio";
                        break;
                    case R.id.rbGrande:
                        tamanho = "Grande";
                        break;
                    default:
                        tamanho = "Não Sei";
                }
            }
        });

        rgTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCasa:
                        tipo = "Casa";
                        break;
                    case R.id.rbApartamento:
                        tipo = "Apartamento";
                        break;
                    case R.id.rbLoja:
                        tipo = "Loja";
                        break;
                    default:
                        tipo = "Não Sei";
                }
            }
        });

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                telefone = edtTelefone.getText().toString();

                status = cbStatus.isChecked() ? "Em Construção" : "Pronto";

                if (!validTelephone(edtTelefone.getText().toString())) {
                    Toast.makeText(InsertActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        OnGPS();
                    } else {
                        getLocation();
                    }

                    LocationEstate estate = new LocationEstate(tipo, tamanho, telefone, status,
                            latitude, longitude);

                    Log.v("Type", estate.TYPE);
                    Log.v("Size", estate.SIZE);
                    Log.v("Phone", String.valueOf(estate.PHONE));
                    Log.v("Lat", String.valueOf(estate.LATITUDE));
                    Log.v("Lon", String.valueOf(estate.LONGITUDE));

                    // Get singleton instance of database
                    EstateData databaseHelper = EstateData.getInstance(InsertActivity.this);

                    // Add sample post to the database
                    databaseHelper.addEstate(estate);

                    Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(InsertActivity.this, getString(R.string.msgInsertedSuccessful), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        
    }

    public boolean validTelephone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msgEnableGPS)).setCancelable(false).setPositiveButton(getString(R.string.yes), new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                InsertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                InsertActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = lat;
                longitude = longi;
            } else {
                Toast.makeText(this, getString(R.string.unableLocation), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InsertActivity.this, MainActivity.class);
        startActivity(intent);
    }
}