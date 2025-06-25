# Sistema de Arriendo de Autos

Este sistema permite a un cliente visualizar autos disponibles y realizar reservas de vehículos ingresando su nombre, RUT, días de arriendo y si desea aplicar un descuento. El programa corre por consola y gestiona autos, clientes y reservas de forma sencilla y estructurada.

---

## Patrones de Diseño Aplicados

### Singleton – `Gestor`
Se aplica para garantizar que exista una única instancia de la clase encargada de gestionar los autos, clientes y reservas. Esto permite un punto central de control de todo el sistema.

Justificación: evita instancias múltiples, centraliza la gestión.

---

### Prototype – `Auto.clone()`
Se utiliza para clonar autos base y crear nuevos vehículos similares con ligeras modificaciones (modelo, marca, precio), sin necesidad de crear cada objeto manualmente desde cero.

Justificación: facilita la carga de múltiples vehículos a partir de un patrón base.

---

### Adapter (Simulado) – `cargarAutos()`
Se simula la carga de datos como si vinieran desde un archivo CSV o una fuente externa, lo que representa el patrón Adapter. En una versión futura podría conectarse realmente a un archivo o base de datos.

Justificación: representa la adaptación entre una fuente de datos externa y la estructura interna del sistema.

---

### Bridge – `Reserva + CalculadoraPrecio`
El patrón Bridge separa la abstracción `Reserva` de su implementación de cálculo de precios (`CalculoNormal`, `CalculoDescuento`), permitiendo cambiar el comportamiento sin modificar la lógica de reservas.

Justificación: permite mantener separadas las reglas de negocio del cálculo de precios de la lógica de reserva.

---

## Instrucciones de compilación y ejecución

Compilación:

```bash
javac SistemaArriendopatrones.java
