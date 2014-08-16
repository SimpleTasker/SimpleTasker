package com.simpletasker.lang.variables;

import com.simpletasker.common.exceptions.WrongTypeException;

import java.util.Date;

/**
 * Created by David on 9-8-2014.
 */
public class Variable {

    public static final Variable voidVariable = new Variable(Type.VOID);
    String value;
    private Type type;

    public Variable(Type type) {
        this(type,"");
    }

    public Variable(Type type,String value) {
        this.type = type;
        this.value = value;
    }

    public static Variable getTypeVariable(Type type,String value) {
        switch (type) {
            case STRING:
                return new StringVariable(value);
            case BOOL:
                return new BooleanVariable(value);
            case NUMBER:
                return new DoubleVariable(value);
            case DATE:
                return new DateVariable(value);
            case VOID:
                return voidVariable;
        }
        return new StringVariable("");
    }

    public static Variable getVariableFromString(String in) {
        System.out.println("In=" + in);
        String var = in.replaceAll("\\s","");
        if(var.startsWith("\"") && var.endsWith("\"")){
            return new StringVariable(var.substring(1,var.length()-1));
        }
        if(var.equalsIgnoreCase("true") || var.equalsIgnoreCase("false")) {
            return new BooleanVariable(var);
        }
        if(var.matches("[\\d|.|,]*")) {
            return new DoubleVariable(var);
        }
        return voidVariable;
    }

    public String getValueOfType(Type retType) throws WrongTypeException {
        if(this.type!=retType) {
            throw new WrongTypeException("Trying to return a " + retType.toString() + " but variable is type of " + type.toString());
        }
        return value;
    }

    public Variable castToSpecific(Type type) throws WrongTypeException {
        return getTypeVariable(type,getValueOfType(type));
    }

    @Override
    public String toString() {
        return "[Type=" + type.toString() + ",value=" + value + "]";
    }

    public String asString() throws WrongTypeException {
        return ((StringVariable)castToSpecific(Variable.Type.STRING)).getActualValue();
    }

    public boolean asBool() throws WrongTypeException {
        return ((BooleanVariable)castToSpecific(Variable.Type.BOOL)).getActualValue();
    }

    public double asNumber() throws WrongTypeException {
        return ((DoubleVariable)castToSpecific(Type.NUMBER)).getActualValue();
    }

    public Date asDate() throws WrongTypeException {
        return ((DateVariable)castToSpecific(Type.DATE)).getActualValue();
    }

    public static enum Type {
        NUMBER,
        STRING,
        BOOL,
        DATE,
        VOID;

        @Override
        public String toString() {
            return name().substring(0,1) + name().substring(1).toLowerCase();
        }
    }

}
