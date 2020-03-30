# Ruokasovellus
Ruokasovellus on sovellus, jonka avulla käyttäjä voi laskea, että kuinka paljon hän sai päivän
 aikana energiaa ja eri makroravinteita. Laskenta tapahtuu sovellukseen 
aiemmin tallennetuista ruoka-aineista koostettuja aterioita yhteen laskemalla.  
  
Ruokasovelluksessa on tässä kehitysvaiheessa vasta tekstikäyttöliittymä.
[Ohjelman juurikansio](https://github.com/ansketom/ot-harjoitustyo/tree/master/ruokasovellus)  
### Dokumentaatio

[Vaatimusmäärittely](https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/vaatimusmaarittely.md)  
[Työaikakirjanpito](https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/tyoaikakirjanpito.md)  






### Komentorivikomennot

#### Ohjelman käyttö
Komentoriviltä juurikansiosta (..ot-harjoitustyo\ruokasovellus) komennolla  
```    mvn compile exec:java -Dexec.mainClass=ruokasovellus.Main    ```  

#### Testaus
Testit saa suoritettua komennolla  
```    mvn test    ```  
Testikattavuusraportin saa luotua komennolla  
```    mvn test jacoco:report    ```  
Selaimella avattava kattavuusraportti: \target\site\jacoco\index.html



