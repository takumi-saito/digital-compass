# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android application written in Kotlin using Jetpack Compose. The project is set up as a modern Android app with a focus on Material Design 3 and Compose UI.

## Build and Development Commands

### Build commands
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Clean build
./gradlew clean

# Build and install on connected device
./gradlew installDebug
```

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Run a specific test class
./gradlew test --tests "t.saito.digitalcompass.ExampleUnitTest"
```

### Code quality
```bash
# Run lint checks
./gradlew lint

# View lint results
# Results are in: app/build/reports/lint-results-debug.html
```

## Architecture

### Technology Stack
- **Language**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose with Material 3
- **Build System**: Gradle with Kotlin DSL
- **Minimum SDK**: 31 (Android 12)
- **Target SDK**: 35 (Android 15)

### Project Structure
- **app/src/main/java/t/saito/digitalcompass/** - Main application code
  - `MainActivity.kt` - Entry point using Compose
  - `ui/theme/` - Compose theme configuration (Color, Type, Theme)
- **app/src/main/res/** - Android resources
  - XML resources for app configuration
  - Drawable and mipmap resources for icons
- **gradle/libs.versions.toml** - Version catalog for dependencies

### Key Dependencies
- AndroidX Core KTX
- Lifecycle Runtime KTX
- Activity Compose
- Compose BOM (Bill of Materials)
- Material 3 Components

## Development Notes

### Compose UI Pattern
The app uses the standard Compose pattern with:
- `setContent` in MainActivity to initialize the UI
- `DigitalCompassTheme` wrapper for consistent theming
- `Scaffold` for Material Design structure
- Preview annotations for development

### Gradle Configuration
- Uses version catalogs (libs.versions.toml) for dependency management
- Kotlin DSL for build scripts
- Java 11 compatibility