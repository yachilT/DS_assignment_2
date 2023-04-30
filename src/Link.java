

public class Link<T> {
    private Link<T> prev;
    private Link<T> next;

    private Link<T> otherAxis;
    private T data;


    public Link(T data, Link<T> prev, Link<T> next, Link<T> otherAxis) {
        this.prev = prev;
        this.next = next;
        this.data = data;
        this.otherAxis = otherAxis;
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

    public Link<T> getOtherAxis() {
        return otherAxis;
    }

    public void setOtherAxis(Link<T> otherAxis) {
        this.otherAxis = otherAxis;
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

    public void delete() {
        if (this.hasNext())
            this.next.prev = this.prev;

        if (this.hasPrev())
            this.prev.next = this.next;

        this.next = null;
        this.prev = null;
        this.otherAxis = null;
    }


}
