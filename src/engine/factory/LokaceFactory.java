/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.factory;

import engine.model.Lokace;
import engine.model.Potycka;
import java.util.ArrayList;

/**
 *
 * @author Jakub Jukl
 */
public class LokaceFactory {
    private static final ArrayList<Lokace> herniLokace = new ArrayList<>();
    
    public static void setLokace(){
        Lokace domov = new Lokace(0, 0, "Domov", "Tady ses narodil a snad tu i zemřeš.", "Domov.png");     
        herniLokace.add(domov);
        
        Lokace zahradnik = new Lokace(0, 1, "Dům zahradníka", "PUNK is not dead! Takový nápis bych tu nečekal.", 
                "Dum_zahradnika.png");
        zahradnik.pridejUkol(UkolFactory.getUkol(UkolFactory.ID_ZIZALY));
        herniLokace.add(zahradnik);
        
        Lokace pole = new Lokace(0, 2, "Pole s obilím", "Tady je ale žížal...", "Pole.png");
        pole.pridejPotycku(new Potycka(MonstrumFactory.getMonstrum(MonstrumFactory.ID_ZIZALA), 100));
        herniLokace.add(pole);
        
        Lokace brana = new Lokace(1, 0, "Městská brána", "Co se stane, když se podívám ven?", "Brana.png");
        herniLokace.add(brana);
        
        Lokace most = new Lokace(2, 0, "Nedostavěný most", "Přes ten most se nedostanu...", "Most.png");
        herniLokace.add(most);
        
        Lokace hospoda = new Lokace(-1, 0, "Hospoda", "Na mě moc Western.", "Hospoda.png");
        hospoda.pridejUkol(UkolFactory.getUkol(UkolFactory.ID_KOVBOJOVE));
        herniLokace.add(hospoda);
        
        Lokace kovboj = new Lokace(-2, 0, "Kovbojská pláň", "Hodně mrtvol, ale jinak nic.", "Kovbojska_plan.png");
        kovboj.pridejPotycku(new Potycka(MonstrumFactory.getMonstrum(MonstrumFactory.ID_KOVBOJ), 100));
        herniLokace.add(kovboj);
        
        Lokace hrbitov = new Lokace(0, -1, "Hřbitov", "Tady mě snad můj konec nečeká.", "Hrbitov.png");
        hrbitov.pridejPotycku(new Potycka(MonstrumFactory.getMonstrum(MonstrumFactory.ID_KOVBOJ), 10));
        hrbitov.pridejPotycku(new Potycka(MonstrumFactory.getMonstrum(MonstrumFactory.ID_UPIR), 90));
        herniLokace.add(hrbitov);
        
        Lokace rodina = new Lokace(0, -2, "Rodina na kopci", "Tyhle lidi budu muset asi zachránit.", "Rodina.png");
        rodina.pridejUkol(UkolFactory.getUkol(UkolFactory.ID_UPIRI));
        herniLokace.add(rodina);
    }
    
    public static Lokace getLokace(int x, int y){   //lokace neupravujeme, takže taky můžeme vracet takto
        return herniLokace.stream().filter(l -> l.getxSourad() == x && l.getySourad() == y).findFirst().orElse(null);
    }

    public static Lokace getDomovskouLokaci(){
        return herniLokace.stream().filter(l -> l.getJmeno().equals("Domov")).findFirst().orElse(null);
    }
    
}
