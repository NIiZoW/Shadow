package fr.taeron.shadow.utils;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class AverageCollector {
    private ArrayList<Double> averageNumbers = new ArrayList<Double>();

    public void add(double d) {
        this.averageNumbers.add(d);
    }

    public double getAverage() {
        double total = 0.0;
        try {
            Iterator<Double> iterator = this.averageNumbers.iterator();
            while (iterator.hasNext()) {
                double d = iterator.next();
                total += d;
            }
        }
        catch (ConcurrentModificationException cme) {
            return -1.0;
        }
        return total /= (double)this.averageNumbers.size();
    }

    public double getMax() {
        double max = 0.0;
        Iterator<Double> iterator = this.averageNumbers.iterator();
        while (iterator.hasNext()) {
            double d = iterator.next();
            if (d <= max) continue;
            max = d;
        }
        return max;
    }

    public double getMin() {
        double min = 9.9999999E7;
        Iterator<Double> iterator = this.averageNumbers.iterator();
        while (iterator.hasNext()) {
            double d = iterator.next();
            if (d >= min) continue;
            min = d;
        }
        if (min != Double.MAX_VALUE) {
            return min;
        }
        return 0.0;
    }

    public ArrayList<Double> getNumbers() {
        return this.averageNumbers;
    }

    public int getDataAmount() {
        return this.averageNumbers.size();
    }

    public void clear() {
        this.averageNumbers.clear();
    }
}

