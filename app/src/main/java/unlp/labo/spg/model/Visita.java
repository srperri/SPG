package unlp.labo.spg.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Visita implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long userId;
    public String fecha;
    public long quintaId;
    public float supCampo;
    public float supInver;
    public String conclusiones;
    public String proximaVisita;
}
