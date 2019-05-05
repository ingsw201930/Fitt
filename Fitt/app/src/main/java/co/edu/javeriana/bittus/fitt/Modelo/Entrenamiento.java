package co.edu.javeriana.bittus.fitt.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Entrenamiento {

    private int ID;
    private int numDiasDescanso;
    private String descripcion;
    private String dificultad;
    private boolean publica;
    private String nombre;
    private int frecuencia;
    private List<EjercicioEntrenamiento> ejercicioEntrenamientoList;

    public Entrenamiento(int ID, int numDiasDescanso, String descripcion, String dificultad, boolean publica, String nombre, int frecuencia) {
        this.ID = ID;
        this.numDiasDescanso = numDiasDescanso;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.publica = publica;
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        ejercicioEntrenamientoList = new ArrayList<EjercicioEntrenamiento>();
    }
    public Entrenamiento() {super();}

    public List<EjercicioEntrenamiento> getEjercicioEntrenamientoList() {
        return ejercicioEntrenamientoList;
    }

    public void setEjercicioEntrenamientoList(List<EjercicioEntrenamiento> ejercicioEntrenamientoList) {
        this.ejercicioEntrenamientoList = ejercicioEntrenamientoList;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNumDiasDescanso() {
        return numDiasDescanso;
    }

    public void setNumDiasDescanso(int numDiasDescanso) {
        this.numDiasDescanso = numDiasDescanso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}
