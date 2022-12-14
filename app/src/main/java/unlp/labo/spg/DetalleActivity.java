package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.TipoDetalle;
import unlp.labo.spg.model.VisitaDetalle;

public class DetalleActivity extends AppCompatActivity {

    public static final String EXTRA_VISITA_DETALLE = "visitaDetalle";
    protected VisitaDetalle mVisitaDetalle;
    protected Detalle mDetalle;
    protected TipoDetalle mTipoDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        mVisitaDetalle = (VisitaDetalle) getIntent().getSerializableExtra(EXTRA_VISITA_DETALLE);
        mTipoDetalle = TipoDetalle.values()[0];
        mDetalle = mVisitaDetalle.getDetalleByTipoId(mTipoDetalle.id());
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView tvTitulo1 = findViewById(R.id.detalle_title_1);
        tvTitulo1.setText(mTipoDetalle.titulo());
        TextView tvTitulo2 = findViewById(R.id.detalle_title_2);
        tvTitulo2.setText(mTipoDetalle.subtitulo());
        CheckBox cbCumple = findViewById(R.id.detalle_cumple);
        cbCumple.setChecked(mDetalle.cumple);
        EditText etObservacion = findViewById(R.id.detalle_observacion);
        etObservacion.setText(mDetalle.observacion);
        etObservacion.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etObservacion.setRawInputType(InputType.TYPE_CLASS_TEXT);
        EditText etAspiracion = findViewById(R.id.detalle_aspiracion);
        etAspiracion.setText(mDetalle.aspiracion);
        etAspiracion.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etAspiracion.setRawInputType(InputType.TYPE_CLASS_TEXT);
        EditText etSugerencia = findViewById(R.id.detalle_sugerencia);
        etSugerencia.setText(mDetalle.sugerencia);
        etSugerencia.setImeOptions(EditorInfo.IME_ACTION_GO);
        etSugerencia.setRawInputType(InputType.TYPE_CLASS_TEXT);

        Button btContinuar = (Button) findViewById(R.id.detalle_continuar);
        btContinuar.setOnClickListener(view -> onClickButton());
        if (mTipoDetalle.id() + 1 == TipoDetalle.values().length) {
            btContinuar.setText(R.string.finalizar);
        }
    }

    private void onClickButton() {
        CheckBox cbCumple = findViewById(R.id.detalle_cumple);
        mDetalle.cumple = cbCumple.isChecked();
        EditText etObservacion = findViewById(R.id.detalle_observacion);
        mDetalle.observacion = etObservacion.getText().toString();
        EditText etAspiracion = findViewById(R.id.detalle_aspiracion);
        mDetalle.aspiracion = etAspiracion.getText().toString();
        EditText etSugerencia = findViewById(R.id.detalle_sugerencia);
        mDetalle.sugerencia = etSugerencia.getText().toString();
        if (mTipoDetalle.id() + 1 < TipoDetalle.values().length) {
            mTipoDetalle = TipoDetalle.values()[mTipoDetalle.id()+1];
            mDetalle = mVisitaDetalle.getDetalleByTipoId(mTipoDetalle.id());
            this.onStart();

        } else {
            AppDatabase.getInstance(this).visitaDao().insertVisitaDetalle(mVisitaDetalle);
            finish();
        }

    }
}
