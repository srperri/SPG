package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.internal.ContextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import unlp.labo.spg.dao.FamiliaDao;
import unlp.labo.spg.dao.QuintaDao;
import unlp.labo.spg.dao.UsuarioDao;
import unlp.labo.spg.dao.VisitaDao;
import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.Familia;
import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.TipoDetalle;
import unlp.labo.spg.model.Usuario;
import unlp.labo.spg.model.Visita;
import unlp.labo.spg.model.VisitaDetalle;

public class LoginActivity extends AppCompatActivity {
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuarioDao = AppDatabase.getInstance(this).usuarioDao();
        if (usuarioDao.empty()) {
            Usuario usuario = new Usuario("admin", "admin");
            usuarioDao.insert(usuario);
        }
        Button btNuevo=findViewById(R.id.btNuevo);
        btNuevo.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, UsuarioEditarActivity.class);
            startActivity(intent);
        });
    }

    public void init_session(View view) {
        EditText etUserName = findViewById(R.id.etUsuario);
        String userName = etUserName.getText().toString();
        if (userName.equalsIgnoreCase("reinit")) {
            reinit_db();
            Toast.makeText(this, "Se Reinicio la Base de Datos", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText editTextPassword = findViewById(R.id.etPassword);
        String password = editTextPassword.getText().toString();
        Usuario usuario = usuarioDao.validate(userName, password);
        if (usuario != null) {
            Intent intent = new Intent(this, VisitasActivity.class);
            intent.putExtra("uid", usuario.getUid());
            SharedPreferences sharedPref = getSharedPreferences("user",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("uid", usuario.getUid());
            editor.commit();
            startActivity(intent);
            // Do something in response to button
            finish();

        } else {
            Toast.makeText(LoginActivity.this, "Usuario/Clave incorrecta.", Toast.LENGTH_SHORT).show();

        }
    }

    private void reinit_db() {
        usuarioDao.deleteAll();
        FamiliaDao familiaDao = AppDatabase.getInstance(this).familiaDao();
        familiaDao.deleteAll();
        Usuario usuario1 = new Usuario("admin", "admin");
        usuario1.setUid(usuarioDao.insert(usuario1));
        Usuario usuario2 = new Usuario("yessica", "yessica");
        usuario2.setUid(usuarioDao.insert(usuario2));
        Usuario usuario3 = new Usuario("claudia", "claudia");
        usuario3.setUid(usuarioDao.insert(usuario3));
        Familia familia1 = new Familia();
        familia1.userId=usuario1.getUid();
        familia1.nombre="Lopez";
        familia1.id = familiaDao.insert(familia1);
        Familia familia2 = new Familia();
        familia2.userId=usuario1.getUid();
        familia2.nombre="Gonzalez";
        familia2.id = familiaDao.insert(familia2);
        Familia familia3 = new Familia();
        familia3.userId=usuario2.getUid();
        familia3.nombre="Rodriguez";
        familia3.id = familiaDao.insert(familia3);
        QuintaDao quintaDao = AppDatabase.getInstance(this).quintaDao();
        Quinta quinta1 = new Quinta();
        quinta1.familiaId = familia1.id;
        quinta1.nombre = "Las acacias";
        quinta1.direccion = "137 y 614 - Arana";
        quinta1.id = quintaDao.insert(quinta1);
        Quinta quinta2 = new Quinta();
        quinta2.familiaId = familia2.id;
        quinta2.nombre = "El Remanso";
        quinta2.direccion = "Ruta 6 Km.87";
        quinta2.id = quintaDao.insert(quinta2);
        Quinta quinta3 = new Quinta();
        quinta3.familiaId = familia2.id;
        quinta3.nombre = "Lo de Claudio";
        quinta3.direccion = "520 y 164 - Abasto";
        quinta3.id = quintaDao.insert(quinta3);
        Quinta quinta4 = new Quinta();
        quinta4.familiaId = familia3.id;
        quinta4.nombre = "La Linda";
        quinta4.direccion = "122 y 615";
        quinta4.id = quintaDao.insert(quinta4);
        VisitaDetalle mVisitaDetalle1 = new VisitaDetalle();
        mVisitaDetalle1.visita = new Visita();
        try {
            mVisitaDetalle1.visita.fecha = new SimpleDateFormat("dd/MM/yyyy").parse("06/01/2022");
        }catch (ParseException e) {
            e.printStackTrace();
        }
        mVisitaDetalle1.visita.quintaId=quinta1.id;
        mVisitaDetalle1.detalles = new ArrayList<>();
        for (TipoDetalle t : TipoDetalle.values()) {
            Detalle mDetalle = new Detalle();
            mDetalle.tipoId = t.id();
            mDetalle.cumple = true;
            mDetalle.observacion = "Observación de " + t.titulo() + " " + t.subtitulo();
            mDetalle.aspiracion = "Aspiración de " + t.titulo() + " " + t.subtitulo();
            mDetalle.sugerencia = "Sugerencia de " + t.titulo() + " " + t.subtitulo();
            mVisitaDetalle1.detalles.add(mDetalle);
        }
        VisitaDao visitaDao = AppDatabase.getInstance(this).visitaDao();
        visitaDao.insertVisitaDetalle(mVisitaDetalle1);
        VisitaDetalle mVisitaDetalle2 = new VisitaDetalle();
        mVisitaDetalle2.visita = new Visita();
        mVisitaDetalle2.visita.quintaId=quinta4.id;
        try {
            mVisitaDetalle2.visita.fecha = new SimpleDateFormat("dd/MM/yyyy").parse( "14/02/2022");
        }catch (ParseException e) {
            e.printStackTrace();
        }
        mVisitaDetalle2.detalles = new ArrayList<>();
        for (TipoDetalle t : TipoDetalle.values()) {
            Detalle mDetalle = new Detalle();
            mDetalle.tipoId = t.id();
            mDetalle.cumple = false;
            mDetalle.observacion = "Obs " + t.id();
            mDetalle.aspiracion = "Asp. " + t.id();
            mDetalle.sugerencia = "Sug. " + t.id();
            mVisitaDetalle2.detalles.add(mDetalle);
        }
        visitaDao.insertVisitaDetalle(mVisitaDetalle2);
    }
}
