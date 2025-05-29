package ngordnet.main;

import ngordnet.hugbrowsermagic.HugNgordnetServer;
import ngordnet.ngrams.NGramMap;

public class Main {

    //for graph, only run list to string when geting and returning, leave it as string oherwie, and when get is
    //ran can check for empty etc, can only cause bugs being rran too much.s
    public static void main(String[] args) {
        HugNgordnetServer hns = new HugNgordnetServer();
//        String wordFile = "./data/ngrams/top_49887_words.csv";
//        String countFile = "./data/ngrams/total_counts.csv";
//
//        String synsetFile = "./data/wordnet/synsets.txt";
//        String hyponymFile = "./data/wordnet/hyponyms.txt";


        String wordFile = "data/ngrams/very_short.csv";
        String countFile = "data/ngrams/total_counts.csv";

        String synsetFile = "data/wordnet/synsets16.txt";
        String hyponymFile = "data/wordnet/hyponyms16.txt";

        NGramMap ngm = new NGramMap(wordFile, countFile);
        //wordnet constructor should take in two above filex, like ngm does
        WordNet wm = new WordNet(synsetFile, hyponymFile, ngm);

        hns.startUp();
//        hns.register("history", new HistoryHandler(ngm));
//        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(wm, ngm));

    }
}
