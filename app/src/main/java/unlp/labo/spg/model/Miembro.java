package unlp.labo.spg.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = Usuario.class, parentColumns = "uid", childColumns = "usuarioId")},
        indices = {@Index("usuarioId")})
public class Miembro implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long usuarioId;
    public String nombre;
    public String apellido;
    public Rol rol;

}
