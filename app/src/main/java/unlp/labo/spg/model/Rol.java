package unlp.labo.spg.model;

import java.io.Serializable;

public enum Rol implements Serializable {
    TECNICO(0, "Técnico"),
    PRODUCTOR(1, "Productor"),
    CONSUMIDOR(2, "Consumidor");
    private final int id;
    private final String descripcion;

    Rol(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int id() {
        return id;
    }

    public String descripcion() {
        return descripcion;
    }
}
