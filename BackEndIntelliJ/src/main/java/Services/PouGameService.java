package Services;

import Entities.Exceptions.*;
import Entities.ObjetoArmario;
import Entities.ObjetoTienda;
import Entities.Pou;
import Entities.ValueObjects.Credenciales;
import Entities.ValueObjects.InfoRegistro;
import Entities.ValueObjects.InformacionPou;
import Managers.PouGameDBManagerImpl;
import Managers.PouGameManager;
import Managers.PouGameManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/pougame", description = "Endpoint to Pou Game Service")
@Path("/pougame")

public class PouGameService {
    private PouGameManager jvm;

    final static org.apache.log4j.Logger logger = Logger.getLogger(PouGameManagerImpl.class);

    public PouGameService() throws PouIDYaExisteException, CorreoYaExisteException, ObjetoTiendaYaExisteException, SalaYaExisteException, PouIDNoExisteException {
        this.jvm = PouGameDBManagerImpl.getInstance();
        if (jvm.size()==0) {
            //this.jvm.crearPou("marcmmonfort", "Marc", "28/10/2001", "marc@gmail.com", "28102001");
            //this.jvm.crearPou("victorfernandez", "Victor", "13/06/2001", "victor@gmail.com", "13062001");
            //this.jvm.crearPou("albaserra", "Alba", "29/06/2001", "alba@gmail.com", "29062001");


           // this.jvm.addObjetosATienda("B001","Manzana",1,"Comida",10,0,0,0 );
           // this.jvm.addObjetosATienda("B002","Gafas de sol",30,"Ropa",0,0,0,0);

            //this.jvm.addObjetosATienda("C001","Manzana",1,"Comida",10,0,0,0 );
            //this.jvm.addObjetosATienda("B001","Agua",4,"Bebida",4,4,0,0);
            //this.jvm.addObjetosATienda("P001","Salud",10,"Pocion",0,20,0,0);
            //this.jvm.addObjetosATienda("R001","Gafas de sol",30,"Ropa",0,0,0,0);

        }
    }

    // OPERACION 1: Registro.
    // MÉTODO HTTP: POST.
    // ESTRUCTURA: public void crearPou(String pouId, String nombrePou, String nacimientoPou, String correo, String password);
    // EXCEPCIONES: CorreoYaExisteException, PouIDYaExisteException

