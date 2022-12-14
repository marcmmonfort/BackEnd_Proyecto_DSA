package Managers;

import Entities.*;
import Entities.Exceptions.*;
import Entities.ValueObjects.*;

import java.util.*;
import org.apache.log4j.Logger;

public class PouGameManagerImpl implements PouGameManager {


    Map<String, Pou> pousGame; // Hashmap con todos los pous registrados. ---> KEY = "pouId" (String)

    Map<String, ObjetoTienda> objetosTienda; // Lista con todos los elementos de la tienda. ---> KEY = "articuloId" (Integer)

    private static PouGameManager instance;

    final static Logger logger = Logger.getLogger(PouGameManagerImpl.class);

    public static PouGameManager getInstance() {
        if (instance == null) instance = new PouGameManagerImpl();
        return instance;
    }

    public PouGameManagerImpl() {
        this.pousGame = new HashMap<>();
        this.objetosTienda = new HashMap<>();
    }

    // OPERACIÓN 1: OBTENER NÚMERO DE POUS

    @Override
    public int size() {
        int ret = this.pousGame.size();
        logger.info("Número de Pous: " + ret);
        return ret;
    }

    // OPERACIÓN 2: REGISTRO POU (CREAR POU)

    @Override
    public void crearPou(String pouId, String nombrePou, String nacimientoPou, String correo, String password) throws CorreoYaExisteException, PouIDYaExisteException {
        logger.info("Se quiere registrar un Pou con ID " + pouId + ".");
        Credenciales pouCredentials = new Credenciales(correo, password);
        Pou nuevoPou = new Pou(pouId, nombrePou, nacimientoPou, pouCredentials);
        // Recorremos los Pous registrados para ver si hay alguno con este correo.
        boolean mailYaExiste = false;
        List<Pou> listaPous = new ArrayList<>(this.pousGame.values());
        for (int i = 0; i < listaPous.size(); i++) {
            if (Objects.equals(listaPous.get(i).getCredencialesPou().getCorreoPou(), correo)) {
                mailYaExiste = true;
                break;
            }
        }
        if (this.pousGame.containsKey(pouId)) {
            logger.warn("El ID de Pou " + pouId + " ya existe.");
            throw new PouIDYaExisteException();
        } else if (mailYaExiste) {
            logger.warn("El correo " + correo + " ya está registrado en un Pou.");
            throw new CorreoYaExisteException();
        } else {
            this.pousGame.put(pouId, nuevoPou);
            logger.info("Pou creado con ID " + pouId + ".");
        }
    }

    // OPERACIÓN 3: LOGIN POU

    @Override
    public void loginPou(String correo, String password) throws CorreoNoExisteException, PasswordIncorrectaException {
        logger.info("Se quiere hacer login con el correo " + correo + " y la contraseña " + password + ".");
        boolean mailExiste = false;
        boolean contraCorrecta = false;
        String pouId = null;
        List<Pou> listaPous = new ArrayList<>(this.pousGame.values());
        for (int i = 0; i < listaPous.size(); i++) {
            if (Objects.equals(listaPous.get(i).getCredencialesPou().getCorreoPou(), correo)) {
                mailExiste = true;
                if (Objects.equals(listaPous.get(i).getCredencialesPou().getPasswordPou(), password)) {
                    contraCorrecta = true;
                    pouId = listaPous.get(i).getPouId();
                }
                break;
            }
        }
        if (!mailExiste) {
            logger.warn("No hay ningún Pou registrado con el correo " + correo + ".");
            throw new CorreoNoExisteException();
        } else if (!contraCorrecta) {
            logger.warn("La contraseña introducida, " + correo + ", no es la correcta para este correo.");
            throw new PasswordIncorrectaException();
        } else {
            logger.info("Se ha hecho correctamente el login en el Pou con ID " + pouId + ".");
        }
    }

    // OPERACIÓN 4: OBTENER TODOS LOS OBJETOS TIENDA

