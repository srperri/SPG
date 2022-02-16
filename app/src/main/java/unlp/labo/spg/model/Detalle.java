package unlp.labo.spg.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.io.Serializable;

@Entity(primaryKeys = {"visitaId", "tipoId"},
        foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = Visita.class, parentColumns = "id", childColumns = "visitaId")},
        indices = {@Index("visitaId")})
public class Detalle implements Serializable {
    public long visitaId;
    public int tipoId;
    public boolean cumple;
    public String observacion;
    public String aspiracion;
    public String sugerencia;
}
