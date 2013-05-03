import java.util.*;

public class SnakeDLL{

    public class Cell {
    public int x, y;
    Cell next, prev;

    Cell (int x, int y, Cell n, Cell p) {
        this.x=x; this.y=y; next=n; prev=p;
    }

    Cell() {     // used only to create header cell
        next=prev=this;
    }
    }

    public Cell header = new Cell();
   
    public void addBefore(Cell p, int x, int y) {
    p.prev.next = new Cell(x, y, p, p.prev);
    p.prev = p.prev.next;
    }


    public void addLast(int x, int y) {
    addBefore(header, x, y);
    /*header.prev.next = new Cell(s, header, header.prev);
    header.prev = header.prev.next;
    */
    }

    public void addFirst(int x, int y) {
    addBefore(header.next, x, y);
    //header.next = new Cell (s, header.next, header);
    }

    public void delete(Cell p) {
    p.next.prev = p.prev;
    p.prev.next = p.next;
    }

    public void removeLast() {
	// if (isEmpty()) throw new RuntimeException("Deleting from empty Snake");
    delete(header.prev);
    }

    public boolean isEmpty() {
    return header.next == header;
    }

    public int getX(Cell p) {
        if (p!=null)
        return p.x;
    else return -1;
    }

    public int getY(Cell p) {
        if (p!=null)
        return p.y;
    else return -1;
    }
}