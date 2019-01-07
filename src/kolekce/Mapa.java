package kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomáš Vondra
 */
public class Mapa<K, E> implements IMapa<K, E> {

    private final IKolekce<Prvek> seznam;

    private class Prvek<K, E> {

        private final K klic;
        private E data;

        public Prvek(K klic, E data) {
            this.klic = klic;
            this.data = data;
        }

        public K getKlic() {
            return klic;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }
    }

    public Mapa() throws KolekceException {
        seznam = new Seznam();
    }

    @Override
    public void vloz(K klic, E data) throws KolekceException {
        Prvek prvek = new Prvek(klic, data);
        seznam.pridej(prvek);
    }

    @Override
    public E dej(K klic) {
        for (Prvek prvek : seznam) {
            if (prvek.getKlic().equals(klic)) {
                return (E) prvek.getData(); //TODO Proč musíme přetypovat ?
            }
        }

        return null;
    }

    @Override
    public E odeber(K klic) throws KolekceException {
        seznam.nastavPrvni();
        Prvek p = seznam.zpristupni();
        if(p.getKlic().equals(klic)){
            return (E) p.getData();
        }
        while (seznam.jeDalsi()) {
            p = seznam.zpristupni();
            if (p.getKlic().equals(klic)) {
                return (E) p.getData(); // Todo proč přetypováváme ?
            }
            seznam.prejdiNaDalsi();
        }
        
        throw new KolekceException("Prvek není v seznamu");
    }

    @Override
    public Iterator<E> iterator() {

        return new mujIterator();
    }

    private class mujIterator implements Iterator<E> {

        Iterator<Prvek> iterator = seznam.iterator();
        Prvek p;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            if (hasNext()) {
                p = iterator.next();
                return (E)p.getData();
            }
            throw new RuntimeException();
        }

    }
}
