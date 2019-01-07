package krizovatka;

import casovani.Casovac;
import kolekce.IMapa;
import kolekce.KolekceException;
import kolekce.Mapa;
import krizovatka.IKrizovatka.SmerPrujezdu;

/**
 *
 * @author Tomáš Vondra
 */
public final class RadicOdjezdu {

    private long dobaPrujezdu;
    private final IMapa<SmerPrujezdu, FrontaAut> fronty;
    private long cas;
    private volatile boolean running;

    public RadicOdjezdu(long dobaPrujezdu, SmerPrujezdu smer, FrontaAut... fronty)
            throws KolekceException {
        this.dobaPrujezdu = dobaPrujezdu;
        this.fronty = new Mapa();
        for (FrontaAut frontaAut : fronty) {
            this.fronty.vloz(smer, frontaAut);
        }
        
        stop();
        Casovac.instance().pridej(() -> {
            cas += Casovac.PERIODA;
            if (running && cas >=  dobaPrujezdu) {
                this.fronty.stream().forEach(s -> {
                    if (!s.jePrazdny()) {
                        s.odeber();
                    }
                });
                cas = 0;
            }
        });
    }
    
    public synchronized void start(){
        cas = 0;
        running = true;
    }
    
    public synchronized void stop(){
        running = false;
    }
    
    public synchronized void setDobaPrujezdu(long dobaPrijezdu){
        this.dobaPrujezdu = dobaPrijezdu;
    }
    
    public synchronized long getDobaPrujezdu(){
        return dobaPrujezdu;
    }
}
