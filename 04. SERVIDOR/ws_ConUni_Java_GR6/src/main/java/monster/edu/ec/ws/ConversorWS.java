package monster.edu.ec.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import monster.edu.ec.modelo.Conversor;
import monster.edu.ec.servicios.ConversorServicio;

@WebService(serviceName = "ConversorWS")
public class ConversorWS {

    // Instanciamos el servicio de lógica
    private ConversorServicio servicio = new ConversorServicio();

    @WebMethod(operationName = "convertirFtoC")
    public Conversor convertirFtoC(@WebParam(name = "gradosF") double gradosF) {
        return servicio.fahrenheitACelsius(gradosF);
    }
    
    @WebMethod(operationName = "convertirCtoF")
    public Conversor convertirCtoF(@WebParam(name = "gradosC") double gradosC) {
        return servicio.celsiusAFahrenheit(gradosC);
    }
    @WebMethod(operationName = "convertirCtoK")
public Conversor convertirCtoK(@WebParam(name = "gradosC") double gradosC) {
    return servicio.celsiusAKelvin(gradosC);
}

@WebMethod(operationName = "convertirKtoC")
public Conversor convertirKtoC(@WebParam(name = "gradosK") double gradosK) {
    return servicio.kelvinACelsius(gradosK);
}
@WebMethod(operationName = "convertirFtoK")
public Conversor convertirFtoK(@WebParam(name = "gradosF") double f) {
    return servicio.fahrenheitAKelvin(f);
}

@WebMethod(operationName = "convertirKtoF")
public Conversor convertirKtoF(@WebParam(name = "gradosK") double k) {
    return servicio.kelvinAFahrenheit(k);
}
}