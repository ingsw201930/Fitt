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
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;
import co.edu.javeriana.bittus.fitt.Utilidades.Utils;
import co.edu.javeriana.bittus.fitt.Vista.InformacionEjercicioActivity;
import pl.droidsonroids.gif.GifImageView;

public class PopCrearEjercicioEntrenamientoTiempo extends Activity {

    private ImageButton informacionEjercicioB;
    private ImageButton aceptarButton;
    private ImageButton cancelarButton;

    private TextView nombreEjercicioT;
    private TextView musculosEjercicioT;
    private TextView tipoEjercicioT;
    private TextView dificultadEjercicioT;
    private GifImageView gifImageView;

    private Ejercicio ejercicio;

    private EditText duracionT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_agregar_ejercicio_sesion_duracion);

        aceptarButton = (ImageButton)findViewById(R.id.buttonAceptar3);
        cancelarButton = (ImageButton)findViewById(R.id.buttonCancelar3);
        informacionEjercicioB = (ImageButton)findViewById(R.id.buttonInformacionEjercicio2);
        duracionT = (EditText)findViewById(R.id.editTextDuracion2);

        nombreEjercicioT = (TextView)findViewById(R.id.textNombreEjercicio2);
        musculosEjercicioT = (TextView)findViewById(R.id.textMusculosEjercicio2);
        tipoEjercicioT = (TextView)findViewById(R.id.textTipoEjercicio2);
        dificultadEjercicioT = (TextView)findViewById(R.id.textDificultadEjercicio2);
        gifImageView = (GifImageView) findViewById(R.id.gifEjercicio2);

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
        Intent inten = new Intent(PopCrearEjercicioEntrenamientoTiempo.this, InformacionEjercicioActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO,ejercicio);
        inten.putExtras(bundle);

        startActivity(inten);

    }

        private void crearEjercicioSesion() {
        String sDuracion = duracionT.getText().toString();

        if(sDuracion.isEmpty()){
                duracionT.setError(StringsMiguel.CAMPO_OBLIGATORIO);
        }else {
            int duracion = Integer.parseInt(sDuracion);

            EjercicioDescanso ejercicioDescanso = new EjercicioDescanso(ejercicio,duracion);

            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putSerializable(StringsMiguel.LLAVE_EJERCICIO_ENTRENAMIENTO_TIEMPO, ejercicioDescanso);
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