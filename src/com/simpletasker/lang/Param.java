package com.simpletasker.lang;

import javax.print.DocFlavor;

/**
 * Created by David on 9-8-2014.
 */
public class Param {

    private Type type;


    public Param(Type type) {
        this.type = type;
    }

    private static enum Type {
        NUMBER,
        STRING,
        BOOL,
        DATE;
    }
}
