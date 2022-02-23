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
import java.util.Locale;

import unlp.labo.spg.model.Visita;

public class VisitaAdapter extends RecyclerView.Adapter<VisitaAdapter.ViewHolder> implements Filterable {

    private final List<VisitaQuintaFamilia> mData;
    private final List<VisitaQuintaFamilia> mDataAll;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    VisitaAdapter(Context context, List<VisitaQuintaFamilia> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>(data);
        this.mDataAll = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = mInflater.inflate(R.layout.visita_item, viewGroup, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(VisitaAdapter.ViewHolder viewHolder, int position) {
        VisitaQuintaFamilia mVisita = mData.get(position);
        viewHolder.textViewVisitaFamiliaNombre.setText(mVisita.quintaFamilia.familia.nombre);
        viewHolder.textViewVisitaFecha.setText(mVisita.visita.fecha);
        viewHolder.textViewVisitaDireccion.setText(mVisita.quintaFamilia.quinta.direccion);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return visitaFilter;
    }

    private final Filter visitaFilter= new Filter() {
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView textViewVisitaFamiliaNombre;
        final TextView textViewVisitaFecha;
        final TextView textViewVisitaDireccion;
        final ImageView imageViewDelete;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewVisitaFamiliaNombre = (TextView) view.findViewById(R.id.textViewVisitaFamiliaNombre);
            textViewVisitaFecha = (TextView) view.findViewById(R.id.textViewVisitaFecha);
            textViewVisitaDireccion = (TextView) view.findViewById(R.id.textViewVisitaDireccion);
            imageViewDelete = (ImageView) view.findViewById(R.id.imageBorrar);
            itemView.setOnClickListener(this);
            imageViewDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.imageBorrar) {
                mClickListener.onItemDeleteClick(this.getLayoutPosition());
            } else {
                mClickListener.onItemEditClick(this.getLayoutPosition());
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
//        void onItemClick(View view, int position);
        void onItemEditClick(int position);
        void onItemDeleteClick(int position);
    }
}
