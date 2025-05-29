package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import org.hamcrest.core.StringEndsWith;

import java.awt.desktop.SystemEventListener;
import java.lang.reflect.Array;
import java.util.*;

public class Graph {
    private HashMap dict;

    private HashMap hyps;
    private HashMap nums;
    private HashMap WordsToNums;

    public Graph() {
        hyps = new HashMap();
        dict = new HashMap();
        nums = new HashMap();
        WordsToNums = new HashMap();
    }

    private void MakeDict(In synsetFileCopy) {

        while (synsetFileCopy.hasNextLine()) {
            ArrayList<String> WordIndexes = new ArrayList<>();
            String line = synsetFileCopy.readLine();
            String[] split = line.split(",");
            String ind = split[0];//2
            WordIndexes.add(ind);
            String word = split[1];
            String[] splitKeys = word.split(" ");
            for (Integer i = 0; i < splitKeys.length; i++) {
                String curr = splitKeys[i];
                if (WordsToNums.containsKey(curr)) {

                    ArrayList<String> exists = (ArrayList<String>) WordsToNums.get(curr);
                    WordIndexes.addAll(exists);
                    removeDupes(WordIndexes);
                    WordsToNums.put(curr, WordIndexes);

                } else {
                    removeDupes(WordIndexes);
                    WordsToNums.put(curr, WordIndexes);
                }
            }
            dict.put(ind, word);
        }
    }

    //extraCopy.addAll(jk);
//            for (Integer i = 0; i < keytr.length; i++) {
//                jk = new ArrayList<>();
//                jk.addAll(extraCopy);
//                String currW = (String) keytr[i];
//                if (hyps.containsKey(currW)) {
//                    ArrayList<String> existing = (ArrayList<String>) hyps.get(currW);
//                    jk.addAll(existing);
//                    copy = jk;
//                    removeDupes(copy);
//                    Collections.sort(copy);
//                    hyps.put(currW, copy);

//    ArrayList<String> existing = (ArrayList<String>) hyps.get(currW);
//                    jk.addAll(existing);
//                    copy = jk;
//                    removeDupes(copy);
//                    Collections.sort(copy);
//                    hyps.put(currW, copy);

    private void MakeNums(In hyponymFileCopy)  {

        while (hyponymFileCopy.hasNextLine()) {
            ArrayList<String> hypNums = new ArrayList<>();
            String line = hyponymFileCopy.readLine();
            String[] split = line.split(",");
            String num = split[0];
            for (Integer i = 1; i < split.length; i++) {
                hypNums.add(split[i]);
            }
            if (nums.containsKey(num)) {
                ArrayList<String> exists = (ArrayList<String>) nums.get(num);
                hypNums.addAll(exists);
                nums.put(num, hypNums);
            } else {
                nums.put(num, hypNums);
            }
        }
    }

    public String getFunc(String word) {
        if (hyps.get(word) == null) {
            return "[]";
        }
        if ((!hyps.containsKey(word))) {
            return "[]";
        }
        return (String) hyps.get(word);

    }

    public String ListToString(ArrayList x) {
        String res = "[";
        for (Integer i = 0; i < x.size() - 1; i++) {
            res += x.get(i);
            res += ", ";

        }
        res += x.get(x.size() - 1);
        res += "]";
        return res;
    }

    public ArrayList StringToList(String s) {
        ArrayList<String> output = new ArrayList<>();
        s = s.replace("[", "");
        s = s.replace("]", "");
        String[] res = s.split(", ");
        for (Integer i = 0; i < res.length; i++) {
            output.add(res[i]);
        }
        Collections.sort(output);
        return output;
    }



    private void HypoGetta(ArrayList res, String num) {
        //if num is an edge

        if (!(nums.containsKey(num))) {
            return;
         // if it has children
        } else {
            ArrayList curr = (ArrayList) nums.get(num);
            for (Integer i = 0; i < curr.size(); i++) {
                res.add(curr.get(i));
                HypoGetta(res, (String) curr.get(i));
            }
        }
    }

    public void removeDupes(ArrayList lst) {
        for (Integer i = 0; i < lst.size(); i++) {
            String word = (String) lst.get(i);
            int occurrences = Collections.frequency(lst, word);
            while (occurrences != 1) {
                lst.remove(word);
                occurrences--;
            }
//            if (occurrences != 1) {
//                lst.remove(word);
//            }
        }
    }

