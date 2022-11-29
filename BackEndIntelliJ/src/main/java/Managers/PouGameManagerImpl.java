package Managers;

import Entities.*;
import Entities.Exceptions.*;
import Entities.ValueObjects.*;

import java.util.*;
import org.apache.log4j.Logger;

public class PouGameManagerImpl implements PouGameManager {

    Map<String, Pou> pousGame; // Hashmap on  todos los pous registrados. ---> KEY = "pouId" (String)
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
        if (this.pousGame.containsKey(pouId) == true) {
            logger.info("El Pou si que existe. Vamos a realizar una búsqueda para encontrarlo.");
            List<Pou> listaPous = new ArrayList<>(this.pousGame.values());
            for (int i = 0; i < listaPous.size(); i++) {
                if (Objects.equals(listaPous.get(i).getPouId(), pouId)) {
                    pouEncontrado = listaPous.get(i);
                    logger.info("Se ha encontrado el Pou con id " + pouId + ".");
                }
            }
        } else {
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
        return null;
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
        return null;
    }

    // OPERACIÓN 11: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS POCIONES DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerPocionesTienda() {
        return null;
    }

    // OPERACIÓN 12: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS ROPAS DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerRopasTienda() {
        return null;
    }

    // OPERACIÓN 13: CREAR SALA (AÑADIENDO TAMBIEN LOS OBJETOS DE LA TIENDA QUE LE CORRESPONDAN)

    @Override
    public void crearSala(String pouId, String salaId, String nombreSala) throws SalaYaExisteException {

    }

    // OPERACIÓN 14: AÑADIR ELEMENTO ARMARIO POU (POU COMPRA UN OBJETO DE UNA SALA) (HAY QUE PONER CUANTOS)

    @Override
    public void pouCompraArticulos(String pouId, String articuloId, Integer cantidad) throws SalaNoExisteException, ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    // OPERACIÓN 15: BORRAR ELEMENTO ARMARIO POU (PORQUE SE HA CONSUMIDO) (SE RESTA 1 (UNITARIAMENTE))

    @Override
    public ObjetoTienda pouConsumeArticulo(String pouId, String articuloId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {
        return null;
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

    }

    // OPERACIÓN 21: POU MODIFICA SU NIVEL DE SALUD

    @Override
    public void pouModificaNivelSalud(String pouId, Integer varNivelSalud) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    // OPERACIÓN 22: POU MODIFICA SU NIVEL DE DIVERSION

    @Override
    public void pouModificaNivelDiversion(String pouId, Integer varNivelDiversion) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

    }

    // OPERACIÓN 23: POU MODIFICA SU NIVEL DE SUEÑO

    @Override
    public void pouModificaNivelSueno(String pouId, Integer varNivelSueno) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException {

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


    // ----------------------------------------------------------------------------------------------------

    // VIEJO ...

    /*
    @Override
    public int numUsuarios(){
        return this.usuarios.size();
    }

    @Override
    public int numJuegos(){
        return this.juegos.size();
    }

    public Juego dameJuego(String juegoId){
        return this.juegos.get(juegoId);
    }

    public Pou dameUsuario(String usuarioId){
        return this.usuarios.get(usuarioId);
    }

    public Partida damePartidaUsuario(String usuarioId){
        return this.partidasUsuarios.get(usuarioId);
    }

    @Override
    public void crearUsuario(String usuario){
        logger.info("Se quiere crear un usuario con ID "+usuario+".");
        Pou nuevoUsuario = new Pou(usuario);
        usuarios.put(usuario, nuevoUsuario);
        Partida nuevaPartida = new Partida();
        partidasUsuarios.put(usuario, nuevaPartida);
        logger.info("Usuario creado con ID "+usuario+".");
    }

    // OPERACION 1: Crear un Juego.
    @Override
    public void crearJuego(String juegoId, String juegoDescripcion, int numeroNivelesJuego){
        logger.info("Se quiere crear un juego con ID "+juegoId+", Descripcion "+juegoDescripcion+" y "+numeroNivelesJuego+" Niveles.");
        Juego nuevoJuego = new Juego(juegoId, juegoDescripcion, numeroNivelesJuego);
        juegos.put(juegoId, nuevoJuego);
        logger.info("Juego creado con ID "+juegoId+", Descripcion "+juegoDescripcion+" y "+numeroNivelesJuego+" Niveles.");
    }

    // OPERACION 2: Iniciar una Partida (por parte de un Usuario).
    @Override
    public void iniciarPartida (String juegoId, String usuarioId) throws JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        logger.info("Se quiere crear una partida del Usuario "+usuarioId+" en el Juego "+juegoId+".");
        Partida nuevaPartida = new Partida (juegoId, usuarioId);
        if (!this.juegos.containsKey(juegoId)){
            logger.warn("El juego "+juegoId+" no existe.");
            throw new JuegoIdNoExisteException();
        } else if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else if (partidasUsuarios.get(usuarioId).isPartidaEnCurso()) {
            logger.warn("El usuario "+usuarioId+" ya está en una partida.");
            throw new UsuarioIdYaEstaEnPartidaException();
        } else {
            partidasUsuarios.put(usuarioId, nuevaPartida);
            logger.info("Partida creada para el "+usuarioId+" en el Juego "+juegoId+".");
        }
    }

    // OPERACION 3: Pedir el Nivel Actual de la Partida en la que está el Usuario introducido.
    @Override
    public int pedirNivelJuegoDePartida (String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException {
        logger.info("Se quiere obtener el nivel de la partida en la que está "+usuarioId+".");
        if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario " + usuarioId + " no existe.");
            throw new UsuarioIdNoExisteException();
        } else {
            int nivelActual = partidasUsuarios.get(usuarioId).getNivelActual();
            logger.info("El usuario "+usuarioId+" está en el nivel "+nivelActual+".");
            return nivelActual;
        }
    }

    // OPERACION 4: Pedir la Puntuación Actual en una Partida (por parte de un Usuario).
    @Override
    public int pedirPuntosDePartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException{
        logger.info("Se quiere obtener la puntuación de la partida en la que está "+usuarioId+".");
        if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else {
            int puntos = partidasUsuarios.get(usuarioId).getPuntosTotales();
            logger.info("El usuario "+usuarioId+" de momento tiene "+puntos+" puntos en la partida.");
            return puntos;
        }
    }

    // OPERACION 5: Pasar de Nivel en una Partida.
    @Override
    public void pasarDeNivel(String usuarioId, int puntosLogrados, String fechaCambioNivel) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException {
        logger.info("Se quiere que el usuario "+usuarioId+" pase de nivel con "+puntosLogrados+" puntos a fecha de "+fechaCambioNivel+".");
        if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else {
            if (partidasUsuarios.get(usuarioId).getNivelActual() < juegos.get(partidasUsuarios.get(usuarioId).getJuegoId()).getNumeroNivelesJuego()){
                partidasUsuarios.get(usuarioId).aumentarNivel(fechaCambioNivel, puntosLogrados);
                logger.info("El usuario "+usuarioId+" ha pasado de nivel.");
            } else { // Si está en el último nivel, hay que acabar el juego.
                partidasUsuarios.get(usuarioId).finalizarPartida(fechaCambioNivel, puntosLogrados);
                Partida partidaGuardada = partidasUsuarios.get(usuarioId);
                usuarios.get(usuarioId).nuevaPartidaAcabada(partidaGuardada);
                logger.info("El usuario "+usuarioId+" ha finalizado la partida porque ha pasado todos los niveles.");
            }
        }
    }

    // OPERACION 6: Finalizar una Partida.
    @Override
    public void finalizarPartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException{
        logger.info("Se quiere que el usuario "+usuarioId+" acabe una partida.");
        if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario "+usuarioId+" no existe.");
            throw new UsuarioIdNoExisteException();
        } else if (!partidasUsuarios.get(usuarioId).isPartidaEnCurso()){
            logger.warn("El usuario "+usuarioId+" no está jugando ninguna partida.");
            throw new UsuarioIdNoEstaEnPartidaException();
        } else {
            partidasUsuarios.get(usuarioId).forzarFinPartida();
            Partida partidaGuardada = partidasUsuarios.get(usuarioId);
            usuarios.get(usuarioId).nuevaPartidaAcabada(partidaGuardada);
            logger.info("El usuario "+usuarioId+" ha finalizado la partida porque ha querido.");
        }
    }

    // OPERACION 7: Obtener los Usuarios que han jugado un cierto Juego ordenados por Puntos (de mayor a menor).
    @Override
    public List<Pou> obtenerHistorialUsuariosDeJuego(String juegoId) throws JuegoIdNoExisteException {
        logger.info("Queremos saber que usuarios han jugado al juego "+juegoId+".");
        if (!this.juegos.containsKey(juegoId)) {
            logger.warn("El juego " + juegoId + " no existe.");
            throw new JuegoIdNoExisteException();
        } else {
            List<Pou> historialUsuariosDeJuego = new ArrayList<>();
            // Pasamos el Hashmap a ArrayList para poder hacer la búsqueda sin los userId.
            List<Pou> listaUsuarios = new ArrayList<>(this.usuarios.values());
            for (int i=0; i < listaUsuarios.size(); i++){
                int numPartidasJugadas = listaUsuarios.get(i).getPartidasJugadas().size();
                for (int j=0; j < numPartidasJugadas; j++){
                    if (Objects.equals(listaUsuarios.get(i).getPartidasJugadas().get(j).getJuegoId(), juegoId)){
                        historialUsuariosDeJuego.add(listaUsuarios.get(i));
                        j = numPartidasJugadas; // Paramos la búsqueda.
                    }
                }
            }
            // Tenemos una lista ("historialUsuariosDeJuego") con los usuarios de juego. Procedemos a ordenarla.
            historialUsuariosDeJuego.sort((Pou p1, Pou p2)->(p2.damePuntosTotalesEnUnJuego(juegoId) - p1.damePuntosTotalesEnUnJuego(juegoId) ));
            logger.info("Se devuelve correctamente la lista de usuarios que han jugado al juego "+juegoId+".");
            return historialUsuariosDeJuego;
        }
    }

    // OPERACION 8: Obtener las Partidas en las que ha jugado un Usuario.
    @Override
    public List<Partida> obtenerPartidasUsuario(String usuarioId) throws UsuarioIdNoExisteException {
        logger.info("Queremos saber que partidas ha jugado el usuario "+usuarioId+".");
        if (!this.usuarios.containsKey(usuarioId)) {
            logger.warn("El usuario " + usuarioId + " no existe.");
            throw new UsuarioIdNoExisteException();
        } else {
            logger.info("Se devuelven las partidas en las que ha jugado "+usuarioId+".");
            return this.usuarios.get(usuarioId).getPartidasJugadas();
        }
    }

    // OPERACION 9: Obtener información sobre las Partidas de un Usuario en un cierto Juego.
    @Override
    public Estado obtenerInfoUsuarioJuego(String juegoId, String usuarioId) {
        logger.info("Queremos los detalles de la última partida que ha jugado "+usuarioId+" en el juego "+juegoId+".");
        logger.info("Se devuelven los datos de la última partida que ha jugado "+usuarioId+" en el juego "+juegoId+".");
        return this.usuarios.get(usuarioId).dameInfoPartidaUsuario(juegoId);
    }
    */
}
