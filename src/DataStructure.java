import javax.crypto.Cipher;
import java.util.Comparator;

public class DataStructure implements DT {

	private Link<Container> xFirst;
	private Link<Container> xLast;
	private Link<Container> yFirst;
	private Link<Container> yLast;
	private int size;

	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure() {
		this.xFirst = null;
		this.xLast = null;
		this.yFirst = null;
		this.yLast = null;
		this.size = 0;
	}

	@Override
	public void addPoint(Point point) {
		if (size == 0) {
			xFirst = new Link<>(new Container(point, new XComparator()), null, null, null);
			xLast = xFirst;
			yFirst = new Link<>(new Container(point, new YComparator()), null, null, xFirst);
			yLast = yFirst;

			xFirst.setOtherAxis(yFirst);
		} else {

			Link<Container> toAddX = new Link<>(new Container(point, new XComparator()), null, null, null);
			Link<Container> toAddY = new Link<>(new Container(point, new YComparator()), null, null, toAddX);
			toAddX.setOtherAxis(toAddY);

			add(toAddX, xFirst);
			add(toAddY, yFirst);

			if (xFirst.hasPrev())
				xFirst = xFirst.getPrev();

			if (xLast.hasNext())
				xLast = xLast.getNext();

			if (yFirst.hasPrev())
				yFirst = yFirst.getPrev();

			if (yLast.hasNext())
				yLast = yLast.getNext();
		}
		size++;
	}

	private void add(Link<Container> toAdd, Link<Container> curr) {
		Point point = toAdd.getData().getData();
		while (curr.hasNext() && curr.getData().compareTo(point) < 0) {
			curr = curr.getNext();
		}

		if (curr.getData().compareTo(point) > 0) {
			toAdd.setPrev(curr.getPrev());
			toAdd.setNext(curr);
		} else {
			toAdd.setPrev(curr);
			toAdd.setNext(curr.getNext());
		}

		if (toAdd.hasNext())
			toAdd.getNext().setPrev(toAdd);

		if (toAdd.hasPrev())
			toAdd.getPrev().setNext(toAdd);

	}

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		Point[] points = new Point[size];
		int index = 0;

		Link<Container> curr = axis ? xFirst : yFirst;
		Point minPoint = new Point(min,min);
		Point maxPoint = new Point(max,max);


		while (curr.hasNext() & curr.getData().compareTo(maxPoint) < 0) {
			if (curr.getData().compareTo(minPoint) > 0) {
				points[index] = curr.getData().getData();
				index++;
			}
		}

		Point[] output = new Point[index];
		for(index = 0;index < output.length; index++)
			output[index] = points[index];
		return output;
	}

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		return getPointsInRangeRegAxis(min, max, !axis);
	}

	@Override
	public double getDensity() {
		return (double)(size) / ((xLast.getData().getData().getX() - xFirst.getData().getData().getX()) *
				(yLast.getData().getData().getY() - yFirst.getData().getData().getY()));
	}

	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		Link<Container> first = axis ? xFirst : yFirst;
		Link<Container> last = axis ? xLast : yLast;

		Point minPoint = new Point(min,min);
		Point maxPoint = new Point(max,max);

		while((first.hasNext() & last.hasPrev())
				&& first.getData().compareTo(minPoint) < 0 & last.getData().compareTo(maxPoint) > 0){
			Link<Container> toRemoveFirst = first;
			Link<Container> toRemoveLast = last;

			first = first.getNext();
			last = last.getPrev();

			delete(toRemoveFirst);
			delete(toRemoveLast);
		}

		while (first.hasNext() && first.getData().compareTo(minPoint) < 0) {
			Link<Container> toRemoveFirst = first;
			first = first.getNext();
			delete(toRemoveFirst);
		}

		while (last.hasPrev() && last.getData().compareTo(maxPoint) > 0) {
			Link<Container> toRemoveLast = last;
			last = last.getPrev();
			delete(toRemoveLast);
		}

	}

	@Override
	public Boolean getLargestAxis() {
		return xLast.getData().getData().getX() - xFirst.getData().getData().getX() >
				yLast.getData().getData().getY() - yFirst.getData().getData().getY();
	}

	@Override
	public Container getMedian(Boolean axis) {
		Link<Container> curr = axis ? xFirst : yFirst;
		int index = 0;
		while (index < this.size){
			curr = curr.getNext();
		}
		return curr.getData();
	}

	@Override
	public Point[] nearestPairInStrip(Container container, double width,
									  Boolean axis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point[] nearestPair() {
		// TODO Auto-generated method stub
		return null;
	}
	private void delete(Link<Container> toDelete){
		toDelete.getOtherAxis().delete();
		toDelete.delete();
		this.size--;
	}

	//TODO: add members, methods, etc.

}