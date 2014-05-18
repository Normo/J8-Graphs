package J8Graphs.util;

import java.util.Arrays;
import J8Graphs.model.Node;

/**
 * Stellt einen binären Heap als Prioritätswarteschlange für Knoten bereit. Als
 * Schlüssel dient vorerst das Distanz-Attribut eines Knotens.
 * @author normo
 *
 */
public class BinaryHeap {

	/**
	 * Anfangskapazität
	 */
	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Array, das die Heapstruktur abbildet.
	 */
	public Node[] heap;

	/**
	 * Anzahl der Elemente, die im Heap gespeichert sind.
	 */
	public int size;

	/**
	 * Standard-Konstruktor.
	 */
	public BinaryHeap() {
		this.heap = new Node[DEFAULT_CAPACITY];
		this.size = 0;
	}

	/**
	 * Konstruktor mit Angabe der initialen Kapazität.
	 * @param capacity Anfangsgröße des Heaps
	 */
	public BinaryHeap(int capacity) {
		this.heap = new Node[capacity];
		this.size = 0;
	}

	/**
	 * Konstruktor, der aus einem gegebenen Knoten-Array einen binären Heap
	 * aufbaut.
	 * @param array Knoten-Array
	 */
	public BinaryHeap(Node[] array) {
		this.heap = array;
		this.size = array.length;
		this.build();
	}

	/**
	 * Soll die Heap-Bedingung des Binärbaums wiederherstellen. Dazu werden
	 * Kind- und Eltern-Knoten solange rekursiv nach unten getauscht, bis die 
	 * Heap-Eigenschaft nicht mehr verletzt ist.
	 * @param startIndex Index, ab dem die Heap-Bedingung wiederhergestellt werden soll.
	 */
	public void heapify(int startIndex) {
		int i = startIndex;
		int min;

		do {
			min = i;

			if (this.hasLeftChild(i) && this.key(this.leftIndex(i)) < this.key(min)) {
				// der linke Kindknoten hat einen kleineren Key als der Elterknoten
				min = this.leftIndex(i);
				// Falls der rechte Kindknoten einen noch kleineren Key, wähle ihn stattdessen als Minimum
				if (this.hasRightChild(i) && this.key(this.rightIndex(i)) < this.key(min)) {
					min = this.rightIndex(i);
				}
			} else if (this.hasRightChild(i) && this.key(this.rightIndex(i)) < this.key(min)) {
				// der rechte Kindknoten hat einen kleineren Key als der Elterknoten
				min = this.rightIndex(i);
			} else if (min == i) {
				// Heap-Bedingung ist erfüllt
				break;
			}

			//tausche die Knoten
			this.swap(i, min);

			i = min;
		} while (true);
	}

	/**
	 * Konstruiert den binären Heap, indem iterativ heapify auufgerufen wird.
	 * Es wird bei den Blättern begonnen und sukzessive bis zur Wurzel 
	 * hochgearbeitet (Bottom-up Arbeitsweise).
	 */
	public void build() {
		if (this.heap.length == 0) return;

		/* ab Position n/2-1 sind nur noch Blätter gespeichert, d.h. ab n/2-1
		 * beginnt die unterste Ebene des binären Baums 
		 */
		for (int i = this.heap.length / 2-1; i >= 0; --i) {
			this.heapify(i);
		}
	}

	/**
	 * Verringert den Schlüsselwert eines Elements und stellt die Heap-Bedingung
	 * wieder her, indem von unten nach oben getauscht wird (bottom-up).
	 * @param i Index des Elements
	 * @param newKey neuer Schlüsselwert
	 */
	public void decreaseKey(int i, int newKey) {
		if(this.key(i) >= newKey) {
			this.heap[i].distance = newKey;
			// tausche solange nach oben, bis Heap-Eigenschaft erfüllt
			while (i > 0 && this.key(i) < this.key(this.parentIndex(i))) {
				this.swap(i, this.parentIndex(i));
				i = this.parentIndex(i);
			}
		}	
	}

	/**
	 * Fügt das Element an das Ende des Heaps und ruft decreaseKey auf, um die
	 * Heap-Eigenschaft wiederherzustellen.
	 * @param node Neues Element, das hinzugefügt werden soll
	 */
	public void insert(Node node) {
		if (size >= this.heap.length-1) {
			this.heap = this.resize();
		}
		size++;
		int i = this.size-1;
		this.heap[i] = node;
		this.decreaseKey(i, node.distance);
	}

