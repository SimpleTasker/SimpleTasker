package com.simpletasker.lang.variables;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by David on 11-8-2014.
 */
public class DateVariable extends Variable {

    public DateVariable(String s) {
        super(Type.DATE);
        this.value = s;
    }

    public Date getActualValue() {
        return Calendar.getInstance().getTime();
    }
}
