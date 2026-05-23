# GymForge — Android App

A multi-page gym companion app built for Android Studio.

## Pages (Bottom Navigation)

| Screen | Description |
|--------|-------------|
| **Home** | Dashboard with greeting, today's stats strip, and quick-start workout cards |
| **Workouts** | Full list of 6 workout plans; tap any card to open a modal with the full routine |
| **Progress** | Log workout sessions and track current vs goal weight (stored via SharedPreferences) |
| **Settings** | Save name, fitness level, weekly goal slider, notification toggle, and weight unit |

## Key Features
- ✅ **Custom launch/splash screen** (`SplashActivity`)
- ✅ **Custom app icon** (vector dumbbell drawable)
- ✅ **Modal (DialogFragment) popup** — Info dialog on Home (instructions) + Workout Detail dialogs
- ✅ **Multiple pages** — 4 screens via Bottom Navigation + NavController
- ✅ **Persistent user preferences** — `SharedPreferences` via `PreferenceManager` (name, level, goal, unit, weight data)
- ✅ **Dark theme** with orange accent

## How to Open in Android Studio

1. Unzip `GymApp.zip`
2. Open Android Studio → **File → Open** → select the `GymApp` folder
3. Wait for Gradle sync to complete (requires internet for dependency download)
4. Click **Run ▶** (or Shift+F10) to deploy to an emulator or device
   - Minimum SDK: API 24 (Android 7.0)
   - Target SDK: API 34 (Android 14)

## Project Structure

```
GymApp/
├── app/src/main/
│   ├── java/com/example/gymapp/
│   │   ├── SplashActivity.java          ← Launch screen
│   │   ├── MainActivity.java            ← Hosts bottom nav
│   │   ├── HomeFragment.java            ← Dashboard + info modal trigger
│   │   ├── WorkoutsFragment.java        ← Workout list
│   │   ├── ProgressFragment.java        ← Session counter + weight tracker
│   │   ├── SettingsFragment.java        ← SharedPreferences settings
│   │   ├── InfoDialogFragment.java      ← About/instructions modal
│   │   └── WorkoutDetailDialogFragment.java ← Workout detail modal
│   └── res/
│       ├── layout/                      ← All XML layouts
│       ├── navigation/mobile_navigation.xml
│       ├── menu/bottom_nav_menu.xml
│       ├── values/ (colors, strings, themes)
│       └── drawable/ (custom app icon vectors)
```

## Assessment Requirements Met

| Requirement | Implementation |
|---|---|
| Multiple-view app | 4 pages via Navigation Component + Bottom Nav |
| Basic storage of user preferences | `SharedPreferences` in Progress + Settings |
| Custom launch screen | `SplashActivity` with 2-second delay |
| Custom app icon | Vector dumbbell in mipmap/ |
| Pop-up modal view with info/instructions | `InfoDialogFragment` (tap ? on Home) |
