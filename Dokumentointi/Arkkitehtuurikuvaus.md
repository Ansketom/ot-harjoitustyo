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

Päiväkirjan toiminnot ovat erilliset ruoka-aineisiin ja -annoksiin liittyvistä toiminnoista. Päiväkirjaa (DatabaseDiary) käyttävä domain-luokka [DiaryFunctions](https://github.com/ansketom/ot-harjoitustyo/blob/master/ruokasovellus/src/main/java/ruokasovellus/domain/DiaryFunctions.java)
tosin käyttää tietoja myös sekä DatabaseIncredients- että DatabasePortions-luokista.  
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
#### Ruoka-aineen lisäys ja poisto
Ruoka-aineen lisääminen tapahtuu täyttämällä käyttöliittymän kenttiin ruoka-aineen nimi, energiamäärä sekä makroravinteet pyydetyssä muodossa, ja painamalla painiketta "Lisää".  
Ruoka-aineen poistaminen tapahtuu täyttämällä käyttöliittymän kenttään ruoka-aineen nimi ja Kcal -kenttään "-1", ja painamalla sitten painiketta "Poista".
Näiden toimintojen tapauksessa etenee ohjelma seuraavasti:    
(lisäys, yritys lisätä samanniminen uudestaan, poistaminen, lisäys)    
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/ruokaaineenlisays.png?raw=true" width="746">    
Ruokasovellus päivittää näkyvän Ruoka-aineslistan automaattisesti lisäyksiä ja poistoja tehdessä.
    
#### Ruoka-annoksen lisäys
Ruoka-annoksen lisääminen tapahtuu lisäämällä ensin tietokantaan uusi ruoka annos täyttämällä kenttään ruoka-annoksen nimi, ja painamalla sitten painiketta "Lisää uusi ruoka-annos".
Kun ruoka-annos on lisätty, voidaan siihen alkaa liittää aterian osiksi ruoka-aineita. Tämä tapahtuu halutun annoksen ollessa kirjoitettuna ruoka-ainnoksen nimi-kenttään kirjoittamalla Ruoka-aine -kenttään
 jo aiemmin lisätyn ruoka-aineen nimi, kirjoittamalla ruoka-aineen määrä -kenttään annoksessa oleva määrä ruoka-ainetta grammoissa, ja sitten painamalla painiketta "Lisää ruoka-aine annokseen".
 Ohjelman toiminta näiden toimintojen tekemiseksi on kuvattu alla:    
<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/annoksenlisays.png?raw=true" width="754">    

#### Päiväkirjan pito - ruoka-annoksen ja vesimäärän lisäys päiväkirjaan
Päiväkirjan näyttää toimivan samantyylisesti kuin yllä kuvatut toiminnot, mutta se suoran tietokannan käytön sijaan se käyttää toimiakseen apuluokkaa DiaryFunctions.
Päiväkirja vaatii ruokien laskemisen aloittamiseksi jonkin merkkijonon, esimerkiksi päivämäärän. Tämän päivän päivämäärän saa haettua päivämäärä-kenttään painamalla "hae päivämäärä (tänään)" -painiketta.
Sitten päivämäärän voi alustaa painamalla "alusta lasku". Tämä tuo ikkunan alalaidan listaukseen näkyviin annetun päivämäärän alustetun rivin.
Tämän jälkeen voi tälle päivämäärälle (päivämääräkenttä) ruveta lisäämään ohjelmaan aiemmin lisättyjä ruoka-annoksia kirjoittamalla annoksen nimi "Ruoka-annos" -kenttään, ja sitten painamalla 
"Lisää annos"-painiketta. Muutos näkyy heti päiväkirjasivun alalaidan kirjanpidossa.  

Veden lisäys päivämäärälle menee päivän alustamisen jälkeen siten, että kun päivämääräkentässä on haluttu päivämäärä jolle vesimäärä lisätään, voidaan koko päivän aikana juotu vesimäärä kirjata 
desilitroina päivämääräsivun keskellä olevaan kenttään, ja sitten painaa painiketta "Tallenna vesimäärä".

<img src = "https://github.com/ansketom/ot-harjoitustyo/blob/master/Dokumentointi/kuvat/paivakirjanpito.png?raw=true" width="1130">    
Int[] data -listat, joita DiaryFunctions käsittelee, koostuvat päivän tai ruoka-aineen kcal-määristä ja makroravinteista (int[]data = {kcal, ch, prot, fat}).
DatabasePortions-luokasta haettu ArrayList sisältää päivämäärälle lisättävän annoksen ruoka-aineet, ja niiden määrät.
getIncredientDataInInt-metodia kutsutaan niin monta kertaa, kuin lisättävässä annoksessa on annoksenosia.

Ruokaohjelman toimintojen vaatimat tiedot on kirjattu sulkeisiin painikkeiden nimiin, mikä voi helpottaa ohjelman käyttöä.


## Ohjelman rakenteeseen jääneet heikkoudet  

### Käyttöliittymä
Ohjelman luonne on sellainen, että sen käyttöliittymän ulkoasulla ja toiminnalla on todella paljon merkitystä käyttökokemukseen.
Ruokasovelluksen käyttöliittymä ei ole niin korkeaa standardia, etteikö ison osan sen toiminnoista käyttö tuntuisi vaivalloiselta verrattuna 
oikeastaan kaikkiin ohjelmiin mitä nykyihmiset ovat tottuneet tietokoneellaan tai puhelimillaan käyttämään. 
Ruokaohjelma nykymuodossaan tarvitsee käytön opettelua, sen toimintaan liittyvien asioiden muistamista ja huolellisuutta - käytössä on 
suuri riski tehdä huolimattomuusvirheitä joiden korjaus vaatii aika paljon vaivaa (esim pilkkuvirhe ruoka-aineen kirjauksessa).
Tällaisen ohjelman käytön pitäisi olla loogista ja vaivatonta, sekä niin totuttua, että keskivertokäyttäjä käyttää sitä suoraan oikein.    

### Testit  
Ohjelman testaaminen tyhjentää ohjelman tietokannan, tai häiriintyy sen olemassaolosta. Testit pitäisi ehkä rakentaa siten, että ne tekisivät oman tietokantatiedostonsa jonnekin muualle testien ajaksi.

### Luokkien sekä metodien nimeäminen ja määrä
Luokkien ja metodien nimeämisessä on käytetty sinänsä yhtenäistä ja loogista linjaa, mutta ne eivät ole sellaisia mitä yleisen standardin mukaan mielestäni pitäisi käyttää. 
Ruokaohjelma on tehty siten että se ylipäätään toimisi niin kuin halutaan, mikä on toimintojen rakentelun ketjuna pitkän päällekäinkasaamisen seurauksena
johtanut joiltain osin vähän mielenkiintoisiin ratkaisuihin ja nimeämisiin.
