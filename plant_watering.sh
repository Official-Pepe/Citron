#!/bin/bash
java -cp /path/to/pi4j-core.jar:/path/to/your/code PlantWateringSystem &

# Check if Internet is connected
while ! ping -c 1 -W 1 8.8.8.8 > /dev/null; do
    echo “Waiting for internet connection...”
    sleep 5
done
echo “Internet connected!”
