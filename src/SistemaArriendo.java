import java.util.*;

// ---------------- AUTO (Prototype) ----------------
class Auto implements Cloneable {
    int id;
    String marca;
    String modelo;
    String tipo;
    double precioPorDia;
    boolean disponible;

    public Auto(int id, String marca, String modelo, String tipo, double precioPorDia) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.precioPorDia = precioPorDia;
        this.disponible = true;
    }

    @Override
    public Auto clone() {
        try {
            return (Auto) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "ID " + id + " - " + marca + " " + modelo + " (" + tipo + ") - $" + precioPorDia + "/día - Disponible: " + disponible;
    }
}

// ---------------- CLIENTE ----------------
class Cliente {
    int id;
    String nombre;
    String rut;

    public Cliente(int id, String nombre, String rut) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
    }
}

// ---------------- BRIDGE: Implementación ----------------
interface CalculadoraPrecio {
    double calcular(int dias, double precioPorDia);
}

class CalculoNormal implements CalculadoraPrecio {
    @Override
    public double calcular(int dias, double precioPorDia) {
        return dias * precioPorDia;
    }
}

class CalculoDescuento implements CalculadoraPrecio {
    @Override
    public double calcular(int dias, double precioPorDia) {
        return dias >= 7 ? dias * precioPorDia * 0.9 : dias * precioPorDia;
    }
}

// ---------------- BRIDGE: Abstracción ----------------
class Reserva {
    Cliente cliente;
    Auto auto;
    int dias;
    CalculadoraPrecio calculadora;

    public Reserva(Cliente cliente, Auto auto, int dias, CalculadoraPrecio calculadora) {
        this.cliente = cliente;
        this.auto = auto;
        this.dias = dias;
        this.calculadora = calculadora;
    }

    public double calcularPrecio() {
        return calculadora.calcular(dias, auto.precioPorDia);
    }
}

// ---------------- SINGLETON ----------------
class Gestor {
    private static Gestor instancia = null;

    List<Auto> autos = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();
    List<Reserva> reservas = new ArrayList<>();

    private Gestor() {}

    public static Gestor getInstancia() {
        if (instancia == null) {
            instancia = new Gestor();
        }
        return instancia;
    }

    public void cargarAutos() {
        Auto base = new Auto(1, "Toyota", "Corolla", "Sedan", 30000);
        Auto copia1 = base.clone(); 
        copia1.id = 2; copia1.marca = "Mazda"; copia1.modelo = "3"; copia1.precioPorDia = 35000;

        Auto copia2 = base.clone(); 
        copia2.id = 3; copia2.marca = "Hyundai"; copia2.modelo = "Tucson"; copia2.tipo = "SUV"; copia2.precioPorDia = 40000;

        autos.add(base);
        autos.add(copia1);
        autos.add(copia2);

        System.out.println("Autos cargados (Adapter simulado).");
    }

    public void mostrarAutosDisponibles() {
        System.out.println("\nAutos disponibles:");
        for (Auto a : autos) {
            if (a.disponible) System.out.println(a);
        }
    }

    public void crearReserva(Cliente cliente, int idAuto, int dias, boolean conDescuento) {
        for (Auto a : autos) {
            if (a.id == idAuto && a.disponible) {
                CalculadoraPrecio calculadora = conDescuento ? new CalculoDescuento() : new CalculoNormal();
                Reserva r = new Reserva(cliente, a, dias, calculadora);
                reservas.add(r);
                a.disponible = false;
                System.out.println("\nReserva creada por " + dias + " días. Total: $" + r.calcularPrecio());
                return;
            }
        }
        System.out.println("Auto no disponible.");
    }
}

// ---------------- MAIN ----------------
public class SistemaArriendo {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Gestor gestor = Gestor.getInstancia();

            gestor.cargarAutos();

            System.out.print("Ingrese su nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese su RUT: ");
            String rut = sc.nextLine();
            Cliente cliente = new Cliente(1, nombre, rut);

            boolean seguir = true;
            while (seguir) {
                System.out.println("\n--- MENÚ ---");
                System.out.println("1. Ver autos disponibles");
                System.out.println("2. Reservar auto");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                int op = sc.nextInt();

                switch (op) {
                    case 1 -> gestor.mostrarAutosDisponibles();
                    case 2 -> {
                        gestor.mostrarAutosDisponibles();
                        System.out.print("Ingrese ID del auto a reservar: ");
                        int id = sc.nextInt();
                        System.out.print("Ingrese número de días: ");
                        int dias = sc.nextInt();
                        System.out.print("¿Aplicar descuento por semana? (true/false): ");
                        boolean desc = sc.nextBoolean();
                        gestor.crearReserva(cliente, id, dias, desc);
                    }
                    case 3 -> seguir = false;
                    default -> System.out.println("Opción inválida.");
                }
            }
        }
        System.out.println("Gracias por usar el sistema. ¡Adiós!");
    }
}
