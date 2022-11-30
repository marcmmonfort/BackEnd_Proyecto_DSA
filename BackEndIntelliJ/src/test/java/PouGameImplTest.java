import Entities.Exceptions.*;
import Entities.ObjetoTienda;
import Entities.Pou;
import Managers.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.HashMap;
import java.util.Optional;

public class PouGameImplTest {

    final static Logger logger = Logger.getLogger(PouGameManagerImpl.class);

    PouGameManager jvm;

    @Before

    public void setUp() throws PouIDYaExisteException, CorreoYaExisteException, ObjetoTiendaYaExisteException, SalaYaExisteException, PouIDNoExisteException {
        this.jvm = new PouGameManagerImpl();

        this.jvm.crearPou("marcmmonfort", "Marc", "28/10/2001", "marc@gmail.com", "28102001");
        this.jvm.crearPou("victorfernandez", "Victor", "13/06/2001", "victor@gmail.com", "13062001");
        this.jvm.crearPou("albaserra", "Alba", "29/06/2001", "alba@gmail.com", "29062001");

        this.jvm.addObjetosATienda("C001","Manzana",1,"Comida",10,0,0,0 );
        this.jvm.addObjetosATienda("B001","Agua",4,"Bebida",4,4,0,0);
        this.jvm.addObjetosATienda("P001","Salud",10,"Pocion",0,20,0,0);
        this.jvm.addObjetosATienda("R001","Gafas de sol",30,"Ropa",0,0,0,0);



        this.jvm.crearSala("marcmmonfort","S001","cocina");
        this.jvm.crearSala("marcmmonfort","S002","dormitorio");

    }

    @After
    public void tearDown() {
        this.jvm = null;
    }

    @Test
    public void testRegistrarPou() throws PouIDYaExisteException, CorreoYaExisteException {
        // CASO 1 = ID del Pou ya existe.
        Assert.assertThrows(PouIDYaExisteException.class, ()-> this.jvm.crearPou("marcmmonfort", "Carlos", "20/10/2001", "carlos@gmail.com", "20102001"));
        // CASO 2 = Correo del Pou ya registrado.
        Assert.assertThrows(CorreoYaExisteException.class, ()-> this.jvm.crearPou("carlossainz", "Carlos", "20/10/2001", "marc@gmail.com", "20102001"));
        // CASO 3 = Registro Satisfactorio.
        this.jvm.crearPou("eloimoncho", "Eloi", "28/08/2001", "eloi@gmail.com", "28082001");
        Assert.assertEquals(4, this.jvm.size());
    }

    @Test
    public void testLoginPou() throws CorreoNoExisteException, PasswordIncorrectaException{
        // CASO 1 = Correo del Login no existe.
        Assert.assertThrows(CorreoNoExisteException.class, ()-> this.jvm.loginPou("psg@gmail.com","12345678"));
        // CASO 2 = Contraseña del Login no es la correcta.
        Assert.assertThrows(PasswordIncorrectaException.class, ()-> this.jvm.loginPou("marc@gmail.com","28102000"));
        // CASO 3 = Login Satisfactorio.
        this.jvm.loginPou("marc@gmail.com","28102001");
    }

    @Test
    public void testObtenerPou() throws PouIDNoExisteException{
        // CASO 1 = El Pou no existe. No se encuentra el Id.
        Assert.assertThrows(PouIDNoExisteException.class, () -> this.jvm.obtenerPou("eloimoncho"));
        // CASO 2 = El Pou sí que existe.
        Pou test = this.jvm.obtenerPou("marcmmonfort");
        Assert.assertEquals("marcmmonfort",test.getPouId());
    }



    @Test
    public void testObtenerPous() throws PouIDYaExisteException, CorreoYaExisteException {
        Map<String, Pou> pouMap = this.jvm.obtenerPous();
        Assert.assertEquals(3, pouMap.size());
        this.jvm.crearPou("eloimoncho", "Eloi", "28/08/2001", "eloi@gmail.com", "28082001");
        pouMap = this.jvm.obtenerPous();
        Assert.assertEquals(4, pouMap.size());
    }

