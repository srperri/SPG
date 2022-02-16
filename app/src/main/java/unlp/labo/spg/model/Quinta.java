package unlp.labo.spg.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = Familia.class, parentColumns = "id", childColumns = "familiaId")},
        indices = {@Index("familiaId")})
public class Quinta implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long familiaId;
    public String nombre;
    public String direccion;
    public String localidad;

    // geopoint acceso


}
