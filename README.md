[![Dropit_up](https://github.com/user-attachments/assets/0a19571c-3c33-4321-92e9-d4176c368254)
](https://drive.google.com/file/d/1HBf4QS1UOoG_XLS6ozG_MOTdtYTu6w13/view?usp=drive_link)
------

# Description
A repo for the code collection for an watering system for plants.

# version Dilema

Every Version that ends with an .0 is an beta version and is not safe to use, every version without an .0 is an full version and is safe to use, if you are not shure just go to Releases and use that version. Every version with an that has an odd nummber in the middle of its name is an version with reservoir usage, if you want to use just the massegaing type use the newest version with an Even nummber.

# Useage

Download the newest version or the one that fits for your Rasberry pi vesion, and chose if you want an resrvoir/Pushbullet and download it

# Shell file setup

Customizing the script
Adapt paths: Replace ``/path/to/pi4j-core.jar`` and ``/path/to/your/code`` with the actual paths.
Adapt Java classes: Make sure that the classes ``PlantWateringSystemServo`` (for the servo motor version) and ``PlantWateringSystemPushbullet`` (for the pushbullet version) are present in ``CODE_PATH``.

## create an Sytemd file

``sudo nano /etc/systemd/system/plant_watering.service``

### Systemd file input

````
[Unit]
Description=Automatisches Pflanzen-Bewässerungssystem
After=network.target

[Service]
ExecStart=/pfad/zu/plant_watering.sh
Restart=always
User=pi
Environment=DISPLAY=:0

[Install]
WantedBy=multi-user.target

````

### last steps
we have this file also as an download available, 
Safe the file and close it, then reaload deamon to refresh you system with ``sudo systemctl daemon-reload`` and now enable the service file with this command ``sudo systemctl enable plant_watering.service``

# Reservoir set up 
>[!WARNING]
>not working, early beta

## Hardware setup

You need an Ressberry Pi that you can get [here](https://www.raspberrypi.com/products/compute-module-5/?variant=cm5-108032) and you need an Humidity sensor we recommend [this one](https://amzn.eu/d/1NiQAjr) also you need an chip that translates the code from the Rasberry Pi to the servomotr, you can get it [here](https://www.microchip.com/en-us/product/mcp3008), Finaly you need an servo motor that 


# Message set up 

>[!CAUTION]
>No reservoir Support (coming soon)

## Hardware

You need an Ressberry Pi that you can get [here](https://www.raspberrypi.com/products/compute-module-5/?variant=cm5-108032) and you need an Humidity sensor we recommend [this one](https://amzn.eu/d/1NiQAjr).

# Pushbullet setup

### Software

Sign in your pushbullet acount or get one, and get your API key under “Settings” > “Create Access Token”.
Then change the value in the code ``YOUR_PUSHBULLET_API_KEY`` and pste there your api key, __nothing else__

### Java library for Pushbullet

Use the Pushbullet API Wrapper library for Java or send HTTP requests with a standard library.

# Coming soon 
- Messages for multiple plants / Irrigation 
- Personalized messages
- more Compatibility
- and more connectivity

## Thoughts what could come in the future
- Room humidy sensors (basic automation, with 5 humidysensors)
- 3D Printed shell
- Apps (windows, Mac, Ios, Andoid)
   - These are the Host applications
- An Shop...?

# Extras 

https://i.pinimg.com/736x/91/5c/fc/915cfcea6292a6a3b7d08ec16a061d8c.jpg
