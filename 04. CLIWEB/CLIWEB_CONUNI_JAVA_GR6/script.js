const WS_URL = "http://localhost:8080/ws_ConUni_Java_GR6/ConversorWS";

function login() {
    const user = document.getElementById("txtUsuario").value.trim();
    const pass = document.getElementById("txtPassword").value.trim();

    if (user === "monster" && pass === "monster9") {
        document.getElementById("login-container").style.display = "none";
        document.getElementById("main-container").style.display = "block";
    } else {
        alert("Credenciales incorrectas");
    }
}

async function calcular() {
    const valor = document.getElementById("txtValor").value;
    const origen = document.getElementById("cbOrigen").value;
    const destino = document.getElementById("cbDestino").value;
    
    if (!valor) return alert("Ingresa un valor");

    // 1. Determinar el método y el nombre del parámetro exacto del Java
    const inicialOrigen = origen.charAt(0); // C, F o K
    const inicialDestino = destino.charAt(0); // C, F o K
    const metodo = `convertir${inicialOrigen}to${inicialDestino}`;
    
    // Mapeo de parámetros según tus @WebParam en Java
    let paramName = "gradosC"; 
    if (inicialOrigen === 'F') paramName = "gradosF";
    if (inicialOrigen === 'K') paramName = "gradosK";

    // 2. El sobre SOAP con el namespace y parámetro correcto
    const xmlBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://ws.ec.edu.monster/">
       <soapenv:Header/>
       <soapenv:Body>
          <ser:${metodo}>
             <${paramName}>${valor}</${paramName}>
          </ser:${metodo}>
       </soapenv:Body>
    </soapenv:Envelope>`;

    try {
        const response = await fetch(WS_URL, {
            method: "POST",
            headers: { "Content-Type": "text/xml" },
            body: xmlBody
        });

        const data = await response.text();
        console.log(data); // Para que veas la respuesta completa en consola

        // 3. Extraer valorDestino (etiqueta generada por tu clase Conversor)
        const match = data.match(/<valorDestino>(.*?)<\/valorDestino>/);
        const resultado = match ? parseFloat(match[1]).toFixed(2) : "Error";

        document.getElementById("lblResultado").innerText = `Resultado: ${resultado} ${destino}`;
    } catch (e) {
        alert("Error de conexión. ¿Está la extensión de CORS activa y Payara encendido?");
    }
}

function limpiar() {
    document.getElementById("txtValor").value = "";
    document.getElementById("lblResultado").innerText = "Resultado: ...";
}