package monster.edu.ec.cli_conuni_java_gr6;

import java.util.Scanner;
import monster.edu.ec.cliente.ConversorWS;
import monster.edu.ec.cliente.ConversorWS_Service;
import monster.edu.ec.cliente.Conversor;

public class Cli_ConUni_Java_GR6 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean accesoConcedido = false;

        // Bucle de Login: no termina hasta que las credenciales sean correctas
        do {
            System.out.println("=== LOGIN SISTEMA MONSTER ===");
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contrasena: ");
            String pass = sc.nextLine();

            if (user.equals("monster") && pass.equals("monster9")) {
                System.out.println("\nAcceso concedido");
                accesoConcedido = true;
                mostrarMenu(sc);
            } else {
                System.out.println("\nCredenciales incorrectas. Intente de nuevo.\n");
            }
        } while (!accesoConcedido);
    }

    public static void mostrarMenu(Scanner sc) {
        try {
            // Conexion al Web Service (Controlador Remoto)
            ConversorWS_Service service = new ConversorWS_Service();
            ConversorWS port = service.getConversorWSPort();
            
            int opcion = 0;
            do {
                System.out.println("\n--- MENU CONVERSOR GRUPO 6 ---");
                System.out.println("1. Fahrenheit a Celsius");
                System.out.println("2. Celsius a Fahrenheit");
                System.out.println("3. Celsius a Kelvin");
                System.out.println("4. Kelvin a Celsius");
                System.out.println("5. Fahrenheit a Kelvin");
                System.out.println("6. Kelvin a Fahrenheit");
                System.out.println("7. Salir");
                System.out.print("Seleccione una opcion: ");
                
                // Validacion simple para evitar errores si no ingresan un numero
                while (!sc.hasNextInt()) {
                    System.out.println("Por favor, ingrese un numero valido.");
                    sc.next();
                }
                opcion = sc.nextInt();

                if (opcion >= 1 && opcion <= 6) {
                    System.out.print("Ingrese el valor a transformar: ");
                    double valor = sc.nextDouble();
                    Conversor res = null;

                    switch(opcion) {
                        case 1: res = port.convertirFtoC(valor); break;
                        case 2: res = port.convertirCtoF(valor); break;
                        case 3: res = port.convertirCtoK(valor); break;
                        case 4: res = port.convertirKtoC(valor); break;
                        case 5: res = port.convertirFtoK(valor); break;
                        case 6: res = port.convertirKtoF(valor); break;
                    }

                    if (res != null) {
                        System.out.println("\n>> Resultado:");
                        System.out.println(res.getValorOrigen() + " grados " + res.getUnidadOrigen() + 
                                         " equivalen a " + res.getValorDestino() + " grados " + res.getUnidadDestino());
                    }
                } else if (opcion != 7) {
                    System.out.println("Opcion no valida.");
                }

            } while (opcion != 7); // Salida con la opcion 7

            System.out.println("Gracias por usar el sistema. Adios");
            System.exit(0); // Cierra la aplicacion completamente al salir

        } catch (Exception e) {
            System.out.println("Error de conexion con el servidor SOAP.");
            e.printStackTrace();
        }
    }
}