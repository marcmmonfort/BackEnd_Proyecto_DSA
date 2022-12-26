import Entities.Exceptions.*;
import Entities.ObjetoArmario;
import Managers.PouGameDBManagerImpl;
import Managers.PouGameManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PouGameDBImplTest {
    PouGameManager pgm;

    @Before
    public void setUp(){
        pgm = new PouGameDBManagerImpl();
        //this.pgm.addObjetosAArmario(3,"prueba", "Pocion","P001",1);
        //this.pgm.addObjetosAArmario(4,"prueba", "Comida","C001",2);
        //this.pgm.addObjetosAArmario(5,"prueba", "Bebida","B001",1);
    }

    @After
    public void tearDown(){
        this.pgm = null;
    }

    @Test
    public void registerTest() throws PouIDYaExisteException, CorreoYaExisteException {
        this.pgm.crearPou("eloimoncho", "Eloi", "28/08/2001", "eloi@gmail.com", "gnx");
        int num = this.pgm.size();
        Assert.assertEquals(6, num);
    }

    @Test
    public void loginTest() throws PasswordIncorrectaException, CorreoNoExisteException {
        this.pgm.loginPou("alba@gmail.com","africa");
    }

    @Test
    public void addObjetoTiendaTest() throws ObjetoTiendaYaExisteException {
        this.pgm.addObjetosATienda("C001","Manzana",1,"Comida",10,0,0,0 );
        this.pgm.addObjetosATienda("B001","Agua",4,"Bebida",4,4,0,0);
        this.pgm.addObjetosATienda("P001","Salud",10,"Pocion",0,20,0,0);
        this.pgm.addObjetosATienda("R001","Gafas de sol",30,"Ropa",0,0,0,0);
    }

    @Test
    public void obtenerObjetosArmarioPouTest() throws PouIDNoExisteException {
        Map<String, ObjetoArmario> armario = this.pgm.obtenerObjetosArmarioPou("prueba");
        List<ObjetoArmario> lista = new ArrayList<>(armario.values());
        Assert.assertEquals(3, armario.size());
        Assert.assertEquals("C001", lista.get(0).getIdArticulo());
        Assert.assertEquals("B001", lista.get(1).getIdArticulo());
        Assert.assertEquals("P001", lista.get(2).getIdArticulo());
        //Assert.assertEquals("C001", lista.get(0).getIdArticulo());
    }

}
