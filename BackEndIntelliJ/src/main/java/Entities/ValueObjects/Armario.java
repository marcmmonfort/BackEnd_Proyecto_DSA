package Entities.ValueObjects;

import Entities.ObjetoTienda;

import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Armario {

    // ATRIBUTOS

    Map<String, ObjetoTienda> comidas;
    Map<String, ObjetoTienda> bebidas;
    Map<String, ObjetoTienda> pociones;
    Map<String, ObjetoTienda> ropa;

    // CONSTRUCTOR

    public Armario() {
        this.comidas = new HashMap<>();
        this.bebidas = new HashMap<>();
        this.pociones = new HashMap<>();
        this.ropa = new HashMap<>();
    }

    // GETTER Y SETTERS


    public Map<String, ObjetoTienda> getComidas() {
        return comidas;
    }

    public void setComidas(Map<String, ObjetoTienda> comidas) {
        this.comidas = comidas;
    }

    public Map<String, ObjetoTienda> getBebidas() {
        return bebidas;
    }

    public void setBebidas(Map<String, ObjetoTienda> bebidas) {
        this.bebidas = bebidas;
    }

    public Map<String, ObjetoTienda> getPociones() {
        return pociones;
    }

    public void setPociones(Map<String, ObjetoTienda> pociones) {
        this.pociones = pociones;
    }

    public Map<String, ObjetoTienda> getRopa() {
        return ropa;
    }

    public void setRopa(Map<String, ObjetoTienda> ropa) {
        this.ropa = ropa;
    }
}
