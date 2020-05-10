# Testausdokumentti

Ohjelmalla on sekä automoituja JUnit yksikkötestejä, ja sen toimintaa on testattu myös käyttämällä ohjelmaa.

## Yksikkötestaus
Koska ohjelma on lähinnä vain käyttöliittymä joka käyttää tietokantaa, ovat kaikki paitsi yksi luokka käyttöliittymän ulkopuolella niin sanottuja DAO-luokkia.
Näillä luokilla on metodien oletuksenmukaisen toiminnan testaavat yksikkötestit,
 jotka testaavat metodit samaan tapaan kuin itse ohjelma niitä käyttää.
 
### Testauskattavuus
 Käyttöliittymää (DesktopUI- ja Main-luokkaa) varten ei ole testejä. Muuten testauksella on 100% rivikattavuus ja 87%   
 <img src="https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/ruokasovellus_testikattavuus.PNG?raw=true" width="800">
 
 Testikattavuus ei ole täysi, koska testien koodaajalla loppui taidot kesken.
 
## Järjestelmätestaus
 Järjestelmätestaus suoritettiin manuaalisesti.
 
### Ohjelman asennus
 Ohjelman käyttöä on testattu windows-käyttöliittymässä sekä Netbeans:n kautta, että komentorivin kautta. Ohjelma tarvitsee aina sqlite-jdbc-3-30-1.jar -kirjaston lib-kansioonsa - ilman tätä ohjelma ei toimi.  
     
 Ohjelma osaa luoda itselleen tietokannan, mikäli sitä ei käynnistäessä ole.
 
### Toiminnallisuuden testaus
 Ohjelman käyttöä on testattu määrittelydokumentin kuvaamien toimintojen osalta huolellisella käytöllä. Vaikka ohjelma kaatuisi epähuolellisen käytön vuoksi, sen ei pitäisi hukata aiemmin siihen tallennettua tietoa, ellei ohjelman tietokantatiedostoa (ruokasovellus.db) mene manuaalisesti poistamaan.
 
## Sovelluksen laatuongelmat
 Osa ohjelman virheilmoituksista ja viesteistä tulee käyttöliittymäikkunan sijaan komentoriville. Tällä ei juuri ole merkitystä, sillä käyttäjä todennäköisesti hyvin pian ymmärtää muutenkin, että mikä toiminto toimi tai ei toiminut, ja miksi (avainsana: huolellinen käyttö).
 