package co.edu.javeriana.bittus.fitt.Vista.PopUps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.InformacionEjercicioActivity;
import pl.droidsonroids.gif.GifImageView;

public class PopCrearEjercicioEntrenamientoRepeticion extends Activity {

    private ImageButton informacionEjercicioB;
    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView tipoEjercicioT;
    private TextView dificultadEjercicioT;
    private GifImageView gifImageView;

    private  Ejercicio ejercicio;

    private EditText repeticionesT;
    private EditText seriesT;
    private EditText descansoT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_repeticion);

        aceptarButton = (ImageButton)findViewById(R.id.imageButtonAceptar);
        cancelarButton = (ImageButton)findViewById(R.id.imageButtonCancelar);
        informacionEjercicioB = (ImageButton)findViewById(R.id.imageButtonInformacion);
        repeticionesT = (EditText)findViewById(R.id.editTextRepeticiones);
        seriesT = (EditText)findViewById(R.id.editTextSeries);
        descansoT = (EditText)findViewById(R.id.editTextDescansos);

        nombreEjercicioT = findViewById(R.id.textNombreEjercicio3);
        musculosEjercicioT = findViewById(R.id.textMusculosEjercicio3);
        tipoEjercicioT = findViewById(R.id.textTipoEjercicio3);
        dificultadEjercicioT = findViewById(R.id.textDificultadEjercicio3);
        gifImageView = findViewById(R.id.gifEjercicio3);

        Bundle bundle = this.getIntent().getExtras();

        ejercicio = (Ejercicio) bundle.getSerializable(StringsMiguel.LLAVE_EJERCICIO);

        nombreEjercicioT.setText(ejercicio.getNombre());
        musculosEjercicioT.setText(ejercicio.getMusculos());
        tipoEjercicioT.setText(ejercicio.getTipo());
        dificultadEjercicioT.setText(ejercicio.getDificultad());
        Utils.descargarYMostrarGIF(ejercicio.getRutaGIF(),gifImageView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width/1.1), (int) (height/1.5));

        informacionEjercicioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verInfo();
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

    private void verInfo() {
        Intent inten = new Intent(PopCrearEjercicioEntrenamientoRepeticion.this, InformacionEjercicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO,ejercicio);
        inten.putExtras(bundle);

        startActivity(inten);

    }

    private void crearEjercicioSesion() {
        String sRepeticiones = repeticionesT.getText().toString();
        String sSeries = seriesT.getText().toString();
        String sDescansos = descansoT.getText().toString();

        boolean completado = true;

        if(sRepeticiones.isEmpty()){
            repeticionesT.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completado = false;
        }
        if(sSeries.isEmpty()){
            seriesT.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completado = false;
        }
        if(sDescansos.isEmpty()){
            descansoT.setError(StringsMiguel.CAMPO_OBLIGATORIO);
            completado = false;
        }
        if(completado){
            int repeticiones = Integer.parseInt(sRepeticiones);
            int series = Integer.parseInt(sSeries);
            int descansos = Integer.parseInt(sDescansos);

            EjercicioRepeticiones ejercicioRepeticiones = new EjercicioRepeticiones(ejercicio, repeticiones, series,descansos);

            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO_REPETICION, ejercicioRepeticiones);
            intent.putExtras(bundle);


            if (getParent() == null) {
                setResult(Activity.RESULT_OK, intent);
            } else {
                getParent().setResult(Activity.RESULT_OK, intent);
            }
            finish();
        }



    }
}
