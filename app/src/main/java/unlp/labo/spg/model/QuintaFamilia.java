package unlp.labo.spg.model;

import androidx.room.Embedded;
import androidx.room.Relation;


public class QuintaFamilia {
    @Embedded
    public Quinta quinta;
    @Relation(
            parentColumn = "familiaId",
            entityColumn = "id"
    )
    public Familia familia;

}
