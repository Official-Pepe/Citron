
import com.pi4j.io.gpio.;
import com.pi4j.io.gpio.event.;

public class PlantWateringSystem {

private static final int MOISTURE_THRESHOLD = 500;

   * Hauptmethode der Anwendung.
   *
   * @param args Kommandozeilenargumente (werden ignoriert)
   */
public static void main(String[] args) {
  private static final int MOISTURE_THRESHOLD = 500; // Einstellbarer Schwellenwert
public static void main(String[] args) {
    // GPIO Controller initialisieren
    final GpioController gpio = GpioFactory.getInstance();

    // GPIO-Pin f   r den Feuchtigkeitssensor konfigurieren (z. B. GPIO 1)
    // GPIO-Pin für den Feuchtigkeitssensor konfigurieren (z. B. GPIO 1)
    final GpioPinAnalogInput moistureSensor = gpio.provisionAnalogInputPin(RaspiPin.GPIO_01, "MoistureSensor");

    // GPIO-Pin f   r den Servomotor konfigurieren (z. B. GPIO 2)
    // GPIO-Pin für den Servomotor konfigurieren (z. B. GPIO 2)
    final GpioPinPwmOutput servoMotor = gpio.provisionPwmOutputPin(RaspiPin.GPIO_02, "ServoMotor");

    // PWM f   r den Servomotor initialisieren
    servoMotor.setPwmRange(100); // Bereich von 0-100 f   r einfache Steuerung
    // PWM für den Servomotor initialisieren
    servoMotor.setPwmRange(100); // Bereich von 0-100 für einfache Steuerung

    System.out.println("Pflanzen-Bew   ssungssystem gestartet...");
    System.out.println("Pflanzen-Bewässerungssystem gestartet...");

    while (true) {
        int moistureLevel = (int) moistureSensor.getValue();
        System.out.println("Feuchtigkeitswert: " + moistureLevel);

        if (moistureLevel < MOISTURE_THRESHOLD) {
            System.out.println("Feuchtigkeit niedrig! Aktivieren des Servomotors...");
            activateServo(servoMotor);
        } else {
            System.out.println("Feuchtigkeit ausreichend.");
            deactivateServo(servoMotor);
        }

        try {
            Thread.sleep(5000); // 5 Sekunden warten, bevor erneut gepr   ft wird
            Thread.sleep(5000); // 5 Sekunden warten, bevor erneut geprüft wird
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Aktiviert den Servomotor.
 *
 * @param servoMotor Instanz des Servomotors
 */
private static void activateServo(GpioPinPwmOutput servoMotor) {
    // Servomotor auf Aktivierungsposition drehen (z. B. 75% Duty Cycle)
    servoMotor.setPwm(75);
}

/**
 * Deaktiviert den Servomotor.
 *
 * @param servoMotor Instanz des Servomotors
 */
private static void deactivateServo(GpioPinPwmOutput servoMotor) {
    // Servomotor in Ruheposition bringen (z. B. 0% Duty Cycle)
    servoMotor.setPwm(0);
    }
}
