package com.simpletasker.lang.variables;

/**
 * Created by David on 11-8-2014.
 */
public class DoubleVariable extends Variable {
    public DoubleVariable(String s) {
        super(Type.NUMBER);
        this.value = s;
    }

    public double getActualValue() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println("Could not parse number from:"+value);
        }
        return 0.0;
    }

}
