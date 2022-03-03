package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Familia;

public class FamiliaEditarActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPLY_FAMILIA="familia";
    public static final String EXTRA_FAMILIA = "familia";
    private Familia mFamilia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia_editar);
        mFamilia = (Familia) getIntent().getSerializableExtra(EXTRA_FAMILIA);
        if (mFamilia.id==0){
            ((TextView) findViewById(R.id.tvFamiliaEditarTitulo)).setText(R.string.nueva_familia);
        }
        ((EditText) findViewById(R.id.editTextFamilia)).setText(mFamilia.nombre);
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText mEditText = findViewById(R.id.editTextFamilia);
        if (TextUtils.isEmpty(mEditText.getText())) {
            Toast.makeText(this, "Debe indicar un nombre", Toast.LENGTH_SHORT).show();
        }else {
            mFamilia.nombre=mEditText.getText().toString();
            if (mFamilia.id==0){
                mFamilia.id=AppDatabase.getInstance(this).familiaDao().insert(mFamilia);
            } else{
                AppDatabase.getInstance(this).familiaDao().update(mFamilia);
            }
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_FAMILIA, mFamilia);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}