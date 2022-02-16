package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Familia;

public class FamiliaNuevaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPLY_FAMILIA="familia";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia_nueva);
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText mEditText = findViewById(R.id.editTextFamilia);
        if (TextUtils.isEmpty(mEditText.getText())) {
            Toast.makeText(this, "Debe indicar un nombre", Toast.LENGTH_SHORT).show();
        }else {
            Familia mFamilia = new Familia(mEditText.getText().toString());
            mFamilia.id = AppDatabase.getInstance(this).familiaDao().insert(mFamilia);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_FAMILIA, mFamilia);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}