# UUID Generator IDEA Plugin

IntelliJ IDEA plugin that generates UUID directly into empty string literals via intention action.

## Features

- Place cursor inside an empty string literal (`""`)
- Press `Alt+Enter` (or `Option+Enter` on Mac) to show intentions
- Select "Generate UUID" to insert a random UUID

## Supported Languages

- Java
- Kotlin
- JSON
- YAML
- XML
- Plain text

## Installation

### From disk

1. Build the plugin: `./gradlew buildPlugin`
2. The plugin ZIP will be in `build/distributions/`
3. In IDEA: Settings → Plugins → ⚙️ → Install Plugin from Disk...
4. Select the ZIP file and restart IDEA

### Development

Run IDEA with the plugin:
```bash
./gradlew runIde
```

## Usage

```kotlin
val id = "" // place cursor here, press Alt+Enter → Generate UUID
// Result: val id = "550e8400-e29b-41d4-a716-446655440000"
```

## Build

```bash
./gradlew buildPlugin
```

## Requirements

- IntelliJ IDEA 2024.1+
- JDK 17+
