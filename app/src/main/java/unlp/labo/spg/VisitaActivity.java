package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.VisitaDetalle;

public class VisitaActivity extends AppCompatActivity {

    public static final String EXTRA_VISITA_DETALLE = "visitaDetalle";

    private VisitaDetalle mVisitaDetalle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);
        mVisitaDetalle = (VisitaDetalle) getIntent().getSerializableExtra(EXTRA_VISITA_DETALLE);
        TextView tvTitulo = (TextView) findViewById(R.id.textViewVisitaTitle);
        EditText etQuintaNombre = (EditText) findViewById(R.id.editTextQuintaNombre);

        if (mVisitaDetalle.visita.id == 0) {
            tvTitulo.setText("Nueva Visita");
        } else {
            tvTitulo.setText("Editar Visita");
            Quinta mQuinta=AppDatabase.getInstance(this).quintaDao().getById(mVisitaDetalle.visita.quintaId);
            etQuintaNombre.setText(mQuinta.nombre.toString());
        }
        EditText etSupCampo = (EditText) findViewById(R.id.editTextSupCampo);
        etSupCampo.setText(String.valueOf(mVisitaDetalle.visita.supCampo));
        etSupCampo.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        EditText etSupInver = (EditText) findViewById(R.id.editTextSupInver);
        etSupCampo.setImeOptions(EditorInfo.IME_ACTION_GO);
        etSupInver.setText(String.valueOf(mVisitaDetalle.visita.supInver));
        etQuintaNombre.setInputType(InputType.TYPE_NULL);
        etQuintaNombre.setOnClickListener(view -> {
            Intent intent = new Intent(this.getApplication(), QuintaElegirActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            Quinta mQuinta = (Quinta) data.getSerializableExtra(QuintaElegirActivity.EXTRA_REPLY_QUINTA);
            mVisitaDetalle.visita.quintaId = mQuinta.id;
            EditText etQuintaDesc = (EditText) findViewById(R.id.editTextQuintaNombre);
            String mQuintaDesc = mQuinta.direccion;
            etQuintaDesc.setText(mQuintaDesc);
            findViewById(R.id.editTextSupCampo).requestFocus();
        }
    }

    public void continuar(View view) {
        if (mVisitaDetalle.visita.quintaId==0)
            Toast.makeText(this, "Debe elegir una Quinta.", Toast.LENGTH_SHORT).show();
        try {
            mVisitaDetalle.visita.supCampo = Float.parseFloat(((EditText) findViewById(R.id.editTextSupCampo)).getText().toString());
        } catch (NumberFormatException e) {
            mVisitaDetalle.visita.supCampo = 0;
        }
        try {
            mVisitaDetalle.visita.supInver = Float.parseFloat(((EditText) findViewById(R.id.editTextSupInver)).getText().toString());
        } catch (NumberFormatException e) {
            mVisitaDetalle.visita.supCampo = 0;
        }
        Intent intent = new Intent(this.getApplication(), DetalleActivity.class);
        intent.putExtra(DetalleActivity.EXTRA_VISITA_DETALLE, mVisitaDetalle);
        startActivity(intent);
        finish();
    }


}
