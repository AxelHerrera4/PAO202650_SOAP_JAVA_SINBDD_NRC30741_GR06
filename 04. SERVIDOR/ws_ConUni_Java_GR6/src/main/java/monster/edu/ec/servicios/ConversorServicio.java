package monster.edu.ec.servicios;

import monster.edu.ec.modelo.Conversor;

public class ConversorServicio {
    
    public Conversor fahrenheitACelsius(double fahrenheit) {
        // Fórmula: (F - 32) * 5/9
        double celsius = (fahrenheit - 32) * 5.0 / 9.0;
        return new Conversor(fahrenheit, "Fahrenheit", celsius, "Celsius");
    }

    public Conversor celsiusAFahrenheit(double celsius) {
        // Fórmula: (C * 9/5) + 32
        double fahrenheit = (celsius * 9.0 / 5.0) + 32;
        return new Conversor(celsius, "Celsius", fahrenheit, "Fahrenheit");
    }
public Conversor celsiusAKelvin(double celsius) {
    // Orden: ValorOrigen, UnidadOrigen, ValorDestino, UnidadDestino
    return new Conversor(celsius, "C", (celsius + 273.15), "K");
}

public Conversor kelvinACelsius(double kelvin) {
    return new Conversor(kelvin, "K", (kelvin - 273.15), "C");
}
public Conversor fahrenheitAKelvin(double f) {
    double k = (f - 32) / 1.8 + 273.15;
    // El orden correcto segun tu error es: Valor, Unidad, Valor, Unidad
    return new Conversor(f, "F", k, "K");
}

public Conversor kelvinAFahrenheit(double k) {
    double f = (k - 273.15) * 1.8 + 32;
    // Valor Origen, Unidad Origen, Valor Destino, Unidad Destino
    return new Conversor(k, "K", f, "F");
}
}