    public void MakeGraph(String synsetFile, String hyponymFile) {
        String synsCopy = synsetFile;
        String hypsCopy = hyponymFile;
        In sysnCopyFile = new In(synsCopy);
        In hypsCopyFile = new In(hypsCopy);
        MakeDict(sysnCopyFile);
        MakeNums(hypsCopyFile);
//        ArrayList<String> copy = new ArrayList<>();
//        Set s = nums.keySet();
//        Iterator motor = s.iterator();
//        while (motor.hasNext()) {
//            //curNum = number input
//            //to get number input have a dictonary of all words and corresponding indexes
//            //so act brings up 6, then run recurse to get all hypos of 6.
//            //now we have what weeve got here, asseemble evrything into a list
//            //each word can have multitple values, so for change it would have the two indexes
//            //and get the hypnyms of both 2 and 8 into the same list, then turn that to words.
//            String currNum = (String) motor.next();
//            ArrayList<String> jj = new ArrayList<>();
//            ArrayList<String> jk = new ArrayList<>();
//            ArrayList<String> extraCopy = new ArrayList<>();
//
//            HypoGetta(jj, currNum);
//            for (String num : jj) {
//                String hp = (String) dict.get(num);
//                String[] addThese = hp.split(" ");
//                jk.addAll(List.of(addThese));
//            }
//
//            //correct to here
//
//
//            String keysss = (String) dict.get(currNum);
//            String[] keytr = keysss.split(" ");
//            jk.addAll(List.of(keytr));
//
//            extraCopy.addAll(jk);
//            for (Integer i = 0; i < keytr.length; i++) {
//                jk = new ArrayList<>();
//                jk.addAll(extraCopy);
//                String currW = (String) keytr[i];
//                if (hyps.containsKey(currW)) {
//                    ArrayList<String> existing = (ArrayList<String>) hyps.get(currW);
//                    jk.addAll(existing);
//                    copy = jk;
//                    removeDupes(copy);
//                    Collections.sort(copy);
//                    hyps.put(currW, copy);
//
//                } else {
//                    copy = jk;
//                    removeDupes(copy);
//                    Collections.sort(copy);
//                    hyps.put(currW, copy);
//                }
//
//            }
//            }
//        return hyps;
    }

    public String GETYES(String s) {
        ArrayList<String> HyposOfS = new ArrayList<>();
        ArrayList<String> index = (ArrayList<String>) WordsToNums.get(s);
        ArrayList<String> handy = new ArrayList<>();
        ArrayList<String> copy = new ArrayList<>();
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> IndexCheck = new ArrayList<>();

        if (!(WordsToNums.containsKey(s))) {
            return "[]";
        }

        for (Integer l = 0; l < index.size(); l++) {
            String asr = index.get(l);
            String as = (String) dict.get(asr);
            IndexCheck.add(as);
        }
        for (String word : IndexCheck) {

            ArrayList<String> IndexCheckHelper = new ArrayList<>();


            String[] WordSplit = word.split(" ");
            IndexCheckHelper.addAll(List.of(WordSplit));
            if (IndexCheckHelper.contains(s)) {
                res.addAll(List.of(WordSplit));
            }
        }

        removeDupes(res);

        for (Integer i = 0; i < index.size(); i++) {
            String currNum = index.get(i);
            String CurrKey = (String) dict.get(currNum);
            String[] CurrKeySplit = CurrKey.split(" ");
            for (String k : CurrKeySplit) {
                if (k.equals(s)) {
                    HypoGetta(handy, currNum);
                    copy = handy;
                    HyposOfS.addAll(copy);
                    handy = new ArrayList<>();
                }
            }

        }
        removeDupes(HyposOfS);
        for (String nums : HyposOfS) {
            String curr = (String) dict.get(nums);
            String[] spt = curr.split(" ");
            res.addAll(List.of(spt));
        }
        removeDupes(res);
        Collections.sort(res);
        removeDupes(res);



        if (res == null) {
            return "[]";
        }
        if ((!res.contains(s))) {
            return "[]";
        }
        if (res.size() > 0) {
            return ListToString(res);
        }
        return "[]";
    }

    }
