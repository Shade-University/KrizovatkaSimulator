package krizovatka;

import casovani.Casovac;
import java.util.function.Consumer;
import kolekce.KolekceException;
import krizovatka.IKrizovatka.SmerPrujezdu;

/**
 *
 * @author Tomáš Vondra
 */
public class RadicSemafor {

    private long casSeverJih, casVychodZapad;
    private long cas;
    private SmerPrujezdu smer;
    private final RadicOdjezdu radicSeverJih;
    private final RadicOdjezdu radicVychodZapad;
    private boolean runFirstTime; //Trigger consumera při startu

    private volatile boolean running;
    private Consumer<SmerPrujezdu> message;

    public RadicSemafor(long casSeverJih, long casVychodZapad, RadicOdjezdu severJih,
            RadicOdjezdu vychodZapad) throws KolekceException {
        this.casSeverJih = casSeverJih;
        this.casVychodZapad = casVychodZapad;
        this.radicSeverJih = severJih;
        this.radicVychodZapad = vychodZapad;
        this.smer = SmerPrujezdu.SEVER_JIH;

        this.start();
        runFirstTime = true;

        Casovac.instance().pridej(() -> {
            cas += Casovac.PERIODA;
            if (running) {
                if(runFirstTime){
                    if(message != null){
                        message.accept(smer);
                    }
                    runFirstTime = false;
                }
                switch (smer) {
                    case SEVER_JIH:
                        if (cas >= this.casSeverJih) {
                            radicSeverJih.stop();
                            cas = 0;
                            smer = SmerPrujezdu.VYCHOD_ZAPAD;
                            radicVychodZapad.start();

                            if (message != null) {
                                message.accept(smer);
                            }
                        }
                        break;
                    case VYCHOD_ZAPAD:
                        if (cas >= this.casVychodZapad) {
                            radicVychodZapad.stop();
                            cas = 0;
                            smer = SmerPrujezdu.SEVER_JIH;
                            radicSeverJih.start();

                            if (message != null) {
                                message.accept(smer);
                            }
                        }
                        break;
                }
            }
        });
    }

    public synchronized long getCasSeverJih() {
        return casSeverJih;
    }

    public synchronized void setCasSeverJih(long casSeverJih) {
        this.casSeverJih = casSeverJih;
    }

    public synchronized long getCasVychodZapad() {
        return casVychodZapad;
    }

    public synchronized void setCasVychodZapad(long casVychodZapad) {
        this.casVychodZapad = casVychodZapad;
    }

    public synchronized void setHlaseni(Consumer<SmerPrujezdu> hlaseni) {
        message = hlaseni;
    }

    public synchronized void start() {

        if (smer == SmerPrujezdu.SEVER_JIH) {
            radicSeverJih.start();
        } else if (smer == SmerPrujezdu.VYCHOD_ZAPAD) {
            radicVychodZapad.start();
        }
        cas = 0;
        running = true;
    }

    public synchronized void stop() {
        running = false;
    }

}
