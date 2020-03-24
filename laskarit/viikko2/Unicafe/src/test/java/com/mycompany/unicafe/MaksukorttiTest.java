package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    @Test
    public void kortinSaldoAlussaOikein(){
        assertEquals(10, kortti.saldo());
    }
    @Test
    public void rahanLataaminenKasvattaaSaldoa(){
        kortti.lataaRahaa(15);
        kortti.lataaRahaa(5);
        assertEquals(30, kortti.saldo());
    }
    @Test
    public void kortinSaldoVaheneeKunRahaaOtetaan(){
        kortti.otaRahaa(5);
        //saldo nyt 5
        assertEquals(5, kortti.saldo());
    }
    @Test
    public void kortinSaldoaEiVoiVahentaaMiinukselle(){
        kortti.otaRahaa(5);
        //kortin saldo nyt 5
        kortti.otaRahaa(7);
        assertEquals(5, kortti.saldo());
    }
    @Test
    public void otaRahaaPalauttaaTrueJosRahaaRiittavasti(){
        assertTrue(kortti.otaRahaa(5));
    }
    @Test
    public void otaRahaaPalauttaFalseJosRahaaEiRiittavasti(){
        assertFalse(kortti.otaRahaa(12));
    }
    @Test
    public void toStringPalauttaaStringin(){
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
}
