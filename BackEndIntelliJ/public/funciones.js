
function registro() {
    var userid = $('#registro_userId').val();
    var pouname = $('#registro_pouname').val();
    var birthday = $('#registro_birthday').val();
    var email = $('#registro_email').val();
    var email2 = $('#registro_email2').val();
    var password = $('#registro_password').val();
    var password2 = $('#registro_password2').val();

    if (userid === "" || pouname === "" || birthday === "" || email === "" || email2 === "" || password === "" || password2 === ""){
        alert("Asegurate de que no hayas dejado ningun textbox en blanco");
    }else if (email !== email2){
        alert("Los emails que has introducido no son iguales");
    }else if ( password !== password2) {
        alert("Las contraseñas que has introducido no son iguales");
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

function comprarObjeto(){

    var idPou = $('#id_pou').val();
    console.log(idPou);
    var idCompra = $('#id_compra').val();
    console.log(idCompra);
    var cantidadCompra = $('#cantidad_compra').val();
    console.log(cantidadCompra);
    var tipo = $('#tipo_compra').val();
    console.log(tipo);

    $.ajax({
        //contentType: "application/json",
        type: 'PUT',
        url: '/dsaApp/pougame/tienda/comprar/' + idPou + '/' + idCompra + '/' + cantidadCompra + '/' + tipo,
        //data: JSON.stringify({userId: n, objetoId: p, mail: m}),
        dataType: 'text',
        success: function(){
            alert("¡Objecto comprado con exito!");
        },
        error: function(){
            alert("Error al comprar un objeto");
            console.log(xhr.status);
            if(xhr.status === 406){
                alert("Id del objeto incorrecto");
            }
        }
    });

}

function comprarObjeto2(){

    var idPou1 = $('#id_pou').val();
    console.log(idPou1);
    var idCompra1 = $('#id_compra').val();
    console.log(idCompra1);
    var cantidadCompra1 = $('#cantidad_compra').val();
    console.log(cantidadCompra1);
    var tipo1 = $('#tipo_compra').val();
    console.log(tipo1);
    // Create an XMLHttpRequest object
    const xhttp1 = new XMLHttpRequest();

    // Define a callback function
    xhttp1.onload = function() {
        // Here you can use the Data
        console.log("Información recibida");
        var datos=  JSON.parse(this.responseText);
        console.log(datos);

    }

    // Send a request
    console.log("Se envía la petición");
    var url='http://localhost:8080/dsaApp/pougame/tienda/comprar/' + idPou1 + '/' + idCompra1 + '/' + cantidadCompra1 + '/' + tipo1;
    //var url='http://147.83.7.203/dsaApp/pougame/tienda/listaObjetos';
    xhttp.open("PUT",url, true);
    xhttp.send();
}