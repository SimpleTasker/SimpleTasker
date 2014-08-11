package com.simpletasker.lang.variables;

/**
 * Created by David on 11-8-2014.
 */
public class BooleanVariable extends Variable {
    public BooleanVariable(String s) {
        super(Type.BOOL);
        this.value = s;
    }

    public boolean getActualValue() {
        return value.equalsIgnoreCase("true");
    }
}
