package Entities;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sala {

    // ATRIBUTOS

    String salaId;
    String nombreSala;
    Map<String, ObjetoTienda> productos;

    // CONSTRUCTORES

    public Sala() {}

    public Sala(String salaId, String nombreSala) {
        this.salaId = salaId;
        this.nombreSala = nombreSala;
        this.productos = new HashMap<>();
    }

    // GETTERS Y SETTERS

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public  Map<String, ObjetoTienda> getProductos() {
        return productos;
    }

    public void setProductos( Map<String, ObjetoTienda> productos) {
        this.productos = productos;
    }
}
