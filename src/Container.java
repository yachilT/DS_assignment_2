import java.util.Comparator;

//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	private Comparator<Point> cmpr;

	public Container(Point data, Comparator<Point> cmpr) {
		this.data = data;
		this.cmpr = cmpr;
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
}
