import java.util.ArrayList;
import java.util.List;

public class SlowBruteAutocomplete extends BruteAutocomplete {
    /**
     * Create immutable instance with terms constructed from parameter
     *
     * @param terms   words such that terms[k] is part of a word pair 0 <= k < terms.length
     * @param weights weights such that weights[k] corresponds to terms[k]
     * @throws NullPointerException     if either parameter is null
     * @throws IllegalArgumentException if terms.length != weights.length
     * @throws IllegalArgumentException if any elements of weights is negative
     * @throws IllegalArgumentException if any elements of terms is duplicate
     */
    public SlowBruteAutocomplete(String[] terms, double[] weights) {
        super(terms, weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k){
        List<Term> list = new ArrayList<>();
        for (Term t: myTerms){
            if (t.getWord().startsWith(prefix)){
                list.add(t);
            }
        }
    }
}
