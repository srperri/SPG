package unlp.labo.spg.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import unlp.labo.spg.model.Persona;

@Dao
public interface PersonaDao {
    @Query("SELECT * FROM persona WHERE id = (:id)")
    Persona getById(long id);

    @Query("SELECT * FROM persona WHERE userId = (:userId)")
    List<Persona> getByUserId(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Persona persona);

    @Delete
    void delete(Persona persona);

    @Query("DELETE FROM persona")
    void deleteAll();


}
