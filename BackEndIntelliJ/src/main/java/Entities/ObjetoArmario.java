package Entities;

public class ObjetoArmario {

    // ATRIBUTOS

    int idArmario;
    String pouId;
    String tipoObjeto;
    String idProducto;
    Integer cantidad;


    // CONSTRUCTOR
    public ObjetoArmario(){}

    public ObjetoArmario(int idArmario, String pouId, String tipoObjeto, String idProducto, Integer cantidad) {
        this.idArmario = idArmario;
        this.pouId = pouId;
        this.tipoObjeto = tipoObjeto;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdArmario() {
        return idArmario;
    }

    public void setIdArmario(int idArmario) {
        this.idArmario = idArmario;
    }

    public String getPouId() {
        return pouId;
    }

    public void setPouId(String pouId) {
        this.pouId = pouId;
    }

    public String getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(String tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
