package co.edu.javeriana.bittus.fitt.Vista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.MisEntrenamientosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDescanso;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioDistancia;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioEntrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioRepeticiones;
import co.edu.javeriana.bittus.fitt.Modelo.EjercicioTiempo;
import co.edu.javeriana.bittus.fitt.Modelo.Entrenamiento;
import co.edu.javeriana.bittus.fitt.Modelo.EntrenamientoAdoptado;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerEntrenamientoRow;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerMisEntrenamientoRow;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;
import co.edu.javeriana.bittus.fitt.Utilidades.StringsMiguel;

public class MisEntrenamientosActivity extends AppCompatActivity {



    private ListView listViewEntrenamientos;
    private MisEntrenamientosAdapter adapter;
    private List<Entrenamiento> entrenamientos;
    private List<Boolean> adoptado;
    private List<Boolean> esMio;

    private String llave = "";
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private String llaveCreador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_entrenamientos);

        listViewEntrenamientos = (ListView) findViewById(R.id.listViewMisEntrenamientos);
        esMio  = new ArrayList<>();

        adoptado = new ArrayList<>();
        entrenamientos = new ArrayList<>();



        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        adapter = new MisEntrenamientosAdapter(this,R.layout.item_entrenamiento_mio_row,entrenamientos, new BtnClickListenerMisEntrenamientoRow() {

            @Override
            public void onBtnClickAdoptarOEliminar(int position) {

            }

            @Override
            public void onBtnClickInfo(int position) {
                obtenerKeys(entrenamientos.get(position));
            }

            @Override
            public void onBtnClickBorrar(int position) {

            }
        }, adoptado);

        listViewEntrenamientos.setAdapter(adapter);
        obtenerEntrenamientos();

    }

    private void mostrarInfo(Entrenamiento entrenamiento) {
        Intent intent = new Intent(MisEntrenamientosActivity.this, InformacionMiEntrenamientoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO, entrenamiento);

        bundle.putSerializable(StringsMiguel.LLAVE_LLAVE,llave );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void obtenerEntrenamientos() {
        myRef = database.getReference(RutasBaseDeDatos.RUTA_ENTRENAMIENTOS+firebaseAuth.getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Entrenamiento entrenamiento = singleSnapshot.getValue(Entrenamiento.class);

                    esMio.add(Boolean.TRUE);

                    entrenamientos.add(entrenamiento);
                    List<EjercicioEntrenamiento> ejercicioEntrenamientos = new ArrayList<>();
                    String llaveEntrenamiento = singleSnapshot.getKey();
                    Log.i("ENTRENAMIENTO_KEY", llaveEntrenamiento);
                    DataSnapshot singleSnapshotE = singleSnapshot.child("ejercicioEntrenamientoList");
                    for(DataSnapshot singleSnapshotF: singleSnapshotE.getChildren()){
                        EjercicioEntrenamiento ejercicioEntrenamiento = singleSnapshotF.getValue(EjercicioEntrenamiento.class);

                        if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_REPETICIÓN)){
                            EjercicioRepeticiones ejercicioRepeticiones = singleSnapshotF.getValue(EjercicioRepeticiones.class);
                            ejercicioEntrenamientos.add(ejercicioRepeticiones);
                        }
                        if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DESCANSO)){
                            EjercicioDescanso ejercicioDescanso = singleSnapshotF.getValue(EjercicioDescanso.class);
                            ejercicioEntrenamientos.add(ejercicioDescanso);
                        }
                        if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_TIEMPO)){
                            EjercicioTiempo ejercicioTiempo = singleSnapshotF.getValue(EjercicioTiempo.class);
                            ejercicioEntrenamientos.add(ejercicioTiempo);
                        }
                        if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DISTANCIA)){
                            EjercicioDistancia ejercicioDistancia = singleSnapshotF.getValue(EjercicioDistancia.class);
                            ejercicioEntrenamientos.add(ejercicioDistancia);
                        }


                    }



                    entrenamiento.setEjercicioEntrenamientoList(ejercicioEntrenamientos);
                    adoptado.add(Boolean.FALSE);
                    adapter.notifyDataSetChanged();
                }
                obtenerEntrenamientosAdoptados();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void obtenerEntrenamientosAdoptados() {
        myRef = database.getReference(RutasBaseDeDatos.getRutaEntrenamientosAdoptados()+firebaseAuth.getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {

                    for(DataSnapshot singleSnapsho2: singleSnapshot.getChildren()){
                        EntrenamientoAdoptado entrenamientoAdoptado = singleSnapsho2.getValue(EntrenamientoAdoptado.class);
                        boolean existe = false;
                        for (Entrenamiento entrenamiento: entrenamientos) {
                            if(entrenamientoAdoptado.getEntrenamiento().getNombre().equals(entrenamiento.getNombre())){
                                existe = true;
                                break;
                            }
                        }
                        if(!existe){
                            esMio.add(Boolean.FALSE);
                            List<EjercicioEntrenamiento> ejercicioEntrenamientos = new ArrayList<>();
                            String llaveEntrenamiento = singleSnapsho2.getKey();
                            Log.i("ENTRENAMIENTO_KEY", llaveEntrenamiento);
                            DataSnapshot singleSnapshotE = singleSnapsho2.child("entrenamiento").child("ejercicioEntrenamientoList");
                            for(DataSnapshot singleSnapshotF: singleSnapshotE.getChildren()){
                                EjercicioEntrenamiento ejercicioEntrenamiento = singleSnapshotF.getValue(EjercicioEntrenamiento.class);

                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_REPETICIÓN)){
                                    EjercicioRepeticiones ejercicioRepeticiones = singleSnapshotF.getValue(EjercicioRepeticiones.class);
                                    ejercicioEntrenamientos.add(ejercicioRepeticiones);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DESCANSO)){
                                    EjercicioDescanso ejercicioDescanso = singleSnapshotF.getValue(EjercicioDescanso.class);
                                    ejercicioEntrenamientos.add(ejercicioDescanso);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_TIEMPO)){
                                    EjercicioTiempo ejercicioTiempo = singleSnapshotF.getValue(EjercicioTiempo.class);
                                    ejercicioEntrenamientos.add(ejercicioTiempo);
                                }
                                if(ejercicioEntrenamiento.getEjercicio().getTipo().equals(StringsMiguel.EJERCICIO_TIPO_DISTANCIA)){
                                    EjercicioDistancia ejercicioDistancia = singleSnapshotF.getValue(EjercicioDistancia.class);
                                    ejercicioEntrenamientos.add(ejercicioDistancia);
                                }


                            }



                            entrenamientoAdoptado.getEntrenamiento().setEjercicioEntrenamientoList(ejercicioEntrenamientos);
                            entrenamientos.add(entrenamientoAdoptado.getEntrenamiento());
                            adoptado.add(Boolean.TRUE);
                            adapter.notifyDataSetChanged();
                        }
                    }



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void obtenerKeys(final Entrenamiento entrenamiento) {


        myRef = database.getReference(RutasBaseDeDatos.RUTA_ENTRENAMIENTOS);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {

                    for (DataSnapshot singleSnapshot2: singleSnapshot.getChildren()) {
                        Entrenamiento entrenamientoE = singleSnapshot2.getValue(Entrenamiento.class);

                        if(entrenamientoE.getNombre().equals(entrenamiento.getNombre())){
                            llaveCreador = singleSnapshot.getKey();
                            llave = singleSnapshot2.getKey();
                            if(singleSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                                mostrarInfo(entrenamiento);
                            }else{
                                mostrarInfoOtro(entrenamiento);
                            }

                        }

                    }


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void mostrarInfoOtro(Entrenamiento entrenamiento) {
        Intent intent = new Intent(MisEntrenamientosActivity.this, InformacionEntrenamientoReseñarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StringsMiguel.LLAVE_ENTRENAMIENTO, entrenamiento);
        bundle.putSerializable(StringsMiguel.LLAVE_USUARIO, llaveCreador);
        bundle.putSerializable(StringsMiguel.LLAVE_LLAVE,llave );
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
