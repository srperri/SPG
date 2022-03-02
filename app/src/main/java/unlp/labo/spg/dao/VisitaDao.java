package unlp.labo.spg.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import unlp.labo.spg.model.VisitaQuintaFamilia;
import unlp.labo.spg.model.Detalle;
import unlp.labo.spg.model.Visita;
import unlp.labo.spg.model.VisitaDetalle;

import java.util.Date;
import java.util.List;

@Dao
public interface VisitaDao {
    @Query("SELECT * FROM Visita where id=:id")
    Visita getById(long id);

    @Transaction
    @Query("SELECT visita.* FROM visita "+
            "join quinta on visita.quintaId=quinta.id join familia on quinta.familiaId=familia.id "+
            "where familia.userId=(:userId) order by visita.fecha desc")
    List<VisitaQuintaFamilia> getVisitaQuintaFamilia(long userId);

    @Transaction
    @Query("SELECT visita.* FROM visita "+
            "join quinta on visita.quintaId=quinta.id join familia on quinta.familiaId=familia.id "+
            "where familia.userId=(:userId) "+
            "and upper(quinta.nombre) like  '%' || upper(:quintaNombre)|| '%' "+
            "and upper(familia.nombre) like  '%' || upper(:familiaNombre)|| '%' "+
            "and (:fecha is null or visita.fecha=(:fecha)) "+
            "order by visita.fecha desc")
    List<VisitaQuintaFamilia> getVisitaQuintaFamilia(long userId, String quintaNombre, String familiaNombre, Date fecha);

    @Transaction
    @Query("SELECT * FROM Visita where quintaId=(:quintaId)  order by fecha desc")
    List<VisitaQuintaFamilia> getVisitaQuintaFamiliaByQuintaId(long quintaId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Visita visita);

    @Transaction
    @Query("DELETE FROM Visita where id=:visitaId")
    void deleteById(long visitaId);

    @Transaction
    @Query("SELECT * FROM Visita where id=:id")
    VisitaDetalle getVisitaDetalleById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Detalle> detalles);

    @Transaction
    default long insertVisitaDetalle(VisitaDetalle visitaDetalle){
        long newId= insert(visitaDetalle.visita);
        for (Detalle d :visitaDetalle.detalles) {
            d.visitaId = newId;
        }
        insertAll(visitaDetalle.detalles);
        return newId;
        //insert(visitaDetalle);
    }

}
