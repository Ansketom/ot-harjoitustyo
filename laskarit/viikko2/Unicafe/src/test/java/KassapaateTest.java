
package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate kassa;
    Maksukortti kortti;
    public KassapaateTest() {
    }
    
    @Before
    public void setUp() {
        kassa=new Kassapaate();
        kortti=new Maksukortti(1000);
    }
    @Test
    public void alkukassaOikein(){
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
    @Test
    public void AlussaEdullisiaLounaitaMyytyNolla(){
        assertTrue(kassa.edullisiaLounaitaMyyty()==0);
    }
    @Test
    public void AlussaMaukkaitaLounaitaMyytyNolla(){
        assertTrue(kassa.maukkaitaLounaitaMyyty()==0);
    }
    @Test
    public void kassanRahamaaraKasvaaOikeinSyotaessaEdullisesti(){
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    @Test
    public void vaihtorahaMeneeOikeinSyotaessaEdullisesti(){
        assertEquals(260, kassa.syoEdullisesti(500));
    }
    @Test
    public void josMaksuRiittaaNiinEdullisestiMyytyjenMaaraMuuttuu(){
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(250);
        kassa.syoEdullisesti(260);
        assertEquals(3, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void josMaksuEdulliseenEiRiitaNiinKassanRahamaaraEiMuutu(){
        kassa.syoEdullisesti(239);
        kassa.syoEdullisesti(0);
        kassa.syoEdullisesti(-300);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void josMaksuEdulliseenEiRiitaNiinKaikkiRahatPalautetaan(){
        assertEquals(239, kassa.syoEdullisesti(239));
    }
    @Test
    public void josMaksuEdulliseenEiRiitaNiinMyytyjenLounaidenMaaraEiMuutu(){
        kassa.syoEdullisesti(239);
        kassa.syoEdullisesti(0);
        kassa.syoEdullisesti(-300);
        kassa.syoEdullisesti(240);
        //myyty 1kpl lounaita
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void kassanRahamaaraKasvaaOikeinSyotaessaMaukkaasti(){
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    @Test
    public void vaihtorahaMeneeOikeinSyotaessaMaukkaasti(){
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    @Test
    public void josMaksuRiittaaNiinMaukkaastiMyytyjenMaaraMuuttuu(){
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(410);
        kassa.syoMaukkaasti(420);
        assertEquals(3, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void josMaksuMaukkaaseenEiRiitaNiinKassanRahamaaraEiMuutu(){
        kassa.syoMaukkaasti(399);
        kassa.syoMaukkaasti(0);
        kassa.syoMaukkaasti(-300);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void josMaksuMaukkaaseenEiRiitaNiinKaikkiRahatPalautetaan(){
        assertEquals(399, kassa.syoMaukkaasti(399));
    }
    @Test
    public void josMaksuMaukkaaseenEiRiitaNiinMyytyjenLounaidenMaaraEiMuutu(){
        kassa.syoMaukkaasti(399);
        kassa.syoMaukkaasti(0);
        kassa.syoMaukkaasti(-300);
        kassa.syoMaukkaasti(400);
        //myyty 1kpl lounaita
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    //maksukorttitestit kassasta
    @Test
    public void edullisenVeloitusKortiltaToimiiJosTarpeeksiRahaa(){
        kassa.syoEdullisesti(kortti);
        //kortilla jaljella 760
        assertEquals(760, kortti.saldo());
    }
    @Test
    public void edullisenVeloitusKortiltaKunRahaRiittaaPalauttaaTrue(){
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    @Test
    public void maukkaanVeloitusKortiltaToimiiJosTarpeeksiRahaa(){
        kassa.syoMaukkaasti(kortti);
        //kortilla jaljella 760
        assertEquals(600, kortti.saldo());
    }
    @Test
    public void maukkaanVeloitusKortiltaKunRahaRiittaaPalauttaaTrue(){
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    @Test
    public void edullisenMaksaminenKortillaLisaaMyytyjaLounaita(){
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(3, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void maukkaanMaksaminenKortillaLisaaMyytyjaLounaita(){
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void JosSaldoKortillaEiRiitaEdulliseenNiinSeEiMuutu(){
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        //saldo nyt 40
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(40, kortti.saldo());
    }
    @Test
    public void JosSaldoKortillaEiRiitaEdulliseenMyytyjenMaaraEiMuutu(){
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        //Saldo ei enää riitä
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(4, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void JosSaldoKortillaEiRiitaEduliseenNiinPalautetaanFalse(){
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    @Test
    public void JosSaldoKortillaEiRiitaMaukkaaseenNiinSeEiMuutu(){
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        //saldo nyt 200
        kassa.syoMaukkaasti(kortti);
        assertEquals(200, kortti.saldo());
    }
    @Test
    public void JosSaldoKortillaEiRiitaMaukkaaseenMyytyjenMaaraEiMuutu(){
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        //Saldo ei enää riitä
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void JosSaldoKortillaEiRiitaMaukkaaseenNiinPalautetaanFalse(){
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    //Rahan lataus kortille
    @Test
    public void rahanLatausKortilleMuuttaaKortinSaldoa(){
        kassa.lataaRahaaKortille(kortti, 1200);
        assertEquals(2200, kortti.saldo());
    }
    @Test
    public void rahanLatausKortilleLisaaLadatunRahanKassaan(){
        kassa.lataaRahaaKortille(kortti, 1200);
        assertEquals(101200, kassa.kassassaRahaa());
    }
    @Test
    public void negatiivisenSummanLataaminenKortilleEiMuutaSenSaldoa(){
        kassa.lataaRahaaKortille(kortti, -300);
        assertEquals(1000, kortti.saldo());
    }
}
