package monster.edu.ec.modelo;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "Conversor") 
public class Conversor {
    private double valorOrigen;
    private String unidadOrigen;
    private double valorDestino;
    private String unidadDestino;

    // Constructor vacío obligatorio para SOAP
    public Conversor() {}

    public Conversor(double valorOrigen, String unidadOrigen, double valorDestino, String unidadDestino) {
        this.valorOrigen = valorOrigen;
        this.unidadOrigen = unidadOrigen;
        this.valorDestino = valorDestino;
        this.unidadDestino = unidadDestino;
    }

    // Getters y Setters
    public double getValorOrigen() { return valorOrigen; }
    public void setValorOrigen(double valorOrigen) { this.valorOrigen = valorOrigen; }

    public String getUnidadOrigen() { return unidadOrigen; }
    public void setUnidadOrigen(String unidadOrigen) { this.unidadOrigen = unidadOrigen; }

    public double getValorDestino() { return valorDestino; }
    public void setValorDestino(double valorDestino) { this.valorDestino = valorDestino; }

    public String getUnidadDestino() { return unidadDestino; }
    public void setUnidadDestino(String unidadDestino) { this.unidadDestino = unidadDestino; }
}