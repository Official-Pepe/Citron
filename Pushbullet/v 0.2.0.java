import com.pi4j.io.gpio.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class PlantWateringSystem {

  private static final int MOISTURE_THRESHOLD = 500; // Einstellbarer Schwellenwert
  private static final String PUSHBULLET_API_KEY = "YOUR_PUSHBULLET_API_KEY";

  public static void main(String[] args) {
    // GPIO Controller initialisieren
    final GpioController gpio = GpioFactory.getInstance();

    // GPIO-Pin für den Feuchtigkeitssensor konfigurieren (z. B. GPIO 1)
    final GpioPinAnalogInput moistureSensor = gpio.provisionAnalogInputPin(RaspiPin.GPIO_01, "MoistureSensor");

    System.out.println("Pflanzen-Bewässerungssystem gestartet...");

    while (true) {
        int moistureLevel = (int) moistureSensor.getValue();
        System.out.println("Feuchtigkeitswert: " + moistureLevel);

        if (moistureLevel < MOISTURE_THRESHOLD) {
            System.out.println("Feuchtigkeit niedrig! Sende Benachrichtigung...");
            sendPushNotification("Feuchtigkeit niedrig! Bitte die Pflanze gießen.");
        } else {
            System.out.println("Feuchtigkeit ausreichend.");
        }

        try {
            Thread.sleep(5000); // 5 Sekunden warten, bevor erneut geprüft wird
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

private static void sendPushNotification(String message) {
    try {
        URL url = new URL("<https://api.pushbullet.com/v2/pushes>");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Access-Token", PUSHBULLET_API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");

        String input = "{\\"type\\":\\"note\\",\\"title\\":\\"Pflanzenalarm\\",\\"body\\":\\"" + message + "\\"}";

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.err.println("Fehler beim Senden der Push-Benachrichtigung: " + conn.getResponseCode());
        } else {
            System.out.println("Benachrichtigung erfolgreich gesendet.");
        }

        conn.disconnect();
    } catch (Exception e) {
        e.printStackTrace();
    }
}