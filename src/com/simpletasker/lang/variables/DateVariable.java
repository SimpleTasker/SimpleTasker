package com.simpletasker.lang.variables;

import java.util.Calendar;
import java.util.Date;

/**
 * Created on 11-8-2014.
 */
public class DateVariable extends Variable {

    public DateVariable(Date d) {
        this(d.toString());
    }

    public DateVariable(String s) {
        super(Type.DATE);
        this.value = s;
    }

    public Date getActualValue() {
        return Calendar.getInstance().getTime();
    }
}
