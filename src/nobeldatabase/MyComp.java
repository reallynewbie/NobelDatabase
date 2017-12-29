package nobeldatabase;

import java.util.Comparator;

/**
 * Sorter for Countries
 * @author Shawn Gagnon(gagnons23@mymacewan.ca)
 */
public class MyComp extends Countries implements Comparator<Countries> {

    /**
     * don't touch this
     * @param n
     * @param code 
     */
    public MyComp(String n, String code) {
        super(n, code);
    }

    /**
     * compare function
     * @param o1 - country 1
     * @param o2 - country 2
     * @return -1 if less then 0 if equal 1 if greater than
     */
    @Override
    public int compare(Countries o1, Countries o2) {
        return o1.getName().compareTo (o2.getName());
    }
}
