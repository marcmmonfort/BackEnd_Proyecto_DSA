package Entities;

import Entities.ValueObjects.*;

import java.util.*;

public class Pou {

    // ATRIBUTOS

    String pouId;
    String nombrePou;
    String nacimientoPou;
    String correoPou;
    String passwordPou;
    int dineroPou;
    Integer nivelHambrePou;
    Integer nivelSaludPou;
    Integer nivelDiversionPou;
    Integer nivelSuenoPou;
    Integer camisetaId;
    Integer pantalonId;
    Integer gorraId;
    Integer gafasId;



    // CONSTRUCTORES

    public Pou() {}
    public Pou(String pouId, String nombrePou, String nacimientoPou, String correoPou, String passwordPou) {
        this.pouId = pouId;
        this.nombrePou = nombrePou;
        this.nacimientoPou = nacimientoPou;
        this.correoPou = correoPou;
        this.passwordPou = passwordPou;
        this.dineroPou = 100;
        this.nivelDiversionPou = 100;
        this.nivelHambrePou = 100;
        this.nivelSaludPou = 100;
        this.nivelSuenoPou = 100;
        this.camisetaId = 0;
        this.pantalonId = 0;
        this.gorraId = 0;
        this.gafasId = 0;
    }

    // GETTERS Y SETTERS

    public String getPouId() {
        return pouId;
    }

    public void setPouId(String pouId) {
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

    public String getCorreoPou() {
        return correoPou;
    }

    public void setCorreoPou(String correoPou) {
        this.correoPou = correoPou;
    }

    public String getPasswordPou() {
        return passwordPou;
    }

    public void setPasswordPou(String passwordPou) {
        this.passwordPou = passwordPou;
    }

    public int getDineroPou() {
        return dineroPou;
    }

    public void setDineroPou(int dineroPou) {
        this.dineroPou = dineroPou;
    }

    public Integer getNivelHambrePou() {
        return nivelHambrePou;
    }

    public void setNivelHambrePou(Integer nivelHambrePou) {
        this.nivelHambrePou = nivelHambrePou;
    }

    public Integer getNivelSaludPou() {
        return nivelSaludPou;
    }

    public void setNivelSaludPou(Integer nivelSaludPou) {
        this.nivelSaludPou = nivelSaludPou;
    }

    public Integer getNivelDiversionPou() {
        return nivelDiversionPou;
    }

    public void setNivelDiversionPou(Integer nivelDiversionPou) {
        this.nivelDiversionPou = nivelDiversionPou;
    }

    public Integer getNivelSuenoPou() {
        return nivelSuenoPou;
    }

    public void setNivelSuenoPou(Integer nivelSuenoPou) {
        this.nivelSuenoPou = nivelSuenoPou;
    }

    public Integer getCamisetaId() {
        return camisetaId;
    }

    public void setCamisetaId(Integer camisetaId) {
        this.camisetaId = camisetaId;
    }

    public Integer getPantalonId() {
        return pantalonId;
    }

    public void setPantalonId(Integer pantalonId) {
        this.pantalonId = pantalonId;
    }

    public Integer getGorraId() {
        return gorraId;
    }

    public void setGorraId(Integer gorraId) {
        this.gorraId = gorraId;
    }

    public Integer getGafasId() {
        return gafasId;
    }

    public void setGafasId(Integer gafasId) {
        this.gafasId = gafasId;
    }
}
