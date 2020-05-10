# Vaatimusmaarittely

### Sovelluksen tarkoitus
Ruokasovellus on sovellus, jonka avulla käyttäjä voi laskea, 
että kuinka paljon hän sai päivän aikana energiaa ja eri makroravinteita, sekä tallentaa nämä laskutulokset ja päivän aikana juomansa
veden määrän tietokantaan. Laskenta tapahtuu sovellukseen aiemmin tallennetuista ruoka-aineista koostettuja aterioita yhteen laskemalla. 
 

### Käyttäjät
Sovellus on henkilökohtainen, eli siinä ei ole eritelty käyttäjää tai käyttäjätilejä. 

Sovellus aukeaa ensimmäisenä näkymään, jossa käyttäjä voi laskea päivän aikana syödyt, aiemmin ohjelmaan lisätyt, ateriat yhteen.  

### Perusversion tarjoama toiminnallisuus
-Käyttäjä voi lisätä sovellukseen ruoka-aineen (nimi ja makroravinteet/100g).(Näkymä1)   
-Käyttäjä voi poistaa sovelluksesta ruoka-aineen.(Näkymä1)    
-Käyttäjä voi muodostaa ruoka-aineista ja niiden eri määristä aterian.(Näkymä2)  
-Käyttäjä voi muokata lisäämänsä aterian sisältöä myös poistamalla sen osia. (Näkymä2)  
-Käyttäjä voi laskea aterioita yhteen päiväkirjaan, ja saada siten selville päivän ruokien energiamäärän (kcal) ja makroravinnemäärät
 (gramoina: hiilihydraattia, proteiinia, rasvaa)(Näkymä3).    
-Käyttäjä voi tallettaa päiväkirjaan myös päivän aikana juomansa veden määrän.    
-Päiväkirjasta voi myös poistaa aterioita, ja sinne tehdyt laskelmat voi myös nollata.    
-Käyttäjä voi vaihtaa näkymää välilehtien selaamisen tapaan ohjelmassa olevien kolmen(3) näkymän välillä.   


### Jatkokehitysideoita

Mikäli sovellus koodaajalla olisi ylimääräistä aikaa, 
voisi sovellusta täydentää vielä seuraavilla ominaisuuksilla:  

1) Tuoda ruoka-aineet valmiiksi hakemistoksi sovellukseen. Tähän tarvittava data on saatavilla Fineli.fi. Tämä saattaa olla työlästä toteuttaa, sillä dataa pitää karsia sovelluksen tarpeisiin käytön helpottamiseksi, ja suuri tallennettu ruoka-ainemäärä asettaa vaateita myös haluttujen ruoka-aineiden löytämiselle sovelluksesta (ohjelman näkymien selkeys ja käytettävyys).   

2) Laajentaa sovelluksen ruoka-ainetietokanta koskemaan myös hivenaineita ja vitamiineja.

3) Käyttäjätilien luominen sovellukseen:  
-Peruskäyttäjillä olisi mahdollisuus lisätä ja poistaa ruoka-aineita ja aterioita, mutta ei muokata ohjelmassa valmiina ollutta kirjastoa tai muiden käyttäjien lisäämiä ruoka-aineita tai aterioita. Lisäksi on laajempioikeuksinen Admin-käyttäjätili.  
-Käyttäjä voi saada näkyviin salasanansa kirjautuneena ja muuttaa sitä. Admin voi hakea kenen vain salasanan näkyviin käyttäjänimen perusteella, mutta muuttaa vain omaansa.  
	-Admin -käyttäjä voi muokata kaikkia ruoka-aineita ja aterioita.  
	-Alkunäkymä on kirjautumissivu. Käyttäjä voisi kirjautua myös ulos.  
-Käyttäjät saavat näkyviin kaikkien tallentamat ruoka-aineet ja ateriat, mutta vain oman ruokapäiväkirjansa.  
