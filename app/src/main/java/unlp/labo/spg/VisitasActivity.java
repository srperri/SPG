package unlp.labo.spg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.TipoDetalle;
import unlp.labo.spg.model.Visita;
import unlp.labo.spg.model.VisitaDetalle;
import unlp.labo.spg.model.VisitaQuintaFamilia;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitasActivity extends AppCompatActivity implements VisitaAdapter.ItemClickListener {

    VisitaAdapter mAdapter;
    RecyclerView mRecyclerView;
    AppDatabase db;
    long uid;
    private Menu mOptionsMenu;
    boolean busquedaActiva =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitas);
        uid = getIntent().getLongExtra(Intent.EXTRA_UID, 0);
        if (uid == 0) {
            startActivity(new Intent(this.getApplication(), LoginActivity.class));
            finish();
        }
        db = AppDatabase.getInstance(this.getApplication());
        mRecyclerView = findViewById(R.id.rvVisitas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton mFAB = findViewById(R.id.faVisitasNueva);
        mFAB.setOnClickListener(view -> {
            nueva_visita();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_visitas, menu);
        menu.findItem(R.id.itMenuVisitasQuitarBusqueda).setVisible(busquedaActiva);
        MenuItem buscar = menu.findItem(R.id.itMenuVisitaFiltrar);
        SearchView sv = (SearchView) buscar.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.itMenuVisitasBuscar:
                busquedaAvanzada();
                return true;
            case R.id.itMenuVisitasQuitarBusqueda:
                busquedaActiva =false;
                invalidateOptionsMenu();
                onResume();
                return true;
            case R.id.itMenuVisitasFamilias:
                intent =new Intent(this.getApplication(), FamiliasActivity.class);
                intent.putExtra(Intent.EXTRA_UID, uid);
                startActivity(intent);
                return true;
            case R.id.itMenuVisitasQuintas:
                intent= new Intent(this.getApplication(), QuintasActivity.class);
                intent.putExtra(Intent.EXTRA_UID, uid);
                startActivity(intent);
                return true;
            case R.id.itMenuVisitasUsuario:
                intent = new Intent(this, UsuarioEditarActivity.class);
                intent.putExtra(Intent.EXTRA_UID, uid);
                startActivity(intent);
                return true;
            case R.id.itMenuVisitasCerrarSesion:
                intent=new Intent(this.getApplication(), LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<VisitaQuintaFamilia> mVisitas = db.visitaDao().getVisitaQuintaFamilia(uid);
        mAdapter = new VisitaAdapter(this, mVisitas);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        if (mOptionsMenu != null) {
            SearchView searchView = (SearchView) mOptionsMenu.findItem(R.id.itMenuVisitaFiltrar).getActionView();
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
        }
        if (busquedaActiva){
           busquedaActiva =false;
           invalidateOptionsMenu();
        }
    }


    @Override
    public void onItemClick(int position) {
        VisitaDetalle mVisitaDetalle = db.visitaDao().getVisitaDetalleById(mAdapter.getItem(position).visita.id);
        Intent intent = new Intent(this.getApplication(), VisitaInfoActivity.class);
        intent.putExtra(Intent.EXTRA_UID, uid);
        intent.putExtra(VisitaInfoActivity.EXTRA_VISITA_DETALLE, mVisitaDetalle);
        startActivity(intent);
    }

    @Override
    public void onItemEditarClick(int position) {
        VisitaDetalle mVisitaDetalle = db.visitaDao().getVisitaDetalleById(mAdapter.getItem(position).visita.id);
        Intent intent = new Intent(this.getApplication(), VisitaEditarActivity.class);
        intent.putExtra(Intent.EXTRA_UID, uid);
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

    public void nueva_visita() {
        VisitaDetalle mVisitaDetalle = new VisitaDetalle();
        mVisitaDetalle.visita = new Visita();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        try {
            mVisitaDetalle.visita.fecha =sdf.parse(sdf.format(new Date()));
        }catch (ParseException e) {
            e.printStackTrace();
        }
        mVisitaDetalle.detalles = new ArrayList<>();
        for (TipoDetalle t : TipoDetalle.values()) {
            Detalle mDetalle = new Detalle();
            mDetalle.tipoId = t.id();
            mVisitaDetalle.detalles.add(mDetalle);
        }
        Intent intent = new Intent(this.getApplication(), VisitaEditarActivity.class);
        intent.putExtra(Intent.EXTRA_UID, uid);
        intent.putExtra(VisitaEditarActivity.EXTRA_VISITA_DETALLE, mVisitaDetalle);
        startActivity(intent);
    }

    private void busquedaAvanzada(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_buscar_visita, null);
        VisitaAdapter.ItemClickListener itemClickListener=this;
        Context context=this;
        builder.setView(mView)
                .setTitle(R.string.busqueda_avanzada)
                .setNegativeButton(R.string.aceptar, (dialog, id) -> {
                    Date fecha=null;
                    try {
                        String s=((EditText) mView.findViewById(R.id.etBuscarVisitaFecha)).getText().toString().trim();
                        fecha=new SimpleDateFormat("dd/MM/yyyy").parse( s);
                    }catch (ParseException e) {
                        e.printStackTrace();
                    }
                    List<VisitaQuintaFamilia> mVisitas = db.visitaDao().getVisitaQuintaFamilia(uid,
                            ((EditText) mView.findViewById(R.id.etBuscarVisitaQuintaNombre)).getText().toString().trim(),
                            ((EditText) mView.findViewById(R.id.etBuscarVisitaFamiliaNombre)).getText().toString().trim(),
                            fecha);
                    if (mVisitas.size()>0) {
                        mAdapter = new VisitaAdapter(context, mVisitas);
                        mAdapter.setClickListener(itemClickListener);
                        mRecyclerView.setAdapter(mAdapter);
                        busquedaActiva =true;
                        invalidateOptionsMenu();
                        Toast.makeText(getApplicationContext(), "Se encontraron "+mVisitas.size()+" coincidencias.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "No se encontraron coincidencias.", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }
}

