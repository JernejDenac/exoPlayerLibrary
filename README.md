# ExoPlayer

ExoPlayer je prilagodljiva knjižnica za predvajanje videoposnetkov, avdio vsebin. Prav tako omogoča neposredni prenos.

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
* Redno posodablanje/Aktivna skupnost

## Slabosti ❌
* Velikost knjižnice
* Zakasnitve pri predvajanju RTSP tokov
* Poraba baterije

## Licenca 📜

 Apache License 2.0, ki dovoljuje prosto uporabo, distribucijo in spreminjanje programske opreme.

## Število zvezdic, forkov ⭐
* Zvezdice: 21,8k
* forki: 6k

## Vzdrževanje projekta 🔧
* Zadnja različica exoplayer:2.19.1(2024-04-03)
* Število razvijalcev 239
* Migacija na AndroidX Media3

## Primer uporabe 👀

<p align="center">
 <img src="https://github.com/user-attachments/assets/a9c85d95-2e80-46a4-ba84-7bbb2e0938b4"   1" width="20%" 
 style="margin-right: 5%;" />
 <img src="https://github.com/user-attachments/assets/a53c60a3-da20-47b7-8b0b-2b669a7dcd0c"  2" width="20%">
</p>

```sh
 pickVideo.launch("video/*")
```

<p align="center">
  <img src="https://github.com/user-attachments/assets/9024306b-9d00-44c8-8a2e-eb47197d2019"  1" width="20%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/d77418f4-0d58-4a58-a6b7-31f99f96c172"  2" width="20%">
</p>

```sh
 private fun playVideo(videoUri: Uri) {
        exoPlayer = ExoPlayer.Builder(this).build().also { player ->
            binding.playerView.player = player
            val mediaItem = MediaItem.fromUri(videoUri)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
        }
    }
```
* Also omogoča uporabo nove instance player znotraj bloka
* Ustvari MediaItem iz URI-ja, ki kaže na lokacijo videoposnetka
* Začne nalagati video iz URI-ja, Dekodira video in zvok, da pripravi predvajanje.

  
Možnost filtriranja po statusu plinomerov (npr. "Aktiven," "Izklopljen").
Možnost iskanja plinomerja po ID-ju ali lokaciji.
### Podrobnosti plinomera: 📈

Prikaz podrobnosti o plinomeru, kot so:
Trenutna poraba plina.
Lokacija (GPS koordinate).
Stanje pretoka plina(relativno ali absolutno odčitavanje).

### Dodajanje novega plinomera: ⚡

Obrazec za vnos podatkov novega plinomera (npr. lokacija, ID naprave, stanje).
