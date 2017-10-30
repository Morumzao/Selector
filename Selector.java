import java.util.*;

public class Selector<T> {

    private Map<Long, T> map = new LinkedHashMap<>();

    private Selector(Collection<T> collection){
        createMap(collection);
    }

    private void createMap(Collection<T> collection){
        T[] items = (T[]) collection.toArray();
        long score = 1L;
        for(int i = 0; i < collection.size(); i++){
            map.put(score, items[i]);
            score <<= 1;
        }
    }

    private boolean isPowerOfTwo(long value){
        double result = Math.log(value)/Math.log(2);
        return result == (int) result;
    }

    private long powerOfTwo(long score){
        return (long) Math.pow(2, (int) Math.sqrt(score));
    }

    public List<T> get(long score){
        List<T> result = new ArrayList<>();
        while(score >= 1){
            if(isPowerOfTwo(score)){
                result.add(map.get(score));
                return result;
            }
            long power = powerOfTwo(score);
            result.add(map.get(power));
            score -= power;
        }
        return result;
    }

    public static <T> Selector<T> from(T... array){
        return new Selector<>(Arrays.asList(array));
    }

    public static <T> Selector<T> from(Collection<T> collection){
        return new Selector<>(collection);
    }

}
