package unlp.labo.spg.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import unlp.labo.spg.model.QuintaFamilia;
import unlp.labo.spg.model.Quinta;

import java.util.List;
@Dao
public interface QuintaDao {
        @Query("SELECT * FROM quinta")
        List<Quinta> getAll();

        @Query("SELECT * FROM quinta WHERE id = (:id)")
        Quinta getById(long id);

        @Query("SELECT * FROM quinta WHERE familiaId = (:familiaId)")
        List<Quinta> getByFamiliaId(long familiaId);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        long insert(Quinta quinta);

        @Query("DELETE FROM quinta")
        void deleteAll();

        @Transaction
        @Query("SELECT * FROM quinta")
        List<QuintaFamilia> getQuintaYFamilia();


}
