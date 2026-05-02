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

    // ============ CONVERSIONES DE LONGITUD ============

    @WebMethod(operationName = "convertirMetrosAKm")
    public Conversor convertirMetrosAKm(@WebParam(name = "metros") double metros) {
        return servicio.metrosAKilometros(metros);
    }

    @WebMethod(operationName = "convertirKmAMetros")
    public Conversor convertirKmAMetros(@WebParam(name = "kilometros") double km) {
        return servicio.kilometrosAMetros(km);
    }

    @WebMethod(operationName = "convertirMetrosAPies")
    public Conversor convertirMetrosAPies(@WebParam(name = "metros") double metros) {
        return servicio.metrosAPies(metros);
    }

    @WebMethod(operationName = "convertirPiesAMetros")
    public Conversor convertirPiesAMetros(@WebParam(name = "pies") double pies) {
        return servicio.piesAMetros(pies);
    }

    @WebMethod(operationName = "convertirMetrosAMillas")
    public Conversor convertirMetrosAMillas(@WebParam(name = "metros") double metros) {
        return servicio.metrosAMillas(metros);
    }

    @WebMethod(operationName = "convertirMillasAMetros")
    public Conversor convertirMillasAMetros(@WebParam(name = "millas") double millas) {
        return servicio.millasAMetros(millas);
    }

    @WebMethod(operationName = "convertirMillasAKm")
    public Conversor convertirMillasAKm(@WebParam(name = "millas") double millas) {
        return servicio.millasAKilometros(millas);
    }

    @WebMethod(operationName = "convertirKmAMillas")
    public Conversor convertirKmAMillas(@WebParam(name = "kilometros") double km) {
        return servicio.kilometrosAMillas(km);
    }

    @WebMethod(operationName = "convertirMetrosACm")
    public Conversor convertirMetrosACm(@WebParam(name = "metros") double metros) {
        return servicio.metrosACentimetros(metros);
    }

    @WebMethod(operationName = "convertirCmAMetros")
    public Conversor convertirCmAMetros(@WebParam(name = "centimetros") double cm) {
        return servicio.centimetrosAMetros(cm);
    }

    @WebMethod(operationName = "convertirMetrosAMm")
    public Conversor convertirMetrosAMm(@WebParam(name = "metros") double metros) {
        return servicio.metrosAMilimetros(metros);
    }

    @WebMethod(operationName = "convertirMmAMetros")
    public Conversor convertirMmAMetros(@WebParam(name = "milimetros") double mm) {
        return servicio.milimetrosAMetros(mm);
    }

    @WebMethod(operationName = "convertirPiesAYardas")
    public Conversor convertirPiesAYardas(@WebParam(name = "pies") double pies) {
        return servicio.piesAYardas(pies);
    }

    @WebMethod(operationName = "convertirYardasAPies")
    public Conversor convertirYardasAPies(@WebParam(name = "yardas") double yardas) {
        return servicio.yardasAPies(yardas);
    }

    @WebMethod(operationName = "convertirPulgadasACm")
    public Conversor convertirPulgadasACm(@WebParam(name = "pulgadas") double pulgadas) {
        return servicio.pulgadasACentimetros(pulgadas);
    }

    @WebMethod(operationName = "convertirCmAPulgadas")
    public Conversor convertirCmAPulgadas(@WebParam(name = "centimetros") double cm) {
        return servicio.centimetrosAPulgadas(cm);
    }
}