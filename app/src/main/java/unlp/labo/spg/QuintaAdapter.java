package unlp.labo.spg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import unlp.labo.spg.model.QuintaFamilia;

public class QuintaAdapter extends RecyclerView.Adapter<QuintaAdapter.ViewHolder> {

    //    private List<Quinta> mData;
    private final List<QuintaFamilia> mData;
    private final LayoutInflater mInflater;
    private QuintaAdapter.ItemClickListener mClickListener;

    QuintaAdapter(Context context, List<QuintaFamilia> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuintaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = mInflater.inflate(R.layout.quinta_item, viewGroup, false);
        return new unlp.labo.spg.QuintaAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(unlp.labo.spg.QuintaAdapter.ViewHolder viewHolder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        QuintaFamilia mQuintaFamilia = mData.get(position);
        viewHolder.textViewQuintaNombre.setText(mQuintaFamilia.quinta.nombre);
        viewHolder.textViewQuintaDireccion.setText(mQuintaFamilia.quinta.direccion);
        viewHolder.textViewQuintaFamiliaNombre.setText("De:" + mQuintaFamilia.familia.nombre);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView textViewQuintaNombre;
        final TextView textViewQuintaDireccion;
        final TextView textViewQuintaFamiliaNombre;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewQuintaNombre = (TextView) view.findViewById(R.id.textViewQuintaNombre);
            textViewQuintaDireccion = (TextView) view.findViewById(R.id.textViewQuintaDirección);
            textViewQuintaFamiliaNombre = (TextView) view.findViewById(R.id.textViewQuintaFamiliaNombre);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    QuintaFamilia getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(QuintaAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}