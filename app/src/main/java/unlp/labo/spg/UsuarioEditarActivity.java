package unlp.labo.spg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Miembro;
import unlp.labo.spg.model.Rol;
import unlp.labo.spg.model.Usuario;
import unlp.labo.spg.model.UsuarioMiembros;

public class UsuarioEditarActivity extends AppCompatActivity implements MiembroAdapter.ItemClickListener {

    MiembroAdapter mAdapter;
    RecyclerView mRecyclerView;
    AppDatabase db;
    UsuarioMiembros mUsuarioMiembros;
    long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_usuario_editar);
        uid = getIntent().getLongExtra(Intent.EXTRA_UID, 0);
        db = AppDatabase.getInstance(this.getApplication());
        mRecyclerView = findViewById(R.id.rvMiembros);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button btAgregar = findViewById(R.id.btAgregar);
        btAgregar.setOnClickListener(view -> {
            agregarMiembro();
        });
        Button btGuardar = findViewById(R.id.btGuardar);
        btGuardar.setOnClickListener(view -> {
            guardarUsuario();
        });
        if (uid != 0) {
            mUsuarioMiembros = db.usuarioDao().getUsuarioMiembrosByUid(uid);
        } else {
            mUsuarioMiembros = new UsuarioMiembros();
            mUsuarioMiembros.usuario = new Usuario("", "");
            mUsuarioMiembros.miembros = new ArrayList<>();
        }
        EditText etNombre = findViewById(R.id.etUsuario);
        etNombre.setText(mUsuarioMiembros.usuario.getNombre());
        EditText etPassword = findViewById(R.id.etPassword);
        etPassword.setText(mUsuarioMiembros.usuario.getPassword());
        EditText etPasswordR = findViewById(R.id.etRepetirPassword);
        etPasswordR.setText(mUsuarioMiembros.usuario.getPassword());
        etPasswordR.setImeOptions(EditorInfo.IME_ACTION_GO);
    }


    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new MiembroAdapter(this, mUsuarioMiembros.miembros);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemDeleteClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma que desea eliminar el miembro?")
                .setTitle("Alerta")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mUsuarioMiembros.miembros.remove(mAdapter.getItem(position));
//                            mUdb.visitaDao().deleteById(mAdapter.getItem(position).visita.id);
                        onResume();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                    }
                });
        AlertDialog mAlert = builder.create();
        mAlert.show();
        Toast.makeText(this, "Borrar miembro a " + mAdapter.getItem(position).apellido + "?", Toast.LENGTH_SHORT).show();
    }

    public void agregarMiembro() {
        Miembro mMiembro = new Miembro();
        mMiembro.apellido = "";
        mMiembro.nombre = "";
        mMiembro.rol = Rol.CONSUMIDOR;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_miembro_nuevo, null);
        RadioGroup rgRoles = mView.findViewById(R.id.radioGroupRol);
        for (Rol r : Rol.values()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(r.descripcion());
            rb.setOnClickListener(view -> {
                mMiembro.rol = r;
            });
            rgRoles.addView(rb);
            if (r == mMiembro.rol) rb.setChecked(true);
        }

        builder.setView(mView)
                .setTitle("Nuevo Miembro")
                .setNegativeButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mMiembro.nombre = ((EditText) mView.findViewById(R.id.editTextMiembroNombre)).getText().toString();
                        mMiembro.apellido = ((EditText) mView.findViewById(R.id.editTextMiembroApellido)).getText().toString();
                        mUsuarioMiembros.miembros.add(mMiembro);
                        Toast.makeText(getApplicationContext(), "Se agregó el miembro " + mMiembro.apellido + ", " + mMiembro.nombre + ".", Toast.LENGTH_SHORT).show();
                        onResume();

                    }
                });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }

    public void guardarUsuario() {
        String mNombre=((EditText) findViewById(R.id.etUsuario)).getText().toString();
        String mPassword=((EditText) findViewById(R.id.etPassword)).getText().toString();
        String mPasswordR=((EditText) findViewById(R.id.etRepetirPassword)).getText().toString();
        if (mNombre.isEmpty()){
            Toast.makeText(this, "Ingrese una nombre de usuario.", Toast.LENGTH_SHORT).show();
            findViewById(R.id.etUsuario).requestFocus();
            return;
        }
        if (mPassword.isEmpty()){
            Toast.makeText(this, "Ingrese una contraseña.", Toast.LENGTH_SHORT).show();
            findViewById(R.id.etPassword).requestFocus();
            return;
        }

        if (!mPassword.equals(mPasswordR)){
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            findViewById(R.id.etPassword).requestFocus();
            return;
        }
        mUsuarioMiembros.usuario.setNombre(mNombre);
        mUsuarioMiembros.usuario.setPassword(mPassword);
        db.usuarioDao().insertUsuarioMiembros(mUsuarioMiembros);
        finish();
    }
}