    @Override
    public List<ObjetoTienda> obtenerObjetosTienda() {
        List<ObjetoTienda> listaObjetosTienda = new ArrayList<>(this.objetosTienda.values());
        return listaObjetosTienda;
    }

    // OPERACIÓN 5: OBTENER TODOS LOS POUS

    @Override
    public Map<String, Pou> obtenerPous() {
        logger.info("Queremos obtener un Map con todos los pous registrados ");
        int num = this.pousGame.size();
        logger.info("Número de Pous: " + num);
        return this.pousGame;
    }

    // OPERACIÓN 6: OBTENER POU POR SU ID ("pouId")

    @Override
    public Pou obtenerPou(String pouId) throws PouIDNoExisteException {
        logger.info("Se quiere obtener el Pou que se identifica con el id " + pouId + ".");
        Pou pouEncontrado = new Pou();
        if (this.pousGame.containsKey(pouId)) {
            logger.info("El Pou si que existe. Vamos a realizar una búsqueda para encontrarlo.");
            if (this.pousGame.containsKey(pouId)){
                logger.info("El Pou sí que existe. Vamos a realizar una búsqueda para encontrarlo.");
                List<Pou> listaPous = new ArrayList<>(this.pousGame.values());
                for (int i = 0; i < listaPous.size(); i++) {
                    if (Objects.equals(listaPous.get(i).getPouId(), pouId)) {
                        pouEncontrado = listaPous.get(i);
                        logger.info("Se ha encontrado el Pou con id " + pouId + ".");
                    }
                }
            }
        }
        else {
            logger.info("El Pou con id " + pouId + " no existe.");
            throw new PouIDNoExisteException();
        }
        return pouEncontrado;
    }

    // OPERACIÓN 7: AÑADIR OBJETOS A LA TIENDA

    @Override
    public void addObjetosATienda(String articuloId, String nombreArticulo, double precioArticulo, String tipoArticulo, Integer recargaHambre, Integer recargaSalud, Integer recargaDiversion, Integer recargaSueno) throws ObjetoTiendaYaExisteException {
        logger.info("Se quiere añadir un ObjetoTienda con ID " + articuloId + ".");
        ObjetoTienda nuevoObjetoTienda = new ObjetoTienda(articuloId, nombreArticulo, precioArticulo, tipoArticulo, recargaHambre, recargaSalud, recargaDiversion, recargaSueno);
        if (this.objetosTienda.containsKey(articuloId)) {
            logger.warn("La ID del articulo " + articuloId + " ya existe.");
            throw new ObjetoTiendaYaExisteException();
        } else {
            this.objetosTienda.put(articuloId, nuevoObjetoTienda);
            logger.info("Pou creado con ID " + articuloId + ".");
        }
    }

    // OPERACIÓN 8: OBTENER OBJETO DE LA TIENDA POR SU ID ("articuloId")

    @Override

    public ObjetoTienda obtenerObjetoTienda(String articuloId) throws ObjetoTiendaNoExisteException {
        logger.info("Se quiere obtener el objeto de la tienda que se identifica con el id "+articuloId+".");
        ObjetoTienda objetoTiendaEncontrado = new ObjetoTienda();
        if (this.objetosTienda.containsKey(articuloId)){
            logger.info("El objeto con id "+articuloId+" sí que existe. Vamos a realizar una búsqueda para encontrarlo.");
            List<ObjetoTienda> listaObjetosTienda = new ArrayList<>(this.objetosTienda.values());
            for (ObjetoTienda objetoTienda : listaObjetosTienda) {
                if (Objects.equals(objetoTienda.getArticuloId(), articuloId)) {
                    objetoTiendaEncontrado = objetoTienda;
                    logger.info("Se ha encontrado el objeto con id " + articuloId + ".");
                }
            }
        }
        else{
            logger.info("El objeto con id "+articuloId+" no existe.");
            throw new ObjetoTiendaNoExisteException();
        }
        return objetoTiendaEncontrado;
    }

