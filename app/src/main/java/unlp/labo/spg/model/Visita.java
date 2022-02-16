package unlp.labo.spg.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = User.class, parentColumns = "uid", childColumns = "userId")},
        indices = {@Index("userId")})
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
