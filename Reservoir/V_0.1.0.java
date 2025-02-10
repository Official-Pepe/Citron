// Date: 2025/01/31

    /**
     * Main method of the application.
     *
     * @param args Command line arguments (are ignored)
     */
    public static void main(String[] args) {
        System.out.println("Plant irrigation system launched...");

        // Initialize GPIO controller
        final GpioController gpio = GpioFactory.getInstance();

        // Configure the GPIO pin for the humidity sensor (e.g. GPIO 1)
        final GpioPinAnalogInput moistureSensor = gpio.provisionAnalogInputPin(RaspiPin.GPIO_01, "MoistureSensor");

        // Event handler for the humidity sensor
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
        })
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
