package unlp.labo.spg.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import unlp.labo.spg.model.Quinta;
import unlp.labo.spg.model.QuintaFamilia;
import unlp.labo.spg.model.Visita;

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


