package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wn;
    private NGramMap m;

    public HyponymsHandler(WordNet wn, NGramMap m) {
        this.wn = wn;
        this.m = m;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        Integer k = q.k();
        int startYear = q.startYear();
        int endYear = q.endYear();
        if (words.size() == 0) {
            return "[]";
        }
        if (k < 0) {
            return "[]";
        }
        if (words.size() == 1 && k == 0) {
            return wn.GetHypos(words.get(0));
        }
        if (k > 0 && words.size() > 0) {
            return wn.topWords(m, q);
        }
        if (words.size() > 0) {
            return wn.multiWords(q);
            //return wn.topWords(m, q);
        }
        return "[]";
    }
}
