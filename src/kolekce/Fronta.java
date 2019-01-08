
package kolekce;

import java.util.Iterator;

/**
 *
 * @author Tomáš Vondra
 */
public class Fronta<E> implements IFronta<E> {

    private final IKolekce<E> seznam;
    
    public Fronta() throws KolekceException {
        this.seznam = new Seznam();
    }
    @Override
    public int getPocet() {
        return seznam.getPocet();
    }

    @Override
    public boolean jePrazdny() {
        return seznam.jePrazdny();
    }

    @Override
    public void vloz(E data) throws KolekceException {
        seznam.pridej(data);
    }

    @Override
    public E odeber() throws KolekceException {
        return seznam.odeberPrvni();
    }

    @Override
    public void zrus() {
        seznam.zrus();
    }

    @Override
    public Iterator<E> iterator() {
        return seznam.iterator();
    }

}
