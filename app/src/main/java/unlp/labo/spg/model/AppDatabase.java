package unlp.labo.spg.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import unlp.labo.spg.dao.FamiliaDao;
import unlp.labo.spg.dao.PersonaDao;
import unlp.labo.spg.dao.QuintaDao;
import unlp.labo.spg.dao.UserDao;
import unlp.labo.spg.dao.VisitaDao;

@Database(
        entities = {
                User.class,
                Familia.class,
                Quinta.class,
                Visita.class,
                Detalle.class,
                Persona.class,
        }, version = 21, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract FamiliaDao familiaDao();

    public abstract QuintaDao quintaDao();

    public abstract VisitaDao visitaDao();

    public abstract PersonaDao personaDaoDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "labo_spg_db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
