package unlp.labo.spg.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(indices = {@Index(value = "nombre", unique = true)})
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private long uid;

    @NonNull
    private String nombre;

    @NonNull
    private String password;

    public Usuario(@NonNull String nombre, @NonNull String password) {
        this.nombre = nombre;
        this.password = password;

    }

    public long getUid() { return uid; }

    public void setUid(long uid) { this.uid = uid; }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