    @Test
    public void testObtenerObjetoTienda() throws ObjetoTiendaNoExisteException{
        // CASO 1 = El objeto no existe. No se encuentra el Id.
        Assert.assertThrows(ObjetoTiendaNoExisteException.class, () -> this.jvm.obtenerObjetoTienda("A001"));
        // CASO 2 = El objeto sí que existe.
        ObjetoTienda test = this.jvm.obtenerObjetoTienda("C001");
        Assert.assertEquals("C001",test.getArticuloId());
    }

    @Test
    public void testObtenerComidasTienda() throws ObjetoTiendaYaExisteException {
        List<ObjetoTienda> listaComidas = this.jvm.obtenerComidasTienda();
        int numComidas = listaComidas.size();
        Assert.assertEquals(1, numComidas);
        this.jvm.addObjetosATienda("C002","Patatas",2,"Comida",10,0,0,0 );
        this.jvm.addObjetosATienda("C003","Arroz",3,"Comida",15,0,0,0 );
        listaComidas = this.jvm.obtenerComidasTienda();
        numComidas = listaComidas.size();
        Assert.assertEquals("C001",listaComidas.get(0).getArticuloId());
        Assert.assertEquals("C002",listaComidas.get(1).getArticuloId());
        Assert.assertEquals("C003",listaComidas.get(2).getArticuloId());
        Assert.assertEquals(3, numComidas);
    }

    @Test
    public void testObtenerBebidasTienda() throws ObjetoTiendaYaExisteException {
        List<ObjetoTienda> listaBebidas = this.jvm.obtenerBebidasTienda();
        int numBebidas = listaBebidas.size();
        Assert.assertEquals(1, numBebidas);
        this.jvm.addObjetosATienda("B002","Fanta",10,"Bebida",5,0,0,0 );
        this.jvm.addObjetosATienda("B003","Cafe",6,"Bebida",3,0,0,0 );
        listaBebidas = this.jvm.obtenerBebidasTienda();
        numBebidas = listaBebidas.size();
        Assert.assertEquals("B001",listaBebidas.get(0).getArticuloId());
        Assert.assertEquals("B003",listaBebidas.get(1).getArticuloId());
        Assert.assertEquals("B002",listaBebidas.get(2).getArticuloId());
        Assert.assertEquals(3, numBebidas);
    }

    @Test
    public void testObtenerPocionesTienda() throws ObjetoTiendaYaExisteException {
        List<ObjetoTienda> listaPociones = this.jvm.obtenerPocionesTienda();
        int numPociones = listaPociones.size();
        Assert.assertEquals(1, numPociones);
        this.jvm.addObjetosATienda("P002","Hambre",12,"Pocion",20,0,0,0);
        this.jvm.addObjetosATienda("P003","Energia",11,"Pocion",0,0,0,20);
        listaPociones = this.jvm.obtenerPocionesTienda();
        numPociones = listaPociones.size();
        Assert.assertEquals("P001",listaPociones.get(0).getArticuloId());
        Assert.assertEquals("P003",listaPociones.get(1).getArticuloId());
        Assert.assertEquals("P002",listaPociones.get(2).getArticuloId());
        Assert.assertEquals(3, numPociones);
    }

