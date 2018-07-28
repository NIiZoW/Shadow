/*
 * Decompiled with CFR 0_118.
 */
package fr.taeron.shadow.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmallAverageCollector {
    private List<Double> averages = new ArrayList<Double>();
    private int collection;

    public SmallAverageCollector(int collection) {
        this.collection = collection;
    }

    public void add(double d) {
        if (this.averages.size() >= this.collection) {
            ArrayList<Double> newValues = new ArrayList<Double>();
            newValues.add(d);
            int i = 1;
            while (i < this.averages.size()) {
                newValues.add(this.averages.get(i - 1));
                ++i;
            }
            this.averages = newValues;
        } else {
            this.averages.add(d);
        }
    }

    public double getAverage() {
        double total = 0.0;
        Iterator<Double> iterator = this.averages.iterator();
        while (iterator.hasNext()) {
            double d = iterator.next();
            total += d;
        }
        return total / (double)this.averages.size();
    }

    public int size() {
        return this.averages.size();
    }

    public void clear() {
        this.averages.clear();
    }
}

