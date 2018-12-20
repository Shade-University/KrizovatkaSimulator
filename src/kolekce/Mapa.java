package kolekce;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 *
 * @author Tomáš Vondra
 */
public class Mapa<K, E> implements IMapa<K, E> {
    
    //TODO Resize, trim atd
    private int size = 0;
    private int DEFAULT_CAPACITY = 10;
    @SuppressWarnings("unchecked")
    private MyEntry<K, E>[] values = new MyEntry[DEFAULT_CAPACITY];
    
    public int getSize(){
        return size;
    }
    
    @Override
    public void vloz(K klic, E data) throws KolekceException {
        if(size + 1 > DEFAULT_CAPACITY)
            throw new KolekceException("Mapa má plnou kapacitu");
        
        values[size] = new MyEntry<>(klic,data);
        size++;
    }

    @Override
    public E dej(K klic) {
        for (int i = 0; i < size; i++) {
            if (values[i] != null) {
                if (values[i].getKey().equals(klic)) {
                    return values[i].getValue();
                }
            }
        }
        return null;
    }

    @Override
    public E odeber(K klic) throws KolekceException {
        if(size == 0)
            throw new KolekceException("Mapa je prázdná");
        
        for (int i = 0; i < size; i++) {
            if(values[i].getKey() == klic){
                E value = values[i].getValue();
                for (int j = i; j < size; j++) {
                    values[j] = values[j + 1];
                }
                values[size - 1] = null;
                size--;
                return value;
            }
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class MyEntry<K, V> {

        private final K key;
        private V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

}
