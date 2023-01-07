package Managers;

import Entities.Exceptions.*;
import Entities.ObjetoArmario;
import Entities.ObjetoTienda;
import Entities.Pou;
import Entities.ValueObjects.Credenciales;
import edu.upc.eetac.dsa.FactorySession;
import edu.upc.eetac.dsa.Session;
import org.apache.log4j.Logger;

import java.util.*;

public class PouGameDBManagerImpl implements PouGameManager {

    Session session;

    Map<String, Pou> pousGame; // Hashmap con todos los pous registrados. ---> KEY = "pouId" (String)
    List<ObjetoTienda> objetosTienda; // Lista con todos los elementos de la tienda. ---> KEY = "articuloId" (Integer)

    Map<String, ObjetoArmario> objetosArmario; // Lista con todos los elementos del armario. ---> KEY = "articuloId" (Integer)

    private static PouGameManager instance;
    final static org.apache.log4j.Logger logger = Logger.getLogger(PouGameManagerImpl.class);

    public static PouGameManager getInstance(){
        if (instance==null) instance = new PouGameDBManagerImpl();
        return instance;
    }

    public PouGameDBManagerImpl(){
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/crud","eloim", "YES");
        this.pousGame = new HashMap<>();
        this.objetosTienda = new ArrayList<>();
        this.objetosArmario = new HashMap<>();
        this.pousGame = this.obtenerPous();
        this.objetosTienda = this.obtenerObjetosTienda();
    }

    public int size() {
        int ret = this.pousGame.size();
        return ret;
    }

    @Override
    public void crearPou(String pouId, String nombrePou, String nacimientoPou, String correo, String password) throws CorreoYaExisteException, PouIDYaExisteException {
        Pou miPou = new Pou(pouId,nombrePou,nacimientoPou,correo, password);
        List<Pou> listaPous= this.session.findAll(Pou.class);

        for(Pou p:listaPous){
            if(Objects.equals(p.getCorreoPou(),correo)){
                throw new CorreoYaExisteException();
            }
        }
        this.session.save(miPou);
        this.pousGame.put(pouId,miPou);
        logger.info("El usuario pou "+ pouId + " se ha añadido correctamente");
    }

    @Override
    public void loginPou(String correo, String password) throws CorreoNoExisteException, PasswordIncorrectaException {
        List<Object> listaPous = this.session.findAll(Pou.class);
        boolean mailExiste = false;
        boolean contraCorrecta = false;
        String pouId = null;
        for(Object pou : listaPous){
            Pou p = (Pou) pou;
            if(Objects.equals(p.getCorreoPou(), correo)) {
                mailExiste = true;
                if (Objects.equals(p.getPasswordPou(), password)) {
                    contraCorrecta = true;
                    pouId = p.getPouId();
                }
            }
        }
        if (!mailExiste) {
            logger.warn("No hay ningún Pou registrado con el correo " + correo + ".");
            throw new CorreoNoExisteException();
        } else if (!contraCorrecta) {
            logger.warn("La contraseña introducida, " + password + ", no es la correcta para este correo.");
            throw new PasswordIncorrectaException();
        } else {
            logger.info("Se ha hecho correctamente el login en el Pou con ID " + pouId + ".");
        }
    }

    @Override
    public List<ObjetoTienda> obtenerObjetosTienda() {
        List<ObjetoTienda> list = this.session.findAll(ObjetoTienda.class);
        return list;
    }

    @Override
    public Map<String, Pou> obtenerPous() {
        List<Object> listaPous= this.session.findAll(Pou.class);
        for(int i=0; i<listaPous.size();i++) {
            Pou pou = (Pou) listaPous.get(i);
            this.pousGame.put(pou.getPouId(), pou);
        }
        return pousGame;
    }


    @Override
    public Map<String, ObjetoArmario> obtenerObjetosArmarioPou(String pouId) {
        Map<String, ObjetoArmario> armarioPou = new HashMap<>();
        List<ObjetoArmario> armarioPouLista = (List<ObjetoArmario>) this.session.getElementos(ObjetoArmario.class, "pouId",pouId);
        for(int i=0; i<armarioPouLista.size();i++) {
            armarioPou.put(armarioPouLista.get(i).getIdArticulo(),armarioPouLista.get(i));
        }
        return armarioPou;
    }

