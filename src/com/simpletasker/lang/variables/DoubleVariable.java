package com.simpletasker.lang.variables;

import com.simpletasker.Lib;

/**
 * Created by David on 11-8-2014.
 */
public class DoubleVariable extends Variable {

    public DoubleVariable(double d) {
        this(Double.toString(d));
    }

    public DoubleVariable(String s) {
        super(Type.NUMBER);
        this.value = s;
    }

    public double getActualValue() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println(Lib.getLang("doubleVar.error")+value);
        }
        return 0.0;
    }

}
