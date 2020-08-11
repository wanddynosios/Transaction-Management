package model.basic;

import java.util.IllformedLocaleException;

public class Range<T extends Comparable> {
    private T minimum;
    private T maximum;
    public Range(T minimum, T maximum){
        if (! (minimum.compareTo(maximum) > 0)){
            this.minimum = minimum;
            this.maximum = maximum;
        } else throw new IllformedLocaleException("Minimum has to be smaller (<) than maximum");

    }

    public T getMinimum() {
        return minimum;
    }

    public T getMaximum() {
        return maximum;
    }

    public boolean contains(T containee){
        return  ((containee.compareTo(minimum) >= 0) && (containee.compareTo(maximum) <= 0));
    }

    public Long getSize(){
        return (Long) maximum - (Long) minimum;
    }
}