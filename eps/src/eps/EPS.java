import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class EPS {
    
    public static void main(String[] args) {
        Queue<Paciente> cola = new LinkedList<>();
        int contadorTurnos = 0;
        Timer timer = new Timer();
        
        // Programar llamado automático cada 5 segundos
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!cola.isEmpty()) {
                    Paciente pacienteActual = cola.peek();
                    System.out.println("Turno #" + pacienteActual.getTurno() + ": " + pacienteActual.getNombreCompleto());
                    cola.poll();
                }
            }
        }, 0, 5000000);
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Ingrese su nombre y apellidos:");
            String nombreCompleto = scanner.nextLine();
            
            System.out.println("Ingrese su edad:");
            int edad = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("Ingrese su tipo de afiliación (POS o PC):");
            String tipoAfiliacion = scanner.nextLine();
            
            System.out.println("¿Tiene alguna condición especial? (Sí o No)");
            String respuesta = scanner.nextLine();
            boolean condicionEspecial = respuesta.equalsIgnoreCase("Sí");
            
            Paciente pacienteNuevo = new Paciente(nombreCompleto, edad, tipoAfiliacion, condicionEspecial, ++contadorTurnos);
            
            // Validar excepciones prioritarias
            if (pacienteNuevo.isPrioritario()) {
                cola.add(pacienteNuevo);
                System.out.println("Su turno es #" + contadorTurnos + ". Espere su llamado.");
            } else {
                cola.offer(pacienteNuevo);
                System.out.println("Su turno es #" + contadorTurnos + ". Espere su llamado.");
            }
        }
    }
}

class Paciente {
    private String nombreCompleto;
    private int edad;
    private String tipoAfiliacion;
    private boolean condicionEspecial;
    private int turno;

    public Paciente(String nombreCompleto, int edad, String tipoAfiliacion, boolean condicionEspecial, int turno) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.tipoAfiliacion = tipoAfiliacion;
        this.condicionEspecial = condicionEspecial;
        this.turno = turno;
    }
    
    public boolean isPrioritario() {
        if (edad >= 60 || edad < 12 || condicionEspecial || tipoAfiliacion.equalsIgnoreCase("PC")) {
            return true;
        } else {
            return false;
        }
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getTurno() {
        return turno;
    }
}
