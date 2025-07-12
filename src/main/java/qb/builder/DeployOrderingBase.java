package qb.builder;

import org.apache.commons.lang3.tuple.MutablePair;

import javax.swing.*;

public interface DeployOrderingBase {
    default MutablePair<Object, SortOrder> asc() { return J2SQLShared.asc(this); }
    default MutablePair<Object, SortOrder> desc() { return J2SQLShared.desc(this); }
}
