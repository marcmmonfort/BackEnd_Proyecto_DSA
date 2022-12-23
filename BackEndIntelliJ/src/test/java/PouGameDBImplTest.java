import Entities.Exceptions.*;
import Managers.PouGameDBManagerImpl;
import Managers.PouGameManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PouGameDBImplTest {
    PouGameManager pgm;

    @Before
    public void setUp(){
        pgm = new PouGameDBManagerImpl();
    }

    @After
    public void tearDown(){
        this.pgm = null;
    }

    @Test
    public void registerTest() throws PouIDYaExisteException, CorreoYaExisteException {
        this.pgm.crearPou("prueba", "Probando", "23/12/2022", "prueba@gmail.com", "prueba");
        int num = this.pgm.size();
        Assert.assertEquals(5, num);
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


}
