package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    private HashMap hash;
    private TimeSeries counts;
    public NGramMap(String wordsFilename, String countsFilename) {
        boolean end = false;
        boolean end2 = false;
        hash = new HashMap();
        counts = new TimeSeries();
        ArrayList lst = new ArrayList();
        TimeSeries ts = new TimeSeries();
        In in = new In(wordsFilename);
        In in2 = new In(countsFilename);
        String word = "";
        String curr = "?";
        while (!end) {
            if (!in.isEmpty()) {
                curr = in.readString();
            }
            if (!curr.equals(word)) {
                if (!word.equals("")) {
                    TimeSeries value = new TimeSeries();
                    value = ts;
                    hash.put(word, value);
                    ts = new TimeSeries();
                }
                word = curr;
            }
            Integer year = Integer.valueOf(in.readString());
            Double appearances = in.readDouble();
            ts.put(year, appearances);
            in.readString();
            if (in.isEmpty()) {
                hash.put(word, ts);
                break;
            }
        }
        while (!end2) {
            String line = in2.readLine();
            String[] nums = line.split(",");
            int year = Integer.parseInt(nums[0]);
            Double uses = Double.valueOf(nums[1]);
            counts.put(year, uses);
            if (!in2.hasNextLine()) {
                break;
            }

        }
    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        if (hash.containsKey(word)) {
            TimeSeries ts = (TimeSeries) hash.get(word);
            TimeSeries res = new TimeSeries();
            res = ts;
            return res;
        }
        return null;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (hash.containsKey(word)) {
            TimeSeries res = new TimeSeries();
            TimeSeries ts = (TimeSeries) hash.get(word);
            res = new TimeSeries(ts, startYear, endYear);
            return res;
        }
        return null;
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries res = new TimeSeries();
        int year = counts.firstKey();
        for (Integer i = 0; i < counts.size(); i++) {
            Double num = counts.get(year);
            res.put(year, num);
            if (!(counts.higherKey(year) == null)) {
                year = counts.higherKey(year);
            }
        }
        return res;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        if (hash.containsKey(word)) {
            TimeSeries res = new TimeSeries();
            TimeSeries ts = (TimeSeries) hash.get(word);
            res = ts.dividedBy(counts);
            return res;
        }
        return null;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (hash.containsKey(word)) {
            TimeSeries ts = (TimeSeries) hash.get(word);
            TimeSeries res = new TimeSeries();
            TimeSeries use = new TimeSeries();
            use = new TimeSeries(ts, startYear, endYear);
            res = use.dividedBy(counts);
            return res;
        }
        return null;
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries res = new TimeSeries();
        for (String str : words) {
            if (countHistory(str) != null) {
                if (res.isEmpty()) {
                    res = weightHistory(str);
                } else {
                    res = res.plus(weightHistory(str));
                }
            }
        }
        return res;
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words,
                              int startYear, int endYear) {
        TimeSeries res = summedWeightHistory(words);
        if (res != null) {
            return new TimeSeries(res, startYear, endYear);
        }
        return null;
    }


}
