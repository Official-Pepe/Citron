#!/bin/bash
java -cp /path/to/pi4j-core.jar:/path/to/your/code PlantWateringSystem &

# Überprüfen, ob Internet verbunden ist
while ! ping -c 1 -W 1 8.8.8.8 > /dev/null; do
    echo "Warte auf Internetverbindung..."
    sleep 5
done
echo "Internet verbunden!"
