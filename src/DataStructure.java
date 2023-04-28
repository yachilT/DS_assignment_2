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
			xFirst = new Link<>(new Container(point, new XComparator()), null, null);
			xLast = xFirst;
			yFirst = new Link<>(new Container(point, new YComparator()), null, null);
			yLast = yFirst;
		} else {
			add(point, xFirst);
			add(point, yFirst);

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

	private void add(Point point, Link<Container> curr) {
		while (curr.hasNext() && curr.getData().compareTo(point) < 0) {
			curr = curr.getNext();
		}

		Link<Container> newPX;
		if (curr.getData().compareTo(point) > 0) {

			newPX = new Link<>(new Container(point, curr.getData().getCmpr()), curr.getPrev(), curr);
		} else {
			newPX = new Link<>(new Container(point, curr.getData().getCmpr()), curr, curr.getNext());
		}

		if (newPX.hasNext())
			newPX.getNext().setPrev(newPX);

		if (newPX.hasPrev())
			newPX.getPrev().setNext(newPX);

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
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean getLargestAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container getMedian(Boolean axis) {
		// TODO Auto-generated method stub
		return null;
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


	//TODO: add members, methods, etc.

}