    // OPERACIÓN 9: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS COMIDAS DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerComidasTienda() {
        logger.info("Se quiere obtener las comidas de la tienda ordenadas por precio creciente");
        List<ObjetoTienda> listaComidas = this.listaObjetosTipo("Comida");
        int num = listaComidas.size();
        logger.info("Hay " + num + " comidas en la tienda");
        return listaComidas;
    }

    // OPERACIÓN 10: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS BEBIDAS DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerBebidasTienda() {
        logger.info("Se quiere obtener las bebidas de la tienda ordenadas por precio creciente");
        List<ObjetoTienda> listaBebidas = this.listaObjetosTipo("Bebida");
        int num = listaBebidas.size();
        logger.info("Hay " + num + " bebidas en la tienda");
        return listaBebidas;
    }

    // OPERACIÓN 11: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS POCIONES DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerPocionesTienda() {
        logger.info("Se quiere obtener las pociones de la tienda ordenadas por precio creciente");
        List<ObjetoTienda> listaPociones = this.listaObjetosTipo("Pocion");
        int num = listaPociones.size();
        logger.info("Hay " + num + " pociones en la tienda");
        return listaPociones;
    }

    // OPERACIÓN 12: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS ROPAS DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerRopasTienda() {
        logger.info("Se quiere obtener las prendas de ropa de la tienda ordenadas por precio creciente");
        List<ObjetoTienda> listaRopa = this.listaObjetosTipo("Ropa");
        int num = listaRopa.size();
        logger.info("Hay " + num + " prendas de ropa en la tienda");
        return listaRopa;
    }

    // OPERACIÓN 13: CREAR SALA (AÑADIENDO TAMBIEN LOS OBJETOS DE LA TIENDA QUE LE CORRESPONDAN)

    @Override
    public void crearSala(String pouId, String salaId, String nombreSala) throws SalaYaExisteException, PouIDNoExisteException {
        logger.info("Se quiere crear una sala con ID "+salaId+".");
        Pou miPou = this.obtenerPou(pouId);
        Sala nuevaSala = new Sala(salaId, nombreSala);
        if (miPou.getSalasPou().containsKey(salaId)){
            logger.info("La sala con ID "+salaId+" ya existe para el Pou con ID "+pouId+".");
            throw new SalaYaExisteException();
        }
        else{
            miPou.getSalasPou().put(salaId, nuevaSala);
            }
    }

    // OPERACIÓN 14: AÑADIR ELEMENTO ARMARIO POU (POU COMPRA UN OBJETO DE UNA SALA) (HAY QUE PONER CUANTOS)

    @Override
    public void pouCompraArticulos(String pouId, String articuloId, Integer cantidad) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {



    }

    // OPERACIÓN 15: BORRAR ELEMENTO ARMARIO POU (PORQUE SE HA CONSUMIDO) (SE RESTA 1 (UNITARIAMENTE))
    // Podríem fer aquí que aquí es cridi les funcions de modificar nivel i així és com que et prens la poció o lo que sigui

    @Override
    public ObjetoTienda pouConsumeArticulo(String pouId, String articuloId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {
        logger.info("Se quiere que el Pou con id " + pouId + " consuma un artículo de la tienda con id "+ articuloId + ".");
        Pou miPou = this.obtenerPou(pouId);
        ObjetoTienda miObjeto = this.obtenerObjetoTienda(articuloId);
        Armario miArmario = miPou.getArmarioPou();
        if (miArmario.getComidas().containsKey(articuloId)){
            logger.info("El artículo es una comida");
            int varNivel = miObjeto.getRecargaHambre();
            this.pouModificaNivelHambre(pouId, varNivel);
            Map<String, ObjetoTienda> map = miArmario.getBebidas();
            map.remove(miObjeto);
            miArmario.setBebidas(map);
            miPou.setArmarioPou(miArmario);
        }
        if (miArmario.getBebidas().containsKey(articuloId)){
            logger.info("El artículo es una bebida");
            int varNivel = miObjeto.getRecargaHambre();
            this.pouModificaNivelHambre(pouId, varNivel);
            Map<String, ObjetoTienda> map = miArmario.getBebidas();
            map.remove(miObjeto);
            miArmario.setBebidas(map);
            miPou.setArmarioPou(miArmario);
        }
        if (miArmario.getPociones().containsKey(articuloId)){
            logger.info("El artículo es una pocion");

        }
        if (miArmario.getRopa().containsKey(articuloId)){
            logger.info("El artículo es una prenda de ropa");
        }
        return miObjeto;
    }

    // OPERACIÓN 16: POU MODIFICA SU CAMISETA (OUTFIT)

    @Override
    public void pouCambiaCamiseta(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    // OPERACIÓN 17: POU MODIFICA SU PANTALON (OUTFIT)

    @Override
    public void pouCambiaPantalon(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    // OPERACIÓN 18: POU MODIFICA SU GORRA (OUTFIT)

    @Override
    public void pouCambiaGorra(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    // OPERACIÓN 19: POU MODIFICA SUS GAFAS (OUTFIT)

    @Override
    public void pouCambiaGafas(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    // OPERACIÓN 20: POU MODIFICA SU NIVEL DE HAMBRE

    @Override
    public void pouModificaNivelHambre(String pouId, Integer varNivelHambre) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {
        logger.info("Se quiere que el Pou con id "+pouId+" modifique su nivel de hambre en " +varNivelHambre+" puntos.");
        Pou miPou = this.obtenerPou(pouId);
        Estado miEstado = miPou.getEstadoPou();
        miEstado.setNivelHambrePou(miEstado.getNivelHambrePou()+varNivelHambre);
        if(miEstado.getNivelHambrePou()>100){
            miEstado.setNivelHambrePou(100);
            throw new NivelPorEncimaDelMaximoException();
        }
        if(miEstado.getNivelHambrePou()<0){
            miEstado.setNivelHambrePou(0);
            throw new NivelPorDebajoDelMinimoException();
        }

        miPou.setEstadoPou(miEstado);
        logger.info("El pou con id "+pouId+" tiene "+miEstado.getNivelHambrePou()+" nivel de hambre.");
    }

    // OPERACIÓN 21: POU MODIFICA SU NIVEL DE SALUD

    @Override
    public void pouModificaNivelSalud(String pouId, Integer varNivelSalud) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {
        logger.info("Se quiere que el Pou con id "+pouId+" modifique su nivel de hambre en " +varNivelSalud+" puntos.");
        Pou miPou = this.obtenerPou(pouId);
        Estado miEstado = miPou.getEstadoPou();
        miEstado.setNivelSaludPou(miEstado.getNivelSaludPou()+varNivelSalud);
        if(miEstado.getNivelSaludPou()>100){
            miEstado.setNivelSaludPou(100);
            throw new NivelPorEncimaDelMaximoException();
        }
        if(miEstado.getNivelSaludPou()<0){
            miEstado.setNivelSaludPou(0);
            throw new NivelPorDebajoDelMinimoException();
        }
        miPou.setEstadoPou(miEstado);
        logger.info("El pou con id "+pouId+" tiene "+miEstado.getNivelSaludPou()+" nivel de salud.");
    }


    // OPERACIÓN 22: POU MODIFICA SU NIVEL DE DIVERSION

    @Override
    public void pouModificaNivelDiversion(String pouId, Integer varNivelDiversion) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {
        logger.info("Se quiere que el Pou con id "+pouId+" modifique su nivel de hambre en " +varNivelDiversion+" puntos.");
        Pou miPou = this.obtenerPou(pouId);
        Estado miEstado = miPou.getEstadoPou();
        miEstado.setNivelDiversionPou(miEstado.getNivelDiversionPou()+varNivelDiversion);
        if(miEstado.getNivelDiversionPou()>100){
            miEstado.setNivelDiversionPou(100);
            throw new NivelPorEncimaDelMaximoException();
        }
        if(miEstado.getNivelDiversionPou()<0){
            miEstado.setNivelDiversionPou(0);
            throw new NivelPorDebajoDelMinimoException();
        }
        miPou.setEstadoPou(miEstado);
        logger.info("El pou con id "+pouId+" tiene "+miEstado.getNivelDiversionPou()+" nivel de diversión.");
    }

    // OPERACIÓN 23: POU MODIFICA SU NIVEL DE SUEÑO

    @Override
    public void pouModificaNivelSueno(String pouId, Integer varNivelSueno) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {
        logger.info("Se quiere que el Pou con id "+pouId+" modifique su nivel de hambre en " +varNivelSueno+" puntos.");
        Pou miPou = this.obtenerPou(pouId);
        Estado miEstado = miPou.getEstadoPou();
        miEstado.setNivelSuenoPou(miEstado.getNivelSuenoPou()+varNivelSueno);
        if(miEstado.getNivelSuenoPou()>100){
            miEstado.setNivelSuenoPou(100);
            throw new NivelPorEncimaDelMaximoException();
        }
        if(miEstado.getNivelSuenoPou()<0){
            miEstado.setNivelSuenoPou(0);
            throw new NivelPorDebajoDelMinimoException();
        }
        miPou.setEstadoPou(miEstado);
        logger.info("El pou con id "+pouId+" tiene "+varNivelSueno+" nivel de sueño.");
    }

    // OPERACIÓN 24: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS COMIDAS DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerComidasArmario() {
        return null;
    }

    // OPERACIÓN 25: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS BEBIDAS DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerBebidasArmario() {
        return null;
    }

    // OPERACIÓN 26: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS POCIONES DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerPocionesArmario() {
        return null;
    }

    // OPERACIÓN 27: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LA ROPA DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerRopaArmario() {
        return null;
    }

    // OPERACIÓN 28: POU GASTA DINERO / GANA DINERO.

    @Override
    public void pouModificaDinero(String pouId, double varDinero) throws PouIDNoExisteException, PouNoTieneDineroSuficienteException {

    }

    // OPERACIÓN 29: OBTENER EL NÚMERO DE ARTÍCULOS QUE HAY EN LA TIENDA.

    public Integer dameNumArticulosTienda() {
        return this.objetosTienda.size();
    }

    //OPERACIÓN 30: OBTENER UN LISTA DEL TIPO DE ARTÍCULO QUE SE PIDE.
    @Override
    public List<ObjetoTienda> listaObjetosTipo(String tipoArticulo) {
        logger.info("Se quiere obtener una lista de objetos de tipo " + tipoArticulo + ".");
        List<ObjetoTienda> listaObjetosTienda = new ArrayList<>(this.objetosTienda.values());
        List<ObjetoTienda> listaTipo = new ArrayList<>();
        for (int i = 0; i < this.objetosTienda.size(); i++) {
            if (Objects.equals(listaObjetosTienda.get(i).getTipoArticulo(), tipoArticulo)) {
                listaTipo.add(listaObjetosTienda.get(i));
            }
        }
        listaTipo.sort(Comparator.comparingDouble(ObjetoTienda::getPrecioArticulo));
        return listaTipo;
    }

    //OPERACIÓN 31: OBTENER UN POU A PARTIR DE UNOS CREDENCIALES
    @Override
    public Pou obtenerPouByCredentials(Credenciales credenciales) {
        logger.info("Se quiere obtener el pou que tenga el correo" + credenciales.getCorreoPou() + " y la contraseña " + credenciales.getPasswordPou() + ".");
        List<Pou> listaPous = new ArrayList<>(this.pousGame.values());
        String pouId = "";
        Pou miPou = new Pou();
        for (Pou pous : listaPous) {
            if (Objects.equals(pous.getCredencialesPou().getCorreoPou(), credenciales.getCorreoPou())) {
                if (Objects.equals(pous.getCredencialesPou().getPasswordPou(), credenciales.getPasswordPou())) {
                    pouId = pous.getPouId();
                    miPou = pous;
                }
                break;
            }
        }
        logger.info("La ID del Pou encontrado es" + pouId + ".");
        return miPou;
    }

}
