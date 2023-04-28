import java.util.Comparator;

public class XComparator implements Comparator<Point> {

    @Override
    public int compare(Point o1,Point o2) {return o1.getX() - o2.getX();}
}
