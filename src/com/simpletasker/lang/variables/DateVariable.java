package com.simpletasker.lang.variables;

import com.simpletasker.common.exceptions.SimpleTaskException;

import java.text.DateFormat;
import java.text.ParseException;
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

    public Date getActualValue() throws SimpleTaskException {
        try {
            return DateFormat.getTimeInstance().parse(value); //i hope ??
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SimpleTaskException("Could not convert date properly:" + value);
        }
    }
}
