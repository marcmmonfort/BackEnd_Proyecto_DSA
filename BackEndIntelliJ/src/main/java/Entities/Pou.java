package Entities;

import Entities.ValueObjects.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Pou {

    //ATRIBUTOS
    Integer pouId;
    String nombrePou;
    String nacimientoPou;
    Credenciales credencialesPou;
    Estado estadoPou;
    Outfit outfitPou;
    Armario armarioPou;

    //CONSTRUCTORES

    public Pou() {}
    public Pou(Integer pouId, String nombrePou, String nacimientoPou, Credenciales credencialesPou, Estado estadoPou, Outfit outfitPou, Armario armarioPou) {
        this.pouId = pouId;
        this.nombrePou = nombrePou;
        this.nacimientoPou = nacimientoPou;
        this.credencialesPou = credencialesPou;
        this.estadoPou = new Estado(100,100,100,100,100);
        this.outfitPou = new Outfit(0,0,0,0);
        this.armarioPou = new Armario();
    }

    //GETTERS Y SETTERS

    public Integer getPouId() {
        return pouId;
    }

    public void setPouId(Integer pouId) {
        this.pouId = pouId;
    }

    public String getNombrePou() {
        return nombrePou;
    }

    public void setNombrePou(String nombrePou) {
        this.nombrePou = nombrePou;
    }

    public String getNacimientoPou() {
        return nacimientoPou;
    }

    public void setNacimientoPou(String nacimientoPou) {
        this.nacimientoPou = nacimientoPou;
    }

    public Credenciales getCredencialesPou() {
        return credencialesPou;
    }

    public void setCredencialesPou(Credenciales credencialesPou) {
        this.credencialesPou = credencialesPou;
    }

    public Estado getEstadoPou() {
        return estadoPou;
    }

    public void setEstadoPou(Estado estadoPou) {
        this.estadoPou = estadoPou;
    }

    public Outfit getOutfitPou() {
        return outfitPou;
    }

    public void setOutfitPou(Outfit outfitPou) {
        this.outfitPou = outfitPou;
    }

    public Armario getArmarioPou() {
        return armarioPou;
    }

    public void setArmarioPou(Armario armarioPou) {
        this.armarioPou = armarioPou;
    }
}
