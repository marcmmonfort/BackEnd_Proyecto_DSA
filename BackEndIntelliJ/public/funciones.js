
function registro() {
    var userid = $('#registro_userId').val();
    var pouname = $('#registro_pouname').val();
    var birthday = $('#registro_birthday').val();
    var email = $('#registro_email').val();
    var password = $('#registro_password').val();

    if (userid === "" || pouname === "" || birthday === "" || email === "" || password === "" ){
        alert("Asegurate de que no hayas dejado ningun textbox en blanco");
    }else{
        $.post({
            url: '/dsaApp/pougame/pou/registro',
            data: JSON.stringify({"pouId": userid, "nombrePou": pouname, "nacimientoPou": birthday, "correo": email, "password": password}),
            contentType: 'application/json; charset=utf-8'
        })
            .done(function(data, status){
                console.log(status);
                alert("Registro con exito!");
                window.location.href = "login.html";
            })
            .fail(function(xhr, err){
                console.log(xhr.status);
                if(xhr.status === 405){
                    alert("¡Ya hay un usuario con ese ID!");
                }
                else{
                    alert("¡Ya hay una cuenta asociada a este correo!");
                    window.location.href = "login.html";
                }
            });
    }
}

function login() {
    var email = $('#login_email').val();
    var password = $('#login_password').val();
    console.log(email);
    console.log(password);
    localStorage.setItem('correo', email)
    localStorage.setItem('password',password);
    if (email === "" || password === "")
        alert("Asegurate de que no hayas dejado ningun textbox en blanco");
    else{
        $.post({
            url: '/dsaApp/pougame/pou/login',
            data: JSON.stringify({"correoPou": email, "passwordPou": password}),
            contentType: 'application/json; charset=utf-8'
        })
            .done(function(data, status){
                console.log(status);
                console.log(data);
                alert("Login con exito!");
                window.location.href = "miPou.html";
            })
            .fail(function(xhr, err){
                console.log(xhr.status);
                if(xhr.status === 405){
                    alert("¡La contraseña introducida es incorrecta!");
                }
                else{
                    alert("¡El correo introducido no existe, registrate!");
                    window.location.href = "registro.html";
                }
            });
    }
}


function GetListaObjectosTienda(){
    // Create an XMLHttpRequest object
    const xhttp = new XMLHttpRequest();

    // Define a callback function
    xhttp.onload = function() {
        // Here you can use the Data
        console.log("Información recibida");
        var datos=  JSON.parse(this.responseText);
        console.log(datos);
        $('#resultados').empty();
        for (var i=0;i<datos.length;++i)
        {
            $("#resultados").append("<tr>"+
                "<td>"+datos[i].nombreArticulo+"</td>"+
                "<td>"+datos[i].articuloId+"</td>"+
                "<td>"+datos[i].precioArticulo+"</td>"+
                "<td>"+datos[i].tipoArticulo+"</td>"+
                "<td>"+datos[i].recargaHambre+"</td>"+
                "<td>"+datos[i].recargaSalud+"</td>"+
                "<td>"+datos[i].recargaDiversion+"</td>"+
                "<td>"+datos[i].recargaSueno+"</td>"+
                "</tr>");
        }
    }

    // Send a request
    console.log("Se envía la petición");
    var url='http://localhost:8080/dsaApp/pougame/tienda/listaObjetos';
    //var url='http://147.83.7.203/dsaApp/pougame/tienda/listaObjetos';
    xhttp.open("GET",url, true);
    xhttp.send();
}

