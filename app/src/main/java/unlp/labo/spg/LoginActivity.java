package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import unlp.labo.spg.dao.FamiliaDao;
import unlp.labo.spg.dao.QuintaDao;
import unlp.labo.spg.dao.UserDao;
import unlp.labo.spg.dao.VisitaDao;
import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.Familia;
import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.TipoDetalle;
import unlp.labo.spg.model.User;
import unlp.labo.spg.model.Visita;
import unlp.labo.spg.model.VisitaDetalle;

public class LoginActivity extends AppCompatActivity {
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDao = AppDatabase.getInstance(this).userDao();
        if (userDao.empty()) {
            User user = new User("admin", "admin");
            userDao.insert(user);
        }
    }

    public void init_session(View view) {
        EditText etUserName = (EditText) findViewById(R.id.editTextUser);
        String userName = etUserName.getText().toString();
        if (userName == "reinit") {
            reinit_db();
            Toast.makeText(this, "Se Reinicio la Base de Datos", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        String password = editTextPassword.getText().toString();
        User user = userDao.validate(userName, password);
        if (user != null) {
            Intent intent = new Intent(this, VisitaElegirActivity.class);
            intent.putExtra(Intent.EXTRA_UID, user.getUid());
            startActivity(intent);
            // Do something in response to button
            finish();

        } else {
            Toast.makeText(LoginActivity.this, "Usuario/Clave incorrecta.", Toast.LENGTH_SHORT).show();

        }
    }

    private void reinit_db() {
        userDao.deleteAll();
        FamiliaDao familiaDao = AppDatabase.getInstance(this).familiaDao();
        familiaDao.deleteAll();
        User user1 = new User("admin", "admin");
        user1.setUid(userDao.insert(user1));
        User user2 = new User("yessica", "yessica");
        user2.setUid(userDao.insert(user2));
        User user3 = new User("claudia", "claudia");
        user3.setUid(userDao.insert(user3));
        Familia familia1 = new Familia("Lopez");
        familia1.id = familiaDao.insert(familia1);
        Familia familia2 = new Familia("Gonzalez");
        familia2.id = familiaDao.insert(familia2);
        Familia familia3 = new Familia("Rodriguez");
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
        mVisitaDetalle1.visita.userId = user2.getUid();
        mVisitaDetalle1.visita.fecha = "06/01/2022";
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
        mVisitaDetalle2.visita.userId = user2.getUid();
        mVisitaDetalle2.visita.fecha = "14/02/2022";
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
