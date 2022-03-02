package unlp.labo.spg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unlp.labo.spg.model.Miembro;

public class MiembroAdapter extends RecyclerView.Adapter<MiembroAdapter.ViewHolder> {

    //    private List<Quinta> mData;
    private final List<Miembro> mData;
    private final LayoutInflater mInflater;
    private MiembroAdapter.ItemClickListener mClickListener;

    MiembroAdapter(Context context, List<Miembro> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = mInflater.inflate(R.layout.miembro_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Miembro mMiembro = mData.get(position);
        viewHolder.tvApellidoNombre.setText(mMiembro.apellido+", "+mMiembro.nombre);
        viewHolder.tvRol.setText(mMiembro.rol.descripcion());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvApellidoNombre;
        final TextView tvRol;
        final ImageView imBorrar;

        public ViewHolder(View view) {
            super(view);
            tvApellidoNombre = (TextView) view.findViewById(R.id.tvApellidoNombre);
            tvRol = (TextView) view.findViewById(R.id.tvRol);
            imBorrar=view.findViewById(R.id.imBorrar);
            imBorrar.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.imBorrar) {
                mClickListener.onItemDeleteClick(this.getLayoutPosition());
            }
        }
    }

    Miembro getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(MiembroAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemDeleteClick(int position);
    }
}
