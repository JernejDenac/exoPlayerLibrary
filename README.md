# ExoPlayer

Smart Gass Metering aplikacija omogoča prikaz lokacij pametnih plinomerov na interaktivnem zemljevidu. Namen aplikacije je pomagati tehnikom in upravljavcem pri iskanju in upravljanju plinomerov v realnem času. Poleg tega aplikacija omogoča odčitavanje podatkov na daljavo, kot so trenutna poraba plina, stanje naprave in zadnji pregled.

## Funkcionalnosti 📋
* Adaptivno pretakanje -> DASH, HLS in SmoothStreaming
* Zaščita vsebin -> DRM tehnologije
* Podnapisi
* Zvočne sledi
* Predpomnenje in prenos vsebin
* prilagoditev in razširitev komponent(po potrebi)
* Prilagodljive kontrolne komponente uporabniškega vmesnika
* Podpora za obvestila in zaklenjeni zaslon

## Prednosti ✅
* Prilagajanje in razširjanje funkcionalnosti
* Podpora za različne avdio in video formate
* Redno posodablanje

## Slabosti ❌
* Velikost knjižnice
* Zakasnitve pri predvajanju RTSP tokov

## Licenca 📜

 Apache License 2.0, ki dovoljuje prosto uporabo, distribucijo in spreminjanje programske opreme.

## Število zvezdic, forkov ⭐
* Zvezdice: 21,8k
* forki: 6k

## Vzdrževanje projekta 🔧
* Zadnja različica exoplayer:2.19.1(2024-04-03)
* Migacija na AndoirdX Media3

## Primeri uporabe 👀

<p align="center">
  <img src="https://github.com/user-attachments/assets/a53c60a3-da20-47b7-8b0b-2b669a7dcd0c"  1" width="20%" style="margin-right: 5%;" />
 <img src="https://github.com/user-attachments/assets/a9c85d95-2e80-46a4-ba84-7bbb2e0938b4"  2" width="20%">
</p>


<p align="center">
  <img src="https://github.com/user-attachments/assets/9024306b-9d00-44c8-8a2e-eb47197d2019"  1" width="20%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/d77418f4-0d58-4a58-a6b7-31f99f96c172"  2" width="20%">
</p>

```sh
 pickVideo.launch("video/*")
```

Možnost filtriranja po statusu plinomerov (npr. "Aktiven," "Izklopljen").
Možnost iskanja plinomerja po ID-ju ali lokaciji.
### Podrobnosti plinomera: 📈

Prikaz podrobnosti o plinomeru, kot so:
Trenutna poraba plina.
Lokacija (GPS koordinate).
Stanje pretoka plina(relativno ali absolutno odčitavanje).

### Dodajanje novega plinomera: ⚡

Obrazec za vnos podatkov novega plinomera (npr. lokacija, ID naprave, stanje).
