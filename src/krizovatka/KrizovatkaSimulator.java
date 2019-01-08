
package krizovatka;

import casovani.Casovac;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.IFronta;
import kolekce.IMapa;
import kolekce.KolekceException;
import kolekce.Mapa;

/**
 *
 * @author 
 */
public class KrizovatkaSimulator implements IKrizovatka{
    private final long DEFAULTNI_CETNOST = 30; //Četnost vozidel za minut
    private final long DEFAULTNI_DOBA_PRUJEZDU = 500;
    private final long DEFAULTNI_DOBA_SEMAFORU = 5000; //Defaultní hodnoty

    IMapa<Smer, FrontaAut> fronty;
    IMapa<SmerPrujezdu, RadicOdjezdu> radiceOdjezdu;
    IMapa<Smer, RadicPrijezdu> radicePrijezdu;
    
    RadicSemafor semafor;
    
    public KrizovatkaSimulator() throws KolekceException{
        fronty = new Mapa();
        radiceOdjezdu = new Mapa();
        radicePrijezdu = new Mapa();      
        naplnMapy();  
        
        semafor = new RadicSemafor(DEFAULTNI_DOBA_SEMAFORU,
                DEFAULTNI_DOBA_SEMAFORU,
                radiceOdjezdu.dej(SmerPrujezdu.SEVER_JIH),
                radiceOdjezdu.dej(SmerPrujezdu.VYCHOD_ZAPAD));
    }
    
    private void naplnMapy() throws KolekceException{
        for (Smer smer : Smer.values()) {
            FrontaAut fronta = new FrontaAut(smer);
            fronty.vloz(smer, fronta);
            radicePrijezdu.vloz(smer,new RadicPrijezdu(DEFAULTNI_CETNOST, fronta));
        }
        
        radiceOdjezdu.vloz(SmerPrujezdu.SEVER_JIH,
                new RadicOdjezdu(DEFAULTNI_DOBA_PRUJEZDU, SmerPrujezdu.SEVER_JIH,
                        fronty.dej(Smer.SEVER), fronty.dej(Smer.JIH)));
        radiceOdjezdu.vloz(SmerPrujezdu.VYCHOD_ZAPAD,
                new RadicOdjezdu(DEFAULTNI_DOBA_PRUJEZDU, SmerPrujezdu.VYCHOD_ZAPAD,
                        fronty.dej(Smer.VYCHOD), fronty.dej(Smer.ZAPAD)));
    }
    
    
    @Override
    public void setSemaforDobaZelena(SmerPrujezdu prujezd, long x) {
        switch (prujezd){
            case SEVER_JIH:
                semafor.setCasSeverJih(x);
                break;
            case VYCHOD_ZAPAD:
                semafor.setCasVychodZapad(x);
                break;
        }
    }

    @Override
    public long getSemaforDobaZelena(SmerPrujezdu prujezd) {
        long output = 0;
        switch (prujezd){
            case SEVER_JIH:
                output = semafor.getCasSeverJih();
                break;
            case VYCHOD_ZAPAD:
                output = semafor.getCasVychodZapad();
                break;
        }
        return output;
    }

    @Override
    public void setCetnostPrijezdu(Smer prijezd, long cetnostA) {
        radicePrijezdu.dej(prijezd).setCetnost(cetnostA);
    }

    @Override
    public long getCetnostPrijezdu(Smer prijezd) {
        return radicePrijezdu.dej(prijezd).getCetnost();
    }

    @Override
    public void setDobaPrujezdu(long s) {
        for (RadicOdjezdu radicOdjezdu : radiceOdjezdu) {
            radicOdjezdu.setDobaPrujezdu(s);
        }
    }

    @Override
    public long getDobaPrujezdu() {
        return radiceOdjezdu.dej(SmerPrujezdu.SEVER_JIH).getDobaPrujezdu();
    }

    @Override
    public int getPocetCekajicichZeSmeru(Smer smer) {
        return fronty.dej(smer).getPocet();
    }

    @Override
    public IFronta<Auto> getFrontaSmeru(Smer smer) {
        return fronty.dej(smer);
    }

    @Override
    public void setHlaseniPrijezduZeSmeru(Smer smer, Consumer<Auto> hlaseni) {
        fronty.dej(smer).setHlaseniPrijezdu(hlaseni);
    }

    @Override
    public void setHlaseniOdjezduZeSmeru(Smer smer, Consumer<Auto> hlaseni) {
        fronty.dej(smer).setHlaseniOdjezdu(hlaseni);
    }

    @Override
    public void setHlaseniSemaforu(Consumer<SmerPrujezdu> hlaseni) {
        semafor.setHlaseni(hlaseni);
    }

    @Override
    public void start() {
        try {
            Casovac.instance().start();
        } catch (KolekceException ex) {
            Logger.getLogger(KrizovatkaSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        try {
            Casovac.instance().zrus();
            Casovac.instance().stop(); //Stop kompletně zruší křižovatku. 
        } catch (KolekceException ex) {
            Logger.getLogger(KrizovatkaSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void pause() {
         try {
            Casovac.instance().stop();
        } catch (KolekceException ex) {
            Logger.getLogger(KrizovatkaSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
