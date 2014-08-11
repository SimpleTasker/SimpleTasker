package com.simpletasker.lang;

import com.simpletasker.common.exceptions.WrongTypeException;

/**
 * Created by David on 9-8-2014.
 */
public class Variable {

    private Type type;

    private String value;

    public Variable(Type type) {
        this(type,"");
    }

    public Variable(Type type,String value) {
        this.type = type;
        this.value = value;
    }

    public String toType(Type retType) throws WrongTypeException {
        if(this.type!=retType) {
            throw new WrongTypeException("Trying to return a " + retType.toString() + " but variable is type of " + type.toString());
        }
        switch(type){
            case STRING:
                return value;

        }
        return "";
    }

    public static enum Type {
        NUMBER,
        STRING,
        BOOL,
        DATE;

        @Override
        public String toString() {
            return name().substring(0,1) + name().substring(1).toLowerCase();
        }
    }


}