    @POST
    @ApiOperation(value = "Registro", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Juego creado satisfactoriamente"),
            @ApiResponse(code = 404, message = "Ya existe el correo"),
            @ApiResponse(code = 405, message = "Ya existe el PouID")
    })
    @Path("/pou/registro")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response crearPou(InfoRegistro infoRegistro){
        try{
            this.jvm.crearPou(infoRegistro.getPouId(), infoRegistro.getNombrePou(), infoRegistro.getNacimientoPou(), infoRegistro.getCorreo(), infoRegistro.getPassword());
        }catch(CorreoYaExisteException e){
            return Response.status(404).build();
        }catch (PouIDYaExisteException e){
            return Response.status(405).build();
        }
        return Response.status(200).build();
    }

    // OPERACION 2: Login.
    // MÉTODO HTTP: POST.
    // ESTRUCTURA: public void loginPou(String correo, String password);
    // EXCEPCIONES: CorreoNoExisteException, PasswordIncorrectaException

    @POST
    @ApiOperation(value = "Login", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login correcto"),
            @ApiResponse(code = 404, message = "El correo no exite"),
            @ApiResponse(code = 405, message = "Contraseña incorrecta")
    })
    @Path("/pou/login")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response loginPou(Credenciales credenciales){
        try{
            this.jvm.loginPou(credenciales.getCorreoPou(), credenciales.getPasswordPou());
        }catch(CorreoNoExisteException e){
            return Response.status(404).build();
        }catch (PasswordIncorrectaException e){
            return Response.status(405).build();
        }
        return Response.status(200).build();
    }

    // OPERACION 3: Obtener la lista de objetos de la tienda
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public List<ObjetoTienda> obtenerObjetosTienda();
    // EXCEPCIONES:-

    @GET
    @ApiOperation(value = "Obtener la lista de objetos de la tienda", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "¡Hecho!", response = ObjetoTienda.class, responseContainer="List"),
    })
    @Path("/tienda/listaObjetos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerObjetosTienda() {
        GenericEntity<List<ObjetoTienda>> listaObjetosTienda = new GenericEntity<List<ObjetoTienda>>(this.jvm.obtenerObjetosTienda()) {};
        return Response.status(200).entity(listaObjetosTienda).build();
    }

    // OPERACION 4: Obtener un Pou
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public Pou obtenerPou(String pouId) throws PouIDNoExisteException;
    // EXCEPCIONES:-
    @GET
    @ApiOperation(value = "Obtener un Pou", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "¡Hecho!", response = Pou.class),
    })
    @Path("/perfil/pou_id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPou(String id) throws PouIDNoExisteException {
        GenericEntity<Pou> miPou = new GenericEntity<Pou>(this.jvm.obtenerPou(id)) {};
        return Response.status(200).entity(miPou).build();
    }

    // OPERACIÓN 14: AÑADIR ELEMENTO ARMARIO POU (POU COMPRA UN OBJETO DE UNA SALA) (HAY QUE PONER CUANTOS)
    // MÉTODO HTTP: PUT.
    // ESTRUCTURA: public void pouCompraArticulos(String pouId, String articuloId, Integer cantidad, String tipoArticulo);
    // EXCEPCIONES: ObjetoTiendaNoExisteException, PouIDNoExisteException, PouNoTieneDineroSuficienteException

    @PUT
    @ApiOperation(value = "Comprar objeto", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!"),
            @ApiResponse(code = 407, message = "Dinero insuficiente"),
            @ApiResponse(code = 406, message = "El objeto introducido no existe")
    })
    @Path("/tienda/comprar/{idPou}/{idCompra}/{cantidadCompra}/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprarObjeto(@PathParam("idPou") String idPou,@PathParam("idCompra") String idCompra,@PathParam("cantidadCompra") String cantidadCompra,@PathParam("tipo") String tipo) {
        try {
            this.jvm.pouCompraArticulos(idPou, idCompra, Integer.parseInt(cantidadCompra), tipo);
        }catch (ObjetoTiendaNoExisteException e) {
            return Response.status(406).build();
        }catch (PouNoTieneDineroSuficienteException e) {
            return Response.status(407).build();
        }catch (PouIDNoExisteException e){
            return Response.status(405).build();
        }
        return Response.status(201).build();
    }

    // OPERACION 31: OBTENER UN POU A PARTIR DE UNOS CREDENCIALES
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public Pou obtenerPouByCredentials(Credenciales credenciales);
    // EXCEPCIONES: -
    @POST
    @ApiOperation(value = "Obtener un Pou", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "¡Hecho!", response = Pou.class)
    })
    @Path("/perfil/pou")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response obtenerPouByCredentials(Credenciales credenciales){
        GenericEntity<Pou> miPou = new GenericEntity<Pou>(this.jvm.obtenerPouByCredentials(credenciales)) {};
        return Response.status(200).entity(miPou).build();
    }

    // OPERACION X: OBTENER UNA LISTA DE UN TIPO DE ARMARIO A PARTIR DE UNOS ID DE USUARIO Y UN TIPO DE ARMARIO
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public List<ObjetoArmario> obtenerObjetosArmarioPouTipo (String pouId, String tipoArticulo);
    // EXCEPCIONES: -

    @GET
    @ApiOperation(value = "Obtener una lista de objetos del armario de un tipo en concreto y del usuario correspondiente", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!", response = ObjetoArmario.class, responseContainer="List"),
    })
    @Path("/armario/tipo/{idPou}/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerObjetosArmarioPouTipo(@PathParam("idPou") String idPou, @PathParam("tipo") String tipo) {

        List<ObjetoArmario> listaObjetos = this.jvm.obtenerObjetosArmarioPouTipo(idPou, tipo);

        GenericEntity<List<ObjetoArmario>> enviarListaObjetos = new GenericEntity<List<ObjetoArmario>>(listaObjetos) {};
        return Response.status(201).entity(enviarListaObjetos).build();
    }

    // OPERACION ANDROID 1: OBTENER UNA LISTA CON TODA LA INFORMACIÓN NECESARIA DEL USUARIO
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: public List<ObjetoArmario> obtenerObjetosArmarioPouTipo (String pouId, String tipoArticulo);
    // EXCEPCIONES: -
    @GET
    @ApiOperation(value = "Obtener una lista de objetos del armario de un tipo en concreto y del usuario correspondiente", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!", response = InformacionPou.class),
    })
    @Path("/pou/cargarDatos/{gmail}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfoAndroidPou(@PathParam("gmail") String gmail, @PathParam("password") String password) throws PouIDNoExisteException {

        Credenciales credenciales = new Credenciales(gmail, password);

        InformacionPou informacionPou= this.jvm.getInfoAndroidPou(credenciales);

        GenericEntity<InformacionPou> enviarListaObjetosAndroid = new GenericEntity<InformacionPou>(informacionPou) {};
        return Response.status(201).entity(enviarListaObjetosAndroid).build();
    }

    // OPERACIÓN ANDROID 2: MODIFICAMOS LAS TABLAS CON LOS NUEVOS VALORES DE LA APP
    // MÉTODO HTTP: PUT.
    // ESTRUCTURA:
    // EXCEPCIONES: ObjetoTiendaNoExisteException, PouIDNoExisteException, PouNoTieneDineroSuficienteException

    @PUT
    @ApiOperation(value = "Actualizar los datos", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!")
    })
    @Path("/pou/actualizarDatos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateObjetoArmario(InformacionPou informacionPou) {

        this.jvm.updateAndroid(informacionPou);

        return Response.status(201).build();
    }

    // OPERACIÓN ANDROID 3: COMPROBAMOS SI EL CORREO CAMBIADO ESTÁ DISPONIBLE.
    // MÉTODO HTTP: PUT.
    // ESTRUCTURA: -
    // EXCEPCIONES: CorreoYaExisteException

    @PUT
    @ApiOperation(value = "Comprobar el correo", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!"),
            @ApiResponse(code = 405, message = "¡El correo ya existe!")
    })
    @Path("pou/comprobarCorreo/{gmail}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprobarCorreo(@PathParam("gmail") String gmail) throws CorreoYaExisteException{
        try {
            this.jvm.comprobarCorreo(gmail);
        }catch (CorreoYaExisteException e) {
            return Response.status(405).build();
        }
        return Response.status(201).build();
    }

    // OPERACIÓN ANDROID 4: PEDIR LA INFORMACIÓN DE UN ARTÍCULO DE LA TIENDA POR SU ID.
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: -
    // EXCEPCIONES: -

    @GET
    @ApiOperation(value = "Pedir la información de un artículo de la tienda por su ID.", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Información del artículo obtenida!", response = ObjetoTienda.class)
    })
    @Path("/tienda/obtenerarticulo/{articuloid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerInfoObjeto(@PathParam("articuloid") String articuloId) {
        ObjetoTienda objetoTienda = this.jvm.obtenerInfoObjeto(articuloId);

        GenericEntity<ObjetoTienda> enviarObjetoTienda = new GenericEntity<ObjetoTienda>(objetoTienda) {};
        return Response.status(201).entity(enviarObjetoTienda).build();
    }

    // OPERACIÓN ANDROID 5: PEDIR LOS POUS ORDENADOS POR UNA DE LAS COLUMNAS.
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: -
    // EXCEPCIONES: -

    @GET
    @ApiOperation(value = "Pedir la información de los Pous ordenados por una de sus columnas.", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!", response = Pou.class, responseContainer="List"),
    })
    @Path("/pou/ranking/{rankingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPousOrdenadosDescendentemente(@PathParam("rankingId") String rankingId) {
        logger.info("LLEGA AQUÍ");
        List<Pou> listaPous = this.jvm.obtenerPousOrdenadosDescendentemente(rankingId);
        GenericEntity<List<Pou>> enviarListaPous = new GenericEntity<List<Pou>>(listaPous) {};
        return Response.status(201).entity(enviarListaPous).build();
    }

    // OPERACIÓN ANDROID 6: PEDIR LOS OBJETOS ARMARIO ORDENADOS POR UNA DE LAS COLUMNAS.
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: -
    // EXCEPCIONES: -

    @GET
    @ApiOperation(value = "Pedir la información de los ObjetosArmario ordenados por una de sus columnas.", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!", response = ObjetoArmario.class, responseContainer="List"),
    })
    @Path("/objetoArmario/ranking/{rankingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerArmarioOrdenadoDescendentemente (@PathParam("rankingId") String rankingId) {
        logger.info("LLEGA AQUÍ");
        List<ObjetoArmario> listaObjetoArmario = this.jvm.obtenerArmarioOrdenadoDescendentemente(rankingId);
        GenericEntity<List<ObjetoArmario>> enviarListaArmarios = new GenericEntity<List<ObjetoArmario>>(listaObjetoArmario) {};
        return Response.status(201).entity(enviarListaArmarios).build();
    }

    // OPERACIÓN ANDROID 7: PEDIR LOS OBJETOS TIENDA ORDENADOS POR UNA DE LAS COLUMNAS.
    // MÉTODO HTTP: GET.
    // ESTRUCTURA: -
    // EXCEPCIONES: -

    @GET
    @ApiOperation(value = "Pedir la información de los ObjetosTienda ordenados por una de sus columnas.", notes = "-")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "¡Hecho!", response = ObjetoTienda.class, responseContainer="List"),
    })
    @Path("/objetoTienda/ranking/{rankingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTiendaOrdenadaDescendentemente (@PathParam("rankingId") String rankingId) {
        logger.info("LLEGA AQUÍ");
        List<ObjetoTienda> listaObjetoTienda = this.jvm.obtenerTiendaOrdenadaDescendentemente(rankingId);
        GenericEntity<List<ObjetoTienda>> enviarTienda = new GenericEntity<List<ObjetoTienda>>(listaObjetoTienda) {};
        return Response.status(201).entity(enviarTienda).build();
    }
}

