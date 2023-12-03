import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;


    public HashListAutocomplete(String[] terms, double[] weights) {

        if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
    
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        List<Term> list = new ArrayList<>();
        if(myMap.containsKey(prefix)) {
            List<Term> all = myMap.get(prefix);
            list = all.subList(0, Math.min(k, all.size()));
        }
        return list;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();
        mySize = 0;
        int count1 = 0;
        int count2 = 0;
        myMap.putIfAbsent("", new ArrayList<Term>());
        for (String t : terms){
            List<Term> list = myMap.get("");
            list.add(new Term(t, weights[count1]));
            myMap.put("", list);
            count1++;
        }
        for (String t : terms){
            int min = Math.min(MAX_PREFIX, t.length());
            for (int i = 0 ; i < min ; i++){
                myMap.putIfAbsent(t.substring(0, i+1), new ArrayList<Term>());
                List<Term> list = myMap.get(t.substring(0, i+1));
                list.add(new Term(t, weights[count2]));
                myMap.put(t.substring(0, i+1), list);
            }
            count2++;
        }
        for (String k : myMap.keySet()){
            Collections.sort(myMap.get(k),Comparator.comparing(Term::getWeight).reversed());
        }
        
    }

    @Override
    public int sizeInBytes() {
        if(mySize == 0) {
            for (String s : myMap.keySet()){
                mySize += BYTES_PER_CHAR * s.length();
                for (Term t : myMap.get(s)){
                    mySize += BYTES_PER_DOUBLE + BYTES_PER_CHAR * t.getWord().length();
                }
            }
        }
        return mySize;
    }
    
}
