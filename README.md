# 🌦️ Weather App

A simple and stylish Android application that shows real-time weather updates using the **OpenWeather API**.  
This app provides current temperature, weather conditions, and other details based on your location.

---

## ✨ Features
- 🌍 Real-time weather updates for any location
- 📍 Location-based weather detection
- 🌡️ Displays temperature, humidity, wind speed, and conditions
- 🫁 Air Quality Index (AQI) monitoring
- 🎨 Simple & clean user interface

---

## 📲 Download APK
You can directly install the app by downloading the APK:  
👉 [Download Weather App APK](https://drive.google.com/file/d/1T2veTxZv4_sUkimTDnNyqmh_05cl8Hti/view?usp=sharing)

---

## ⚙️ Tech Stack
- Android Studio
- Java/Kotlin
- OpenWeather API

---

## 🚀 How to Use
1. Download and install the APK on your Android device.
2. Open the app and allow location permission.
3. View weather details instantly.

---
## 🗂️ Project Structure (example)

```
app/
 └─ src/
    ├─ main/
    │  ├─ java/com/example/weather/
    │  │   ├─ data/            # DTOs, repository
    │  │   ├─ network/         # Retrofit service
    │  │   ├─ ui/              # Activities/Fragments
    │  │   └─ viewmodel/       # ViewModels
    │  ├─ res/                 # Layouts, drawables, strings
    │  └─ AndroidManifest.xml
 └─ build.gradle
```

---
## 🖼️ Screenshots

Here’s a look at the Weather App interface:

![Screenshot 1](assets/screenshot 1.png)  
*Home screen showing a search bar to enter a location*

![Screenshot 2](assets/screenshot2.png)  
*Air Quality Index and detailed weather information*

## 🧪 API

* Default base URL: `https://api.openweathermap.org/`
* Sample endpoint (Weather): `/data/2.5/weather?q={city}&appid={API_KEY}&units=metric`
* Sample endpoint (Air Quality): `/data/2.5/air_pollution?lat={lat}&lon={lon}&appid={API_KEY}`
---

## 🤝 Team

* **Krithika** 
* **Prajnashree**
* **Khushi**



## 📝 License
This project is for educational purposes.
