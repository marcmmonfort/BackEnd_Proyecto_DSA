
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
    localStorage.setItem('correo', email);
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

            $('#comidasArmarioTabla').empty();
            $('#bebidasArmarioTabla').empty();
            $('#pocionesArmarioTabla').empty();
            $('#ropaArmarioTabla').empty();


            const pouId = data.pouId;
            localStorage.setItem('pouId', pouId);
            const nombrePou = data.nombrePou;
            console.log(nombrePou);
            const nacimientoPou = data.nacimientoPou;
            const dineroPou = data.dineroPou;
            const nivelHambrePou = data.nivelHambrePou;
            const nivelSaludPou = data.nivelSaludPou;
            const nivelDiversionPou = data.nivelDiversionPou;
            const nivelSuenoPou = data.nivelSuenoPou;
            const camisetaId = data.camisetaId;
            const zapatosId = data.zapatosId;
            const gorrasId = data.gorrasId;
            const gafasId = data.gafasId;

            document.getElementById("pouId").value = pouId;
            document.getElementById("dineroPou").value = dineroPou;
            document.getElementById("estadoHambre").value = nivelHambrePou;
            document.getElementById("estadoSalud").value = nivelSaludPou;
            document.getElementById("estadoDiversion").value = nivelDiversionPou;
            document.getElementById("estadoSueño").value = nivelSuenoPou;

            for (var i1=0; i1<comidasArmario.length;++i1)
            {
                console.log("articuloID: ",comidasArmario[0].value.nombreArticulo);
                console.log("articuloID: ",comidasArmario[0].value.articuloId);
                console.log("articuloID: ",comidasArmario[0].value.precioArticulo);
                console.log("articuloID: ",comidasArmario[0].value.tipoArticulo);
                console.log("articuloID: ",comidasArmario[0].value.recargaHambre);
                console.log("articuloID: ",comidasArmario[0].value.recargaSalud);

                $("#comidasArmarioTabla").append("<tr>"+
                    "<td>"+comidasArmario[i1].value.nombreArticulo+"</td>"+
                    "<td>"+comidasArmario[i1].value.articuloId+"</td>"+
                    "<td>"+comidasArmario[i1].value.precioArticulo+"</td>"+
                    "<td>"+comidasArmario[i1].value.tipoArticulo+"</td>"+
                    "<td>"+comidasArmario[i1].value.recargaHambre+"</td>"+
                    "<td>"+comidasArmario[i1].value.recargaSalud+"</td>"+
                    "</tr>");
            }

            for (var i2=0;i2<bebidasArmario.length;++i2)
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

            for (var i3=0;i3<pocionesArmario.length;++i3)
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

            for (var i4=0;i4<ropaArmario.length;++i4)
            {
                $("#ropaArmarioTabla").append("<tr>"+
                    "<td>"+ropaArmario[i4].nombreArticulo+"</td>"+
                    "<td>"+ropaArmario[i4].articuloId+"</td>"+
                    "<td>"+ropaArmario[i4].precioArticulo+"</td>"+
                    "<td>"+ropaArmario[i4].tipoArticulo+"</td>"+
                    "</tr>");
            }
            alert("¡Cargados los datos del Pou!");
        })
        .fail(function(xhr, err){
            console.log(xhr.status);
            if(xhr.status === 405){
                alert("¡¡No funciona!!");
            }
        });

}

/*
function comprarObjeto(ID, Precio, tipo){

    var idPou = $('#id_pou').val();
    console.log(idPou);
    var idCompra = $('#id_compra').val();
    console.log(idCompra);
    var cantidadCompra = $('#cantidad_compra').val();
    console.log(cantidadCompra);
    var tipo = $('#tipo_compra').val();
    console.log(tipo);
    $.ajax({
        type: 'PUT',
        url: '/dsaApp/pougame/tienda/comprar/' + idPou + '/' + idCompra + '/' + cantidadCompra + '/' + tipo,
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

 */

function comprarObjeto(idCompra, cantidadCompra, tipo){

    const idPou = localStorage.getItem('pouId');
    console.log(idCompra);
    console.log(cantidadCompra);
    console.log(tipo);

    $.ajax({
        type: 'PUT',
        url: '/dsaApp/pougame/tienda/comprar/' + idPou + '/' + idCompra + '/' + cantidadCompra + '/' + tipo,
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