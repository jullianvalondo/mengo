/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mengo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jullian
 */
public final class RuleMatch {

    HashMap<String, Rule> RuleSet = new HashMap();

    RuleMatch() {
        FillNumberOfProductionsPerStates();
    }

    boolean Match(String Rule, TreeNode head) {

        Rule checker = RuleSet.get(Rule);

        return checker.MatchChildren(head);
    }

    void printProd() {
        Iterator it = RuleSet.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getValue());
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

    void FillNumberOfProductionsPerStates() {

        //rules for mengo grammar
        ArrayList<TreeNode> tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.HELLO)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("initialization"));
        tempprod.add(new TreeNode("main"));
        tempprod.add(new TreeNode("task"));
        tempprod.add(new TreeNode(new Token(TokenType.GOODBYE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));

        RuleSet.put("1", new Rule("1", "program", 7, tempprod));
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.HELLO)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("initialization"));
        tempprod.add(new TreeNode("main"));
        tempprod.add(new TreeNode(new Token(TokenType.GOODBYE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));

        RuleSet.put("2", new Rule("2", "program", 6, tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.HELLO)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("main"));
        tempprod.add(new TreeNode("task"));
        tempprod.add(new TreeNode(new Token(TokenType.GOODBYE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("3", new Rule("3", "program", 6, tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.HELLO)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("main"));
        tempprod.add(new TreeNode(new Token(TokenType.GOODBYE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("4", new Rule("4", "program", 5, tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.STARTHERE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.ENDHERE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("5", new Rule("5", "main", 5, tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.STARTHERE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode(new Token(TokenType.ENDHERE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("6", new Rule("6", "main", 4,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("taskdeclaration"));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.ENDTASK)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("task"));
        RuleSet.put("7", new Rule("7", "task", 5,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("taskdeclaration"));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.ENDTASK)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("8", new Rule("8", "task", 4,tempprod));
        tempprod = new ArrayList();
        
        tempprod.add(new TreeNode(new Token(TokenType.TASK)));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("9", new Rule("9", "taskdeclaration", 3,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.TASK)));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.INVOLVES)));
        tempprod.add(new TreeNode(new Token(TokenType.LPAREN)));
        tempprod.add(new TreeNode("taskcalldec"));
        tempprod.add(new TreeNode(new Token(TokenType.RPAREN)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("10", new Rule("10", "taskdeclaration", 7,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("declaration"));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("taskcalldec"));
        RuleSet.put("11", new Rule("11", "taskcalldec", 3,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("declaration"));
        RuleSet.put("12", new Rule("12", "taskcalldec", 1,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        RuleSet.put("13", new Rule("13", "taskcall", 1,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.INVOLVES)));
        tempprod.add(new TreeNode(new Token(TokenType.LPAREN)));
        tempprod.add(new TreeNode("paramlist"));
        tempprod.add(new TreeNode(new Token(TokenType.RPAREN)));
        RuleSet.put("14", new Rule("14", "taskcall", 5,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("value"));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("paramlist"));
        RuleSet.put("15", new Rule("15", "paramlist", 3,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("value"));
        RuleSet.put("16", new Rule("16", "paramlist", 1,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.PERMANENT)));
        tempprod.add(new TreeNode("datatype"));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        RuleSet.put("17", new Rule("17", "declaration", 3,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("datatype"));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        RuleSet.put("18", new Rule("18", "declaration", 2,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.NUMBER)));
        RuleSet.put("19", new Rule("19", "datatype", 1,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.STRING)));
        RuleSet.put("20", new Rule("20", "datatype", 1,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.BOOLEAN)));
        RuleSet.put("21", new Rule("21", "datatype", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("declaration"));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("initialization"));
        RuleSet.put("22", new Rule("22", "initialization", 3, tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("declaration"));
        tempprod.add(new TreeNode(new Token(TokenType.BE)));
        tempprod.add(new TreeNode("value"));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        tempprod.add(new TreeNode("initialization"));        
        RuleSet.put("23", new Rule("23", "initialization", 5, tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("declaration"));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));     
        RuleSet.put("24", new Rule("24", "initialization", 2, tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("declaration"));
        tempprod.add(new TreeNode(new Token(TokenType.BE)));
        tempprod.add(new TreeNode("value"));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));        
        RuleSet.put("25", new Rule("25", "initialization", 4, tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("initialization"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("26", new Rule("26", "statements", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("whilestatement"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("27", new Rule("27", "statements", 2,tempprod));
       
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("whilestatement"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("28", new Rule("28", "statements", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("assignmentstatement"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("29", new Rule("29", "statements", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("forloopstatement"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("30", new Rule("30", "statements", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("iostatement"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("31", new Rule("31", "statements", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("concatstatement"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("32", new Rule("32", "statements", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("returnstatement"));
        tempprod.add(new TreeNode("statements"));
        RuleSet.put("33", new Rule("33", "statements", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("initialization"));
        RuleSet.put("34", new Rule("34", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("whilestatement"));
        RuleSet.put("35", new Rule("35", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("ifstatement"));
        RuleSet.put("36", new Rule("36", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("assignmentstatement"));
        RuleSet.put("37", new Rule("37", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("forloopstatement"));
        RuleSet.put("38", new Rule("38", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("iostatement"));
        RuleSet.put("39", new Rule("39", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("concatstatement"));
        RuleSet.put("40", new Rule("40", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("returnstatement"));
        RuleSet.put("41", new Rule("41", "statements", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.WHILE)));
        tempprod.add(new TreeNode("logicalexpr"));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.ENDWHILE)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("42", new Rule("42", "whilestatement", 6,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.WHILE)));
        tempprod.add(new TreeNode("logicalexpr"));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.OTHERWISE)));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.ENDWHEN)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("43", new Rule("43", "ifstatement", 8,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.WHILE)));
        tempprod.add(new TreeNode("logicalexpr"));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.ENDWHEN)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("44", new Rule("44", "ifstatement", 6,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.BE)));
        tempprod.add(new TreeNode("E"));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("45", new Rule("45", "assignmentstatement", 4,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.FROM)));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.BE)));
        tempprod.add(new TreeNode("value"));
        tempprod.add(new TreeNode(new Token(TokenType.UNTIL)));
        tempprod.add(new TreeNode("value"));
        tempprod.add(new TreeNode(new Token(TokenType.INCREMENT)));
        tempprod.add(new TreeNode(new Token(TokenType.BY)));
        tempprod.add(new TreeNode("value"));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("statements"));
        tempprod.add(new TreeNode(new Token(TokenType.ENDFROM)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("46", new Rule("46", "forloopstatement", 13,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.RETURN)));
        tempprod.add(new TreeNode("logicalexpr"));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("47", new Rule("47", "returnstatement", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.ACCEPT)));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("48", new Rule("48", "iostatement", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.SHOW)));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("49", new Rule("49", "iostatement", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.CONCATENATE)));
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.BE)));
        tempprod.add(new TreeNode("concatprime"));
        tempprod.add(new TreeNode(new Token(TokenType.PERIOD)));
        RuleSet.put("50", new Rule("50", "concatstatement", 5,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("concatprime"));
        RuleSet.put("51", new Rule("51", "concatprime", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.LITSTRING)));
        tempprod.add(new TreeNode(new Token(TokenType.COMMA)));
        tempprod.add(new TreeNode("concatprime"));
        RuleSet.put("52", new Rule("52", "concatprime", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        RuleSet.put("53", new Rule("53", "concatprime", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.LITSTRING)));
        RuleSet.put("54", new Rule("54", "concatprime", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("E"));
        tempprod.add(new TreeNode(new Token(TokenType.RELOP)));
        tempprod.add(new TreeNode("E"));
        RuleSet.put("55", new Rule("55", "relationalexpr", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("E"));
        RuleSet.put("56", new Rule("56", "relationalexpr", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("relationalexpr"));
        tempprod.add(new TreeNode(new Token(TokenType.LOGOP)));
        tempprod.add(new TreeNode("relationalexpr"));
        RuleSet.put("57", new Rule("57", "logicalexpr", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.NOT)));
        tempprod.add(new TreeNode("relationalexpr"));
        RuleSet.put("58", new Rule("58", "logicalexpr", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("relationalexpr"));
        RuleSet.put("59", new Rule("59", "logicalexpr", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("E"));
        tempprod.add(new TreeNode(new Token(TokenType.ADD)));
        tempprod.add(new TreeNode("T"));
        RuleSet.put("60", new Rule("60", "E", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("E"));
        tempprod.add(new TreeNode(new Token(TokenType.SUB)));
        tempprod.add(new TreeNode("T"));
        RuleSet.put("61", new Rule("61", "E", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("T"));
        RuleSet.put("62", new Rule("62", "E", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("T"));
        tempprod.add(new TreeNode(new Token(TokenType.MUL)));
        tempprod.add(new TreeNode("F"));
        RuleSet.put("63", new Rule("63", "T", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("T"));
        tempprod.add(new TreeNode(new Token(TokenType.DIV)));
        tempprod.add(new TreeNode("F"));
        RuleSet.put("64", new Rule("64", "T", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("T"));
        tempprod.add(new TreeNode(new Token(TokenType.MOD)));
        tempprod.add(new TreeNode("F"));
        RuleSet.put("65", new Rule("65", "T", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("F"));
        RuleSet.put("66", new Rule("66", "T", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.LPAREN)));
        tempprod.add(new TreeNode("E"));
        tempprod.add(new TreeNode(new Token(TokenType.RPAREN)));
        RuleSet.put("67", new Rule("67", "F", 3,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.ID)));
        RuleSet.put("68", new Rule("68", "F", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("value"));
        RuleSet.put("69", new Rule("69", "F", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.LITSTRING)));
        RuleSet.put("70", new Rule("70", "value", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode("unaryoperator"));
        tempprod.add(new TreeNode(new Token(TokenType.NUMCONST)));
        RuleSet.put("71", new Rule("71", "value", 2,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.BOOLEANCONST)));
        RuleSet.put("72", new Rule("72", "value", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.NUMCONST)));
        RuleSet.put("73", new Rule("73", "value", 1,tempprod));
        
        tempprod = new ArrayList();
        tempprod.add(new TreeNode("taskcall"));
        RuleSet.put("74", new Rule("74", "value", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.UNARYPLUS)));
        RuleSet.put("75", new Rule("75", "unaryoperator", 1,tempprod));

        tempprod = new ArrayList();
        tempprod.add(new TreeNode(new Token(TokenType.UNARYMINUS)));
        RuleSet.put("76", new Rule("76", "unaryoperator", 1,tempprod));

        /*
         ArrayList<TreeNode> tempprod = new ArrayList();
         tempprod.clear();
         tempprod.add(new TreeNode(new Token(TokenType.LPAREN, "(")));
         tempprod.add(new TreeNode("A"));
         tempprod.add(new TreeNode(new Token(TokenType.RPAREN, ")")));
         RuleTable.put("1", new Rule("1", "A", tempprod, 3));
         tempprod.clear();
         tempprod.add(new TreeNode(new Token(TokenType.ID, "")));
         RuleTable.put("2", new Rule("2", "A", tempprod, 1));
         */
    }
}
