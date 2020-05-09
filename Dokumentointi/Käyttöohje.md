# Käyttöohje
Ohjelman käynnistämiseksi tarvitsee ladata tiedosto ruokasovellus.jar

## Konfigurointi
Ohjelman pitäisi toimia sellaisenaan, kun java 8 on asennettu tietokoneellesi.  
Ohjelma luo itse tarvitsemansa tietokantatiedoston "ruokasovellus.db", jos sitä ei käynnistyshetkellä .jar -tiedoston kanssa samassa kansiossa ole.

## Ohjelman käynnistys
Ohjelman käynnistys tapahtuu komentoriviltä siitä kansioista, missä "ruokasovellus.jar" on, komennolla
```    java -jar ruokasovellus.jar    ```    

## Ohjelman näkymät ja toiminnot


### Näkymä 1 - Ruoka-aineiden hallinta
Näkymässä 1 käyttäjä voi lisätä ja poistaa ruoka-aineita. Ruoka-aineet ovat aterian osia, joista koostetaan aterioita näkymässä 2.    
Poistettaessa ruoka-aineita, olisi suotavaa poistaa niiden yhteydet aterioista.
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/RuokasovellusN1.PNG?raw=true" width="400">    

### Näkymä 2 - Ruoka-annosten hallinta
Näkymässä 2 käyttäjä voi muodostaa näkymässä 1 lisäämistään ruoka-aineista aterioita. 
Tämä tapahtuu siten, että ensin luodaan ruoka-annos tietokantaan, ja sitten siihen lisätään haluttuja määriä tietokannassa jo olevia ruoka-aineita.  
Annoksia muodostaessa on hyvä käyttää apuna tarvitsemiaan tai mieleisiään listauksia lisätyistä ruoka-aineista ja annosten sisällöistä.    
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/RuokasovellusN2.PNG?raw=true" width="400">  
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/RuokasovellusN3.PNG?raw=true" width="400">     

### Näkymä 3 - Päiväkirja
Näkymässä 3 käyttäjä voi laskea, kuinka paljon energiaa ja makroravinteita hänen aterioissaan on. 
Tätä varten on ensin alustettava lasku jollekin merkkijonolle, esimerkiksi päivämäärä. Tämän päivän päivämäärän saa sopivassa muodossa päivämääräkenttään suoraan napista, ja sitten alustamalla laskun tämä muodostaa uuden rivin tietokantaan.
Laskeminen tapahtuu lisäämällä tai vähentämällä annoksia päiväkirjariville. Sivulla on mahdollisuus myös tallentaa juotu vesimäärä päiväkirjaan, sekä nollata rivin laskut.    
Näkymä 3 on hieman nurinkurisesti ohjelman avausnäkymä. Syy tälle on se, että jos syöt usein samoja ruokia, niin kohtahan ne ovat valmiina aterioina tietokannassa. Sitten ei usein muita sivuja tarvitsekaan.
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/RuokasovellusN4.PNG?raw=true" width="400">