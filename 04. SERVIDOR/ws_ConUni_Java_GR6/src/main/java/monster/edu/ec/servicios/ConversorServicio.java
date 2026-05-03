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

    // ============ CONVERSIONES DE LONGITUD ============

    public Conversor metrosAKilometros(double metros) {
        double km = metros / 1000;
        return new Conversor(metros, "m", km, "km");
    }

    public Conversor kilometrosAMetros(double km) {
        double metros = km * 1000;
        return new Conversor(km, "km", metros, "m");
    }

    public Conversor metrosAPies(double metros) {
        // 1 metro = 3.28084 pies
        double pies = metros * 3.28084;
        return new Conversor(metros, "m", pies, "ft");
    }

    public Conversor piesAMetros(double pies) {
        // 1 pie = 0.3048 metros
        double metros = pies * 0.3048;
        return new Conversor(pies, "ft", metros, "m");
    }

    public Conversor metrosAMillas(double metros) {
        // 1 metro = 0.000621371 millas
        double millas = metros * 0.000621371;
        return new Conversor(metros, "m", millas, "mi");
    }

    public Conversor millasAMetros(double millas) {
        // 1 milla = 1609.34 metros
        double metros = millas * 1609.34;
        return new Conversor(millas, "mi", metros, "m");
    }

    public Conversor millasAKilometros(double millas) {
        double km = millas * 1.60934;
        return new Conversor(millas, "mi", km, "km");
    }

    public Conversor kilometrosAMillas(double km) {
        double millas = km / 1.60934;
        return new Conversor(km, "km", millas, "mi");
    }

    public Conversor metrosACentimetros(double metros) {
        double cm = metros * 100;
        return new Conversor(metros, "m", cm, "cm");
    }

    public Conversor centimetrosAMetros(double cm) {
        double metros = cm / 100;
        return new Conversor(cm, "cm", metros, "m");
    }

    public Conversor metrosAMilimetros(double metros) {
        double mm = metros * 1000;
        return new Conversor(metros, "m", mm, "mm");
    }

    public Conversor milimetrosAMetros(double mm) {
        double metros = mm / 1000;
        return new Conversor(mm, "mm", metros, "m");
    }

    public Conversor piesAYardas(double pies) {
        // 1 yarda = 3 pies
        double yardas = pies / 3;
        return new Conversor(pies, "ft", yardas, "yd");
    }

    public Conversor yardasAPies(double yardas) {
        double pies = yardas * 3;
        return new Conversor(yardas, "yd", pies, "ft");
    }

    // ============ PULGADAS <-> CENTÍMETROS ============

    public Conversor pulgadasACentimetros(double pulgadas) {
        // 1 pulgada = 2.54 centímetros
        double cm = pulgadas * 2.54;
        return new Conversor(pulgadas, "in", cm, "cm");
    }

    public Conversor centimetrosAPulgadas(double cm) {
        double pulgadas = cm / 2.54;
        return new Conversor(cm, "cm", pulgadas, "in");
    }

    // ============ CONVERSIONES DE MASA ============

    public Conversor kilogramosAGramos(double kg) {
        // 1 kg = 1000 g
        double gramos = kg * 1000;
        return new Conversor(kg, "kg", gramos, "g");
    }

    public Conversor gramosAKilogramos(double gramos) {
        // 1 g = 0.001 kg
        double kg = gramos / 1000;
        return new Conversor(gramos, "g", kg, "kg");
    }

    public Conversor gramosAMiligramos(double gramos) {
        // 1 g = 1000 mg
        double mg = gramos * 1000;
        return new Conversor(gramos, "g", mg, "mg");
    }

    public Conversor miligramosAGramos(double mg) {
        // 1 mg = 0.001 g
        double gramos = mg / 1000;
        return new Conversor(mg, "mg", gramos, "g");
    }

    public Conversor librasAKilogramos(double libras) {
        // 1 lb = 0.453592 kg
        double kg = libras * 0.453592;
        return new Conversor(libras, "lb", kg, "kg");
    }

    public Conversor kilogramosALibras(double kg) {
        // 1 kg = 2.20462 lb
        double libras = kg * 2.20462;
        return new Conversor(kg, "kg", libras, "lb");
    }

    public Conversor onzasAGramos(double oz) {
        // 1 oz = 28.3495 g
        double gramos = oz * 28.3495;
        return new Conversor(oz, "oz", gramos, "g");
    }

    public Conversor gramosAOnzas(double gramos) {
        // 1 g = 0.035274 oz
        double oz = gramos * 0.035274;
        return new Conversor(gramos, "g", oz, "oz");
    }

    public Conversor toneladasAKilogramos(double t) {
        // 1 t = 1000 kg
        double kg = t * 1000;
        return new Conversor(t, "t", kg, "kg");
    }

    public Conversor kilogramosAToneladas(double kg) {
        // 1 kg = 0.001 t
        double t = kg / 1000;
        return new Conversor(kg, "kg", t, "t");
    }
}