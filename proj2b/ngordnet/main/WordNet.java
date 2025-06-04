package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class WordNet {
    //wrap graph
    private Graph graph;
    private String syn;
    private String hy;
    private HashMap hm;
    private NGramMap ng;

    public WordNet(String synsetFile, String hyponymFile, NGramMap ngm) {
        graph = new Graph();
        syn = synsetFile;
        hy = hyponymFile;
        graph.MakeGraph(synsetFile, hyponymFile);
        ng = ngm;
    }

    public String GetHypos(String s) {

        String res = graph.GETYES(s);
        return res;

    }

    public String multiWords(NgordnetQuery q) {

        //lay out instance variables

        List<String> words = q.words();
        ArrayList<ArrayList> hypos = new ArrayList<>();
        //create list of each words hyponyms
        //didn't  have any  hyponyms, then loop shouldnt have entered

        for (String word : words) {
            String hyponyms = graph.GETYES(word);
            if (!(hyponyms == "[]")) {
                ArrayList<String> addDis = graph.StringToList(hyponyms);
                hypos.add(addDis);
            } else {
                return "[]";
            }
        }
        ArrayList<String> comp = hypos.get(0);
        graph.removeDupes(comp);
        for (Integer j = 1; j < hypos.size(); j++) {
            comp.retainAll(hypos.get(j));
        }

        //check if each word is in all lists
        if (comp.isEmpty()) {
            return "";
        }
        Collections.sort(comp);
        graph.removeDupes(comp);
        String ret = graph.ListToString(comp);
        return ret;

    }

    public Double SumList(List lst) {
        Double result = 0.0;
        for (Integer i = 0; i < lst.size(); i++) {
            Double add = (Double) lst.get(i);
            result += add;
        }
        return result;
    }

    public String topWords(NGramMap ngmap, NgordnetQuery q) {
        Integer start = q.startYear();
        List<String> wordlist = q.words();
        Integer k = q.k();
        TimeSeries ts = new TimeSeries();
        ArrayList<String> reslist = new ArrayList<>();
        TimeSeries tsCopy = new TimeSeries();
        HashMap vals = new HashMap();
        Integer end = q.endYear();
        ArrayList<String> words = graph.StringToList(GetHypos(wordlist.get(0)));
        if (wordlist.size() > 1) {
            words = graph.StringToList(multiWords(q));
        }
        if (graph.ListToString(words).equals("[]")) {
            return "[]";
        }


        for (Integer i = 0; i < words.size(); i++) {
            String word = words.get(i);
            ts = ngmap.countHistory(word, start, end);
            tsCopy = ts;
            List<Double> counts = tsCopy.data();
            List<Double> countsCopy = counts;
            if (SumList(countsCopy) != 0.0) {
                vals.put(word, SumList(countsCopy));
            }
            ts.clear();
        }

        while (k > 0) {
            Double max = 0.0;
            String maxWord = "";
            for (Integer i = 0; i < words.size(); i++) {
                String word = words.get(i);
                if (vals.containsKey(word)) {
                    Double curr = (Double) vals.get(word);
                    if (curr >= max) {
                        max = curr;
                        maxWord = word;
                    }
                }
            }
            if ((!maxWord.equals(""))) {
                reslist.add(maxWord);
                vals.remove(maxWord);
            }
            k--;
        }
        if (reslist.size() == 0) {
            return "[]";
        }
        Collections.sort(reslist);
        return graph.ListToString(reslist);
    }
}
