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

    public static PouGameManager getInstance(){
        if (instance==null) instance = new PouGameManagerImpl();
        return instance;
    }

    public PouGameManagerImpl(){
        this.pousGame = new HashMap<>();
        this.objetosTienda = new HashMap<>();
    }

    // OPERACIÓN 1: OBTENER NÚMERO DE POUS

    @Override
    public Integer size() {
        Integer ret = this.pousGame.size();
        logger.info("Número de Pous: " + ret);
        return ret;
    }

    // OPERACIÓN 2: REGISTRO POU (CREAR POU)

    @Override
    public void crearPou(String pouId, String nombrePou, String nacimientoPou, String correo, String password) throws CorreoYaExisteException, PouIDYaExisteException {

    }

    // OPERACIÓN 3: LOGIN POU

    @Override
    public void loginPou(String correo, String password) throws CorreoNoExisteException, PasswordIncorrectaException{

    }

    // OPERACIÓN 4: OBTENER TODOS LOS POUS

    @Override
    public Map<String, Pou> obtenerPous() {
        return null;
    }

    // OPERACIÓN 4: OBTENER POU POR SU ID ("pouId")

    @Override
    public Pou obtenerPou(String pouId) throws PouIDNoExisteException {
        return null;
    }

    // OPERACIÓN 5: AÑADIR OBJETOS A LA TIENDA

    @Override
    public void addObjetosATienda(String articuloId, String nombreArticulo, double precioArticulo, String tipoArticulo, Integer recargaHambre, Integer recargaSalud, Integer recargaDiversion, Integer recargaSueno) throws ObjetoTiendaYaExisteException{

    }

    // OPERACIÓN 5: OBTENER OBJETO DE LA TIENDA POR SU ID ("articuloId")

    @Override
    public ObjetoTienda obtenerObjetoTienda(String articuloId) throws ObjetoTiendaNoExisteException{
        return null;
    }

    // OPERACIÓN 6: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS COMIDAS DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerComidasTienda(){
        return null;
    }

    // OPERACIÓN 7: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS BEBIDAS DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerBebidasTienda(){
        return null;
    }

    // OPERACIÓN 8: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS POCIONES DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerPocionesTienda(){
        return null;
    }

    // OPERACIÓN 9: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS ROPAS DE LA TIENDA

    @Override
    public List<ObjetoTienda> obtenerRopasTienda(){
        return null;
    }

    // OPERACIÓN 10: CREAR SALA (AÑADIENDO TAMBIEN LOS OBJETOS DE LA TIENDA QUE LE CORRESPONDAN)

    @Override
    public void crearSala(String pouId, String salaId, String nombreSala) throws SalaYaExisteException {

    }

    // OPERACIÓN 11: AÑADIR ELEMENTO ARMARIO POU (POU COMPRA UN OBJETO DE UNA SALA) (HAY QUE PONER CUANTOS)

    @Override
    public void pouCompraArticulos(String pouId, String articuloId, Integer cantidad) throws SalaNoExisteException, ObjetoTiendaNoExisteException, PouIDNoExisteException{

    }

    // OPERACIÓN 12: BORRAR ELEMENTO ARMARIO POU (PORQUE SE HA CONSUMIDO) (SE RESTA 1 (UNITARIAMENTE))

    @Override
    public ObjetoTienda pouConsumeArticulo(String pouId, String articuloId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException{
        return null;
    }

    // OPERACIÓN 13: POU MODIFICA SU CAMISETA (OUTFIT)

    @Override
    public void pouCambiaCamiseta(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException{

    }

    // OPERACIÓN 14: POU MODIFICA SU PANTALON (OUTFIT)

    @Override
    public void pouCambiaPantalon(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException{

    }

    // OPERACIÓN 15: POU MODIFICA SU GORRA (OUTFIT)

    @Override
    public void pouCambiaGorra(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException {

    }

    // OPERACIÓN 16: POU MODIFICA SUS GAFAS (OUTFIT)

    @Override
    public void pouCambiaGafas(String pouId, String camisetaId) throws ObjetoTiendaNoExisteException, PouIDNoExisteException{

    }

    // OPERACIÓN 17: POU MODIFICA SU NIVEL DE HAMBRE

    @Override
    public void pouModificaNivelHambre(String pouId, Integer varNivelHambre) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException{

    }

    // OPERACIÓN 18: POU MODIFICA SU NIVEL DE SALUD

    @Override
    public void pouModificaNivelSalud(String pouId, Integer varNivelSalud) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException{

    }

    // OPERACIÓN 19: POU MODIFICA SU NIVEL DE DIVERSION

    @Override
    public void pouModificaNivelDiversion(String pouId, Integer varNivelDiversion) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException{

    }

    // OPERACIÓN 20: POU MODIFICA SU NIVEL DE SUEÑO

    @Override
    public void pouModificaNivelSueno(String pouId, Integer varNivelSueno) throws PouIDNoExisteException, NivelPorDebajoDelMinimoException, NivelPorEncimaDelMaximoException{

    }

    // OPERACIÓN 21: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS COMIDAS DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerComidasArmario(){
        return null;
    }

    // OPERACIÓN 22: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS BEBIDAS DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerBebidasArmario(){
        return null;
    }

    // OPERACIÓN 23: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS POCIONES DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerPocionesArmario(){
        return null;
    }

    // OPERACIÓN 24: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LA ROPA DEL ARMARIO

    @Override
    public List<ObjetoTienda> obtenerRopaArmario(){
        return null;
    }

    // OPERACIÓN 25: POU GASTA DINERO / GANA DINERO.

    @Override
    public void pouModificaDinero(String pouId, double varDinero) throws PouIDNoExisteException, PouNoTieneDineroSuficienteException {

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
