# Tescik – naprawiona konfiguracja Fabric

Projekt został naprawiony pod **Minecraft 1.21.11**, **Java 21** i stabilny **Fabric Loom 1.7.4**.

## Co zostało zmienione
- Usunięto duplikaty DSL (pozostawiono Groovy: `build.gradle`, `settings.gradle`).
- Zmieniono wersje zależności na kompatybilne z Fabric 1.21.11.
- Wyłączono problematyczne `interfaceInjection` dla zależności Loom.
- Dodano pliki Gradle Wrapper (`gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.properties`).

## Jak zbudować projekt
> Uwaga: `gradle-wrapper.jar` nie jest dołączony binarnie.

1. Wygeneruj wrapper (jednorazowo, wymagany systemowy Gradle):

```bash
gradle wrapper --gradle-version 8.7
```

2. Zbuduj projekt przez wrapper:

```bash
./gradlew build
```

3. (Opcjonalnie) uruchom klienta deweloperskiego:

```bash
./gradlew runClient
```
