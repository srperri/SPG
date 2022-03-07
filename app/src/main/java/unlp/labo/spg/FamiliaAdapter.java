package unlp.labo.spg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import unlp.labo.spg.model.Familia;

import java.util.ArrayList;
import java.util.List;

public class FamiliaAdapter extends RecyclerView.Adapter<FamiliaAdapter.ViewHolder> implements Filterable {

    private final List<Familia> mData;
    private final List<Familia> mDataAll;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public boolean modoElegir=true;

    FamiliaAdapter(Context context, List<Familia> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataAll = data;
        this.mData = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.familia_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Familia mFamilia = mData.get(position);
        viewHolder.tvNombre.setText(mFamilia.nombre);
        viewHolder.ibBorrar.setVisibility(modoElegir?View.INVISIBLE:View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        Filter familiaFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Familia> filteredData = new ArrayList<>();
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    filteredData.addAll(mDataAll);
                } else {
                    for (Familia f : mDataAll) {
                        if (f.nombre.toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredData.add(f);
                        }
                    }
                }
                filterResults.values = filteredData;
                filterResults.count = filteredData.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mData.clear();
                mData.addAll((List<Familia>) filterResults.values);
                notifyDataSetChanged();
            }

        };
        return familiaFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvNombre;
        final ImageButton ibBorrar;
        final ImageButton ibEditar;

        public ViewHolder(View view) {
            super(view);
            tvNombre = view.findViewById(R.id.tvItemFamiliaNombre);
            ibBorrar = view.findViewById(R.id.ibItemFamiliaBorrar);
            ibEditar = view.findViewById(R.id.ibItemFamiliaEditar);
            ibEditar.setOnClickListener(this);
            ibBorrar.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ibItemFamiliaBorrar:
                    mClickListener.onItemBorrarClick(this.getLayoutPosition());
                    break;
                case R.id.ibItemFamiliaEditar:
                    mClickListener.onItemEditarClick(this.getLayoutPosition());
                    break;
                default:
                    mClickListener.onItemClick(this.getLayoutPosition());
            }
        }
    }

    Familia getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);

        void onItemEditarClick(int position);

        void onItemBorrarClick(int position);
    }
}


