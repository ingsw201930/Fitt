package co.edu.javeriana.bittus.fitt.Modelo;

import java.io.Serializable;

public class EjercicioDuracion extends EjercicioSesion implements Serializable {

    private int duracion;


    public EjercicioDuracion(Ejercicio ejercicio, int duracion) {
        super(ejercicio);
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
