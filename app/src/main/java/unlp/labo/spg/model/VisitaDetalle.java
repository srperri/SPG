package unlp.labo.spg.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class VisitaDetalle implements Serializable {
    @Embedded
    public Visita visita;
    @Relation(
            parentColumn = "id",
            entityColumn = "visitaId"
    )
    public List<Detalle> detalles;

    public Detalle getDetalleByTipoId(int tipoId) {
        for (Detalle d : detalles) {
            if (d.tipoId == tipoId) {
                return d;
            }
        }
        return null;
    }
}

