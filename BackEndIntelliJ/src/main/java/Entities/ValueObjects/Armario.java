package Entities.ValueObjects;

import Entities.ObjetoTienda;

import java.util.LinkedList;
import java.util.List;

public class Armario {

    //ATRIBUTOS

    List<ObjetoTienda> comidas;
    List<ObjetoTienda> bebidas;
    List<ObjetoTienda> pociones;
    List<ObjetoTienda> ropa;

    //CONSTRUCTORES

    public Armario() {
        this.comidas = new LinkedList<>();
        this.bebidas = new LinkedList<>();
        this.pociones = new LinkedList<>();
        this.ropa = new LinkedList<>();
    }

    //GETTER Y SETTERS

    public List<ObjetoTienda> getComidas() {
        return comidas;
    }

    public void setComidas(List<ObjetoTienda> comidas) {
        this.comidas = comidas;
    }

    public List<ObjetoTienda> getBebidas() {
        return bebidas;
    }

    public void setBebidas(List<ObjetoTienda> bebidas) {
        this.bebidas = bebidas;
    }

    public List<ObjetoTienda> getPociones() {
        return pociones;
    }

    public void setPociones(List<ObjetoTienda> pociones) {
        this.pociones = pociones;
    }

    public List<ObjetoTienda> getRopa() {
        return ropa;
    }

    public void setRopa(List<ObjetoTienda> ropa) {
        this.ropa = ropa;
    }

}
