package unlp.labo.spg.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import unlp.labo.spg.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE userName = :userName AND " +
            "password = :password")
    User validate(String userName, String password);

    @Query("SELECT (count(*)=0) as vacio FROM user")
    boolean empty();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

}
