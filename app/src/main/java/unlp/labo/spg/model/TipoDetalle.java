package unlp.labo.spg.model;

import java.io.Serializable;

public enum TipoDetalle implements Serializable {
    ROTACION(0, "Rotación", ""),
    VARIACION_CAMPO(1, "Variación", "Campo"),
    VARIACION_INVER(2, "Variación", "Invernáculo"),
    COMBINACION(3, "Combinación", ""),
    BARRERA_CAMPO(4, "Barrera", "Campo"),
    BARRERA_INVER(5, "Barrera", "Invernáculo"),
    CORDON_CAMPO(6, "Cordón", "Campo"),
    CORDON_INVER(7, "Cordón", "Invernáculo");
    private final int id;
    private final String titulo;
    private final String subtitulo;

    TipoDetalle(int id, String titulo, String subtitulo) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
    }

    public int id() {
        return id;
    }

    public String titulo() {
        return titulo;
    }

    public String subtitulo() {
        return subtitulo;
    }

}
