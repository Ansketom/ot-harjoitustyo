# Ruokasovellus
Ruokasovellus on sovellus, jonka avulla käyttäjä voi laskea, että kuinka paljon hän sai päivän
 aikana energiaa ja eri makroravinteita. Laskenta tapahtuu sovellukseen 
aiemmin tallennetuista ruoka-aineista koostettuja aterioita yhteen laskemalla.  
  
Tällä hetkellä Ruokasovelluksessa on mahdollista lisätä ruokia, ja saada ne listattua desktop-käyttöliittymän ikkunassa.
Aiempaan nähden testejä on tullut lisää vain diary-luokalle, joka on uusi. SQL-skeema on, mutta muuten aikani meni nyt
lähinnä vain edes jonkun toiminnallisuuden saamiseen tuohon desktop-käyttöliittymään. Odotan että tämä nyt oikeaa hyvin seuraavan
 viikon aikana ja saan vähän näitä väliin jääneitäkin juttuja tehtyä,
 vaikka ei niistä enää sitten pisteitä toki saa..    
  
### Dokumentaatio

[Vaatimusmäärittely](https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/vaatimusmaarittely.md)  
[Työaikakirjanpito](https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/tyoaikakirjanpito.md)  
[viikon 5 release](https://github.com/ansketom/ot-harjoitustyo/releases/tag/viikko5)  
[SQL-skeema](https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/SQL-skeema.txt)    
muita arkkitehtuuridokumentteja ei vielä ole.  






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
  
Checkstyleraportin tekeminen 
```    mvn jxr:jxr checkstyle:checkstyle    ```




