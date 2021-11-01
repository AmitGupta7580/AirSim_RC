# AirSim_RC

Offical App for Game of Drone Event in Avishkar 2k21. <br>
Android App to control the Airsim Drone running on Remote System.

## Game Setup:
### Installation

- Download and unzip it https://github.com/microsoft/AirSim-NeurIPS2019-Drone-Racing/releases/download/v0.3.0/AirSim.zip. => folder name Airsim
- Download https://github.com/microsoft/AirSim-NeurIPS2019-Drone-Racing/releases/download/v0.3.0/settings.json and replace with your `C:\Users\........\Documents\AirSim\setting.json` or if not exists make a folder and place it.
- Download the map : https://github.com/microsoft/AirSim-NeurIPS2019-Drone-Racing/releases/download/v0.3.0/Soccer_Field.pak and place it in Airsim/AirSimExe/Content/Paks/.
- Download the Android APK : https://github.com/AmitGupta7580/AirSim_RC/blob/master/app-debug.apk
- Download the Python server : https://gist.github.com/AmitGupta7580/fe7612cea70edee78dd4fb9716db683f

### Running

- First open cmd in the AirSim folder and run 
- AirSimExe.exe -windowed -ResX=700 -ResY=400 
- Now your airsim server window will open.
- Open cmd and type ipconfig to know the private ip of your pc. (eg. 192.168.35.25)
- Open the python server file in the text editor and change Host to your pc ip and enter your preferable port number.
- Now run the python server (preferable version of python : 3.7) : `py -3.7 Airsim_Server.py`
  - `py -3.7 -m pip install airsimneurips`
  - `py -3.7 -m pip install airsim`
- Now open the android app and click on Connect wireless then enter your pc ip and port you entered in python server file then click on connect. Now you will see the controller interface then click on start to control the drone.
- Using Left controller You can control the throttle and yaw whereas, using the right controller you can control Pitch and Roll of the drone.


## Screenshots: 
<p align="center">
  <img src="https://github.com/AmitGupta7580/AirSim_RC/blob/master/img/rc_interface.jpeg" width=700 height=300>
</p>
<p align="left">
  <img src="https://github.com/AmitGupta7580/AirSim_RC/blob/master/img/main.jpeg" width=480 height=230>
  <img src="https://github.com/AmitGupta7580/AirSim_RC/blob/master/img/connnection.jpeg" width=480 height=230>
</p>
