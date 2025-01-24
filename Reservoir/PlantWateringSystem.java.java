import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeListener;



    /**
     * Hauptmethode der Anwendung.
     *
     * @param args Kommandozeilenargumente (werden ignoriert)
     */
    public static void main(String[] args) {
        System.out.println("Pflanzen-Bewässerungssystem gestartet...");

        // GPIO Controller initialisieren
        final GpioController gpio = GpioFactory.getInstance();

        // GPIO-Pin für den Feuchtigkeitssensor konfigurieren (z. B. GPIO 1)
        final GpioPinAnalogInput moistureSensor = gpio.provisionAnalogInputPin(RaspiPin.GPIO_01, "MoistureSensor");

        // GPIO-Pin für den Servomotor konfigurieren (z. B. GPIO 2)
        final GpioPinPwmOutput servoMotor = gpio.provisionPwmOutputPin(RaspiPin.GPIO_02, "ServoMotor");

        // PWM für den Servomotor initialisieren
        servoMotor.setPwmRange(100); // Bereich von 0-100 für einfache Steuerung

        // Event-Handler für den Feuchtigkeitssensor hinzufügen
        moistureSensor.addListener(new GpioPinAnalogValueChangeListener() {
            @Override
            public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
                int moistureLevel = event.getValue();
                System.out.println("Feuchtigkeitswert: " + moistureLevel);

                if (moistureLevel < MOISTURE_THRESHOLD) {
                    System.out.println("Feuchtigkeit niedrig! Aktivieren des Servomotors...");
                    activateServo(servoMotor);
                } else {
                    System.out.println("Feuchtigkeit ausreichend.");
                    deactivateServo(servoMotor);
                }
            }
        });

        // Endlosschleife, um die Anwendung nicht beenden zu lassen
        while (true) {
            try {
                Thread.sleep(5000); // 5 Sekunden warten, bevor erneut geprüft wird
            } catch (InterruptedException e) {
                System.err.println("Fehler beim Warten: " + e.getMessage());
            }
        }
    }

    /**
     * Aktiviert den Servomotor.
     *
     * @param servoMotor Instanz des Servomotors
     */
    private static void activateServo(GpioPinPwmOutput servoMotor) {
        System.out.println("Aktiviere Servomotor...");
        // Servomotor auf Aktivierungsposition drehen (z. B. 75% Duty Cycle)
        servoMotor.setPwm(75);
    }

    /**
     * Deaktiviert den Servomotor.
     *
     * @param servoMotor Instanz des Servomotors
     */
    private static void deactivateServo(GpioPinPwmOutput servoMotor) {
        System.out.println("Deaktiviere Servomotor...");
        // Servomotor in Ruheposition bringen (z. B. 0% Duty Cycle)
        servoMotor.setPwm(0);
    }
}
