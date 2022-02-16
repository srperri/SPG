package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Familia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FamiliaElegirActivity extends AppCompatActivity  implements FamiliaAdapter.ItemClickListener {
    public static final String EXTRA_REPLY_FAMILIA="familia";

    FamiliaAdapter mAdapter;
    List<Familia> mFamilias;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia_elegir);
        mFamilias = AppDatabase.getInstance(this).familiaDao().getAll();
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter =new FamiliaAdapter(this, mFamilias);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.nuevo);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(FamiliaElegirActivity.this, FamiliaNuevaActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY_FAMILIA, mAdapter.getItem(position));
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Familia mFamilia = (Familia) data.getSerializableExtra(FamiliaNuevaActivity.EXTRA_REPLY_FAMILIA);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY_FAMILIA, mFamilia);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}