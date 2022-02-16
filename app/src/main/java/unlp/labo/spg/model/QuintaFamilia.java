package unlp.labo.spg.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import unlp.labo.spg.model.Familia;
import unlp.labo.spg.model.Quinta;


public class QuintaFamilia {
    @Embedded
    public Quinta quinta;
    @Relation(
            parentColumn = "familiaId",
            entityColumn = "id"
    )
    public Familia familia;

}
