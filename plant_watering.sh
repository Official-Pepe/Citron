#!/bin/bash
<<<<<<< HEAD
java -cp /path/to/pi4j-core.jar:/path/to/your/code PlantWateringSystem &
=======
java -cp /path/to/pi4j-core.jar:/path/to/your/code PlantWateringSystem &

# Check if Internet is connected
while ! ping -c 1 -W 1 8.8.8.8 > /dev/null; do
    echo “Waiting for internet connection...”
    sleep 5
done
echo “Internet connected!”
>>>>>>> 4a435ff884a76f5c3295afa88b36976689445e7a