    @Override
    public Pou obtenerPou(String pouId) throws PouIDNoExisteException {
        Pou p = (Pou) this.session.get(Pou.class, pouId);
        return p;
    }

    @Override
    public void addObjetosATienda(String articuloId, String nombreArticulo, Integer precioArticulo, String tipoArticulo, Integer recargaHambre, Integer recargaSalud, Integer recargaDiversion, Integer recargaSueno) throws ObjetoTiendaYaExisteException {
        ObjetoTienda o = new ObjetoTienda(articuloId,nombreArticulo,precioArticulo,tipoArticulo,recargaHambre,recargaSalud,recargaDiversion,recargaSueno);
        this.objetosTienda.add(o);
        this.session.save(o);

    }

    @Override
    public ObjetoTienda obtenerObjetoTienda(String articuloId) throws ObjetoTiendaNoExisteException {
        ObjetoTienda o = (ObjetoTienda) this.session.get(ObjetoTienda.class,articuloId);
        return o;
    }

    @Override
    public List<ObjetoTienda> obtenerComidasTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerBebidasTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerPocionesTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerRopasTienda() {
        return null;
    }

    @Override
    public void pouCompraArticulos(String pouId, String articuloId, Integer cantidad, String tipoArticulo) throws ObjetoTiendaNoExisteException, PouIDNoExisteException, PouNoTieneDineroSuficienteException {
        List<ObjetoTienda> listaTienda = (List<ObjetoTienda>) this.session.getElementos(ObjetoTienda.class, "articuloId", articuloId);
        ObjetoTienda objetoTienda = listaTienda.get(0);
        Pou pou = (Pou) this.session.get(Pou.class,pouId);
        String tipoProducto = objetoTienda.getTipoArticulo();
        int precio = (int) objetoTienda.getPrecioArticulo();
        int descuento = precio * cantidad;
        if(pou.getDineroPou()-descuento < 0){
            throw new PouNoTieneDineroSuficienteException();
        }
        pou.setDineroPou(pou.getDineroPou()-descuento);
        Map<String, ObjetoArmario> miArmario = this.obtenerObjetosArmarioPou(pouId);
        List<ObjetoArmario> listaArmario = new ArrayList<>(miArmario.values());
        this.session.update(pou);
        for(int i=0;i< listaArmario.size();i++){
            if(Objects.equals(listaArmario.get(i).getIdArticulo(), articuloId)){
                int idArmario = listaArmario.get(i).getIdArmario();
                int nuevaCantidad = listaArmario.get(i).getCantidad()+cantidad;
                ObjetoArmario a = new ObjetoArmario(idArmario,pouId,tipoProducto,articuloId, nuevaCantidad);
                logger.info("El objeto es "+articuloId+ " y tendrá " +nuevaCantidad + ".");
                this.session.update(a);
                return;
            }
        }
        List<Object> lista = this.session.findAll(ObjetoArmario.class);
        int idArmario = lista.size();
        ObjetoArmario b = new ObjetoArmario(idArmario,pouId,tipoProducto,articuloId,cantidad);
        this.session.save(b);
        logger.info("El objeto añadido es: " + articuloId);
    }

    @Override
    public ObjetoArmario pouConsumeArticulo(String pouId, String articuloId) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException, ObjetoArmarioNoDisponible {
        return null;
    }

