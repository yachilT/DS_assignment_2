import javax.crypto.Cipher;
import java.util.Arrays;
import java.util.Comparator;

public class DataStructure implements DT {

	private Container xFirst;
	private Container xLast;
	private Container yFirst;
	private Container yLast;
	private int size;



	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure() {
		this.xFirst = null;
		this.xLast = null;
		this.yFirst = null;
		this.yLast = null;
		this.size = 0;
	}

	public DataStructure(Container xFirst, Container xLast, Container yFirst, Container yLast, int size) {
		this.xFirst = xFirst;
		this.xLast = xLast;
		this.yFirst = yFirst;
		this.yLast = yLast;
		this.size = size;
	}

	@Override
	public void addPoint(Point point) {
		if (size == 0) {
			xFirst = new Container(point, new XComparator(), null, null, null);
			xLast = xFirst;
			yFirst = new Container(point, new YComparator(), null, null, xFirst);
			yLast = yFirst;
			xFirst.setOtherAxis(yFirst);
		} else {

			Container toAddX = new Container(point, new XComparator(), null, null, null);
			Container toAddY = new Container(point, new YComparator(), null, null, toAddX);
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

	private void add(Container toAdd, Container curr) {
		Point point = toAdd.getData();
		while (curr.hasNext() && curr.compareTo(point) < 0) {
			curr = curr.getNext();
		}

		if (curr.compareTo(point) > 0) {
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

		Container curr = axis ? xFirst : yFirst;
		Point minPoint = new Point(min,min);
		Point maxPoint = new Point(max,max);


		while (curr.hasNext() & curr.compareTo(maxPoint) < 0) {
			if (curr.compareTo(minPoint) > 0) {
				points[index] = curr.getData();
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
		return (double)(size) / ((xLast.getData().getX() - xFirst.getData().getX()) *
				(yLast.getData().getY() - yFirst.getData().getY()));
	}

	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		Container first = axis ? xFirst : yFirst;
		Container last = axis ? xLast : yLast;

		Point minPoint = new Point(min,min);
		Point maxPoint = new Point(max,max);

		while((first.hasNext() & last.hasPrev())
				&& first.compareTo(minPoint) < 0 & last.compareTo(maxPoint) > 0){
			Container toRemoveFirst = first;
			Container toRemoveLast = last;

			first = first.getNext();
			last = last.getPrev();

			delete(toRemoveFirst);
			delete(toRemoveLast);
		}

		while (first.hasNext() && first.compareTo(minPoint) < 0) {
			Container toRemoveFirst = first;
			first = first.getNext();
			delete(toRemoveFirst);
		}

		while (last.hasPrev() && last.compareTo(maxPoint) > 0) {
			Container toRemoveLast = last;
			last = last.getPrev();
			delete(toRemoveLast);
		}

	}

	@Override
	public Boolean getLargestAxis() {
		return xLast.getData().getX() - xFirst.getData().getX() >
				yLast.getData().getY() - yFirst.getData().getY();
	}

	@Override
	public Container getMedian(Boolean axis) {
		Container curr = axis ? xFirst : yFirst;
		int index = 0;
		while (index < this.size){
			curr = curr.getNext();
		}
		return curr;
	}

	@Override
	public Point[] nearestPairInStrip(Container container, double width,
									  Boolean axis) {

		Point[] inStrip = getPointsInStrip(container,width,axis);
		Arrays.sort(inStrip,axis ? new YComparator() : new XComparator());

		Point[] nearestPoints = new Point[2];
		double minDist = Double.POSITIVE_INFINITY;
		for (int i = 0; i < inStrip.length; i++) {
			for (int j = i + 1; j < i + 8 & j < inStrip.length; j++) {
				double currDist = dist(inStrip[i], inStrip[j]);
				if (currDist < minDist){
					nearestPoints[0] = inStrip[i];
					nearestPoints[1] = inStrip[j];
					minDist = currDist;
				}

			}
		}
		return nearestPoints;
	}

	private Point[] getPointsInStrip(Container container, double width, boolean axis) {
		int maxVal = (int)Math.floor(container.getData().getX() + width / 2 + 1);
		Point upperBound = new Point(maxVal, maxVal);
		int minVal = (int)Math.ceil(container.getData().getX() - width / 2 - 1);
		Point lowerBound = new Point(minVal, minVal);

		Point[] arr = new Point[size];
		if ((axis & xLast.compareTo(upperBound) <= 0 & xFirst.compareTo(lowerBound) >= 0) |
				!axis & yLast.compareTo(upperBound) <= 0 & yFirst.compareTo(lowerBound) >= 0) {
			Container curr = axis ? yFirst: xFirst;
			for (int i = 0; i < arr.length; i++) {
				arr[i] = curr.getData();
				curr = curr.getNext();
			}
			return  arr;
		}


		Container prev = container.getPrev();
		Container next = container.getNext();


		arr[0] = container.getData();
		int index = 1;

		while (prev.compareTo(lowerBound) >= 0 & next.compareTo(upperBound) <= 0) {
			arr[index] = prev.getData();
			arr[index + 1] = next.getData();
			index += 2;
		}

		while (prev.compareTo(lowerBound) >= 0) {
			arr[index] = prev.getData();
			index++;
		}

		while (next.compareTo(upperBound) <= 0) {
			arr[index] = next.getData();
			index++;
		}
		Point[] inStrip = new Point[index];

		for (int i = 0; i < inStrip.length; i++) {
			inStrip[i] = arr[i];
		}

		return inStrip;
	}

	public double dist(Point p1,Point p2){
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(),2) + Math.pow(p1.getY() - p2.getY(),2));
	}

	@Override
	public Point[] nearestPair() {
		if(size <= 2)
			return size < 2 ? new Point[2] : new Point[]{xFirst.getData(),xLast.getData()};


	}

	public double nearestPair(Point[] arr,int left,int right, Point[] nearest){
		Point[] pair = new Point[2];
		if(right + 1 - left <= 3) {
			double minDistance = Double.POSITIVE_INFINITY;
			for (int i = 0; i < arr.length; i++)
				for (int j = i + 1; j < arr.length; j++)
					if(dist(arr[i],arr[j]) < minDistance) {
						pair[0] = arr[i];
						pair[1] = arr[j];
						minDistance = dist(arr[i],arr[j]);
			}
		}
		else{

		}


	}

	private void delete(Container toDelete){
		toDelete.getOtherAxis().delete();
		toDelete.delete();
		this.size--;
	}

	//TODO: add members, methods, etc.

}