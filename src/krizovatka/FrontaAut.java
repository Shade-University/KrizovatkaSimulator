package krizovatka;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kolekce.Fronta;
import kolekce.KolekceException;
import krizovatka.IKrizovatka.Smer;

/**
 *
 * @author Tomáš Vondra
 */
public class FrontaAut extends Fronta<Auto> {

    private Consumer<Auto> hlaseniPrijezdu;
    private Consumer<Auto> hlaseniOdjezdu;
    
    private Smer smer;

    public FrontaAut(Smer smer) throws KolekceException {
        super();
        this.smer = smer;
    }

    public void setHlaseniPrijezdu(Consumer<Auto> hlaseniPrijezdu) {
        this.hlaseniPrijezdu = hlaseniPrijezdu;
    }

    public void setHlaseniOdjezdu(Consumer<Auto> hlaseniOdjezdu) {
        this.hlaseniOdjezdu = hlaseniOdjezdu;
    }

    @Override
    public void vloz(Auto auto) {
        try {
            super.vloz(auto);
            if (hlaseniPrijezdu != null) {
                hlaseniPrijezdu.accept(auto);
            }
        } catch (KolekceException ex) {
            Logger.getLogger(FrontaAut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Auto odeber() {
        Auto auto = null;
        try {
            auto = super.odeber();
            if (hlaseniOdjezdu != null) {
            hlaseniOdjezdu.accept(auto);
        }
        } catch (KolekceException ex) {
            Logger.getLogger(FrontaAut.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return auto;
    }

}
