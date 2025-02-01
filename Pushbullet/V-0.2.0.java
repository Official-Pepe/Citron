import java.io.IOException;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class V_0_2_0 {

  private static final int MOISTURE_THRESHOLD = 500; // Einstellbarer Schwellenwert
  private static final String PUSHBULLET_API_KEY = "YOUR_PUSHBULLET_API_KEY";

    Context pi4j = Pi4J.newAutoContext();
    // Setup moisture sensor pin
    final DigitalInput moistureSensor = pi4j.dout().create(0, "MoistureSensor");
    // Setup moisture sensor pin
    final GpioPinAnalogInput moistureSensor = gpio.provisionAnalogInputPin(RaspiPin.GPIO_00, "MoistureSensor");

    System.out.println("Plant irrigation system launched...");

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(() -> {
        int moistureLevel = (int) moistureSensor.getValue();
        System.out.println("Feuchtigkeitswert: " + moistureLevel);

        if (moistureLevel < MOISTURE_THRESHOLD) {
            System.out.println("Humidity low! Send notification...");
            sendPushNotification("Humidity low! Send notification...");
        } else {
            System.out.println("Sufficient humidity.");
        }
    }, 0, 5, TimeUnit.SECONDS);
    Thread.currentThread().interrupt();
  }

  private static void sendPushNotification(String message) {
    try {
        URL url = new URL("<https://api.pushbullet.com/v2/pushes>");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Access-Token", PUSHBULLET_API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(("{\"type\":\"note\",\"title\":\"Plant Alert\",\"body\":\"" + message + "\"}").getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.err.println("Error when sending the push notification: " + conn.getResponseCode());
        } else {
            System.out.println("Push notification sent succsessfully.");
        }

        conn.disconnect();
    } catch (IOException e) {
    }
  }
}