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
import unlp.labo.spg.model.QuintaFamilia;

public class FamiliaInfoActivity extends AppCompatActivity implements QuintaAdapter.ItemClickListener {

    public static final String EXTRA_FAMILIA = "familia";

    private Familia mFamilia;
    private QuintaAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia_info);
        db = AppDatabase.getInstance(this);
        mFamilia = (Familia) getIntent().getSerializableExtra(EXTRA_FAMILIA);
        TextView tvNombre = findViewById(R.id.tvFamiliaInfoNombre);
        tvNombre.setText(mFamilia.nombre);
        mRecyclerView = findViewById(R.id.rvInfoFamiliaQuintas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        List<QuintaFamilia> mQuintas = db.quintaDao().getQuintaFamiliaByFamiliaId(mFamilia.id);
        mAdapter = new QuintaAdapter(this, mQuintas);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onItemClick(int position) {
        Quinta quinta = mAdapter.getItem(position).quinta;
        Intent intent = new Intent(this.getApplication(), QuintaInfoActivity.class);
        intent.putExtra(Intent.EXTRA_UID,mFamilia.userId);
        intent.putExtra(QuintaInfoActivity.EXTRA_QUINTA, quinta);
        startActivity(intent);
    }

    @Override
    public void onItemEditarClick(int position) {
        Quinta quinta = mAdapter.getItem(position).quinta;
        Intent intent = new Intent(this.getApplication(), QuintaEditarActivity.class);
        intent.putExtra(Intent.EXTRA_UID,mFamilia.userId);
        intent.putExtra(QuintaEditarActivity.EXTRA_QUINTA, quinta);
        startActivity(intent);
    }

    @Override
    public void onItemBorrarClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma que desea eliminar la quinta?")
                .setTitle("Alerta")
                .setPositiveButton("Sí", (dialog, id) -> {
                    db.quintaDao().delete(mAdapter.getItem(position).quinta);
                    Toast.makeText(getApplicationContext(), "Se borró la quinta " + mAdapter.getItem(position).quinta.nombre + ".", Toast.LENGTH_SHORT).show();
                    onResume();
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                });
        AlertDialog mAlert = builder.create();
        mAlert.show();
    }}