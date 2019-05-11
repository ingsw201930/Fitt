package co.edu.javeriana.bittus.fitt.Vista;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.Modelo.Ejercicio;
import co.edu.javeriana.bittus.fitt.R;

public class EjercicioTiempoFragment extends Fragment {
    private FragmentEjercicioRepeticionesListener listener;

    private TextView descripcion;

    private TextView series;
    private Chronometer chrono;


    private ProgressBar seriesPB;
    private ProgressBar segundosPB;


    private EjercicioTiempo ejercicioTiempo;
    private int serie;
    private int segundo = 0;

    private long timeWhenStopped = 0;




    private int estado;

    private static final int CORRIENDO = 0;
    private static final int PAUSADO = 1;

    private boolean ejercicioTerminado = false;

    Thread manejadorEjercicio;

    Activity realizarEntrenaActivity;

    public interface FragmentEjercicioRepeticionesListener {
        void mostrarSiguienteEjercicio();

        void darInstrucciones(String texto);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ejercicioTiempo = (EjercicioTiempo) getArguments().getSerializable("ejercicioTiempo");
        serie = getArguments().getInt("serie");
        return inflater.inflate(R.layout.fragment_ejercicio_tiempo, null, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        TextView descripcion = (TextView) v.findViewById(R.id.descripcionEjercicioTiempo);
        descripcion.setMovementMethod(new ScrollingMovementMethod());

        series = v.findViewById(R.id.tvSerieTiempo);
        chrono = v.findViewById(R.id.tiempoSerie);




        seriesPB = v.findViewById(R.id.pbSeriesTiempo);
        segundosPB = v.findViewById(R.id.pbTiempo);



        seriesPB.setMax(ejercicioTiempo.getSeries());
        seriesPB.setProgress(serie);
        series.setText(serie + "/" + ejercicioTiempo.getSeries());


        segundosPB.setMax(ejercicioTiempo.getTiempo());
        segundosPB.setProgress(0);

        inicializarCronometro(v);
        iniciarEjercicio();


    }

    private void iniciarEjercicio() {
        chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chrono.start();

    }

    private void inicializarCronometro(View v){
        chrono  = (Chronometer) v.findViewById(R.id.tiempoSerie);
        chrono.stop();
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                long segundos = time/1000;
                String t = segundos + " s";
                segundo = (int) segundos;
                chronometer.setText(t);

                segundosPB.setProgress(segundo);



                if (segundos%5 == 0 ){
                    listener.darInstrucciones("Quedan "+(ejercicioTiempo.getTiempo() - segundo) + "segundos");
                }

                if (segundos >= ejercicioTiempo.getTiempo()){
                    listener.mostrarSiguienteEjercicio();
                }

            }
        });
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setText("0 s");

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentEjercicioRepeticionesListener) {
            listener = (FragmentEjercicioRepeticionesListener) context;
            Log.i("Si inicio el listener", "síiii");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }


}
