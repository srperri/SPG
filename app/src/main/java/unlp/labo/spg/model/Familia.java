package unlp.labo.spg.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Familia implements Serializable {


    @PrimaryKey(autoGenerate = true)
    public long id;
    public final String nombre;

    public Familia(String nombre){
        this.nombre =nombre;
    }

}
