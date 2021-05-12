/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juklrpg;

import engine.*;
import engine.model.*;
import engine.factory.*;
import engine.tableModel.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.text.StyledDocument;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Jakub Jukl
 */
public class Form1 extends javax.swing.JFrame {

    private final Hrac soucHrac;
    private Lokace soucLokace;
    private Monstrum soucMonstrum;

    private final static String ZPRAVA_NA_LVL_UP = "\nDostal si se na další level!";
    private final static String ZPRAVA_SPLNENI_UKOLU = "Splnil si úkol ";
    private final static String ZPRAVA_ZADNY_LOOT = "Prošacoval si to, ale nic si nenašel.";
    private final static String ZPRAVA_LOOT = "Dostal si ";
    private final static String ZPRAVA_NOVE_MONSTRUM = "\nObjevil se nový ";
    private final static String TEXT_MONSTRUM_HP_LBL = "Monstrum HP: ";
    private final static String ZPRAVA_PRIJATI_UKOLU = "\nPřijal si ";
    private final static String ZPRAVA_BEZ_MONSTRA = "Není k dispozici žádné monstrum.";
    private final static String ZPRAVA_BEZ_MANY = "Nemáš dost many na vykouzlení ";
    
    private final static String GUI_TITULEK = "Popis grafického rozhraní";
    private final static String GUI_POPIS = """
                                            V tyrkysovém poli se nachází Vaše 'staty'.
                                            Důležité pro hraní jsou HP (životy), mana
                                            (dovoluje používat kouzla), inteligence (ovlivňuje
                                            sílu kouzel), síla (ovlivňuje sílu ručních zbraní).\n 
                                            Herní zprávy se objevují ve černém poli. Objevují se tam
                                            veškeré herní události, ať už se to týká soubojů (kolik ubral
                                            útok nepříteli), úkolů (přijal/odevzdal se úkol) a mnoho
                                            dalších.\n
                                            Tmavě modré pole obsahuje název, obrázek a popis lokace.\n
                                            Ve žlutém poli se objevují monstra. Jejich název, obrázek a 
                                            současný počet životů, což se vyplatí sledovat při souboji.\n
                                            V levém dolním rohu jsou tabulky. V tabulce 'Inventář' můžete vidět
                                            veškeré vlastněné předměty a jejich množství. V tabulce 'Kouzla' 
                                            jsou vidět naučená kouzla a kolik stojí many. 'Přijaté' zobrazuje
                                            úkoly, které vaše postava přijala a ještě neodevzdala. 'Splněné'
                                            naopak zobrazuje ty, které vaše postava už odevzdala. Po najetí 
                                            myší se zobrazí tooltip s popisem dané položky.\n
                                            Postavu ovládáte přes prvky ve fialovém poli. Pokud je ve vaší
                                            současné lokaci příšera, tak se objeví první ComboBox a k němu 
                                            přidružené tlačítko 'Udeř'. Ostatní ComboBoxy jsou viditelné
                                            stále, spolu s tlačítky. V ComboBoxu si vždy vyberete nějaký
                                            předmět/kouzlo a tlačítkem ho použijete.\n
                                            U kouzel je dobré pamatovat na CheckBox 'target sebe'. Pokud
                                            ho máte zaškrtnutý, tak se kouzla provedou na vás, pokud ne
                                            tak na nepřítele (můžete se i samy zabít).\n
                                            Nakonec tlačítka v pravé části pole slouží k pohybu mezi 
                                            lokacemi.
                                            """;   

    private final static double KOEF_NAVYSENI_STATU = 2;
    private final static double KOEF_SNIZENI_STATU = 0.5;
    private final static Color LVLUP_BARVA_TXT = new Color(107, 107, 255); //modrá
    private final static Color QUEST_BARVA_TXT = new Color(255, 255, 0);    //žlutá
    private final static Color LOOT_BARVA_TXT = new Color(45, 180, 45);      //zelená
    private final static Color KRITICKA_BARVA_TXT = new Color(250, 0, 0); //červená
    private final static Color DEFAULT_BARVA_TXT = Color.white;
    
