package ngordnet.ngrams;
import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    private TreeMap tree;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Integer i = startYear; i <= endYear; i++) {
            if (ts.get(i) != null) {
                Double value = ts.get(i);
                this.put(i, value);
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<Integer>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> res = new ArrayList<>();
        if (!this.isEmpty()) {
            int year = (int) this.firstKey();
            for (Integer i = 0; i < this.size(); i++) {
                Double value = this.get(year);
                res.add(value);
                if (!(this.higherKey(year) == null)) {
                    year = this.higherKey(year);
                }
            }
            return res;
        }
        return res;
    }

    /**
     * Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries res = new TimeSeries();
        List years = ts.years();
        List years2 = this.years();
        while (!years2.isEmpty()) {
            int curr = (int) years2.get(0);
            if (!(years.contains(curr))) {
                years.add(curr);
            }
            years2.remove(0);
        }
        int year = 0;
        int stop = years.size();
        for (int i = 0; i < stop; i++) {
            Double sum = 0.0;
            Double val1 = 0.0;
            Double val2 = 0.0;
            year = (int) years.get(0);
            if (ts.get(year) != null) {
                val1 = ts.get(year);
            }
            if (this.get(year) != null) {
                val2 = this.get(year);
            }
            sum = (val1 + val2);
            res.put(year, sum);
            years.remove(0);
        }
        return res;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries res = new TimeSeries();
        List tsYears = ts.years();
        List thisYears = this.years();
        for (Integer i = 0; i < thisYears.size(); i++) {
            int curr = (int) thisYears.get(0);
            if (!(tsYears.contains(curr))) {
                throw new IllegalArgumentException();
            }
            thisYears.remove(0);
        }
        thisYears = this.years();
        int stop = thisYears.size();
        for (int i = 0; i < stop; i++) {
            int year = (int) thisYears.get(0);
            Double val1 = this.get(year);
            Double val2 = ts.get(year);
            Double quotient = (val1 / val2);
            res.put(year, quotient);
            thisYears.remove(0);
        }
        return res;
    }
}