function obtenerPouByCredentials(){

    const correo = localStorage.getItem('correo');
    const password = localStorage.getItem('password');

    console.log(correo);
    console.log(password);

    $.post({
        url: '/dsaApp/pougame/perfil/pou',
        data: JSON.stringify({"correoPou": correo, "passwordPou": password}),
        contentType: 'application/json; charset=utf-8',
    })
        .done(function(data, status){
            //console.log(status);
            console.log(data);
            var datos =  JSON.stringify(data);
            alert("Cargados los datos del Pou!");
            console.log(datos);

            $('#comidasArmarioTabla').empty();
            $('#bebidasArmarioTabla').empty();
            $('#pocionesArmarioTabla').empty();
            $('#ropaArmarioTabla').empty();

            var pouId = datos.pouId;
            console.log(pouId);
            var nombrePou = datos.nombrePou;
            console.log(nombrePou);
            var nacimientoPou = datos.nacimientoPou;
            var credencialesPou = datos.credencialesPou;
            var estadoPou = datos.estadoPou;
            var dinertoPou = estadoPou.dineroPou;
            var nivelHambrePou = estadoPou.nivelHambrePou;
            var nivelSaludPou = estadoPou.nivelSaludPou;
            var nivelDiversionPou = estadoPou.nivelDiversionPou;
            var nivelSuenoPou = estadoPou.nivelSuenoPou;
            var outfitPou = datos.outfitPou;
            var armarioPou = datos.armarioPou;
            var comidasArmario = armarioPou.hash.comidas;
            var bebidasArmario = armarioPou.hash.bebidas;
            var pocionesArmario = armarioPou.hash.pociones;
            var ropaArmario = armarioPou.hash.ropa;

            document.getElementById("estadoHambre").innerText = nivelHambrePou;
            $("#estadoHambre").value = nivelHambrePou;
            $("#estadoSalud").value = nivelSaludPou;
            $("#estadoDiversion").value = nivelDiversionPou;
            $("#estadoSueño").value = nivelSuenoPou;

            for (var i1=0;i<comidasArmario.length;++i)
            {
                $("#comidasArmarioTabla").append("<tr>"+
                    "<td>"+comidasArmario[i1].nombreArticulo+"</td>"+
                    "<td>"+comidasArmario[i1].articuloId+"</td>"+
                    "<td>"+comidasArmario[i1].precioArticulo+"</td>"+
                    "<td>"+comidasArmario[i1].tipoArticulo+"</td>"+
                    "<td>"+comidasArmario[i1].recargaHambre+"</td>"+
                    "<td>"+comidasArmario[i1].recargaSalud+"</td>"+
                    "</tr>");
            }

            for (var i2=0;i<bebidasArmario.length;++i)
            {
                $("#bebidasArmarioTabla").append("<tr>"+
                    "<td>"+bebidasArmario[i2].nombreArticulo+"</td>"+
                    "<td>"+bebidasArmario[i2].articuloId+"</td>"+
                    "<td>"+bebidasArmario[i2].precioArticulo+"</td>"+
                    "<td>"+bebidasArmario[i2].tipoArticulo+"</td>"+
                    "<td>"+bebidasArmario[i2].recargaSalud+"</td>"+
                    "<td>"+bebidasArmario[i2].recargaSueno+"</td>"+
                    "</tr>");
            }

            for (var i3=0;i<pocionesArmario.length;++i)
            {
                $("#pocionesArmarioTabla").append("<tr>"+
                    "<td>"+pocionesArmario[i3].nombreArticulo+"</td>"+
                    "<td>"+pocionesArmario[i3].articuloId+"</td>"+
                    "<td>"+pocionesArmario[i3].precioArticulo+"</td>"+
                    "<td>"+pocionesArmario[i3].tipoArticulo+"</td>"+
                    "<td>"+pocionesArmario[i3].recargaHambre+"</td>"+
                    "<td>"+pocionesArmario[i3].recargaSalud+"</td>"+
                    "<td>"+pocionesArmario[i3].recargaDiversion+"</td>"+
                    "<td>"+pocionesArmario[i3].recargaSueno+"</td>"+
                    "</tr>");
            }

            for (var i4=0;i<ropaArmario.length;++i)
            {
                $("#ropaArmarioTabla").append("<tr>"+
                    "<td>"+ropaArmario[i4].nombreArticulo+"</td>"+
                    "<td>"+ropaArmario[i4].articuloId+"</td>"+
                    "<td>"+ropaArmario[i4].precioArticulo+"</td>"+
                    "<td>"+ropaArmario[i4].tipoArticulo+"</td>"+
                    "</tr>");
            }
        })
        .fail(function(xhr, err){
            console.log(xhr.status);
            if(xhr.status === 405){
                alert("No funciona esta mierda");
            }
        });

}
/*
//Pedimos que nos devuelvan al usuario con x ID
// Create an XMLHttpRequest object
const xhttp = new XMLHttpRequest();

// Define a callback function
xhttp.onload = function() {
    // Here you can use the Data
    console.log("Información recibida");
    var datos=  JSON.parse(this.responseText);
    console.log(datos);
    $('#resultados').empty();
    var pouId = datos.getPouId();
    var nombrePou = datos.getNombrePou();
    var nacimientoPou = datos.getNacimientoPou();
    var credencialesPou = datos.getCredencialesPou();
    var estadoPou = datos.getEstadoPou();
    var dinertoPou = estadoPou.getDineroPou();
    var nivelHambrePou = estadoPou.getNivelHambrePou();
    var nivelSaludPou = estadoPou.getNivelSaludPou();
    var nivelDiversionPou = estadoPou.getNivelDiversionPou();
    var nivelSuenoPou = estadoPou.getNivelSuenoPou();
    var outfitPou = datos.getOutfitPou();
    var armarioPou = datos.getArmarioPou();
    var comidasArmario = armarioPou.hash.getComidas();
    var bebidasArmario = armarioPou.hash.getbebidas();
    var pocionesArmario = armarioPou.hash.getPociones();
    var ropaArmario = armarioPou.hash.getRopa();

    document.getElementById("estadoHambre").innerText = nivelHambrePou;
    $("#estadoHambre").value = nivelHambrePou;
    $("#estadoSalud").value = nivelSaludPou;
    $("#estadoDiversion").value = nivelDiversionPou;
    $("#estadoSueño").value = nivelSuenoPou;

    for (var i1=0;i<comidasArmario.length;++i)
    {
        $("#comidasArmarioTabla").append("<tr>"+
            "<td>"+comidasArmario[i1].nombreArticulo+"</td>"+
            "<td>"+comidasArmario[i1].articuloId+"</td>"+
            "<td>"+comidasArmario[i1].precioArticulo+"</td>"+
            "<td>"+comidasArmario[i1].tipoArticulo+"</td>"+
            "<td>"+comidasArmario[i1].recargaHambre+"</td>"+
            "<td>"+comidasArmario[i1].recargaSalud+"</td>"+
            "</tr>");
    }

    for (var i2=0;i<bebidasArmario.length;++i)
    {
        $("#bebidasArmarioTabla").append("<tr>"+
            "<td>"+bebidasArmario[i2].nombreArticulo+"</td>"+
            "<td>"+bebidasArmario[i2].articuloId+"</td>"+
            "<td>"+bebidasArmario[i2].precioArticulo+"</td>"+
            "<td>"+bebidasArmario[i2].tipoArticulo+"</td>"+
            "<td>"+bebidasArmario[i2].recargaSalud+"</td>"+
            "<td>"+bebidasArmario[i2].recargaSueno+"</td>"+
            "</tr>");
    }

    for (var i3=0;i<pocionesArmario.length;++i)
    {
        $("#pocionesArmarioTabla").append("<tr>"+
            "<td>"+pocionesArmario[i3].nombreArticulo+"</td>"+
            "<td>"+pocionesArmario[i3].articuloId+"</td>"+
            "<td>"+pocionesArmario[i3].precioArticulo+"</td>"+
            "<td>"+pocionesArmario[i3].tipoArticulo+"</td>"+
            "<td>"+pocionesArmario[i3].recargaHambre+"</td>"+
            "<td>"+pocionesArmario[i3].recargaSalud+"</td>"+
            "<td>"+pocionesArmario[i3].recargaDiversion+"</td>"+
            "<td>"+pocionesArmario[i3].recargaSueno+"</td>"+
            "</tr>");
    }

    for (var i4=0;i<ropaArmario.length;++i)
    {
        $("#ropaArmarioTabla").append("<tr>"+
            "<td>"+ropaArmario[i4].nombreArticulo+"</td>"+
            "<td>"+ropaArmario[i4].articuloId+"</td>"+
            "<td>"+ropaArmario[i4].precioArticulo+"</td>"+
            "<td>"+ropaArmario[i4].tipoArticulo+"</td>"+
            "</tr>");
    }
}
// Send a request
console.log("Se envía la petición");
var url='http://localhost:8080/dsaApp/perfil';
//var url='http://147.83.7.203/dsaApp/perfil';
xhttp.open("GET",url, true);
xhttp.send();

 */

function pouCompraArticulos(){

    var idPou = $("#id_pou").val();
    var idCompra = $("#id_compra").val();
    var cantidadCompra = $("#cantidad_compra").val();


    $.ajax({
        type: 'PUT',
        url: '/dsaApp/pougame/tienda/comprar' + idPou + '/' + idCompra + '/' + cantidadCompra,
        //url: '/dsaApp/pougame/tienda/comprar' + idPou + '/' + idCompra + '/' + cantidadCompra,
        dataType: 'text',
        success: function(data, status){
            alert("¡Objecto comprado con exito!");
        },
        error: function(xhr, err){
            alert("Error al comprar un objeto");
            console.log(xhr.status);
            if(xhr.status === 404){
                alert("");
            }
            else{
                alert("");
            }
        }
    });

}