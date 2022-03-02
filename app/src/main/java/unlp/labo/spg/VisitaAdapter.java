package unlp.labo.spg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import unlp.labo.spg.model.VisitaQuintaFamilia;

public class VisitaAdapter extends RecyclerView.Adapter<VisitaAdapter.ViewHolder> implements Filterable {

    private final List<VisitaQuintaFamilia> mData;
    private final List<VisitaQuintaFamilia> mDataAll;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    VisitaAdapter(Context context, List<VisitaQuintaFamilia> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataAll = data;
        this.mData = new ArrayList<>(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.visita_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VisitaAdapter.ViewHolder viewHolder, int position) {
        VisitaQuintaFamilia mVisita = mData.get(position);
        viewHolder.tvFamiliaNombre.setText(mVisita.quintaFamilia.familia.nombre);
        viewHolder.tvQuintaNombre.setText(mVisita.quintaFamilia.quinta.nombre);
        viewHolder.tvFecha.setText(mVisita.visita.fecha);
        viewHolder.tvDireccion.setText(mVisita.quintaFamilia.quinta.direccion);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        Filter visitaFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<VisitaQuintaFamilia> filteredData = new ArrayList<>();
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    filteredData.addAll(mDataAll);
                } else {
                    for (VisitaQuintaFamilia v : mDataAll) {
                        if (v.quintaFamilia.familia.nombre.toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredData.add(v);
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
                mData.addAll((List<VisitaQuintaFamilia>) filterResults.values);
                notifyDataSetChanged();
            }

        };
        return visitaFilter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvFamiliaNombre;
        final TextView tvQuintaNombre;
        final TextView tvFecha;
        final TextView tvDireccion;
        final ImageView imEditar;
        final ImageView imBorrar;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvQuintaNombre = (TextView) view.findViewById(R.id.tvItemVisitaQuintaNombre);
            tvFamiliaNombre = (TextView) view.findViewById(R.id.tvItemVisitaFamiliaNombre);
            tvFecha = (TextView) view.findViewById(R.id.tvItemVisitaFecha);
            tvDireccion = (TextView) view.findViewById(R.id.tvItemVisitaDireccion);
            imEditar = (ImageView) view.findViewById(R.id.ibItemVisitaEditar);
            imBorrar = (ImageView) view.findViewById(R.id.ibItemVisitaBorrar);
            itemView.setOnClickListener(this);
            imEditar.setOnClickListener(this);
            imBorrar.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ibItemVisitaEditar:
                    mClickListener.onItemEditarClick(this.getLayoutPosition());
                    break;
                case R.id.ibItemVisitaBorrar:
                    mClickListener.onItemBorrarClick(this.getLayoutPosition());
                    break;
                default:
                    mClickListener.onItemClick(this.getLayoutPosition());
            }
        }
    }

    VisitaQuintaFamilia getItem(int id) {
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
