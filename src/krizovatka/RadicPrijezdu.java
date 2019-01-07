package krizovatka;

import casovani.Casovac;
import kolekce.KolekceException;

/**
 *
 * @author Tomáš Vondra
 */
public final class RadicPrijezdu {

    private long interval; //Přepočet četnosti
    private long cetnost;
    private final FrontaAut fronta;
    private long cas;
    
    private volatile boolean running;

    public RadicPrijezdu(long cetnost, FrontaAut fronta) throws KolekceException {
        setCetnost(cetnost);
        this.fronta = fronta;
        start();
        Casovac.instance().pridej(() -> {
            cas += Casovac.PERIODA;
            if (running && cas >= interval) {
                fronta.vloz(new Auto());
                cas = 0;
            }
        });
    }

    public synchronized void start() {
        cas = 0;
        running = true;
    }

    public synchronized void stop() {
        running = false;
    }

    public synchronized FrontaAut getFrontaPrijezdAut() {
        return fronta;
    }

    public void setCetnost(long cetnost) {
        this.cetnost = cetnost;
        interval = vypocitejInterval(cetnost);
    }

    public long getCetnost() {
        return cetnost;
    }

    private long vypocitejInterval(long cetnost) {
        return (60 * 1000 / cetnost);
    }

}
