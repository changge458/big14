package com.oldboy.hadoop.mapreduce.day05.temptags;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class TemptagsComparator extends WritableComparator {

    public TemptagsComparator() {
        super(Compkey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Compkey ck1 = (Compkey) a;
        Compkey ck2 = (Compkey) b;

        return ck1.getBusId().compareTo(ck2.getBusId());

    }
}
