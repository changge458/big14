package com.oldboy.hadoop.mapreduce.day05.join.reducer;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyComparator extends WritableComparator {

    public MyComparator() {
        super(Compkey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Compkey ck1 = (Compkey) a;
        Compkey ck2 = (Compkey) b;

        return ck1.getUid() - ck2.getUid();

    }
}
