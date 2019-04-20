package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import pl.droidsonroids.gif.GifImageView;

public class PopCrearEjercicioSesionDistancia extends Activity {

    private Button informacionEjercicioB;
    private Button aceptarButton;
    private Button cancelarButton;

    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView tipoEjercicioT;
    private TextView dificultadEjercicioT;
    private GifImageView gifImageView;
    private Ejercicio ejercicio;

    private EditText distanciaT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_distancia);

        informacionEjercicioB = findViewById(R.id.buttonInformacionEjercicio);

        aceptarButton = findViewById(R.id.buttonAceptar);
        cancelarButton = findViewById(R.id.buttonCancelar);
        distanciaT = findViewById(R.id.editText5);

        nombreEjercicioT = findViewById(R.id.textNombreEjercicio);
        musculosEjercicioT = findViewById(R.id.textMusculosEjercicio);
        tipoEjercicioT = findViewById(R.id.textTipoEjercicio);
        dificultadEjercicioT = findViewById(R.id.textDificultadEjercicio);
        gifImageView = findViewById(R.id.gifEjercicio);

        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (Ejercicio) bundle.getSerializable("ejercicio");

        nombreEjercicioT.setText(ejercicio.getNombre());
        musculosEjercicioT.setText(ejercicio.getMusculos());
        tipoEjercicioT.setText(ejercicio.getTipo());
        dificultadEjercicioT.setText(ejercicio.getDificultad());
        Utils.descargarYMostrarGIF(ejercicio.getRutaGIF(),gifImageView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) height/2);


        informacionEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopCrearEjercicioSesionDistancia.this, InformacionEjercicioActivity.class));
            }
        });

        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEjercicioSesion();
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void crearEjercicioSesion() {
        String sDistancia = distanciaT.getText().toString();
        int distancia = Integer.parseInt(sDistancia);

        EjercicioDistancia ejercicioDistancia = new EjercicioDistancia(ejercicio,distancia);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        bundle.putSerializable("ejercicioDistancia",ejercicioDistancia);
        intent.putExtras(bundle);


        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
        finish();

    }






}
