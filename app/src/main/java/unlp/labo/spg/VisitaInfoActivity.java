package unlp.labo.spg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import unlp.labo.spg.model.AppDatabase;
import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.TipoDetalle;
import unlp.labo.spg.model.VisitaDetalle;

public class VisitaInfoActivity extends AppCompatActivity {
    public static final String EXTRA_VISITA_DETALLE = "visitaDetalle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita_info);
        VisitaDetalle mVisitaDetalle = (VisitaDetalle) getIntent().getSerializableExtra(EXTRA_VISITA_DETALLE);
        Quinta mQuinta = AppDatabase.getInstance(this).quintaDao().getById(mVisitaDetalle.visita.quintaId);
        ((TextView) findViewById(R.id.tvVisitaInfoQuintaNombre)).setText(mQuinta.nombre);
        ((TextView) findViewById(R.id.tvVisitaInfoSupCampo)).setText(String.valueOf(mVisitaDetalle.visita.supCampo));
        ((TextView) findViewById(R.id.tvVisitaInfoSupInver)).setText(String.valueOf(mVisitaDetalle.visita.supInver));
        LinearLayout llContenido = findViewById(R.id.llVisitaInfoContenido);
        for (Detalle detalle : mVisitaDetalle.detalles) {
            if (detalle.cumple || !detalle.observacion.isEmpty() || !detalle.aspiracion.isEmpty() || !detalle.sugerencia.isEmpty()) {
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.info_detalle, llContenido, false);
                llContenido.addView(view);
                TipoDetalle tipo = TipoDetalle.values()[detalle.tipoId];
                ((TextView) view.findViewById(R.id.tvInfoDetalleNombre)).setText(tipo.titulo());
                if (!tipo.subtitulo().isEmpty()) {
                    ((TextView) view.findViewById(R.id.tvInfoDetalleNombre2)).setText(" | " + tipo.subtitulo());
                }
                ((CheckBox) view.findViewById(R.id.cbInfoDetalleCumple)).setChecked(detalle.cumple);
                if (!detalle.observacion.isEmpty()) {
                    ((LinearLayout) view.findViewById(R.id.llInfoDetalleObservacionLayout)).setVisibility(View.VISIBLE);
                    ((TextView) view.findViewById(R.id.tvInfoDetalleObservacion)).setText(detalle.observacion);
                }
                if (!detalle.aspiracion.isEmpty()) {
                    ((LinearLayout) view.findViewById(R.id.llInfoDetalleAspiracionLayout)).setVisibility(View.VISIBLE);
                    ((TextView) view.findViewById(R.id.tvInfoDetalleAspiracion)).setText(detalle.aspiracion);
                }
                if (!detalle.sugerencia.isEmpty()) {
                    ((LinearLayout) view.findViewById(R.id.llInfoDetalleSugerenciaLayout)).setVisibility(View.VISIBLE);
                    ((TextView) view.findViewById(R.id.tvInfoDetalleSugerencia)).setText(detalle.sugerencia);
                }
            }
        }
    }
}