    @Test
    public void testObtenerRopasTienda() throws ObjetoTiendaYaExisteException {
        List<ObjetoTienda> listaRopas = this.jvm.obtenerRopasTienda();
        int numPociones = listaRopas.size();
        Assert.assertEquals(1, numPociones);
        this.jvm.addObjetosATienda("R002","Gorra",20,"Ropa",0,0,0,0);
        this.jvm.addObjetosATienda("R003","Camiseta",15,"Ropa",0,0,0,0);
        listaRopas = this.jvm.obtenerRopasTienda();
        numPociones = listaRopas.size();
        Assert.assertEquals("R003",listaRopas.get(0).getArticuloId());
        Assert.assertEquals("R002",listaRopas.get(1).getArticuloId());
        Assert.assertEquals("R001",listaRopas.get(2).getArticuloId());
        Assert.assertEquals(3, numPociones);
    }
}

    /*

    VIEJO ...

    @Test
    public void testCrearJuego() {
        // Creamos un juego.
        this.jvm.crearJuego("Fall Guys", "Juego de retos", 3);
        Assert.assertEquals(4, this.jvm.numJuegos());

        // Creamos otro juego.
        this.jvm.crearJuego("NBA", "Juego de baloncesto", 4);
        Assert.assertEquals(5, this.jvm.numJuegos());
    }

    @Test
    public void testIniciarPartida() throws JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Comprobamos que Marc no tiene ninguna partida iniciada.
        Assert.assertEquals(false, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());

        // Iniciamos una partida.
        this.jvm.iniciarPartida("FIFA", "Marc");
        Assert.assertEquals(true, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
        Assert.assertEquals("FIFA", this.jvm.damePartidaUsuario("Marc").getJuegoId());
        Assert.assertEquals("Marc", this.jvm.damePartidaUsuario("Marc").getUsuarioId());

        // Probamos las 3 Exceptions posibles.
        Assert.assertThrows(JuegoIdNoExisteException.class, ()-> this.jvm.iniciarPartida("Plane Simulator", "Marc"));
        Assert.assertThrows(UsuarioIdNoExisteException.class, ()-> this.jvm.iniciarPartida("FIFA", "Carlos"));
        Assert.assertThrows(UsuarioIdYaEstaEnPartidaException.class, ()-> this.jvm.iniciarPartida("FIFA", "Marc"));
    }

    @Test
    public void test_PedirNiveles_PedirPuntos_PasarDeNivel() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        Assert.assertThrows(UsuarioIdNoEstaEnPartidaException.class, ()-> this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertThrows(UsuarioIdNoEstaEnPartidaException.class, ()-> this.jvm.pedirPuntosDePartida("Marc"));
        Assert.assertThrows(UsuarioIdNoEstaEnPartidaException.class, ()->  this.jvm.pasarDeNivel("Marc", 50, "01/01/2022"));

        // Iniciamos la partida: entramos en el nivel 1 de los 4 que tiene GTA.
        this.jvm.iniciarPartida("GTA", "Marc");
        Assert.assertEquals(1, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(50, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos al nivel 2.
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");
        Assert.assertThrows(UsuarioIdNoExisteException.class, ()->  this.jvm.pasarDeNivel("Mark", 50, "01/01/2022"));
        Assert.assertEquals(2, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(100, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos al nivel 3.
        this.jvm.pasarDeNivel("Marc", -50, "02/01/2022");
        Assert.assertEquals(3, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(50, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos al nivel 4.
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        Assert.assertEquals(4, this.jvm.pedirNivelJuegoDePartida("Marc"));
        Assert.assertEquals(150, this.jvm.pedirPuntosDePartida("Marc"));

        // Pasamos de nivel (finaliza la partida).
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        Assert.assertEquals(false, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
    }

    // OPERACION 6: Finalizar una Partida.
    // EXCEPCIONES: UsuarioIdNoExisteException, UsuarioIdNoEstaEnPartidaException.

    @Test
    public void testFinalizarPartida() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        Assert.assertThrows(UsuarioIdNoEstaEnPartidaException.class, ()-> this.jvm.finalizarPartida("Marc"));

        // Iniciamos la partida: entramos en el nivel 1 de los 4 que tiene GTA.
        this.jvm.iniciarPartida("GTA", "Marc");

        // Pasamos al nivel 2.
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");

        // Pasamos al nivel 3.
        this.jvm.pasarDeNivel("Marc", -50, "02/01/2022");

        // Forzamos que se acabe la partida.
        Assert.assertEquals(0, this.jvm.dameUsuario("Marc").getPartidasJugadas().size());
        Assert.assertEquals(true, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
        Assert.assertThrows(UsuarioIdNoExisteException.class, ()-> this.jvm.finalizarPartida("Mark"));
        this.jvm.finalizarPartida("Marc");
        Assert.assertEquals(1, this.jvm.dameUsuario("Marc").getPartidasJugadas().size());
        Assert.assertEquals(false, this.jvm.damePartidaUsuario("Marc").isPartidaEnCurso());
    }

    @Test
    public void test_ObtenerUsuariosJuego_ObtenerPartidasUsuario() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Hacemos que 3 usuarios jueguen 3 partidas de un cierto juego y las acaben.
        this.jvm.iniciarPartida("FIFA", "Marc");
        Assert.assertEquals(0,this.jvm.obtenerPartidasUsuario("Marc").size());
        this.jvm.pasarDeNivel("Marc", 450, "01/01/2022");
        this.jvm.pasarDeNivel("Marc", 400, "02/01/2022");
        Assert.assertEquals(1,this.jvm.obtenerPartidasUsuario("Marc").size());
        this.jvm.iniciarPartida("FIFA", "Eloi");
        Assert.assertEquals(0,this.jvm.obtenerPartidasUsuario("Eloi").size());
        this.jvm.pasarDeNivel("Eloi", 50, "01/01/2022");
        this.jvm.pasarDeNivel("Eloi", 100, "03/01/2022");
        Assert.assertEquals(1,this.jvm.obtenerPartidasUsuario("Eloi").size());

        // Comprobamos que obtenemos correctamente el historial de usuarios que han jugado a un juego (ordenados).
        Assert.assertEquals(2, this.jvm.obtenerHistorialUsuariosDeJuego("FIFA").size());
        Assert.assertEquals("Marc",this.jvm.obtenerHistorialUsuariosDeJuego("FIFA").get(0).getUsuarioId());
        Assert.assertEquals("Eloi", this.jvm.obtenerHistorialUsuariosDeJuego("FIFA").get(1).getUsuarioId());

        // Hacemos que uno de los dos usuarios juegue otra partida a otro juego.
        this.jvm.iniciarPartida("GTA", "Marc");
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        this.jvm.pasarDeNivel("Marc", 150, "02/01/2022");
        this.jvm.pasarDeNivel("Marc", 200, "02/01/2022");
        Assert.assertEquals(2,this.jvm.obtenerPartidasUsuario("Marc").size());

        // Comprobamos que obtenemos las partidas que ha jugado un cierto usuario.
        Assert.assertEquals("FIFA",this.jvm.obtenerPartidasUsuario("Marc").get(0).getJuegoId());
        Assert.assertEquals("GTA",this.jvm.obtenerPartidasUsuario("Marc").get(1).getJuegoId());

        // Comprobamos el funcionamiento de las excepciones.
        Assert.assertThrows(JuegoIdNoExisteException.class, ()-> this.jvm.obtenerHistorialUsuariosDeJuego("FORMULA 1"));
        Assert.assertThrows(UsuarioIdNoExisteException.class, ()-> this.jvm.obtenerPartidasUsuario("Mark"));
    }

    @Test
    public void testObtenerInfoUsuarioJuego() throws UsuarioIdNoEstaEnPartidaException, JuegoIdNoExisteException, UsuarioIdNoExisteException, UsuarioIdYaEstaEnPartidaException {
        // Creamos y finalizamos una partida de un juego.
        this.jvm.iniciarPartida("GTA", "Marc");
        this.jvm.pasarDeNivel("Marc", 50, "01/01/2022");
        this.jvm.pasarDeNivel("Marc", 100, "02/01/2022");
        this.jvm.pasarDeNivel("Marc", 150, "03/01/2022");
        this.jvm.pasarDeNivel("Marc", 200, "04/01/2022");

        // Pedimos la información de esta partida.
        Assert.assertEquals("Marc", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getUsuarioId());
        Assert.assertEquals("GTA", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getJuegoId());
        Assert.assertEquals("01/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(0).getFechaNivel());
        Assert.assertEquals("02/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(1).getFechaNivel());
        Assert.assertEquals("03/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(2).getFechaNivel());
        Assert.assertEquals("04/01/2022", this.jvm.obtenerInfoUsuarioJuego("GTA","Marc").getDetallesNiveles().get(3).getFechaNivel());
    }

}

     */
