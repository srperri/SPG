package unlp.labo.spg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.TipoDetalle;
import unlp.labo.spg.model.Visita;
import unlp.labo.spg.model.VisitaDetalle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitaElegirActivity extends AppCompatActivity implements VisitaAdapter.ItemClickListener {

    VisitaAdapter mAdapter;
    RecyclerView mRecyclerView;
    AppDatabase db ;
    long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita_elegir);
        uid = getIntent().getLongExtra(Intent.EXTRA_UID,0);
        db = AppDatabase.getInstance(this.getApplication());
        mRecyclerView = findViewById(R.id.visitaRecyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton mFAB= findViewById(R.id.visitaNueva);
        mFAB.setOnClickListener(view -> {nueva_visita();});
    }

    @Override
    public void onResume() {
        super.onResume();
        List<VisitaQuintaFamilia> mVisitas;
        mVisitas = db.visitaDao().getVisitaQuintaFamiliaByUserId(uid);
        mAdapter = new VisitaAdapter(this, mVisitas);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onItemEditClick(int position) {
        VisitaDetalle mVisitaDetalle = db.visitaDao().getVisitaDetalleById(mAdapter.getItem(position).visita.id);
        Intent intent = new Intent(this.getApplication(), VisitaActivity.class);
        intent.putExtra(VisitaActivity.EXTRA_VISITA_DETALLE,mVisitaDetalle);
        startActivity(intent);
    }
    @Override
    public void onItemDeleteClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma que desea eliminar la visita?")
                .setTitle("Alerta")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.visitaDao().deleteById(mAdapter.getItem(position).visita.id);
                        onResume();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog mAlert= builder.create();
        mAlert.show();
        Toast.makeText(this, "Borrar visita a " + mAdapter.getItem(position).quintaFamilia.familia.nombre+"?", Toast.LENGTH_SHORT).show();
    }

    public void nueva_visita() {
        VisitaDetalle mVisitaDetalle= new VisitaDetalle();
        mVisitaDetalle.visita = new Visita();
        mVisitaDetalle.visita.userId = uid;
        mVisitaDetalle.visita.fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        mVisitaDetalle.detalles = new ArrayList<>();
        for (TipoDetalle t : TipoDetalle.values()) {
            Detalle mDetalle = new Detalle();
            mDetalle.tipoId = t.id();
            mVisitaDetalle.detalles.add(mDetalle);
        }
        Intent intent = new Intent(this.getApplication(), VisitaActivity.class);
        intent.putExtra(VisitaActivity.EXTRA_VISITA_DETALLE,mVisitaDetalle);
        startActivity(intent);
    }

}