    @Override
    public void pouCambiaCamiseta(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouCambiaZapatos(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouCambiaGorra(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouCambiaGafas(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouModificaNivelHambre(String pouId, Integer varNivelHambre) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public void pouModificaNivelSalud(String pouId, Integer varNivelSalud) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public void pouModificaNivelDiversion(String pouId, Integer varNivelDiversion) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public void pouModificaNivelSueno(String pouId, Integer varNivelSueno) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public List<ObjetoArmario> obtenerComidasArmario() {
        return null;
    }

    @Override
    public List<ObjetoArmario> obtenerBebidasArmario() {
        return null;
    }

    @Override
    public List<ObjetoArmario> obtenerPocionesArmario() {
        return null;
    }

    @Override
    public List<ObjetoArmario> obtenerRopaArmario() {
        return null;
    }

    @Override
    public void pouModificaDinero(String pouId, double varDinero) throws PouIDNoExisteException, PouNoTieneDineroSuficienteException {

    }

    @Override
    public Integer dameNumArticulosTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> listaObjetosTiendaTipo(String tipoArticulo) {
        return null;
    }

    @Override
    public List<ObjetoArmario> listaObjetosArmarioTipo(String tipoArticulo) {
        return null;
    }

    @Override
    public List<ObjetoTienda> listaObjetosTipo(String tipoArticulo) {

        return null;
    }

    @Override
    public Pou obtenerPouByCredentials(Credenciales credenciales) {
        logger.info("Se quiere obtener el pou que tenga el correo" + credenciales.getCorreoPou() + " y la contraseña " + credenciales.getPasswordPou() + ".");
        List<Pou> listaPous = new ArrayList<>(this.pousGame.values());
        String pouId = "";
        Pou miPou = new Pou();
        for (Pou pous : listaPous) {
            if (Objects.equals(pous.getCorreoPou(), credenciales.getCorreoPou())) {
                if (Objects.equals(pous.getPasswordPou(), credenciales.getPasswordPou())) {
                    pouId = pous.getPouId();
                    miPou = pous;
                }
                break;
            }
        }
        logger.info("La ID del Pou encontrado es " + pouId + ".");
        return miPou;
    }

    @Override
    public ObjetoArmario obtenerObjetoArmario(String pouId) throws PouIDNoExisteException {
        return null;
    }

    @Override
    public void addObjetosAArmario(int idArmario, String pouId, String tipoArticulo, String idArticulo, Integer cantidad) {
        ObjetoArmario a = new ObjetoArmario(idArmario,pouId,tipoArticulo, idArticulo,cantidad);
        this.session.save(a);
        this.objetosArmario.put(pouId, a);
    }

    @Override
    public void updateObjeto(Object objeto) {
        this.session.update(objeto);
    }
/*
    @Override
    public List<ObjetoTienda> obtenerObjetosTienda() {
        return null;
    }

    @Override
    public Map<String, Pou> obtenerPous() {
        return null;
    }

    @Override
    public Pou obtenerPou(String pouId) throws PouIDNoExisteException {
        return null;
    }

    @Override
    public void addObjetosATienda(String articuloId, String nombreArticulo, double precioArticulo, String tipoArticulo, Integer recargaHambre, Integer recargaSalud, Integer recargaDiversion, Integer recargaSueno) throws ObjetoTiendaYaExisteException {

    }

    @Override
    public ObjetoTienda obtenerObjetoTienda(String articuloId) throws ObjetoTiendaNoExisteException {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerComidasTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerBebidasTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerPocionesTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerRopasTienda() {
        return null;
    }

    @Override
    public void crearSala(String pouId, String salaId, String nombreSala) throws SalaYaExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouCompraArticulos(String pouId, String articuloId, Integer cantidad) throws SalaNoExisteException, ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public ObjetoTienda pouConsumeArticulo(String pouId, String articuloId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {
        return null;
    }

    @Override
    public void pouCambiaCamiseta(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouCambiaPantalon(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouCambiaGorra(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouCambiaGafas(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    @Override
    public void pouModificaNivelHambre(String pouId, Integer varNivelHambre) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public void pouModificaNivelSalud(String pouId, Integer varNivelSalud) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public void pouModificaNivelDiversion(String pouId, Integer varNivelDiversion) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public void pouModificaNivelSueno(String pouId, Integer varNivelSueno) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    @Override
    public List<ObjetoTienda> obtenerComidasArmario() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerBebidasArmario() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerPocionesArmario() {
        return null;
    }

    @Override
    public List<ObjetoTienda> obtenerRopaArmario() {
        return null;
    }

    @Override
    public void pouModificaDinero(String pouId, double varDinero) throws PouIDNoExisteException, PouNoTieneDineroSuficienteException {

    }

    @Override
    public Integer dameNumArticulosTienda() {
        return null;
    }

    @Override
    public List<ObjetoTienda> listaObjetosTipo(String tipoArticulo) {
        return null;
    }

*/
}
