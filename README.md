# Ruokasovellus
Ruokasovellus on sovellus, jonka avulla käyttäjä voi laskea, että kuinka paljon hän sai päivän
 aikana energiaa ja eri makroravinteita. Laskenta tapahtuu sovellukseen 
aiemmin tallennetuista ruoka-aineista koostettuja aterioita yhteen laskemalla.  
  
Ruokasovelluksessa on tässä kehitysvaiheessa vasta tekstikäyttöliittymä.
Tietokanta ei suostunut toimimaan kun kokeilin sovellustani Puttyn kautta laitoksen koneella.
Tämä siis toimii, tai ei toimi nyt. Hankin itselleni mahdollisuuden kokeilla tätä itse linuxilla ensiviikoksi..    
  
### Dokumentaatio

[Vaatimusmäärittely](https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/vaatimusmaarittely.md)  
[Työaikakirjanpito](https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/tyoaikakirjanpito.md)  






### Komentorivikomennot

#### Ohjelman käyttö
Komentoriviltä [juurikansiosta](https://github.com/ansketom/ot-harjoitustyo/tree/master/ruokasovellus)
(..ot-harjoitustyo\ruokasovellus) komennolla  
```    mvn compile exec:java -Dexec.mainClass=ruokasovellus.Main    ```  

#### Testaus
Testit saa suoritettua komennolla  
```    mvn test    ```  
Testikattavuusraportin saa luotua komennolla  
```    mvn test jacoco:report    ```  
Selaimella avattava kattavuusraportti: \target\site\jacoco\index.html



