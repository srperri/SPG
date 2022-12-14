package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Familia;
import unlp.labo.spg.model.Quinta;

public class QuintaEditarActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_QUINTA="quinta";
    public static final String EXTRA_QUINTA = "quinta";

    private Quinta mQuinta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quinta_editar);
        EditText etNombre =  findViewById(R.id.etQuintaEditarNombre);
        EditText etFamiliaNombre =  findViewById(R.id.etQuintaEditarFamiliaNombre);
        EditText etDireccion =  findViewById(R.id.etQuintaEditarDireccion);
        mQuinta = (Quinta) getIntent().getSerializableExtra(EXTRA_QUINTA);
        if (mQuinta==null){
            ((TextView) findViewById(R.id.tvQuintaEditarTitulo)).setText(R.string.nueva_quinta);
            mQuinta = new Quinta();
        }else {
            etNombre.setText(mQuinta.nombre);
            Familia mFamilia=AppDatabase.getInstance(this).familiaDao().getById(mQuinta.familiaId);
            etFamiliaNombre.setText(mFamilia.nombre);
            etDireccion.setText(mQuinta.direccion);
        }

        etFamiliaNombre.setInputType(InputType.TYPE_NULL);
        etFamiliaNombre.setOnClickListener(view -> {
            Intent intent = new Intent(this.getApplication(), FamiliasActivity.class);
            intent.putExtra(FamiliasActivity.EXTRA_MODO_ELEGIR,true);
            startActivityForResult(intent,1);
        });
        etNombre.setImeOptions(EditorInfo.IME_ACTION_GO);
        etDireccion.setImeOptions(EditorInfo.IME_ACTION_GO);

    }

    public void guardar(View view) {
        mQuinta.nombre = ((EditText) findViewById(R.id.etQuintaEditarNombre)).getText().toString();
        mQuinta.direccion = ((EditText) findViewById(R.id.etQuintaEditarDireccion)).getText().toString();
        if (mQuinta.id==0){
            mQuinta.id=AppDatabase.getInstance(this).quintaDao().insert(mQuinta);
        }else{
            AppDatabase.getInstance(this).quintaDao().update(mQuinta);
        }
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY_QUINTA, mQuinta);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Familia mFamilia;
        if (requestCode == 1 && data != null) {
            mFamilia= (Familia) data.getSerializableExtra(FamiliasActivity.EXTRA_REPLY_FAMILIA);
            mQuinta.familiaId = mFamilia.id;
        }else{
            mFamilia=AppDatabase.getInstance(this).familiaDao().getById(mQuinta.familiaId);
        }
        EditText etFamiliaNombre = findViewById(R.id.etQuintaEditarFamiliaNombre);
        etFamiliaNombre.setText(mFamilia.nombre);
        findViewById(R.id.etQuintaEditarDireccion).requestFocus();
    }


}
