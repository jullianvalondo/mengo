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
public class ID {
    private final DataType datatype;
    private final String idName;
    private final Boolean isPermanent;
    private Boolean isInitialized;
    ID(String Name, DataType dataType, Boolean Permanent){
        isInitialized = false;
        idName = Name;
        datatype = dataType;
        isPermanent = Permanent;
    }
    DataType getDataType(){
        return datatype;
    }
    String getIDName(){
        return idName;
    }
    Boolean IsPermanent(){
        return isPermanent;
    }
    public String toString(){
        return idName + " " + datatype.name() + " " + isPermanent.toString();
    }
    public void Initialized(){
        isInitialized = true;
    }
    Boolean isInitialized(){
        return isInitialized;
    }
}
enum DataType{
    NUMBER,
    STRING,
    BOOLEAN
}
