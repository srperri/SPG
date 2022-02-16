package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.QuintaFamilia;

public class QuintaElegirActivity extends AppCompatActivity implements QuintaAdapter.ItemClickListener {

    public static final String EXTRA_REPLY_QUINTA = "quinta";

    private QuintaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quinta_elegir);
        RecyclerView mRecyclerView = findViewById(R.id.quintaRecyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<QuintaFamilia> mQuintas;
        mQuintas = AppDatabase.getInstance(this).quintaDao().getQuintaYFamilia();
        mAdapter = new QuintaAdapter(this, mQuintas);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.quintaNueva);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(QuintaElegirActivity.this, QuintaNuevaActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY_QUINTA, mAdapter.getItem(position).quinta);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Quinta mQuinta = (Quinta) data.getSerializableExtra(QuintaNuevaActivity.EXTRA_REPLY_QUINTA);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_QUINTA, mQuinta);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