    public Form1() {
        ItemFactory.setItemy();
        MonstrumFactory.setMonstra();
        UkolFactory.setUkoly();
        LokaceFactory.setLokace();

        soucHrac = Hrac.ZakladniHrac();
        soucLokace = LokaceFactory.getDomovskouLokaci();

        initComponents();   
        //přiřadit listenery na hráče
        soucHrac.addPropertyChangeListener(Zivocich.PC_SOUCHP, e -> lblHP.setText(String.valueOf(e.getNewValue())));
        soucHrac.addPropertyChangeListener(Zivocich.PC_JMENO, e -> lblJmeno.setText((String) e.getNewValue()));
        soucHrac.addPropertyChangeListener(Hrac.PC_POVOLANI, e -> zmenaPovolani(e.getNewValue()));
        soucHrac.addPropertyChangeListener(Zivocich.PC_SOUCMANA, e -> lblMana.setText(String.valueOf(e.getNewValue())));
        soucHrac.addPropertyChangeListener(Zivocich.PC_INTELEKT, e -> lblIntel.setText(String.valueOf(e.getNewValue())));
        soucHrac.addPropertyChangeListener(Zivocich.PC_SILA, e -> lblSila.setText(String.valueOf(e.getNewValue())));
        soucHrac.addPropertyChangeListener(Zivocich.PC_LEVEL, e -> naLvlUp((int) e.getNewValue()));
        soucHrac.addPropertyChangeListener(Zivocich.PC_ZLATO, e -> lblZlato.setText(String.valueOf(e.getNewValue())));
        soucHrac.addPropertyChangeListener(Hrac.PC_XP, e -> lblXP.setText(String.valueOf(e.getNewValue())));
        soucHrac.addPropertyChangeListener(Hrac.PC_ZBRAN, e -> comboBoxDataZbrane());
        soucHrac.addPropertyChangeListener(Hrac.PC_KONZUM, e -> comboBoxDataKK(cboKonzum));
        soucHrac.addPropertyChangeListener(Hrac.PC_KOUZLO, e -> zmenaKouzel());
        soucHrac.addPropertyChangeListener(Hrac.PC_INVENTAR, e -> zmenaInventare());
        
        //inicializace grafických prvků a combo boxů
        comboBoxDataZbrane();
        comboBoxDataKK(cboKonzum);
        comboBoxDataKK(cboKouzlo);
        zmenaOdevzdanych();
        zmenaPrijatych();
        zmenaKouzel();
        zmenaInventare();
        updateLokace();
    }
    
    private void naLvlUp(int level) {   //event lvl up
        lblLevel.setText(String.valueOf(level));
        appendTextZpravy(ZPRAVA_NA_LVL_UP,LVLUP_BARVA_TXT);
    }

    private void tahNepritele() {       //zavolá se v boji s monstrem potom, co hráč táhnul
        if (soucMonstrum != null) {
            appendTextZpravy(soucMonstrum.getZbran().getAkce().proved(soucMonstrum, soucHrac),DEFAULT_BARVA_TXT);
            if (soucHrac.isMrtvy() == true) {
                smrtHrace();
            }
        }
    }

    private void zmenaPovolani(Object povolani) {   //zajistí přepočet statů po změně povolání
        lblPovolani.setText(String.valueOf(povolani));
        if (povolani == Hrac.Povolani.Kouzelnik) {
            soucHrac.vynasobIntelekt(KOEF_NAVYSENI_STATU);
            soucHrac.vynasobSilu(KOEF_SNIZENI_STATU);
        } else {
            soucHrac.vynasobSilu(KOEF_NAVYSENI_STATU);
            soucHrac.vynasobIntelekt(KOEF_SNIZENI_STATU);
        }
    }

    private void smrtHrace() {  //přesune hráče při smrti do domovské lokace a vyhealuje ho
        appendTextZpravy(soucHrac.zpravaPriSmrti(),KRITICKA_BARVA_TXT);
        soucLokace = LokaceFactory.getDomovskouLokaci();
        updateLokace();
        soucHrac.healNaMax();
        soucHrac.manaNaMax();
    }

    private void smrtMonstra() {    //event smrti monstra
        appendTextZpravy(soucMonstrum.zpravaPriSmrti(),DEFAULT_BARVA_TXT);
        rozdelLoot();
        soucHrac.pridejOdmeny(soucMonstrum);
        soucMonstrum = vygenerujSoucMonstrum(soucLokace.getDostupnePotycky());
    }

    private void zmenaInventare() { //update tabulky s inventářem
        InventarTableModel model = new InventarTableModel(soucHrac.getInventar());
        dgInventar.setModel(model);

    }

