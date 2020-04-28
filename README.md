# Ruokasovellus
Ruokasovellus on sovellus, jonka avulla käyttäjä voi laskea, että kuinka paljon hän sai päivän
 aikana energiaa ja eri makroravinteita. Laskenta tapahtuu sovellukseen 
aiemmin tallennetuista ruoka-aineista koostettuja aterioita yhteen laskemalla.  
  
Ohjelman testauksen kanssa on ollut suuria vaikeuksia, enkä tajua miten se kuuluisi fiksusti hoitaa - varmaan olemalla tekemättä tällaista ohjelmaa..  
Tuhoton aikamäärä mikä tällä viikolla meni mm testien väsäämiseen ja tyhmien toiminnallisuuden luomisen ongelmien (en meinannut saada mitään toimimaan) korjaamiseen teki tilanteen jälleen sellaiseksi että en enää kerinnyt tehdä dokumentointia kun riskinä oli joka tapauksessa vajaa ohjelma joka kaiken lisäksi ei edes aukea missään tai mitenkään.     
  
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




