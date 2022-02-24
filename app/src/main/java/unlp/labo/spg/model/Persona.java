package unlp.labo.spg.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

    @Entity(foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = User.class, parentColumns = "uid", childColumns = "userId")},
            indices = {@Index("userId")})
    public class Persona implements Serializable {
        @PrimaryKey(autoGenerate = true)
        public long id;
        public long userId;
        public String nombre;
        public String apellido;
        public Rol rol;

    }
