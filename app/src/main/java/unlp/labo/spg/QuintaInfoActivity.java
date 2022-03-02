package unlp.labo.spg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Familia;
import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.VisitaDetalle;
import unlp.labo.spg.model.VisitaQuintaFamilia;

public class QuintaInfoActivity extends AppCompatActivity implements VisitaAdapter.ItemClickListener {
    public static final String EXTRA_QUINTA = "quinta";

    private Quinta mQuinta;
    private VisitaAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quinta_info);
        TextView tvNombre = (TextView) findViewById(R.id.tvQuintaInfoNombre);
        TextView tvFamiliaNombre = (TextView) findViewById(R.id.tvQuintaInfoFamiliaNombre);
        TextView tvDireccion = (TextView) findViewById(R.id.tvQuintaInfoDireccion);
        mQuinta = (Quinta) getIntent().getSerializableExtra(EXTRA_QUINTA);
        db = AppDatabase.getInstance(this);
        Familia mFamilia = db.familiaDao().getById(mQuinta.familiaId);
        tvNombre.setText(mQuinta.nombre);
        tvFamiliaNombre.setText(mFamilia.nombre);
        tvDireccion.setText(mQuinta.direccion);
        mRecyclerView = findViewById(R.id.rvInfoQuintaVisitas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onResume() {
        super.onResume();
        List<VisitaQuintaFamilia> mVisitas = db.visitaDao().getVisitaQuintaFamiliaByQuintaId(mQuinta.id);
        mAdapter = new VisitaAdapter(this, mVisitas);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onItemClick(int position) {
        VisitaDetalle mVisitaDetalle = db.visitaDao().getVisitaDetalleById(mAdapter.getItem(position).visita.id);
        Intent intent = new Intent(this.getApplication(), VisitaInfoActivity.class);
        intent.putExtra(VisitaEditarActivity.EXTRA_VISITA_DETALLE, mVisitaDetalle);
        startActivity(intent);
    }

    @Override
    public void onItemEditarClick(int position) {
        VisitaDetalle mVisitaDetalle = db.visitaDao().getVisitaDetalleById(mAdapter.getItem(position).visita.id);
        Intent intent = new Intent(this.getApplication(), VisitaEditarActivity.class);
        intent.putExtra(VisitaEditarActivity.EXTRA_VISITA_DETALLE, mVisitaDetalle);
        startActivity(intent);
    }

    @Override
    public void onItemBorrarClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma que desea eliminar la visita?")
                .setTitle("Alerta")
                .setPositiveButton("Sí", (dialog, id) -> {
                    db.visitaDao().deleteById(mAdapter.getItem(position).visita.id);
                    Toast.makeText(getApplicationContext(), "Se borró la visita a " + mAdapter.getItem(position).quintaFamilia.familia.nombre + ".", Toast.LENGTH_SHORT).show();
                    onResume();
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                });
        AlertDialog mAlert = builder.create();
        mAlert.show();
    }
}