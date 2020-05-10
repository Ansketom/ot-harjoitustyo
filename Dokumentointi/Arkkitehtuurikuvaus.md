# Arkkitehtuurikuvaus  

## Rakenne  
Ohjelman rakenne noudattaa kerrosarkkitehtuuria, jossa on kolme kerrosta.
Suuri osa ohjelmaa käyttää käyttöliittymästä suoraan tietokantaa.    
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/rakenne.PNG?raw=true" width="193">    

Ruokasovelluksella on JavaFX:llä toteutettu graafinen käyttöliittymä, joka on ruokasovellus.ui -kansiossa.
ruokasovellus.dao -kansiossa on ohjelman tietokantaa käyttävät luokat. 
Ruokasovellus.domain -kansiossa on ohjelman tarvitsemia aputoimia käyttöliittymän ja tietokantaa käyttävien luokkien välissä tekevä luokka.

## Käyttöliittymä  

Käyttöliittymässä on kolme eri näkymää
- Ruoka-aineiden hallinta
- Ruoka-annosten hallinta
- Päiväkirja ja laskenta    

Nämä näkymät on toteutettu Scene-olioina, jotka ovat yksi kerrallaan sijoitettu ohjelman stageen, eli näkyvissä.  

Käyttöliittymä -luokassa ei juuri tapahdu muuta kuin itse käyttöliittymään liittyviä asioita.
Käyttöliittymä kutsuu tietokantaa käyttäviä dao-luokkien, tai päiväkirjatoimintoja tekevän domain-luokan metodeja sopivilla parametreilla.  

## Sovelluslogiikka  
Sovelluksen loogisessa datamallissa ovat [DatabaseIncredients](https://github.com/ansketom/ot-harjoitustyo/blob/master/ruokasovellus/src/main/java/ruokasovellus/dao/DatabaseIncredients.java)
 ja  [DatabasePortion](https://github.com/ansketom/ot-harjoitustyo/blob/master/ruokasovellus/src/main/java/ruokasovellus/dao/DatabasePortions.java)
 , sekä [DatabaseDiary](https://github.com/ansketom/ot-harjoitustyo/blob/master/ruokasovellus/src/main/java/ruokasovellus/dao/DatabaseDiary.java). 
Nämä luokat suorittavat nimensä viittaamia toimintoja tietokantaan. Ruoka-annoksia muodostava DatabasePortion käyttää kahta taulua ruoka-annoksien säilömiseen, sekä Incredients-taulua.    

Päiväkirjan toiminnot ovat erilliset ruoka-aineisiin ja -annoksiin liittyvistä toiminnoista. Päiväkirjaa täyttävä domain-luokka [DiaryFunctions](https://github.com/ansketom/ot-harjoitustyo/blob/master/ruokasovellus/src/main/java/ruokasovellus/domain/DiaryFunctions.java)
tosin käyttää tietoja sekä DatabaseIncredients- että DatabasePortions-tauluista.  
Sovelluslogiikan dao-luokat:
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/datamalli.PNG?raw=true" width="461">  
Tietokantakaavio:     
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/tietokantakaavio.png?raw=true" width="577">  

Ruokasovellus on käytännössä Main-luokan käynnistämä graafinen käyttöliittymä DesktopUI, joka luo yhden olion kaikkia muita luokkia. 
Toiminnot ovat sen verran yksinkertaisia hae tai vie tietoa -tyyppisiä, että käyttöliittymä käyttää suurelta osin suoraan tietokantatoimintoja dao-luokista.  
Dao-luokat on ketjutettu siten, että kun tietokanta on luonut ne niin se sitten antaa samat tietokantataulut kaikkien dao-luokkien käyttöön parametrina.
Domain-luokka DiaryFunctions saa myös tämän saman tietokannan ja kaikki tarvitsemansa tietokantaan pääsevät luokat parametrina.  
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/luokkapakkauskaavio.PNG?raw=true" width="426">

## Tietojen pysyväistallennus
Käyttöliittymä luo oliot kaikille tietokantaa käyttäville luokille käynnistyessään, sekä yrittää luoda tietokantatiedostoa ruokaohjelman tarvitsemilla tauluilla siihen kansioon josta ohjelma on käynnistetty.
Tallennetut tiedot eivät katoa ohjelman normaalissa käytössä edes käynnistysten välillä, ellei tietokantatiedostoa konkreettisesti poista ohjelman käynnistyskansiosta.
HUOM: Ohjelman testien ajo nollaa tietokannan! Oma ruokasovellus.db -tietokantatiedosto on kannattaa ottaa käynnistyskansiosta muualle talteen testauksen ajaksi.  

## Tiedostot
Ruokasovellus luo itselleen SQLite tietokantatiedoston (ruokasovellus.db) ohjelman käynnistyskansioon, ellei sellaista sieltä jo löydy.  

## Päätoiminnallisuudet
