package unlp.labo.spg.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class VisitaQuintaFamilia {

    @Embedded
    public Visita visita;
    @Relation(
            entity = Quinta.class,
            parentColumn = "quintaId",
            entityColumn = "id"
    )
    public QuintaFamilia quintaFamilia;

}


