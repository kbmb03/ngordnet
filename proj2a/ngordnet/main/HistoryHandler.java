package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {

    private NGramMap m;
    private List<String> words;

    public HistoryHandler(NGramMap map) {
        m = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>();
        TimeSeries temp = new TimeSeries();
        words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (Integer i = 0; i < words.size(); i++) {
            String curr = words.get(i);
            temp = m.weightHistory(curr, startYear, endYear);
            TimeSeries value = new TimeSeries();
            value = temp;
            lts.add(value);
            temp = new TimeSeries();
        }
        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
