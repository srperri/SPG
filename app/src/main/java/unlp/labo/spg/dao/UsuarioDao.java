package unlp.labo.spg.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.Miembro;
import unlp.labo.spg.model.Usuario;
import unlp.labo.spg.model.UsuarioMiembros;
import unlp.labo.spg.model.VisitaDetalle;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM Usuario WHERE nombre = :nombre AND " +
            "password = :password")
    Usuario validate(String nombre, String password);

    @Query("SELECT (count(*)=0) as vacio FROM Usuario")
    boolean empty();

    @Insert
    long insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Query("DELETE FROM Usuario")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM Usuario where uid=:uid")
    UsuarioMiembros getUsuarioMiembrosByUid(long uid);

    @Insert
    void insertMiembros(List<Miembro> miembros);

    @Query("DELETE FROM miembro WHERE usuarioId=:uid")
    void deleteMiembrosByUid(long uid);

    @Transaction
    default long insertUsuarioMiembros(UsuarioMiembros usuarioMiembros){
        long uid=usuarioMiembros.usuario.getUid();
        if (uid == 0){
            uid= insert(usuarioMiembros.usuario);
        }else{
            update(usuarioMiembros.usuario);
            deleteMiembrosByUid(uid);
        }
        for (Miembro m : usuarioMiembros.miembros) {
            m.usuarioId = uid;
        }
        insertMiembros(usuarioMiembros.miembros);
        return uid;
    }

}
