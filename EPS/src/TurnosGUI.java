import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class TurnosGUI extends JFrame implements ActionListener {
    private JTextField tfNombre, tfEdad, tfAfiliacion, tfCondicion;
    private JTextArea taTurnos;
    private JButton btnNuevoTurno, btnExtenderTiempo;
    private JLabel lblTurno, lblTiempoRestante, lblTurnosPendientes;
    private Queue<Paciente> colaTurnos;
    private Paciente pacienteEnCurso;
    private int tiempoRestante;

   public TurnosGUI() {
        // Configurar la ventana
        setTitle("EPS");
        setSize(800, 500);
        JPanel panel1 = new JPanel();
        panel1.setSize(800, 500);
        panel1.setBackground(Color.RED);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
       

        // Crear los componentes de la interfaz
        tfNombre = new JTextField();
        tfNombre.setFont(new Font("Arial", Font.PLAIN, 50));
        tfNombre.setForeground(Color.BLACK);
        tfEdad = new JTextField();
        tfEdad.setFont(new Font("Arial", Font.PLAIN, 50));
        tfEdad.setForeground(Color.BLACK);
        tfAfiliacion = new JTextField();
        tfAfiliacion.setFont(new Font("Arial", Font.PLAIN, 50));
        tfAfiliacion.setForeground(Color.BLACK);
        tfCondicion = new JTextField();
        tfCondicion.setFont(new Font("Arial", Font.PLAIN, 50));
        tfCondicion.setForeground(Color.BLACK);
        taTurnos = new JTextArea();
        taTurnos.setFont(new Font("Arial", Font.PLAIN, 20));
        taTurnos.setForeground(Color.BLUE);
        JScrollPane scrollPane = new JScrollPane(taTurnos);
        btnNuevoTurno = new JButton("Nuevo turno");
        btnNuevoTurno.setFont(new Font("Arial", Font.BOLD, 20));
        btnNuevoTurno.setForeground(Color.WHITE);
        btnNuevoTurno.setBackground(Color.BLUE);
        btnExtenderTiempo = new JButton("Extender tiempo");
        btnExtenderTiempo.setFont(new Font("Arial", Font.BOLD, 20));
        btnExtenderTiempo.setForeground(Color.WHITE);
        btnExtenderTiempo.setBackground(Color.BLUE);
        lblTurno = new JLabel("Turno en curso:");
        lblTurno.setFont(new Font("Arial", Font.BOLD, 20));
        lblTurno.setForeground(Color.BLUE);
        lblTiempoRestante = new JLabel("Tiempo restante:");
        lblTiempoRestante.setFont(new Font("Arial", Font.BOLD, 20));
        lblTiempoRestante.setForeground(Color.BLUE);
        lblTurnosPendientes = new JLabel("Turnos pendientes:");
        lblTurnosPendientes.setFont(new Font("Arial", Font.BOLD, 20));
        lblTurnosPendientes.setForeground(Color.BLUE);
        

       // Añadir los componentes a la ventana
       Font font = new Font("Time New Roman", Font.BOLD, 20);
       JPanel panel = new JPanel();
       panel.add(new JLabel("Nombre")).setFont(font);
       panel.add(tfNombre);
       panel.add(new JLabel("Edad:")).setFont(font);
       panel.add(tfEdad);
       panel.add(new JLabel("Afiliación (POS o PC):")).setFont(font);
       panel.add(tfAfiliacion);
       Font font1 = new Font("Time New Roman", Font.BOLD, 16);
       panel.add(new JLabel("Condición especial (embarazo o limitación motriz):")).setFont(font1);
       panel.add(tfCondicion);


        panel.setLayout(new GridLayout(4, 2));
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevoTurno);
        btnNuevoTurno.setPreferredSize(new Dimension(200, 80));
        panelBotones.add(btnExtenderTiempo);
        btnExtenderTiempo.setPreferredSize(new Dimension(200, 80));
        JPanel panelTurnos = new JPanel();
        panelTurnos.setLayout(new BoxLayout(panelTurnos, BoxLayout.Y_AXIS));
        panelTurnos.add(lblTurno);
        panelTurnos.add(lblTiempoRestante);
        panelTurnos.add(lblTurnosPendientes);
        panelTurnos.add(taTurnos);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(panelBotones, BorderLayout.CENTER);
        container.add(panelTurnos, BorderLayout.SOUTH);

        // Inicializar la cola de turnos
        colaTurnos = new LinkedList<Paciente>();

        // Añadir los escuchadores de eventos a los botones
        btnNuevoTurno.addActionListener(this);
        btnExtenderTiempo.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevoTurno) {
            // Crear un nuevo paciente con los datos ingresados por el usuario
            String nombre = tfNombre.getText();
            int edad = Integer.parseInt(tfEdad.getText());
            String afiliacion = tfAfiliacion.getText();
            String condicion = tfCondicion.getText();
            Paciente paciente = new Paciente(nombre, edad, afiliacion, condicion);

            // Añadir el paciente a la cola de turnos
            colaTurnos.offer(paciente);

            // Mostrar los turnos pendientes en la cola
taTurnos.setText("Turnos pendientes:\n");
for (Paciente p : colaTurnos) {
taTurnos.append("- " + p.getNombre() + "\n");
}
        // Si no hay ningún paciente en curso, empezar a atender al primero de la cola
        if (pacienteEnCurso == null) {
            atenderSiguientePaciente();
        }
    } else if (e.getSource() == btnExtenderTiempo) {
        // Extender el tiempo del paciente en curso en 5 segundos
        tiempoRestante += 5;
        actualizarTiempoRestante();
    }
}

private void atenderSiguientePaciente() {
    // Tomar el siguiente paciente de la cola
    pacienteEnCurso = colaTurnos.poll();

    // Mostrar el turno del paciente en curso
    lblTurno.setText("Turno en curso: " + pacienteEnCurso.getNombre());
    lblTurno.setFont(new Font("Arial", Font.BOLD, 20));

    // Empezar el contador de tiempo restante
    tiempoRestante = 5;
    actualizarTiempoRestante();

    // Mostrar los turnos pendientes en la cola
    taTurnos.setText("Turnos pendientes:\n");
    for (Paciente p : colaTurnos) {
        taTurnos.append("- " + p.getNombre() + "\n");
    }

    // Si hay pacientes en la cola, programar el siguiente turno para 5 segundos después
    if (!colaTurnos.isEmpty()) {
        Timer timer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atenderSiguientePaciente();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

private void actualizarTiempoRestante() {
    // Mostrar el tiempo restante en la etiqueta correspondiente
    lblTiempoRestante.setText("Tiempo restante: " + tiempoRestante + " segundos");

    // Si se acaba el tiempo, programar el siguiente turno inmediatamente
    if (tiempoRestante == 0) {
        atenderSiguientePaciente();
    } else {
        // Si no se acaba el tiempo, restar un segundo y programar la actualización de nuevo
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                actualizarTiempoRestante();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

private static class Paciente {
    private String nombre;
    private int edad;
    private String afiliacion;
    private String condicion;

    public Paciente(String nombre, int edad, String afiliacion, String condicion) {
        this.nombre = nombre;
        this.edad = edad;
        this.afiliacion = afiliacion;
        this.condicion = condicion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getAfiliacion() {
        return afiliacion;
    }

    public String getCondicion() {
        return condicion;
    }
}

public static void main(String[] args) {
    // Crear y mostrar la interfaz de usuario
    TurnosGUI gui = new TurnosGUI();
    gui.setVisible(true);
}
}
