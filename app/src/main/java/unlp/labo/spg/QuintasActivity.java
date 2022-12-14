package unlp.labo.spg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.QuintaFamilia;

public class QuintasActivity extends AppCompatActivity implements QuintaAdapter.ItemClickListener {

    public static final String EXTRA_MODO_ELEGIR = "elegir";
    public static final String EXTRA_REPLY_QUINTA = "quinta";

    private QuintaAdapter mAdapter;
    private RecyclerView mRecyclerView;
    public boolean mModoElegir;
    protected long uid;
    protected Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quintas);
        SharedPreferences sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        uid = sharedPref.getLong("uid", 0);
        mModoElegir = getIntent().getBooleanExtra(EXTRA_MODO_ELEGIR, false);
        TextView tvTitulo = findViewById(R.id.tvQuintasTitulo);
        tvTitulo.setText(mModoElegir ? R.string.seleccione_una_quinta : R.string.quintas);
        mRecyclerView = findViewById(R.id.rvQuintas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.faQuintasNueva);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(QuintasActivity.this, QuintaEditarActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<QuintaFamilia> mQuintas = AppDatabase.getInstance(this).quintaDao().getQuintaFamilia(uid);
        mAdapter = new QuintaAdapter(this, mQuintas);
        mAdapter.modoElegir=mModoElegir;
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        if (mOptionsMenu != null) {
            SearchView searchView = (SearchView) mOptionsMenu.findItem(R.id.itMenuQuintasBuscar).getActionView();
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_quintas, menu);
        MenuItem filtrar= menu.findItem(R.id.itMenuQuintasBuscar);
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
        if (mModoElegir) {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_QUINTA, mAdapter.getItem(position).quinta);
            setResult(RESULT_OK, replyIntent);
            finish();
        } else {
            Intent intent = new Intent(this, QuintaInfoActivity.class);
            intent.putExtra(QuintaEditarActivity.EXTRA_QUINTA, mAdapter.getItem(position).quinta);
            startActivity(intent);
        }
    }

    @Override
    public void onItemEditarClick(int position) {
        Intent intent = new Intent(this, QuintaEditarActivity.class);
        intent.putExtra(QuintaEditarActivity.EXTRA_QUINTA, mAdapter.getItem(position).quinta);
        startActivity(intent);
    }

    @Override
    public void onItemBorrarClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma que desea eliminar la quinta?")
                .setTitle("Alerta")
                .setPositiveButton("S??", (dialog, id) -> {
                    AppDatabase.getInstance(getApplicationContext()).quintaDao().delete(mAdapter.getItem(position).quinta);
                    Toast.makeText(getApplicationContext(), "Se borr?? la quinta " + mAdapter.getItem(position).quinta.nombre + ".", Toast.LENGTH_SHORT).show();
                    onResume();
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                });
        AlertDialog mAlert = builder.create();
        mAlert.show();

    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mModoElegir && resultCode == RESULT_OK) {
            Quinta mQuinta = (Quinta) data.getSerializableExtra(QuintaEditarActivity.EXTRA_REPLY_QUINTA);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_QUINTA, mQuinta);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
