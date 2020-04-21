
package ruokasovellus;


public class DiaryFunctions {
 
    public String day;
    public int kcal;
    public int ch;
    public int prot;
    public int fat;
    
    public DiaryFunctions (String day){
        this.day=day;
        this.kcal=0;
        this.ch=0;
        this.prot=0;
        this.fat=0;
    }
    
    public void addMeal (int kcal, int ch, int prot, int fat){
        this.kcal+=kcal;
        this.ch+=ch;
        this.prot+=prot;
        this.fat+=fat;
    }
    public boolean substractMeal (int kcal, int ch, int prot, int fat){
        int indicator=0;
        
        this.kcal-=kcal;
        if(this.kcal<0){
            this.kcal=0;
            indicator=1;
        }
        this.ch-=ch;
        if(this.ch<0){
            this.ch=0;
            indicator=1;
        }
        this.prot-=prot;
        if(this.prot<0){
            this.prot=0;
            indicator=1;
        }
        this.fat-=fat;
        if(this.fat<0){
            this.fat=0;
            indicator=1;
        }
        
        if (indicator>0){
            return false;
        }
        return true;
    }
    public String toString(String day){
        return "Päivämäärä: " + day + ", Yht kcal: " + this.kcal +", hh: " + this.ch + "g, prot: " + this.prot + "g, rasva: " + this.fat + "g";
    }
    
}