    private void zmenaKouzel() {    
        KouzlaTableModel model = new KouzlaTableModel(soucHrac.getInventar());
        dgKouzla.setModel(model);
        comboBoxDataKK(cboKouzlo);
    }

    private void zmenaPrijatych() {
        PrijateUkolyTableModel model = new PrijateUkolyTableModel(soucHrac.getPrijateUkoly());
        dgPrijateUkoly.setModel(model);

    }

    private void zmenaOdevzdanych() {
        SplneneUkolyTableModel model = new SplneneUkolyTableModel(soucHrac.getSplneneUkoly());
        dgSplneneUkoly.setModel(model);
    }

    private void jeMozneOdevzdat() {    //zkontroluje, jestli je možné odevzdat quest
        soucHrac.getPrijateUkoly().forEach(ukol -> {
            for (MnozstviItemu item : ukol.getPotrebnyItem()) {
                if (soucHrac.getInventar().stream().anyMatch(i -> i.item.getId() == item.item.getId()
                        && i.mnozstvi >= item.mnozstvi)) {
                    ukol.setSplnenaKriteria(true);
                    zmenaPrijatych();
                }
            }
        });
    }

    private void odevzdaniUkolu(Ukol ukol) {    //odevzdá úkol
        for (MnozstviItemu item : ukol.getPotrebnyItem()) {
            soucHrac.odeberItem(soucHrac.getInventar().stream().filter(i -> i.item.getId()
                    == item.item.getId()).findFirst().get(), item.mnozstvi);
        }
        soucHrac.splnUkol(ukol);
        appendTextZpravy(ZPRAVA_SPLNENI_UKOLU + ukol.getJmeno(),QUEST_BARVA_TXT);
        zmenaPrijatych();
        zmenaOdevzdanych();
    }

    private void comboBoxDataZbrane() {
        int index = cboZbran.getSelectedIndex();
        cboZbran.setModel(new DefaultComboBoxModel<>(soucHrac.getInventar().stream().filter(i
                -> i.item.getDruh() == Item.ItemDruh.Zbran).toArray(size -> new MnozstviItemu[size])));
        if (index == -1 && jeCboNaplneny(cboZbran)) {
            cboZbran.setSelectedIndex(0);
        } else {
            cboZbran.setSelectedIndex(index);
        }
    }

    private void comboBoxDataKK(JComboBox cbo) {    //jedna fce jak pro konzumovatelné tak pro kouzla (nahrání do comboboxu
        int index = cbo.getSelectedIndex();
        JButton btn;
        Item.ItemDruh druh;
        if (cbo.equals(cboKonzum)) {
            druh = Item.ItemDruh.Konzumovatelne;
            btn = btnKonzum;
        } else {
            druh = Item.ItemDruh.Kouzlo;
            btn = btnKouzlo;
        }
        cbo.setModel(new DefaultComboBoxModel<>(soucHrac.getInventar().stream().filter(i
                -> i.item.getDruh() == druh).toArray(size -> new MnozstviItemu[size])));
        if (jeCboNaplneny(cbo)) {
            if (index == -1) {
                index = 0;
            }
            comboBoxBtnViditelnost(cbo, btn, true);
            cbo.setSelectedIndex(index);
        } else {
            comboBoxBtnViditelnost(cbo, btn, false);
        }
    }

    private void comboBoxBtnViditelnost(JComboBox cbo, JButton btn, boolean viditelny) {
        if (jeCboNaplneny(cbo) && viditelny == true) {
            cbo.setVisible(true);
            btn.setVisible(true);
        } else {
            cbo.setVisible(false);
            btn.setVisible(false);
        }

    }

    private boolean jeCboNaplneny(JComboBox cbo) {  //když je cbo prázdný, tak nemusí být vidět
        return cbo.getItemCount() != 0;
    }

    private void rozdelLoot() {     //hodí kostkou a podle výsledku rozdělí loot
        if (soucMonstrum.getLoot().isEmpty()) {
            appendTextZpravy(ZPRAVA_ZADNY_LOOT,DEFAULT_BARVA_TXT);
        } else {
            int kostka;
            for (LootItem loot : soucMonstrum.getLoot()) {  //pro každý item v loot table
                kostka = RNG.cisloMezi(0, 100); //číslo mezi 0 a 100
                if (kostka <= loot.getSance()) {    //pokud hráč hodil méně, než je šance, tak dostane item
                    soucHrac.pridejItem(loot);      //je to matoucí pro větší zábavu
                    appendTextZpravy(ZPRAVA_LOOT + loot.item.getJmeno(),LOOT_BARVA_TXT);
                }
            }
        }
    }

