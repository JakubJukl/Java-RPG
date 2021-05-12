/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juklrpg;

import javax.swing.JOptionPane;
/**
 *
 * @author Jakub Jukl
 */
public class JuklRPG {
    /**
     * @param args the command line arguments
     */
    
    private final static String TITLE_UVODNI = "Úvodní informace";
    private final static String UVODNI_INFO = """
                                              Vítejte v jednoduché RPG hře.\n
                                              Vžijte se do role vesnického dítěte, které chce 
                                              poznávat svět.\n
                                              V obrazovce nastavení, která následuje po úvodním
                                              přivítání, si můžete zvolit jestli chcete být Bojovník, 
                                              využívající hlavně sílu a zbraně k poražení nepřátel.
                                              Na druhé straně se můžete rozhodnout být inteligentní 
                                              Kouzelník spalující nepřátele na uhlík, ale každé kouzlo 
                                              něco stojí. Pokud dojde kouzelníkovi mana, tak mu nezbývá 
                                              nic jiného, než se ponížit na úroveň válečníka.\n
                                              Vaším cílem je splnit všechny 3, zatím přidané, úkoly.
                                              Cestou Vám budou stát v cestě nepřátelé, od hrozivých 
                                              Žížal až po Upíry.\n
                                              Jak hrát hru: Nestvůr, které je potřeba zneškodnit je 
                                              neomezeně. Jedné se zbavíte, další se objeví (doslovně).
                                              Za každou zneškodněnou nestvůru dostanete zkušenosti, nějaké
                                              zlato a pokud máte štěstí, tak mrtvolu i o něco oberete.
                                              S plněním úkolů budete dostávat lepší zbraně a budete si moct
                                              troufnout na silnější nepřátele. Při smrti nic neztrácíte.\n
                                              Pokud si nebudete vědět rady s uživatelským rozhraním, tak
                                              neváhejte kliknout na 'Popis GUI' v horní liště hry.
                                              """;                              
                                                                                      
                                                
    
    public static void main(String[] args) {
        infoBox(UVODNI_INFO, TITLE_UVODNI);
        Form1 hlavniOkno = new Form1();
        hlavniOkno.setVisible(true);
        hlavniOkno.oknoNastaveni();
    }
    
    public static void infoBox(String zprava, String titulek)
    {
        JOptionPane.showMessageDialog(null, String.format(zprava), titulek, JOptionPane.INFORMATION_MESSAGE);
    }
}
