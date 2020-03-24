# Vaatimusmaarittely

### Sovelluksen tarkoitus
Ruokasovellus on sovellus, jonka avulla käyttäjä voi laskea, että kuinka paljon hän sai päivän aikana energiaa ja eri makroravinteita. Laskenta tapahtuu sovellukseen aiemmin tallennetuista ruoka-aineista koostettuja aterioita yhteen laskemalla.  

### Käyttäjät
Sovellus on lähtökohtaisesti henkilökohtainen, eli siinä ei ole eritelty käyttäjää tai käyttäjätilejä (, tai muuten sovellukseen tulisi neljä tietokantataulua).  

Sovellus aukeaa ensimmäisenä näkymään, jossa käyttäjä voi laskea päivän aikana syödyt ateriat yhteen.  

### Perusversion tarjoama toiminnallisuus
-Käyttäjä voi lisätä sovellukseen ruoka-aineen (nimi ja makroravinteet/100g). (Tietokantataulu 1)(Näkymä1)  
-Käyttäjä voi poistaa sovelluksesta ruoka-aineen, tai muokata sen tietoja.(Näkymä1)  
-Käyttäjä voi muodostaa ruoka-aineista aterian. (Tietokantataulu 2)(Näkymä2)  
-Käyttäjä voi poistaa sovelluksesta aterian, tai muokata sen sisältöä. (Näkymä2)  
-Käyttäjä voi laskea aterioita yhteen ja saada siten selville päivän ruokien energiamäärän ja makroravinnemäärät (gramoina: hiilihydraattia, proteiinia, rasvaa)(Näkymä3).  
-Käyttäjä voi vaihtaa näkymää välilehtien selaamisen tapaan ohjelmassa olevien kolmen(3) näkymän välillä.  


### Jatkokehitysideoita

Mikäli sovellus saadaan tähän asti toimimaan hyvin niin että koodaajalla on ylimääräistä aikaa, voidaan sovellusta täydentää vielä seuraavilla ominaisuuksilla:  

1) Mahdollisuus tallentaa yhteenlaskettujen aterioiden tulos päivämäärällä tietokantaan (yksi rivi per päivä). Tämä tekisi sovelluksesta vähän niin kuin ruokapäiväkirjan (teknisesti energia- ja makroravinnepäiväkirja). Tähän kirjanpitoon tulee voida tallentaa myös päivän aikana juodun veden kokonaismäärä.  
Toiminnallisuus siis:  
-Lisätään yksi kerrallaan aterioita päivämäärän riville tietokantaan. Tämä mahdollistaa kirjanpidon pitkin päivää.  
-Päivämäärältä voi myös vähentää aterioita, jonka idea on mahdollistaa väärän merkinnän korjaus. Tietokannassa ei näy, että mitä aterioita millekin päivälle on lisätty, vaan vain niistä laskettu kokonaisenergiamäärä ja ravitoarvoja. Ohjelman tulee näyttää jollain hyvin havaittavalla tavalla, jos viimeisin tehty muutos aiheuttaa miinusmerkkisen arvon tietokannassa.  

2) Laajentaa sovelluksen ruoka-ainetietokanta koskemaan myös hivenaineita ja vitamiineja, ja tuoda ruoka-aineet valmiiksi hakemistoksi sovellukseen. Tähän tarvittava data on saatavilla Fineli.fi. Tämä saattaa olla työlästä toteuttaa, sillä dataa pitää karsia sovelluksen tarpeisiin käytön helpottamiseksi, ja suuri tallennettu ruoka-ainemäärä asettaa vaateita myös haluttujen ruoka-aineiden löytämiselle sovelluksesta (ohjelman näkymien selkeys ja käytettävyys).  

3) Käyttäjätilien luominen sovellukseen:  
-Peruskäyttäjillä olisi mahdollisuus lisätä ja poistaa ruoka-aineita ja aterioita, mutta ei muokata ohjelmassa valmiina ollutta kirjastoa tai muiden käyttäjien lisäämiä ruoka-aineita tai aterioita. Lisäksi on laajempioikeuksinen Admin-käyttäjätili.  
-Käyttäjä voi saada näkyviin salasanansa kirjautuneena ja muuttaa sitä. Admin voi hakea kenen vain salasanan näkyviin käyttäjänimen perusteella, mutta muuttaa vain omaansa.  
	-Admin -käyttäjä voi muokata kaikkia ruoka-aineita ja aterioita.  
	-Alkunäkymä on kirjautumissivu. Käyttäjä voisi kirjautua myös ulos.  
-Käyttäjät saavat näkyviin kaikkien tallentamat ruoka-aineet ja ateriat, mutta vain oman ruokapäiväkirjansa.  
