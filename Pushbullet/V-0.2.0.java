import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlantWateringSystem {

    private static final int MOISTURE_THRESHOLD = 500; // Anpassen nach Kalibrierung
    private static final String PUSHBULLET_API_KEY = "DEIN_API_KEY";
    private static final String PUSHBULLET_DEVICE_ID = "DEIN_DEVICE_ID";
    private static boolean notificationSent = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Plant irrigation system launched...");

        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinAnalogInput moistureSensor = gpio.provisionAnalogInputPin(RaspiPin.GPIO_01, "MoistureSensor");
        final GpioPinPwmOutput servoMotor = gpio.provisionPwmOutputPin(RaspiPin.GPIO_02, "ServoMotor");
        
        servoMotor.setPwmRange(100);

        moistureSensor.addListener(new GpioPinAnalogValueChangeListener() {
            @Override
            public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
                int moistureLevel = event.getValue();
                System.out.println("Feuchtigkeitswert: " + moistureLevel);

                if (moistureLevel < MOISTURE_THRESHOLD) {
                    activateServo(servoMotor);
                    if (!notificationSent) {
                        sendPushNotification("Feuchtigkeit niedrig!", "Die Pflanze benötigt Wasser!");
                        notificationSent = true;
                    }
                } else {
                    deactivateServo(servoMotor);
                    notificationSent = false;
                }
            }
        });

        // Halte die Anwendung aktiv
        while (true) {
            Thread.sleep(5000);
        }
    }

    private static void activateServo(GpioPinPwmOutput servoMotor) {
        System.out.println("Aktiviere Servomotor...");
        servoMotor.setPwm(75);
    }

    private static void deactivateServo(GpioPinPwmOutput servoMotor) {
        System.out.println("Deaktiviere Servomotor...");
        servoMotor.setPwm(0);
    }

    private static void sendPushNotification(String title, String message) {
        try {
            URL url = new URL("https://api.pushbullet.com/v2/pushes");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Access-Token", PUSHBULLET_API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format(
                "{\"type\": \"note\", \"title\": \"%s\", \"body\": \"%s\", \"device_iden\": \"%s\"}",
                title, message, PUSHBULLET_DEVICE_ID
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Pushbullet Response Code: " + responseCode);

        } catch (Exception e) {
            System.err.println("Fehler bei Pushbullet-Nachricht: " + e.getMessage());
        }
    }
}