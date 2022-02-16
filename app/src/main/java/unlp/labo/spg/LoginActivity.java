package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import unlp.labo.spg.dao.UserDao;
import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.User;

public class LoginActivity extends AppCompatActivity {
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDao = AppDatabase.getInstance(this).userDao();
        if (userDao.empty()){
            User user = new User("admin","admin");
            userDao.insert(user);
        }
    }

    public void init_session(View view) {
        EditText etUserName = (EditText) findViewById(R.id.editTextUser);
        String userName = etUserName.getText().toString();
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        String password = editTextPassword.getText().toString();
        User user=userDao.validate(userName, password);
        if (user !=null) {
            Intent intent = new Intent(this, VisitaElegirActivity.class);
            intent.putExtra(Intent.EXTRA_UID, user.getUid());
            startActivity(intent);
            // Do something in response to button
            finish();

        } else {
            Toast.makeText(LoginActivity.this, "Usuario/Clave incorrecta.", Toast.LENGTH_SHORT).show();

        }
    }
}
