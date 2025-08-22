# ☁️ Real‑Time Weather App (Android, Java)

A simple and clean Android app that shows **current weather** and **location-based forecasts** in real time using a public weather API.

---

## ✨ Features

* Get **current temperature**, **feels like**, **humidity**, **wind speed**
* **Auto-detect location** (GPS) or search by **city name**
* **Unit switch**: Celsius ↔ Fahrenheit
* **Clean UI** optimized for small screens
* **Error handling** for no internet / invalid city

---

## 🧰 Tech Stack

* **Language**: Java
* **Framework/IDE**: Android Studio (Gradle)
* **Architecture**: MVVM (ViewModel + LiveData/Flow)
* **Networking**: Retrofit + OkHttp
* **JSON**: Gson / Moshi
* **Location**: FusedLocationProviderClient
* **Images/Icons**: Material Icons / weather icons

---

## 📲 Demo / APK

* **Download APK:** \<PASTE\_YOUR\_GOOGLE\_DRIVE\_OR\_RELEASES\_LINK>

> Make sure the link is set to *Anyone with the link – Viewer*.

---

## 🖼️ Screenshots

Add your app screenshots in a `/screenshots` folder and reference them here:

| Home                          | Search                            | Permissions                           |
| ----------------------------- | --------------------------------- | ------------------------------------- |
| ![Home](screenshots/home.png) | ![Search](screenshots/search.png) | ![Perms](screenshots/permissions.png) |

---

## 🚀 Getting Started (Local Setup)

### Prerequisites

* Android Studio (Giraffe/Koala or newer)
* JDK 17 (as per your Gradle config)
* A weather API key (e.g., **OpenWeatherMap**)

### 1) Clone the repo

```bash
git clone https://github.com/<your-username>/Real-Time-Weather-App.git
cd Real-Time-Weather-App
```

### 2) Add your API key **securely** (no hardcoding)

* Open `<project-root>/local.properties` and append:

```
OPENWEATHER_API_KEY=your_api_key_here
```

> Do **not** commit `local.properties`. It's already ignored via `.gitignore`.

### 3) Expose the key to code via `build.gradle` (Module: app)

Inside `android { defaultConfig { ... } }` add:

```gradle
buildConfigField "String", "OPENWEATHER_API_KEY", '"' + project.properties.get("OPENWEATHER_API_KEY") + '"'
```

### 4) Use in code

```java
String apiKey = BuildConfig.OPENWEATHER_API_KEY;
```

---

## 🔧 Build & Run

* Connect a device or start an emulator
* Click **Run ▶** in Android Studio

---

## 📜 Required Permissions

Add to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

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

## 🧪 API

* Default base URL: `https://api.openweathermap.org/` (or your provider)
* Sample endpoint: `/data/2.5/weather?q={city}&appid={API_KEY}&units=metric`

---

## 🤝 Team

* **Krithika Devadiga** – Primary Contact
* **Prajnashree**
* **Khushi**

---

## ✅ Status

* Development Status: In‑Progress / Completed (update this)
* Forecast completion: <update date>

---

## 📄 License

MIT License — see `LICENSE` file.

---

## 🙏 Acknowledgements

* OpenWeatherMap (or chosen provider)
* Android Developers documentation

---

### Notes

* Don’t commit secrets (API keys, signing keys). Keep them in `local.properties` or CI secrets.
* If you publish an APK, consider using **GitHub Releases** and attach your APK there.