    private Monstrum vygenerujSoucMonstrum(ArrayList<Potycka> dostupnePotycky) {    //spawne monstrum
        Monstrum monstrum = null;
        if (dostupnePotycky.isEmpty()) {    //pokud není žádné monstrum, tak nic nedělej

        } else if (dostupnePotycky.size() == 1) {   //pokud můžeš spawnout jen jedno, tak ho spawni
            monstrum = MonstrumFactory.getMonstrum(dostupnePotycky.stream().findFirst().get().getMonstrum().getId());
        } else {
            int kolektivniSance = dostupnePotycky.stream().mapToInt(p -> p.getSance()).sum();
            int random = RNG.cisloMezi(0, kolektivniSance - 1);
            int sectenaSance = 0;
            for (Potycka potycka : dostupnePotycky) {   //pokud můžeš spawnou víc monster, tak se rozhodni, které
                sectenaSance = sectenaSance + potycka.getSance();   //opět vygenerujeme random číslo, a pokud je šance monstra na spawn
                if (random < sectenaSance) {        //větší, než číslo, tak se spawne, pokud ne, tak se k šanci přičte šance na spawn
                    monstrum = MonstrumFactory.getMonstrum(potycka.getMonstrum().getId());  //dalšího monstra, dokud se nějaké nespawne
                    break;
                }
            }
        }
        if (monstrum != null) { //pokud existuje monstrum ke spawnutí, tak musíme updatovat GUI prvky a přidat listener
            appendTextZpravy(ZPRAVA_NOVE_MONSTRUM + monstrum.getJmeno(),DEFAULT_BARVA_TXT);
            lblMonstrumHP.setText(TEXT_MONSTRUM_HP_LBL + String.valueOf(monstrum.getSoucHP()));
            monstrum.addPropertyChangeListener(Zivocich.PC_SOUCHP, e -> lblMonstrumHP.setText(TEXT_MONSTRUM_HP_LBL
                    + (String) String.valueOf(e.getNewValue())));
        }
        updateMonstrum(monstrum);
        return monstrum;
    }

    private void updateMonstrum(Monstrum monstrum) {    //fce pro update GUI monstra
        if (monstrum == null) {
            viditelnostLblMonstrum(false);
        } else if (soucMonstrum == null || !soucMonstrum.getJmeno().equals(monstrum.getJmeno())) {
            lblMonstrum.setText(monstrum.getJmeno());
            lblObrMonstrum.setIcon(new ImageIcon(monstrum.getJmenoObrazku()));
            if (lblMonstrum.isVisible() == false) {
                viditelnostLblMonstrum(true);
            }
        }
    }

    private void viditelnostLblMonstrum(boolean viditelny) {    
        lblMonstrum.setVisible(viditelny);
        lblObrMonstrum.setVisible(viditelny);
        lblMonstrumHP.setVisible(viditelny);
    }

    private void updateLokace() {       //při přesunu do nové lokace
        lblLokace.setText(soucLokace.getJmeno());
        lblPopisLokace.setText(soucLokace.getPopis());
        lblObrLokace.setIcon(new ImageIcon(soucLokace.getJmenoObrazku()));
        soucMonstrum = vygenerujSoucMonstrum(soucLokace.getDostupnePotycky());
        tlacitkaLokace();
        if (soucMonstrum != null) {
            comboBoxBtnViditelnost(cboZbran, btnZbran, true);
        } else {
            comboBoxBtnViditelnost(cboZbran, btnZbran, false);
        }
        prijmutiUkolu();
    }

    private void presunLokace(int x, int y) {   //slouží k přesunu do jiné lokace
        soucLokace = lokaceOdSoucLokace(x, y);
        updateLokace();
        jeMozneOdevzdat();  //volá kontrolu, zda není možné odevzdat úkol
        Ukol ukol = soucHrac.getPrijateUkoly().stream().findFirst().orElse(null);
        if (ukol != null && ukol.isSplnenaKriteria() && soucLokace.jsouShodne(ukol.getLokaceOdevzdani())) {
            odevzdaniUkolu(ukol);   //pokud jsme v lokaci, kde se má úkol odevzdat a můžeme odevzdat, tak odevzdáme
        }
    }

