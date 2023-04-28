

public class Link<T> {
    private Link<T> prev;
    private Link<T> next;

    private T data;


    public Link(T data, Link<T> prev, Link<T> next) {
        this.prev = prev;
        this.next = next;
        this.data = data;
    }

    public Link<T> getPrev() {
        return prev;
    }

    public void setPrev(Link<T> prev) {
        this.prev = prev;
    }

    public Link<T> getNext() {
        return next;
    }

    public void setNext(Link<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public boolean hasPrev() {
        return this.prev != null;
    }

}
