package kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 *
 * @author Tomáš Vondra
 */
public class Seznam<E> implements IKolekce<E> {

    //Prvky
    private Prvek prvni;
    private Prvek posledni;
    private Prvek aktualni;

    private class Prvek {

        Prvek dalsi;
        E data;

        public Prvek(E data) {
            this(null, data); //TODO nějak změnit ?
        }

        public Prvek(Prvek dalsi, E data) {
            this.dalsi = dalsi;
            this.data = data;
        }
    }

    //Vlastnosti
    private int velikost;
    private int pocet;

    //Konstruktory
    public Seznam() throws KolekceException {
        this(Integer.MAX_VALUE);
    }

    public Seznam(int velikost) throws KolekceException {

        if (velikost <= 0) {
            throw new KolekceException("Velikost kolekce je nulová nebo záporná");
        }

        this.velikost = velikost;
    }

    //Gettery
    @Override
    public int getVelikost() {
        return velikost;
    }

    @Override
    public int getPocet() {
        return pocet;
    }

    @Override
    public boolean jePrazdny() {
        return pocet == 0;
    }

    @Override
    public boolean jePlny() {
        return pocet == velikost;
    }

    //private metody
    private void reset() {
        prvni = null;
        posledni = null;
        pocet = 0;
    }

    private Prvek najdiPredposledni() {
        Prvek p = prvni;
        while (p.dalsi.dalsi != null) {
            p = p.dalsi;
        }
        return p.dalsi;
    } //Koukáme o dva kroky dopředu

    private Prvek najdiPredchozi(Prvek p) throws KolekceException {
        Prvek predchozi = p;
        while (predchozi.dalsi != null) {
            if (predchozi.dalsi == aktualni) {
                return predchozi;
            }
            predchozi = predchozi.dalsi;
        }

        throw new KolekceException("Prvek nebyl nalezen");
    }

    //public metody
    @Override
    public void pridej(E data) throws KolekceException {
        if (data == null) {
            throw new KolekceException("Nebyla vložena data"); //TODO Potřebuju to zde ?
        }
        if (pocet >= velikost) {
            throw new KolekceException("Byla překročena velikost");
        }

        Prvek p = new Prvek(data);
        if (prvni == null) {
            prvni = p;
            posledni = p;
            pocet++;
            return;
        } //Pokud je seznam prázdný, nastavíme první element

        posledni.dalsi = p;
        posledni = p;
        pocet++;
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if (prvni == null) {
            throw new KolekceException("Seznam je prázdný");
        }

        Prvek p = prvni;
        if (prvni == posledni) {
            reset();
            return p.data;
        } //V seznamu zbyl poslední prvek

        prvni = p.dalsi;
        pocet--;
        return p.data;
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        if (posledni == null) {
            throw new KolekceException("Kolekce je prázdná");
        }

        Prvek p = posledni;
        if (prvni == posledni) {
            reset();
            return p.data;
        } //V seznamu zbyl poslední prvek

        posledni = najdiPredposledni();
        pocet--;
        return p.data;
    }

    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        pocet = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        Object[] result = new Object[pocet];
        int i = 0;
        for (Prvek p = prvni; p != null; p = p.dalsi) {
            result[i++] = p.data;
        }
        return (E[]) result;
    }

    @Override
    public E[] toArray(E[] array) throws IllegalArgumentException {
        if (array.length < pocet) {
            throw new IllegalArgumentException();
        }
        
        int i = 0;
        for (Prvek p = prvni; p != null; p = p.dalsi) {
            array[i++] = p.data;
        }

        if (array.length > pocet) {
            array[pocet] = null;
        } //Poslední prvek zakončí null

        return array;
    }

    @Override
    public E[] toArray(Function<Integer, E[]> createFunction) {
        return createFunction.apply(pocet);
    }

    @Override
    public Iterator<E> iterator() {
        return new mujIterator(); //TODO Může být anonymní třída
    }

    //Nepovinné
    @Override
    public void nastavPrvni() throws KolekceException {
        if (prvni == null) {
            throw new KolekceException("Kolekce je prázdná");
        }

        aktualni = prvni;
    }

    @Override
    public void prejdiNaDalsi() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Není nastaven aktuální prvek");
        }

        aktualni = aktualni.dalsi;
    }

    @Override
    public E zpristupni() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Není nastaven aktuální prvek");
        }

        return aktualni.data;
    }

    public boolean jeDalsi() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Není nastaven aktuální prvek");
        }

        return aktualni.dalsi != null;
    }

    @Override
    public E odeber() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Není nastaven aktuální prvek");
        }

        Prvek p = aktualni;

        aktualni = najdiPredchozi(aktualni);
        pocet--;
        return p.data;
    }

    private class mujIterator implements Iterator<E> {

        Prvek index = prvni;

        @Override
        public boolean hasNext() {
            return index != null;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E data = index.data;
                index = index.dalsi;
                return data;
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            if (index == null || aktualni == null) {
                throw new IllegalStateException();
            }
            if (aktualni == prvni) {
                prvni = index;
            }
            if (aktualni == posledni) {
                prvni = null;
            } //TODO ????
        }

    }

}