    private void prijmutiUkolu() {
        if (!soucLokace.getDostupneUkoly().isEmpty()) {
            for (Ukol ukol : soucLokace.getDostupneUkoly()) {   //přijmi všechny úkoly v lokaci
                ukol.setLokaceOdevzdani(soucLokace);
                soucHrac.prijmiUkol(ukol);
                appendTextZpravy(ZPRAVA_PRIJATI_UKOLU + ukol.getJmeno() + "\n" + ukol.getPopis(),QUEST_BARVA_TXT);
            }
            soucLokace.getDostupneUkoly().clear();
            zmenaPrijatych();
        }
    }

    private void tlacitkaLokace() { //jednotná fce pro zajištění, že jsou viditelná jen správná tlačítka, kde je nějaká lokace
        viditelnostTlacitkaLokace(0, 1, btnSev);    //předává se y a x offset od současné lokace
        viditelnostTlacitkaLokace(0, -1, btnJih);
        viditelnostTlacitkaLokace(1, 0, btnVych);
        viditelnostTlacitkaLokace(-1, 0, btnZap);
    }

    private void viditelnostTlacitkaLokace(int x, int y, JButton tlacitko) {
        if (lokaceOdSoucLokace(x, y) == null) {
            tlacitko.setVisible(false);
        } else {
            tlacitko.setVisible(true);
        }
    }

    private Lokace lokaceOdSoucLokace(int x, int y) {   //zadám offset, dostanu lokaci
        return LokaceFactory.getLokace(soucLokace.getxSourad() + x, soucLokace.getySourad() + y);
    }
    
    private void appendTextZpravy(String zprava, Color barva) {  //pro vypisování do tpZpravy
        StyledDocument doc = tpZpravy.getStyledDocument();
        Style style = tpZpravy.addStyle("styl pro tpZpravy", null);
        StyleConstants.setForeground(style, barva);
        try {
            doc.insertString(doc.getLength(), zprava + "\n", style);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void oknoNastaveni() {   //zavolá okno nastavení (změny jména a povolání)
        Nastaveni nastaveni = new Nastaveni(soucHrac);
        nastaveni.setVisible(true);
    }

    private void oknoPopisGUI(){    //zavolá okno popisu uživaetelského rozhraní
        JuklRPG.infoBox(GUI_POPIS, GUI_TITULEK);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblJmeno = new javax.swing.JLabel();
        lblPovolani = new javax.swing.JLabel();
        lblHP = new javax.swing.JLabel();
        lblMana = new javax.swing.JLabel();
        lblZlato = new javax.swing.JLabel();
        lblXP = new javax.swing.JLabel();
        lblLevel = new javax.swing.JLabel();
        lblIntel = new javax.swing.JLabel();
        lblSila = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgInventar = new javax.swing.JTable(){
            public String getToolTipText(MouseEvent e) {
                String tooltip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tooltip = soucHrac.getInventar().get(rowIndex).item.getPopis();
                }finally{
                    return tooltip;
                }
            }
        };
        jScrollPane5 = new javax.swing.JScrollPane();
        dgPrijateUkoly = new javax.swing.JTable(){
            public String getToolTipText(MouseEvent e) {
                String tooltip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tooltip = soucHrac.getPrijateUkoly().get(rowIndex).getPopis();
                } finally{
                    return tooltip;
                }
            }
        };
        JScrollPane7 = new javax.swing.JScrollPane();
        dgSplneneUkoly = new javax.swing.JTable(){
            public String getToolTipText(MouseEvent e) {
                String tooltip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tooltip = soucHrac.getSplneneUkoly().get(rowIndex).getPopis();
                } finally{
                    return tooltip;
                }
            }
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        dgKouzla = new javax.swing.JTable(){
            public String getToolTipText(MouseEvent e) {
                String tooltip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tooltip = soucHrac.getInventar().stream().filter(i ->
                        i.item.getJmeno() == getValueAt(rowIndex, 0))
                    .findFirst().orElse(null).item.getPopis();
                } finally{
                    return tooltip;
                }
            }
        };
        jPanel2 = new javax.swing.JPanel();
        spZpravy = new javax.swing.JScrollPane();
        tpZpravy = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        lblLokace = new javax.swing.JLabel();
        lblPopisLokace = new javax.swing.JLabel();
        lblObrLokace = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblMonstrum = new javax.swing.JLabel();
        lblObrMonstrum = new javax.swing.JLabel();
        lblMonstrumHP = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnSev = new javax.swing.JButton();
        btnVych = new javax.swing.JButton();
        btnJih = new javax.swing.JButton();
        btnZap = new javax.swing.JButton();
        cboZbran = new javax.swing.JComboBox<>();
        cboKouzlo = new javax.swing.JComboBox<>();
        cboKonzum = new javax.swing.JComboBox<>();
        btnZbran = new javax.swing.JButton();
        btnKouzlo = new javax.swing.JButton();
        btnKonzum = new javax.swing.JButton();
        cbTarget = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hlavní okno");

