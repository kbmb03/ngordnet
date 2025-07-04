package ngordnet.ngrams;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }
    @Test
    public void testFromSpec2() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries DogPopulation = new TimeSeries();
        DogPopulation.put(1995, 0.4);
        DogPopulation.put(1980, 0.4);
        DogPopulation.put(1981, 0.4);
        DogPopulation.put(1982, 0.4);

        DogPopulation.put(1983, 0.4);

        DogPopulation.put(1984, 0.4);

        DogPopulation.put(1985, 0.4);

        DogPopulation.put(1986, 0.4);

        DogPopulation.put(1987, 0.4);

        DogPopulation.put(1988, 0.4);

        DogPopulation.put(1989, 0.4);

        DogPopulation.put(1990, 0.4);

        catPopulation.put(1995, 0.1);

        catPopulation.put(1996, 0.1);
        catPopulation.put(1997, 0.1);
        catPopulation.put(1998, 0.1);
        catPopulation.put(1999, 0.1);
        catPopulation.put(2000, 0.1);
        catPopulation.put(2001, 0.1);
        catPopulation.put(2002, 0.1);
        TimeSeries y = new TimeSeries();
        TimeSeries tw =  new TimeSeries();
        TimeSeries w = tw.dividedBy(y);
        TimeSeries res = new TimeSeries(catPopulation, 1996, 2002);
        System.out.println(w.data());



        //catPopulation.tester(DogPopulation);

    }
} 