package unlp.labo.spg.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import unlp.labo.spg.model.Familia;

import java.util.List;

@Dao
public interface FamiliaDao {
    @Query("SELECT * FROM familia")
    List<Familia> getAll();

    @Query("SELECT nombre FROM familia")
    List<String> getAllNombres();

    @Query("SELECT * FROM familia WHERE id = (:id)")
    Familia getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Familia familia);

    @Query("DELETE FROM familia")
    void deleteAll();


}
