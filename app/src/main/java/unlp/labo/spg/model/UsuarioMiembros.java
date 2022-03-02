package unlp.labo.spg.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class UsuarioMiembros implements Serializable {
    @Embedded
    public Usuario usuario;
    @Relation(
            parentColumn = "uid",
            entityColumn = "usuarioId"
    )
    public List<Miembro> miembros;

}


