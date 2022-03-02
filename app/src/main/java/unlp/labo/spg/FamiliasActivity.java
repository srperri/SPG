package unlp.labo.spg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Familia;

public class FamiliasActivity extends AppCompatActivity implements FamiliaAdapter.ItemClickListener {

    public static final String EXTRA_MODO_ELEGIR = "elegir";
    public static final String EXTRA_REPLY_FAMILIA="familia";
    private FamiliaAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean mModoElegir;
    protected long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familias);
        uid = getIntent().getLongExtra(Intent.EXTRA_UID, 0);
        mModoElegir = getIntent().getBooleanExtra(EXTRA_MODO_ELEGIR,false);
        TextView tvTitulo=(TextView) findViewById(R.id.tvFamiliasTitulo);
        tvTitulo.setText(mModoElegir?R.string.seleccione_una_familia :R.string.familias);
        mRecyclerView = findViewById(R.id.rvFamilias);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.faFamiliasNuevo);
        fab.setOnClickListener( view -> {
            Familia mFamilia = new Familia();
            mFamilia.userId=uid;
            Intent intent = new Intent(this, FamiliaEditarActivity.class);
            intent.putExtra(FamiliaEditarActivity.EXTRA_FAMILIA, mFamilia);
            startActivityForResult(intent,1);
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        List<Familia> mFamilias = AppDatabase.getInstance(this).familiaDao().getAll(uid);
        mAdapter =new FamiliaAdapter(this, mFamilias);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_familias, menu);
        MenuItem filtrar= menu.findItem(R.id.itMenuFamiliaFiltrar);
        SearchView sv= (SearchView) filtrar.getActionView();
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
    public void onItemClick(int position) {
        if (mModoElegir){
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_FAMILIA, mAdapter.getItem(position));
            setResult(RESULT_OK, replyIntent);
            finish();
        }else {
            Intent intent = new Intent(this, FamiliaEditarActivity.class);
            intent.putExtra(FamiliaEditarActivity.EXTRA_FAMILIA, mAdapter.getItem(position));
            startActivity(intent);
        }
    }


    @Override
    public void onItemEditarClick(int position) {
        Intent intent = new Intent(this, FamiliaInfoActivity.class);
        intent.putExtra(FamiliaInfoActivity.EXTRA_FAMILIA, mAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onItemBorrarClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma que desea eliminar la familia?")
                .setTitle("Alerta")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AppDatabase.getInstance(getApplicationContext()).familiaDao().delete(mAdapter.getItem(position));
                        Toast.makeText(getApplicationContext(), "Se borrar la familia " + mAdapter.getItem(position).nombre+".", Toast.LENGTH_SHORT).show();
                        onResume();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog mAlert= builder.create();
        mAlert.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mModoElegir && resultCode == RESULT_OK) {
            Familia mFamilia = (Familia) data.getSerializableExtra(FamiliaEditarActivity.EXTRA_REPLY_FAMILIA);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_FAMILIA, mFamilia);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

}