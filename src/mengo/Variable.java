/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mengo;

/**
 *
 * @author Jullian
 */
public class Variable {

    ID idAttributes;
    private String Stringvalue;
    private Boolean Boolvalue;
    private Double Numbervalue;

    Object getValue() {
        if (idAttributes.getDataType() == DataType.BOOLEAN) {
            return Boolvalue;
        }
        if (idAttributes.getDataType() == DataType.NUMBER) {
            return Numbervalue;
        }
        if (idAttributes.getDataType() == DataType.STRING) {
            return Stringvalue;
        }
        return Stringvalue;
    }

    void setValue(Object A) {
        if (A.getClass() == String.class) {
            Stringvalue =(String) A;
            Boolvalue =  null;
            Numbervalue = null;
        }
        if (A.getClass() == Boolean.class) {
            Stringvalue =null;
            Boolvalue =  (Boolean)A;
            Numbervalue = null;
        }
        if (A.getClass() == Double.class) {
            Stringvalue =null;
            Boolvalue =  null;
            Numbervalue = (Double)A;
        }

    }
}
