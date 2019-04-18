package co.edu.javeriana.bittus.fitt.Modelo;

public class EjercicioRepeticiones extends EjercicioSesion {

    private int repeticiones;
    private int series;
    private int descanso;

    public EjercicioRepeticiones(Ejercicio ejercicio, int repeticiones, int series, int descanso) {
        super(ejercicio);
        this.repeticiones = repeticiones;
        this.series = series;
        this.descanso = descanso;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }

}
