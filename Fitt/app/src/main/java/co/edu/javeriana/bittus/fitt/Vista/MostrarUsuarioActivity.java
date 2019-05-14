package co.edu.javeriana.bittus.fitt.Vista;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.edu.javeriana.bittus.fitt.Modelo.Usuario;

import co.edu.javeriana.bittus.fitt.R;


//Mostrar Usuario que no se sigue
public class MostrarUsuarioActivity extends AppCompatActivity {


    private Usuario item;

    private TextView nombre;
    private Button seguidos;
    private Button seguidores;
    private Button seguir;
    private TextView entrenador;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuario);

        item= (Usuario) getIntent().getSerializableExtra("objectData");

        nombre = findViewById(R.id.textViewNombreUsuarioN);
        nombre.setText(item.getNombre());

        seguidos =findViewById(R.id.buttonSeguidosUN);
        seguidos.setText(item.getSeguidosList().size()+" seguidos");

        seguidores =findViewById(R.id.buttonSeguidoresUN);
        seguidos.setText(item.getSeguidoresList().size()+" seguidores");

        seguir =findViewById(R.id.buttonSeguirUN);

        entrenador =findViewById(R.id.textViewNombreEntrenadorUN);
        //entrenador.setText();
    }

}