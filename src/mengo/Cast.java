/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mengo;

/**
 *
 * @author win7
 */
public class Cast {

    private String STRING;
    private Double NUMBER;
    private Boolean BOOLEAN;

    public Cast() {
        STRING = "";
        BOOLEAN = true;
        NUMBER = new Double(0);
    }

    //LITSTRING
    public void StringCast(String StringVal) {
        STRING = StringVal;

        if ("".equals(StringVal)) {
            BOOLEAN = false;
            NUMBER = 0.0;
        } else {
            BOOLEAN = true;
            boolean dotExist = false;
            //System.out.println(StringVal + " THIS= "+StringVal.charAt(0));
            int x = 0;
            if (StringVal.charAt(0) == '+' || StringVal.charAt(0) == '-' || StringVal.charAt(0) == '.' || (StringVal.charAt(x) >= '0' && StringVal.charAt(x) <= '9')) {
                if (StringVal.charAt(0) == '+' || StringVal.charAt(0) == '-') {
                    x = 1;
                } else {
                    x = 0;
                }
                checker:
                for (; x < StringVal.length(); x++) {
                    //System.out.println(StringVal.charAt(x)+" isDot");                            
                    if ((StringVal.charAt(x) >= '0' && StringVal.charAt(x) <= '9') || StringVal.charAt(x) == '.') {
                        //System.out.println("NUMBER");
                        if (StringVal.charAt(x) == '.') {
                            if (!dotExist) {
                                dotExist = true;
                                x++;
                            } else {
                                NUMBER = 0.0;
                                //System.out.println(StringVal.charAt(x)+"STOP");
                                break checker;
                            }
                        }
                        if (x == StringVal.length() - 1) {
                            //System.out.println("ACCEPT");
                            NUMBER = Double.parseDouble(StringVal);
                        }
                    } else {
                        NUMBER = 0.0;
                        //System.out.println(StringVal.charAt(x)+"2STOP");
                        break checker;
                    }
                }
            } else {
                NUMBER = 0.0;
            }
        }

    }
    public Cast(Object val){
        if(val.getClass() == Double.class){
            DoubleCast((Double)val);
        }
        if(val.getClass() == String.class){
            StringCast((String)val);
        }
        if(val.getClass() == Boolean.class){
            BoolCast((Boolean)val);
        }
    }
    //NUMCONST
    public void DoubleCast(Double NumberVal) {
        STRING = NumberVal + "";

        if (NumberVal == 0) {
            BOOLEAN = false;
        } else {
            BOOLEAN = true;
        }
        NUMBER = NumberVal;
    }

    //BOOLEANCONST
    public void BoolCast(Boolean BooleanVal) {
        BOOLEAN = BooleanVal;
        if (BooleanVal) {
            STRING = "true";
            NUMBER = 1.0;
        } else {
            STRING = "false";
            NUMBER = 0.0;
        }
    }

    public String getSTRING() {
        return STRING;
    }

    public boolean getBOOLEAN() {
        return BOOLEAN;
    }

    public double getNUMBER() {
        return NUMBER;
    }

}
