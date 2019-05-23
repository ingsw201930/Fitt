package co.edu.javeriana.bittus.fitt.Vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.util.Log;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.bittus.fitt.Adapters.UsuariosAdapter;
import co.edu.javeriana.bittus.fitt.Modelo.Usuario;
import co.edu.javeriana.bittus.fitt.R;
import co.edu.javeriana.bittus.fitt.Utilidades.BtnClickListenerSeguir;
import co.edu.javeriana.bittus.fitt.Utilidades.RutasBaseDeDatos;

public class SeguidosActivity extends AppCompatActivity implements TextWatcher  {

    private ListView listViewUsuarios;
    private UsuariosAdapter adapterUsuarios;
    private List<Usuario> listUsuarios;
    private List<String> listIDUsuarios;
    private ImageButton ImageButtonBuscarUsuarios;
    private EditText EditTextNombreUsuarioABuscar;

    private Usuario item;
    private String idUsConectado;
    private Usuario usuarioSeleccionado;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_seguidos);

        item= (Usuario) getIntent().getSerializableExtra("usuario");
        idUsConectado= (String) getIntent().getSerializableExtra("id");

        listViewUsuarios = (ListView)findViewById(R.id.listUsuariosBuscar);
        listUsuarios = new ArrayList<Usuario>();
        ImageButtonBuscarUsuarios = (ImageButton) findViewById(R.id.imageButtonBuscarUsuarioSeguido);
        EditTextNombreUsuarioABuscar = (EditText) findViewById(R.id.editTextUsuarioSeguido);

        EditTextNombreUsuarioABuscar.addTextChangedListener((TextWatcher) this);

        if(item.getSeguidosList()!=null) {

            descargarUsuarios();
            adapterUsuarios = new UsuariosAdapter(SeguidosActivity.this, R.layout.item_usuario_row, listUsuarios);

            listViewUsuarios.setAdapter(adapterUsuarios);
            listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    usuarioSeleccionado = listUsuarios.get(position);
                    //abrirPopUp();
                }
            });

            database = FirebaseDatabase.getInstance();



            ImageButtonBuscarUsuarios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else{
            Toast.makeText(SeguidosActivity.this, "No sigues a ningún usuario", Toast.LENGTH_SHORT).show();
        }

    }

    private void descargarUsuarios (){

        for(int i=0;i<item.getSeguidosList().size();i++){
            myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS+item.getSeguidosList().get(i));
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            listUsuarios.add(dataSnapshot.getValue(Usuario.class));
                    if(listUsuarios!=null)
                    {
                        adapterUsuarios.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("Error:", "Error en la consulta", databaseError.toException());
                }
            });
        }

        /*myRef = database.getReference(RutasBaseDeDatos.RUTA_USUARIOS);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if(estaEnLista(dataSnapshot.getKey())) {
                        listUsuarios.add(singleSnapshot.getValue(Usuario.class));
                    }
                }
                adapterUsuarios.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Error:", "Error en la consulta", databaseError.toException());
            }
        });*/
    }

    public boolean estaEnLista(String id){
        for(String ids:item.getSeguidosList()){
            if(ids.compareTo(id)==0){
                return true;
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.adapterUsuarios.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}