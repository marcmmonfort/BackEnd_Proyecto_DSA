package Entities;

import java.util.ArrayList;
import java.util.List;

public class Sala {

    // ATRIBUTOS

    Integer salaId;
    String nombreSala;
    List<ObjetoTienda> productos;

    //CONSTRUCTORES

    public Sala() {}

    public Sala(Integer salaId, String nombreSala) {
        this.salaId = salaId;
        this.nombreSala = nombreSala;
        this.productos = new ArrayList<>();
    }

    //GETTERS Y SETTERS

    public Integer getSalaId() {
        return salaId;
    }

    public void setSalaId(Integer salaId) {
        this.salaId = salaId;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public List<ObjetoTienda> getProductos() {
        return productos;
    }

    public void setProductos(List<ObjetoTienda> productos) {
        this.productos = productos;
    }
}
