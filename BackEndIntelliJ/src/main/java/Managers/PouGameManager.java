package Managers;

import Entities.*;
import Entities.ValueObjects.*;
import Entities.Exceptions.*;

import java.util.List;

public interface PouGameManager {

    // OPERACIÓN 1: OBTENER NÚMERO DE POUS
    // DEVUELVE: Integer.
    // EXCEPCIONES: -

    // OPERACIÓN 2: CREAR POU / REGISTRO POU
    // DEVUELVE: -
    // EXCEPCIONES: (CORREO YA EXISTE) / (pouID YA EXISTENTE)

    // OPERACIÓN 3: LOGIN POU
    // DEVUELVE: -
    // EXCEPCIONES: (CORREO NO EXISTE) / (CONTRASEÑA DEL CORREO INCORRECTA)

    // OPERACIÓN 4: OBTENER TODOS LOS POUS
    // DEVUELVE: Map<String, Pou>
    // EXCEPCIONES: -

    // OPERACIÓN 4: OBTENER POU DE LA TIENDA POR SU ID ("pouId")
    // DEVUELVE: Pou
    // EXCEPCIONES: (NO EXISTE ESTE POU)

    // OPERACIÓN 5: AÑADIR OBJETOS A LA TIENDA
    // DEVUELVE: -
    // EXCEPCIONES: (EL ARTICULO YA EXISTE)

    // OPERACIÓN 5: OBTENER OBJETO DE LA TIENDA POR SU ID ("articuloId")
    // DEVUELVE: ObjetoTienda
    // EXCEPCIONES: (NO EXISTE ESTE ARTICULO)

    // OPERACIÓN 6: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS COMIDAS DE LA TIENDA
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 7: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS BEBIDAS DE LA TIENDA
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 8: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS POCIONES DE LA TIENDA
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 9: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS ROPAS DE LA TIENDA
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 10: CREAR SALA (CON LOS OBJETOS DE LA TIENDA QUE LE CORRESPONDAN)
    // DEVUELVE:
    // EXCEPCIONES: (SALA YA EXISTE)

    // OPERACIÓN 11: AÑADIR ELEMENTO ARMARIO POU (POU COMPRA UN OBJETO DE UNA SALA)
    // DEVUELVE:
    // EXCEPCIONES: (SALA NO EXISTE) / (OBJETOTIENDA NO EXISTE) / (POU NO EXISTE)

    // OPERACIÓN 12: BORRAR ELEMENTO ARMARIO POU (PORQUE SE HA CONSUMIDO)
    // DEVUELVE: ObjetoTienda
    // EXCEPCIONES: (ELEMENTO NO EXISTE EN EL ARMARIO) / (POU NO EXISTE)

    // OPERACIÓN 13: POU MODIFICA SU OUTFIT
    // DEVUELVE:
    // EXCEPCIONES: (EL ARTICULO QUE SE QUIERE PONER NO ESTÁ EN EL ARMARIO DE POU) / (POU NO EXISTE)

    // OPERACIÓN 14: POU MODIFICA SU ESTADO (POR CONSUMIR UNA POCIÓN, POR COMER, POR DORMIR, POR JUGAR ...)
    // DEVUELVE:
    // EXCEPCIONES: (POU NO EXISTE) / (SE BAJA DEL NIVEL MINIMO = 0) / (SE PASA DEL NIVEL MAXIMO = 100)

    // OPERACIÓN 15: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS COMIDAS DEL ARMARIO
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 16: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS BEBIDAS DEL ARMARIO
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 17: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LAS POCIONES DEL ARMARIO
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 18: OBTENER, POR ORDEN DE PRECIO CRECIENTE (DE - A +), LA ROPA DEL ARMARIO
    // DEVUELVE: List<ObjetoTienda>
    // EXCEPCIONES: -

    // OPERACIÓN 19: POU GASTA DINERO / GANA DINERO.
    // DEVUELVE: -
    // EXCEPCIONES: (POU NO EXISTE) / (PO NO TIENE DINERO SUFICIENTE)

    // ----------------------------------------------------------------------------------------------------

    // VIEJO ...

    /*
    // OPERACION 1: Crear un Juego.
    public void crearJuego(String juegoId, String juegoDescripcion, int numeroNivelesJuego);

    // OPERACION 2: Iniciar una Partida (por parte de un Usuario).
    public void iniciarPartida (String juegoId, String usuarioId) throws JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException;

    // OPERACION 3: Pedir el Nivel Actual de la Partida en la que está el Usuario introducido.
    public int pedirNivelJuegoDePartida (String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 4: Pedir la Puntuación Actual en una Partida (por parte de un Usuario).
    public int pedirPuntosDePartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 5: Pasar de Nivel en una Partida.
    public void pasarDeNivel(String usuarioId, int puntosLogrados, String fechaCambioNivel) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 6: Finalizar una Partida.
    public void finalizarPartida(String usuarioId) throws UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException;

    // OPERACION 7: Obtener los Usuarios que han jugado un cierto Juego ordenados por Puntos (de mayor a menor).
    public List<Pou> obtenerHistorialUsuariosDeJuego(String juegoId) throws JuegoIdNoExisteException;

    // OPERACION 8: Obtener las Partidas en las que ha jugado un Usuario.
    public List<Partida> obtenerPartidasUsuario(String usuarioId) throws UsuarioIdNoExisteException;

    // OPERACION 9: Obtener información sobre las Partidas de un Usuario en un cierto Juego.
    public Estado obtenerInfoUsuarioJuego(String juegoId, String usuarioId);

    public int numUsuarios();

    public int numJuegos();

    public Juego dameJuego(String juegoId);

    public Pou dameUsuario(String usuarioId);

    public Partida damePartidaUsuario(String usuarioId);

    public void crearUsuario(String usuario);

    public int size();

    */
}
