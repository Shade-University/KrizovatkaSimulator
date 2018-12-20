package kolekce;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;

/**
 *
 * @author Tomáš Vondra
 */
public class Seznam<E> implements IKolekce<E> {

    private final Integer defaultniVelikost = 10;

    private Object[] pole;
    private Integer maxVelikost;
    private Integer aktualniVelikost;
    private Integer pocetElementu;

    public Seznam() {
        this(Integer.MAX_VALUE);
    }

    public Seznam(Integer velikost) {
        this.maxVelikost = velikost;
        pole = new Object[defaultniVelikost];
        aktualniVelikost = defaultniVelikost;
        pocetElementu = 0;
    }

    private void zvetsiPole() {
        if (aktualniVelikost * 2 <= maxVelikost) {
            Object[] tempPole = new Object[aktualniVelikost * 2];
            for (int i = 0; i < pocetElementu; i++) {
                tempPole[i] = pole[i];
            }
            pole = tempPole;
            aktualniVelikost *= 2;
        }
    } //Pokud nepřesáhneme max velikost, zdvojnásob velikost pole

    private void zmensiPole() {
        if ((aktualniVelikost / 2) > pocetElementu) {
            Object[] tempPole = new Object[aktualniVelikost / 2];
            for (int i = 0; i < pocetElementu; i++) {
                tempPole[i] = pole[i];
            }
            pole = tempPole;
            aktualniVelikost /= 2;
        }
    } //Pokud je pole z poloviny prázdné, zmenši ho
    
    public int getAktualniVelikost(){
        return aktualniVelikost;
    } //Kvůli testu
    
    @Override
    public int getVelikost() {
        return maxVelikost;
    }

    @Override
    public int getPocet() {
        return pocetElementu;
    }

    @Override
    public boolean jePrazdny() {
        return pocetElementu == 0;
    }

    @Override
    public boolean jePlny() {
        return pocetElementu == maxVelikost;
    }

    @Override
    public void pridej(E data) throws KolekceException {
        if ((pocetElementu + 1) > maxVelikost)
            throw new KolekceException("Byla přesažena maximální hodnota");
        
        if ((pocetElementu + 1) >= aktualniVelikost) {
            zvetsiPole();
        }
        pole[pocetElementu] = data;
        pocetElementu++; //Přehlednější
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if(jePrazdny())
            throw new KolekceException("Kolekce je prázdná");
        
        E prvek = (E) pole[0];
        for (int i = 0; i < pocetElementu; i++) {
            pole[i] = pole[i + 1];
        }
        pole[pocetElementu - 1] = null;
        pocetElementu--; //Jde to líp, ale takhle je to přehlednější
        zmensiPole();
        return prvek;
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        if(jePrazdny())
            throw new KolekceException("Kolekce je prázdná");
        
        E prvek = (E) pole[pocetElementu - 1];
        pole[pocetElementu - 1] = null;
        pocetElementu--; //Jde to líp, ale takhle je to přehlednější
        zmensiPole();
        return prvek;
    }

    @Override
    public void zrus() {
        for (int i = 0; i < pocetElementu; i++) {
            pole[i] = null;
        }
        pocetElementu = 0;
    }

    @Override
    public Object[] toArray() {
        return (E[])Arrays.copyOf(pole, pocetElementu); //TODO Jsou toArray správně ?
    }

    @Override
    public E[] toArray(E[] array) throws IllegalArgumentException {
        if (array.length < aktualniVelikost)
            return (E[]) Arrays.copyOf(pole, pocetElementu, array.getClass());
        else{
            throw new IllegalArgumentException();
        }
            
    }

    @Override
    public E[] toArray(Function<Integer, E[]> createFunction) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO Co s tím ??
    }

    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>)Arrays.stream(pole).iterator();
    }

}
