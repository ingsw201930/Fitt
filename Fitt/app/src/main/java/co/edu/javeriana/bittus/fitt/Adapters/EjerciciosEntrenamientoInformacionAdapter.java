package co.edu.javeriana.bittus.fitt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerRow;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;


public class EjerciciosEntrenamientoInformacionAdapter extends ArrayAdapter<EjercicioEntrenamiento> {


    protected List<EjercicioEntrenamiento> listEjercios;
    protected Context context;
    private EjercicioEntrenamiento ejercicioEntrenamiento;



    public EjerciciosEntrenamientoInformacionAdapter(@NonNull Context context, List<EjercicioEntrenamiento> objects) {
        super(context, R.layout.item_ejercicio_tiempo_info_row, objects);
        this.listEjercios = objects;
        this.context = context;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(listEjercios.get(position).getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DISTANCIA)){
            return getViewEjercicioDistancia(position,convertView, parent);
        }
        if(listEjercios.get(position).getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_TIEMPO)){
            return getViewEjercicioTiempo(position,convertView, parent);
        }
        if(listEjercios.get(position).getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DESCANSO)){
            return getViewEjercicioDescanso(position,convertView, parent);
        }
        if(listEjercios.get(position).getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_REPETICIÓN)){
            return getViewEjercicioRepeticiones(position,convertView, parent);
        }
        return  convertView;
    }

    private View getViewEjercicioDescanso(int position, View view, ViewGroup parent) {


        view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_descanso_info_row, null);

        ejercicioEntrenamiento = listEjercios.get(position);
        EjercicioDescanso ejercicio = (EjercicioDescanso) ejercicioEntrenamiento;

        TextView nombre = (TextView) view.findViewById(R.id.textNombreEjercicioDuracion);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView duracion = (TextView) view.findViewById(R.id.textDuracionEjercicioDuracion);
        String duracionS = Integer.toString(ejercicio.getDuracion());
        duracion.setText(duracionS + "s");


        return view;


    }

    private View getViewEjercicioRepeticiones(int position, View view, ViewGroup parent) {


        view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_repeticion_info_row, null);

        ejercicioEntrenamiento = listEjercios.get(position);
        EjercicioRepeticiones ejercicio = (EjercicioRepeticiones) ejercicioEntrenamiento;

        TextView nombre = (TextView) view.findViewById(R.id.textNombreEjercicioRepeticion);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView repeticiones = (TextView)view.findViewById(R.id.textEjercicioRepeticiones);
        String repeticionS = Integer.toString(ejercicio.getRepeticiones());
        repeticiones.setText(repeticionS);


        TextView series = (TextView)view.findViewById(R.id.textEjercicioSeries);
        String serieS = Integer.toString(ejercicio.getSeries());
        series.setText(serieS);

        TextView descanso = (TextView)view.findViewById(R.id.textEjercicioDescanso);
        String descansoS = Integer.toString(ejercicio.getDescanso());
        descanso.setText(descansoS+"s");


        return view;

    }



    private View getViewEjercicioTiempo(int position, View view, ViewGroup parent) {


        view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_tiempo_info_row, null);

        ejercicioEntrenamiento = listEjercios.get(position);
        EjercicioTiempo ejercicio = (EjercicioTiempo) ejercicioEntrenamiento;

        TextView nombre = (TextView) view.findViewById(R.id.textNombreEjercicioTiempo);
        nombre.setText(ejercicio.getEjercicio().getNombre());

        TextView duracion = (TextView) view.findViewById(R.id.textEjercicioTiempo);
        String duracionS = Integer.toString(ejercicio.getTiempo());
        duracion.setText(duracionS + "s");


        TextView series = (TextView)view.findViewById(R.id.textEjercicioSeries);
        String serieS = Integer.toString(ejercicio.getSeries());
        series.setText(serieS);

        TextView descanso = (TextView)view.findViewById(R.id.textEjercicioDescanso);
        String descansoS = Integer.toString(ejercicio.getDescanso());
        descanso.setText(descansoS+"s");



        return view;
    }



    private View getViewEjercicioDistancia(int position, View view, ViewGroup parent) {


        view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio_distancia_nuevo_row, null);

        ejercicioEntrenamiento = listEjercios.get(position);
        EjercicioDistancia ejercicio = (EjercicioDistancia) ejercicioEntrenamiento;

        TextView nombre = (TextView) view.findViewById(R.id.textNombreEjercicioDistancia);
        nombre.setText(ejercicio.getEjercicio().getNombre());


        TextView distancia = (TextView) view.findViewById(R.id.textDistanciaEjercicioDistancia);
        String distanciaS = Integer.toString(ejercicio.getDistancia());
        distancia.setText(distanciaS + "mts");


        return view;
    }




}