	/**
	 * Löscht ein Element, indem es aus seiner Position i im Heap entfernt wird
	 * und das letzte Element im Heap an diese Stelle gesetzt wird. Anschließend
	 * muss die Heap-Eigenschaft mittels heapify oder decreaseKey wiederhergestellt
	 * werden.
	 * @param i Position des zu löschenden Elements
	 * @return das entfernte Element
	 */
	public Node delete(int i) {
		Node removedItem = this.heap[i];
		int lastIndex = size-1;
		this.swap(i, lastIndex);
		this.heap[lastIndex] = null;
		this.shrink(lastIndex-1);
		size--;
		if (i != lastIndex) {
			if (i == 0 || this.key(i) > this.key(this.parentIndex(i))) {
				this.heapify(i);
			} else {
				this.decreaseKey(i, this.heap[i].distance);
			}
		}
		return removedItem;
	}

	/**
	 * Entfernt das Element mit dem minimalen Schlüssel aus dem Heap. Dies sollte
	 * immer an der ersten Position im Heap stehen.
	 * @return Minimum
	 */
	public Node deleteMin() {
		if (size > 0)
			return this.delete(0);
		else
			return null;
	}

	/**
	 * Gibt das kleinste ELement im Heap zurück. Dies sollte immer an der ersten
	 * Position im Heap stehen.
	 * @return Minimum
	 */
	public Node findMin() {
		return this.heap[0];
	}

	/**
	 * Gibt den Schlüsselwert eines Elements zurück.
	 * @param i Index des Elements
	 * @return Wert des Schlüssels
	 */
	private int key(int i) {
		return this.heap[i].distance;
	}

	/**
	 * Tauscht zwei Elemente innerhalb des Heaps miteinander.
	 * @param index1 Index des ersten Elements
	 * @param index2 Index des zweiten Elements
	 */
	public void swap(int index1, int index2) {
		Node tmp = this.heap[index1];
		this.heap[index1] = this.heap[index2];
		this.heap[index2] = tmp;
	}

	/**
	 * Verdoppelt die Kapazität des binären Heaps.
	 * @return Referenz auf den vergößerten Heapspeicher
	 */
	private Node[] resize() {
		return Arrays.copyOf(this.heap, this.heap.length * 2);
	}

	/**
	 * Verkleinert die Kapazität des binären Heaps, um Speicherplatz zu sparen.
	 * @param lastIndex Index des letzten Elements.
	 * @return Referenz auf den verkleinerten Heapspeicher
	 */
	private Node[] shrink(int lastIndex) {
		if (lastIndex < this.heap.length/2) {
			return Arrays.copyOf(this.heap, this.heap.length/2);
		} else {
			return Arrays.copyOf(this.heap, lastIndex+1);
		}
	}

	/**
	 * Liefert den Index des linken Kindknotens.
	 * @param i Index des Elterknotens
	 * @return Index des linken Kindknotens
	 */
	public int leftIndex(int i) {
		return 2 * i + 1;
	}

	/**
	 * Liefert den Index des rechten Kindknotens.
	 * @param i Index des Elterknotens
	 * @return Index des rechten Kindknotens
	 */
	public int rightIndex(int i) {
		return 2 * i + 2;
	}

	/**
	 * Prüft, ob der Elterknoten zum Index i einen linken Kindknoten hat.
	 * @param i Index des Elterknotens
	 * @return TRUE, falls der Elterknoten einen linken Kindknoten hat
	 */
	public boolean hasLeftChild(int i) {
		return this.leftIndex(i) <= this.size-1;
	}

	/**
	 * Prüft, ob der Elterknoten zum Index i einen rechten Kindknoten hat.
	 * @param i Index des Elterknotens
	 * @return TRUE, falls der Elterknoten einen rechten Kindknoten hat
	 */
	public boolean hasRightChild(int i) {
		return this.rightIndex(i) <= this.size-1;
	}

	/**
	 * Prüft, ob ein Knoten einen Elterknoten besitzt.
	 * @param i Index des Knotens
	 * @return TRUE, für alle Knoten mit Index größer 0
	 */
	public boolean hasParent(int i) {
		return i > 0;
	}

	/**
	 * Liefert den Index des Elterknotens
	 * @param i Index des Knotens
	 * @return Index des Elterknotens
	 */
	public int parentIndex(int i) {
		if ((i & 1) == 0) {
			return i / 2 - 1;
		} else {
			return i / 2;
		}
	}

	/**
	 * Liefert eine Referenz auf den Elterknoten zurück.
	 * @param i Index des Knotens
	 * @return Referenz auf Elterknoten
	 */
	public Node parent(int i) {
		if (this.hasParent(i)) {
			return this.heap[parentIndex(i)];
		} else {
			return null;
		}
	}

	/**
	 * Prüft, ob der Heap leer ist.
	 * @return TRUE, falls der Heap keine ELemente mehr gespeichert hat, FALSE sonst
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Liefert eine Zeichenkettenrepräsentation des Heaps.
	 */
	public String toString() {
		return Arrays.toString(this.heap);
	}
}
