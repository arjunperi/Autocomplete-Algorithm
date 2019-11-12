import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        initialize(terms, weights);
        if (terms == null || weights == null)
            throw new NullPointerException("One or more arguments null");
    }

    @Override
    //Fix adding to map part!
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<String, List<Term>>();
        //Loop through terms
        for (int i = 0; i < terms.length; i++) {
            //Pull out a term
            String word = terms[i];
            //Loop through the chars in the word
            for (int j = 0; j < Math.min(word.length(), MAX_PREFIX); j++) {
                //Get the prefixes
                String pre = word.substring(0, j + 1);
                //The current term
                Term add = new Term(terms[i], weights[i]);
                //Put prefix key in map
                myMap.putIfAbsent(pre, new ArrayList<Term>());
                //Add current term as value
                myMap.get(pre).add(add);
            }
            for (String s : myMap.keySet()) {
                Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
            }
        }
    }

    @Override
    public int sizeInBytes() {
        if (mySize == 0) {
            for (String s : myMap.keySet()) {
                mySize += BYTES_PER_CHAR * s.length();
                List<Term> term = myMap.get(s);
                for (int i = 0; i < term.size(); i++) {
                    mySize += BYTES_PER_CHAR * term.get(i).getWord().length();
                    mySize += BYTES_PER_DOUBLE;
                }
            }
        }
        return mySize; 
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (myMap.containsKey(prefix)) {
            List<Term> all = myMap.get(prefix);
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
        }
        return new ArrayList<>();
    }
}


