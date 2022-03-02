package unlp.labo.spg.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import unlp.labo.spg.model.QuintaFamilia;
import unlp.labo.spg.model.Quinta;

import java.util.List;

@Dao
public interface QuintaDao {

    @Query("SELECT * FROM quinta WHERE id = (:id)")
    Quinta getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Quinta quinta);

    @Delete
    void delete(Quinta quinta);

    @Transaction
    @Query("SELECT quinta.* FROM quinta join familia on quinta.familiaId=familia.id where familia.userId=(:userId) ")
    List<QuintaFamilia> getQuintaFamilia(long userId);

    @Transaction
    @Query("SELECT * FROM quinta where familiaId=(:familiaId)")
    List<QuintaFamilia> getQuintaFamiliaByFamiliaId(long familiaId);

}
