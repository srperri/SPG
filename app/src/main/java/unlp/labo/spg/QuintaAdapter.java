package unlp.labo.spg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unlp.labo.spg.model.QuintaFamilia;

public class QuintaAdapter extends RecyclerView.Adapter<QuintaAdapter.ViewHolder>  implements Filterable {

    private final List<QuintaFamilia> mData;
    private final List<QuintaFamilia> mDataAll;
    private final LayoutInflater mInflater;
    private QuintaAdapter.ItemClickListener mClickListener;

    QuintaAdapter(Context context, List<QuintaFamilia> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataAll = data;
        this.mData = new ArrayList<>(data);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuintaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.quinta_item, viewGroup, false);
        return new unlp.labo.spg.QuintaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(unlp.labo.spg.QuintaAdapter.ViewHolder viewHolder, int position) {
        QuintaFamilia mQuintaFamilia = mData.get(position);
        viewHolder.tvNombre.setText(mQuintaFamilia.quinta.nombre);
        viewHolder.tvDireccion.setText(mQuintaFamilia.quinta.direccion);
        viewHolder.tvFamiliaNombre.setText("De:" + mQuintaFamilia.familia.nombre);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        Filter quintaFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<QuintaFamilia> filteredData = new ArrayList<>();
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    filteredData.addAll(mDataAll);
                } else {
                    for (QuintaFamilia quintaFamilia : mDataAll) {
                        if (quintaFamilia.quinta.nombre.toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredData.add(quintaFamilia);
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
                mData.addAll((List<QuintaFamilia>) filterResults.values);
                notifyDataSetChanged();
            }
        };
        return quintaFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvNombre;
        final TextView tvDireccion;
        final TextView tvFamiliaNombre;
        final ImageButton ibEditar;
        final ImageButton ibBorrar;

        public ViewHolder(View view) {
            super(view);
            tvNombre = (TextView) view.findViewById(R.id.tvItemQuintaNombre);
            tvDireccion = (TextView) view.findViewById(R.id.tvItemQuintaDirección);
            tvFamiliaNombre = (TextView) view.findViewById(R.id.tvItemQuintaFamiliaNombre);
            ibEditar = (ImageButton) view.findViewById(R.id.ibItemQuintaEditar);
            ibBorrar = (ImageButton) view.findViewById(R.id.ibItemQuintaBorrar);
            ibEditar.setOnClickListener(this);
            ibBorrar.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ibItemQuintaBorrar:
                    mClickListener.onItemBorrarClick(this.getLayoutPosition());
                    break;
                case R.id.ibItemQuintaEditar:
                    mClickListener.onItemEditarClick(this.getLayoutPosition());
                    break;
                default:
                    mClickListener.onItemClick(this.getLayoutPosition());
            }
        }
    }

    QuintaFamilia getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(QuintaAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);

        void onItemEditarClick(int position);

        void onItemBorrarClick(int position);
    }
}