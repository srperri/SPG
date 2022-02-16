package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Familia;
import unlp.labo.spg.model.Quinta;

public class QuintaNuevaActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_QUINTA="quinta";

    private final Quinta mQuinta = new Quinta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quinta_nueva);
        EditText etFamiliaNombre = (EditText) findViewById(R.id.editTextFamiliaNombre);
        etFamiliaNombre.setInputType(InputType.TYPE_NULL);
        etFamiliaNombre.setOnClickListener(view -> {
            Intent intent = new Intent(this.getApplication(), FamiliaElegirActivity.class);
            startActivityForResult(intent,1);
        });
        EditText etQuintaNombre = (EditText) findViewById(R.id.editTextQuintaNombre);
        etQuintaNombre.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etQuintaNombre.setRawInputType(InputType.TYPE_CLASS_TEXT);
        EditText etQuintaDireccion = (EditText) findViewById(R.id.editTextDireccion);
        etQuintaDireccion.setImeOptions(EditorInfo.IME_ACTION_GO);
        etQuintaDireccion.setRawInputType(InputType.TYPE_CLASS_TEXT);

    }

    public void guardar(View view) {
        mQuinta.nombre = ((EditText) findViewById(R.id.editTextQuintaNombre)).getText().toString();
        mQuinta.direccion = ((EditText) findViewById(R.id.editTextDireccion)).getText().toString();
        mQuinta.id=AppDatabase.getInstance(this).quintaDao().insert(mQuinta);
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY_QUINTA, mQuinta);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            Familia mFamilia= (Familia) data.getSerializableExtra(FamiliaElegirActivity.EXTRA_REPLY_FAMILIA);
            mQuinta.familiaId = mFamilia.id;
            EditText etFamiliaNombre = (EditText) findViewById(R.id.editTextFamiliaNombre);
            etFamiliaNombre.setText(mFamilia.nombre);
            findViewById(R.id.editTextQuintaNombre).requestFocus();
        }
    }


}
