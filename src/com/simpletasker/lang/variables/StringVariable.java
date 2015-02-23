package com.simpletasker.lang.variables;

/**
 * Created on 11-8-2014.
 */
public class StringVariable extends Variable {

    public StringVariable(String s) {
        super(Type.STRING);
        this.value = s;
    }

    public String getActualValue() {
        return value;
    }
}
