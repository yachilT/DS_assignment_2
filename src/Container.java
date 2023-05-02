import java.util.Comparator;

//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	private Comparator<Point> cmpr;
	private Container prev;
	private Container next;

	private Container otherAxis;

	public Container(Point data, Comparator<Point> cmpr, Container prev, Container next, Container otherAxis) {
		this.data = data;
		this.cmpr = cmpr;
		this.next = next;
		this.prev = prev;
		this.otherAxis = otherAxis;
	}


	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}

	public int compareTo(Point p) {
		return this.cmpr.compare(this.data, p);
	}

	public Comparator<Point> getCmpr() {
		return cmpr;
	}

	public Container getPrev() {
		return prev;
	}

	public void setPrev(Container prev) {
		this.prev = prev;
	}

	public Container getNext() {
		return next;
	}

	public void setNext(Container next) {
		this.next = next;
	}

	public Container getOtherAxis() {
		return otherAxis;
	}

	public void setOtherAxis(Container otherAxis) {
		this.otherAxis = otherAxis;
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
