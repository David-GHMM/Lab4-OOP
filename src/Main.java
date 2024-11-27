import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel implements ActionListener {
    static JFrame frame = new JFrame("Гармонические колебания");
    private double length = 500; // длина отрезка
    private double frequency = 10; // частота
    private double amplitude = 100; // амплитуда колебаний
    private double time = 0;
    private double x = 0;
    private Timer timer;
    private boolean running = false;

    public Main()
    {
        timer = new Timer(8, this); // примерно 120 FPS
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawLine(0, frame.getHeight()/2-5, 0, frame.getHeight()/2+5);
        g.drawLine(0, frame.getHeight()/2, (int)length, frame.getHeight()/2);
        g.drawLine((int)length, frame.getHeight()/2-5, (int)length, frame.getHeight()/2+5);

        int y = (int)(amplitude*Math.sin(Math.PI*frequency*2*time)); // вычисление координаты у
        g.fillOval((int)x, -y+(frame.getHeight()/2)-5, 10, 10); // прорисовка точки

        if (x == length){ x = 0; time = 0;}
    }

    public void actionPerformed(ActionEvent e)
    {
        time += 0.001;
        x+=1;
        repaint();
    }

    public void startStop() {
        if (running) {
            timer.stop();
        } else {
            timer.start();
        }
        running = !running;
    }

    public void restart() {
        time = 0;
        x = 0;
        repaint();
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public static void main(String[] args) {
        Main panel = new Main();

        JTextField lengthField = new JTextField("500        ");
        JTextField freqField = new JTextField("10       ");
        JTextField ampField = new JTextField("100       ");
        lengthField.setSize(1000, 10);
        JButton startStopButton = new JButton("Пуск/Стоп");
        JButton restartButton = new JButton("Заново");

        startStopButton.addActionListener(e -> panel.startStop());
        restartButton.addActionListener(e -> panel.restart());

        lengthField.addActionListener(e -> {
            try {
                double length = Double.parseDouble(lengthField.getText());
                panel.setLength(length);
            } catch (NumberFormatException ex) {
                lengthField.setText("Неверное значение");
            }
        });

        freqField.addActionListener(e -> {
            try {
                double frequency = Double.parseDouble(freqField.getText());
                panel.setFrequency(frequency);
            } catch (NumberFormatException ex) {
                freqField.setText("Неверное значение");
            }
        });

        ampField.addActionListener(e -> {
            try {
                double amplitude = Double.parseDouble(ampField.getText());
                panel.setAmplitude(amplitude);
            } catch (NumberFormatException ex) {
                ampField.setText("Неверное значение");
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Длина отрезка: "));
        inputPanel.add(lengthField);
        inputPanel.add(new JLabel("Амплитуда колебаний: "));
        inputPanel.add(ampField);
        inputPanel.add(new JLabel("Частота: "));
        inputPanel.add(freqField);
        inputPanel.add(startStopButton);
        inputPanel.add(restartButton);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