        jPanel1.setBackground(new java.awt.Color(140, 240, 240));

        lblJmeno.setText(soucHrac.getJmeno());

        lblPovolani.setText(String.valueOf(soucHrac.getPovolani()));

        lblHP.setText(String.valueOf(soucHrac.getSoucHP()));

        lblMana.setText(String.valueOf(soucHrac.getSoucMana()));

        lblZlato.setText(String.valueOf(soucHrac.getZlato()));

        lblXP.setText(String.valueOf(soucHrac.getXp()));

        lblLevel.setText(String.valueOf(soucHrac.getLevel()));

        lblIntel.setText(String.valueOf(soucHrac.getIntelekt()));

        lblSila.setText(String.valueOf(soucHrac.getSila()));

        jLabel2.setText("Povolání:");

        jLabel3.setText("HP:");

        jLabel4.setText("Mana:");

        jLabel5.setText("Zlato:");

        jLabel6.setText("XP:");

        jLabel7.setText("Level:");

        jLabel8.setText("Inteligence:");

        jLabel9.setText("Síla:");

        jLabel1.setText("Jméno:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPovolani)
                    .addComponent(lblHP)
                    .addComponent(lblMana)
                    .addComponent(lblZlato)
                    .addComponent(lblXP)
                    .addComponent(lblLevel)
                    .addComponent(lblIntel)
                    .addComponent(lblSila)
                    .addComponent(lblJmeno))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblJmeno))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPovolani)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHP)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMana)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblZlato)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXP)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLevel)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIntel)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(lblSila))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dgInventar.setModel(new InventarTableModel(new ArrayList<MnozstviItemu>())
        );
        jScrollPane1.setViewportView(dgInventar);

        jTabbedPane1.addTab("Inventář", jScrollPane1);

        dgPrijateUkoly.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane5.setViewportView(dgPrijateUkoly);

        jTabbedPane1.addTab("Přijaté", jScrollPane5);

        dgSplneneUkoly.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        JScrollPane7.setViewportView(dgSplneneUkoly);

        jTabbedPane1.addTab("Splněné", JScrollPane7);

        dgKouzla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane2.setViewportView(dgKouzla);

        jTabbedPane1.addTab("Kouzla", jScrollPane2);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        tpZpravy.setEditable(false);
        tpZpravy.setBackground(new java.awt.Color(0, 0, 0));
        tpZpravy.setBorder(BorderFactory.createCompoundBorder(
            tpZpravy.getBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    spZpravy.setViewportView(tpZpravy);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(spZpravy, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(spZpravy)
            .addContainerGap())
    );

    jPanel3.setBackground(new java.awt.Color(140, 140, 240));
    jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    lblLokace.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblLokace.setText(String.valueOf(soucLokace.getJmeno()));
    jPanel3.add(lblLokace, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 303, -1));

    lblPopisLokace.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblPopisLokace.setText(String.valueOf(soucLokace.getPopis()));
    jPanel3.add(lblPopisLokace, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 231, 303, 36));

    lblObrLokace.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblObrLokace.setText(null);
    lblObrLokace.setPreferredSize(new java.awt.Dimension(150, 150));
    jPanel3.add(lblObrLokace, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 63, 303, -1));

    jPanel4.setBackground(new java.awt.Color(240, 240, 140));
    jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    lblMonstrum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblMonstrum.setText("a");
    jPanel4.add(lblMonstrum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 303, -1));

    lblObrMonstrum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblObrMonstrum.setText(null);
    lblObrMonstrum.setMaximumSize(new java.awt.Dimension(150, 150));
    lblObrMonstrum.setMinimumSize(new java.awt.Dimension(150, 150));
    lblObrMonstrum.setPreferredSize(new java.awt.Dimension(150, 150));
    jPanel4.add(lblObrMonstrum, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, 303, -1));
    lblObrMonstrum.setIcon(new ImageIcon(soucLokace.getJmenoObrazku()));

    lblMonstrumHP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblMonstrumHP.setText("t");
    jPanel4.add(lblMonstrumHP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 199, 303, -1));

    jPanel5.setBackground(new java.awt.Color(240, 140, 240));
    jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    btnSev.setText("Sever");
    btnSev.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSevActionPerformed(evt);
        }
    });
    jPanel5.add(btnSev, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 73, -1));

    btnVych.setText("Východ");
    btnVych.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnVychActionPerformed(evt);
        }
    });
    jPanel5.add(btnVych, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, 73, -1));

    btnJih.setText("Jih");
    btnJih.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnJihActionPerformed(evt);
        }
    });
    jPanel5.add(btnJih, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 73, -1));

    btnZap.setText("Západ");
    btnZap.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnZapActionPerformed(evt);
        }
    });
    jPanel5.add(btnZap, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 73, -1));

    cboZbran.setModel(new javax.swing.DefaultComboBoxModel<>(new MnozstviItemu[] { }));
    cboZbran.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            cboZbranPropertyChange(evt);
        }
    });
    jPanel5.add(cboZbran, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 61, 130, -1));

    cboKouzlo.setModel(new javax.swing.DefaultComboBoxModel<>(new MnozstviItemu[] { }));
    jPanel5.add(cboKouzlo, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 102, 130, -1));

    cboKonzum.setModel(new javax.swing.DefaultComboBoxModel<>(new MnozstviItemu[] {}));
    jPanel5.add(cboKonzum, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 143, 130, -1));

    btnZbran.setText("Udeř");
    btnZbran.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnZbranActionPerformed(evt);
        }
    });
    jPanel5.add(btnZbran, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 100, -1));

    btnKouzlo.setText("Vykouzli");
    btnKouzlo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnKouzloActionPerformed(evt);
        }
    });
    jPanel5.add(btnKouzlo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 100, -1));

    btnKonzum.setText("Použij");
    btnKonzum.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnKonzumActionPerformed(evt);
        }
    });
    jPanel5.add(btnKonzum, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 100, -1));

    cbTarget.setText("target sebe");
    cbTarget.setToolTipText("Určuje, zda se kouzlo vykouzlí na Vás (zaškrtnuté), nebo na nepřítele (nezaškrtuné)");
    jPanel5.add(cbTarget, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 100, -1));

    jMenu1.setText("Nastavení");
    jMenu1.setMargin(new java.awt.Insets(0, 10, 0, 2));
    jMenu1.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
        public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
            jMenu1MenuKeyPressed(evt);
        }
        public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
        }
        public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
        }
    });
    jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jMenu1MouseClicked(evt);
        }
    });
    jMenu1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenu1ActionPerformed(evt);
        }
    });
    jMenuBar1.add(jMenu1);

    jMenu2.setText("Popis GUI");
    jMenu2.setMargin(new java.awt.Insets(0, 10, 0, 2));
    jMenu2.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
        public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
            jMenu2MenuKeyPressed(evt);
        }
        public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
        }
        public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
        }
    });
    jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jMenu2MouseClicked(evt);
        }
    });
    jMenu2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenu2ActionPerformed(evt);
        }
    });
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnZapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZapActionPerformed
        presunLokace(-1, 0);
    }//GEN-LAST:event_btnZapActionPerformed

    private void btnJihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJihActionPerformed
        presunLokace(0, -1);
    }//GEN-LAST:event_btnJihActionPerformed

    private void btnVychActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVychActionPerformed
        presunLokace(1, 0);
    }//GEN-LAST:event_btnVychActionPerformed

    private void btnSevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSevActionPerformed
        presunLokace(0, 1);
    }//GEN-LAST:event_btnSevActionPerformed

    private void btnKonzumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonzumActionPerformed
        MnozstviItemu soucKonzum = (MnozstviItemu) cboKonzum.getSelectedItem();
        appendTextZpravy(soucKonzum.item.getAkce().proved(soucHrac, soucHrac),DEFAULT_BARVA_TXT);
        soucHrac.odeberItem(soucKonzum);
        if (soucHrac.isMrtvy()) {
            smrtHrace();
        }
    }//GEN-LAST:event_btnKonzumActionPerformed

    private void btnZbranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZbranActionPerformed
        soucHrac.setSoucZbran(((MnozstviItemu) cboZbran.getSelectedItem()).item);
        appendTextZpravy(soucHrac.getSoucZbran().getAkce().proved(soucHrac, soucMonstrum),DEFAULT_BARVA_TXT);
        if (!soucMonstrum.isMrtvy()) {
            tahNepritele();
        } else {
            smrtMonstra();
        }

    }//GEN-LAST:event_btnZbranActionPerformed

    private void cboZbranPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboZbranPropertyChange

    }//GEN-LAST:event_cboZbranPropertyChange

    private void btnKouzloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKouzloActionPerformed
        Item soucKouzlo = ((MnozstviItemu) cboKouzlo.getSelectedItem()).item;
        if (soucHrac.jeDostMany(soucKouzlo.getAkce().cenaProvedeni())) {    //mám dost many?
            Zivocich target;
            if (cbTarget.isSelected()) {    //jaký target je vybraný?
                target = soucHrac;
                appendTextZpravy(soucKouzlo.getAkce().proved(soucHrac, target),DEFAULT_BARVA_TXT);
                if (soucMonstrum != null && soucMonstrum.isMrtvy() == false) {
                    if (!soucHrac.isMrtvy()) {
                        tahNepritele();
                    }
                }
                if (soucHrac.isMrtvy()) {
                    smrtHrace();
                }
            } else {
                target = soucMonstrum;
                if (target == null) {
                    appendTextZpravy(ZPRAVA_BEZ_MONSTRA,DEFAULT_BARVA_TXT);
                    return;
                }
                appendTextZpravy(soucKouzlo.getAkce().proved(soucHrac, target),DEFAULT_BARVA_TXT);
                if (!soucMonstrum.isMrtvy()) {
                    tahNepritele(); //když přežilo, tak musí táhnout monstrum
                } else {
                    smrtMonstra();
                }
            }
        } else {
            appendTextZpravy(ZPRAVA_BEZ_MANY + soucKouzlo.getJmeno(),KRITICKA_BARVA_TXT);
        }
    }//GEN-LAST:event_btnKouzloActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu1MenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_jMenu1MenuKeyPressed

    }//GEN-LAST:event_jMenu1MenuKeyPressed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        oknoNastaveni();
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenu2MenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_jMenu2MenuKeyPressed
        
    }//GEN-LAST:event_jMenu2MenuKeyPressed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        oknoPopisGUI();
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPane7;
    private javax.swing.JButton btnJih;
    private javax.swing.JButton btnKonzum;
    private javax.swing.JButton btnKouzlo;
    private javax.swing.JButton btnSev;
    private javax.swing.JButton btnVych;
    private javax.swing.JButton btnZap;
    private javax.swing.JButton btnZbran;
    private javax.swing.JCheckBox cbTarget;
    private javax.swing.JComboBox<MnozstviItemu> cboKonzum;
    private javax.swing.JComboBox<MnozstviItemu> cboKouzlo;
    private javax.swing.JComboBox<MnozstviItemu> cboZbran;
    private javax.swing.JTable dgInventar;
    private javax.swing.JTable dgKouzla;
    private javax.swing.JTable dgPrijateUkoly;
    private javax.swing.JTable dgSplneneUkoly;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblHP;
    private javax.swing.JLabel lblIntel;
    private javax.swing.JLabel lblJmeno;
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblLokace;
    private javax.swing.JLabel lblMana;
    private javax.swing.JLabel lblMonstrum;
    private javax.swing.JLabel lblMonstrumHP;
    private javax.swing.JLabel lblObrLokace;
    private javax.swing.JLabel lblObrMonstrum;
    private javax.swing.JLabel lblPopisLokace;
    private javax.swing.JLabel lblPovolani;
    private javax.swing.JLabel lblSila;
    private javax.swing.JLabel lblXP;
    private javax.swing.JLabel lblZlato;
    private javax.swing.JScrollPane spZpravy;
    private javax.swing.JTextPane tpZpravy;
    // End of variables declaration//GEN-END:variables
}
