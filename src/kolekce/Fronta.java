package kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author
 */
public class Fronta<E> implements IFronta<E> {

    private Node<E> first;
    private Node<E> last;
    private int pocet;

    public Fronta() {
        this.pocet = 0;
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
    public void vloz(E data) throws KolekceException {
        Node n = new Node(data);
        if (getPocet() == 0) {
            this.first = n;
            this.last = n;
        } else {
            this.last = n;
            this.last.next = n;
        }
        pocet++;
    }

    @Override
    public E odeber() throws KolekceException {
        if (getPocet() == 0) {
            throw new KolekceException("Fronta je prázdná");
        }
        
        E value = first.value;
        first = first.next;
        pocet--;
        return value;
    }

    @Override
    public void zrus() {
        this.first = null;
        this.last = null;
        pocet = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    /**
     * 79. Vnitrni reprezentace prvku.
     */
    private class Node<E> {

        private E value;
        private Node next;

        public Node(E value) {
            this.value = value;
        }
    }

    private class MyIterator implements Iterator<E> {

        private Node<E> nextNode;

        public MyIterator() {
            nextNode = first;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E res = nextNode.value;
            nextNode = nextNode.next;
            return res;
        }
    }

}
