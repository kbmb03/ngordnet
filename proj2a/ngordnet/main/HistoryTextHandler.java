package ngordnet.main;

import ngordnet.ngrams.NGramMap;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private List<String> words;
    private TimeSeries ts;
    private NGramMap m;

    public HistoryTextHandler(NGramMap map) {
        m = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        words = q.words();
        String res = "";
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (Integer i = 0; i < words.size(); i++) {
            String currWord = words.get(i);
            ts = m.weightHistory(currWord, startYear, endYear);
            res += currWord + ":" + ts.toString() + "\n";
        }
        return res;
    }




}
