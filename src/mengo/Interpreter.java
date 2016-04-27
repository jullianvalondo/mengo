/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mengo;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Jullian
 */
public class Interpreter {

    RuleMatch Matcher = new RuleMatch();
    Boolean ErrorFlag;
    HashMap<String, TaskObject> TaskMap = new HashMap();
    HashMap<String, Variable> GlobalVariable = new HashMap();
    Stack<HashMap<String, Variable>> LocalStackVariable = new Stack();
    //working variable
    ArrayList<ID> TemporaryProtocol;
    Boolean scopeFlagGlobal;

    Interpreter() {
        scopeFlagGlobal = true;
        ErrorFlag = false;
    }

    void interpret(TreeNode root) throws IOException {
        //
        //System.out.println("FirstPass");
        FirstPass(root);
        //if error is found on first pass.
        if (ErrorFlag) {
            System.out.println("Error Found");

        } else {
            Program(root);
        }

        //System.out.println(GlobalVariable);
        //System.out.println(LocalStackVariable);
    }

    void Program(TreeNode root) throws IOException {
        //check if Initialization exist
        if (Matcher.Match("1", root) || Matcher.Match("2", root)) {
            
            //initialization exist
            TreeNode initialization = root.getTree("initialization");
            Initialization(initialization);
        }
        scopeFlagGlobal = false;
        
        //goto Main
        Main(root.getTree("main"));
    }

    void Main(TreeNode main) throws IOException {
        //check if contains statements
        if (Matcher.Match("5", main)) {
            LocalStackVariable.push(new HashMap<>());
            Statements(main.getTree("statements"));
            //System.out.println(LocalStackVariable.peek());
            LocalStackVariable.pop();
        } else if (Matcher.Match("6", main)) {

        } else {
            System.out.println("Invalid Tree for main.");
        }
    }

    void Statements(TreeNode statement) throws IOException {
        if (Matcher.Match("26", statement)) {
            Initialization(statement.getTree("initialization"));
            Statements(statement.getTree("statements"));
        } 
        else if(Matcher.Match("29", statement)){
            AssignmentStatement(statement.getTree("assignmentstatement"));
            Statements(statement.getTree("statements"));
        }
        else if (Matcher.Match("31", statement)) {
            IOStatement(statement.getTree("iostatement"));
            Statements(statement.getTree("statements"));
        } else if (Matcher.Match("34", statement)) {
            Initialization(statement.getTree("initialization"));
        } 
        else if(Matcher.Match("37", statement)){
            AssignmentStatement(statement.getTree("assignmentstatement"));
        }
        else if (Matcher.Match("39", statement)) {
            IOStatement(statement.getTree("iostatement"));
        }
    }

    void IOStatement(TreeNode IOStatement) throws IOException {
        //if input
        if (Matcher.Match("48", IOStatement)) {
            Scanner console = new Scanner(System.in);
            String id = IOStatement.getToken(TokenType.ID).token.getLexeme();
            assign(id, console.nextLine());
        } else if (Matcher.Match("49", IOStatement)) {
            String id = IOStatement.getToken(TokenType.ID).token.getLexeme();
            System.out.print(getIDValue(id));
        } else {
            System.out.println("Invalid tree for IOStatement");
        }
    }

    Object getIDValue(String idName) {
        //check id if it exist in local stack
        if (LocalStackVariable.peek().containsKey(idName)) {
            return LocalStackVariable.peek().get(idName).getValue();
        } else if (GlobalVariable.containsKey(idName)) {
            return GlobalVariable.get(idName).getValue();
        } else {
            System.out.println("\n\nError! Undeclared variable use for: " + idName + "\n\n");
            return null;
        }
        //check id if it exist in global
    }

    void Initialization(TreeNode Initialization) throws IOException {
        //check if initialization contains assignment
        if (Matcher.Match("23", Initialization) || Matcher.Match("25", Initialization)) {
            TreeNode declaration = Initialization.getTree("declaration");
            String idName = Declaration(declaration);
            Object Value = Value(Initialization.getTree("value"));
            assign(idName, Value);
        } //no assignment
        else if (Matcher.Match("22", Initialization) || Matcher.Match("24", Initialization)) {
            TreeNode declaration = Initialization.getTree("declaration");
            Declaration(declaration);
        } else {
            System.out.println("Invalid initializaton tree.");
            ErrorFlag = true;
            return;
        }
        if (Matcher.Match("22", Initialization) || Matcher.Match("23", Initialization)) {
            TreeNode init = Initialization.getTree("initialization");
            Initialization(init);
        }
    }

    String Declaration(TreeNode declaration) {
        String idName;
        //if permanent declaration
        //System.out.println("Declaring: ");
        if (Matcher.Match("17", declaration)) {

            idName = declaration.getToken(TokenType.ID).token.getLexeme();
            DataType type = DataType.valueOf(declaration.getTree("datatype").getChild(0).token.getLexeme());
            Variable temp = new Variable(new ID(idName, type, true));
            declare(idName, temp);
        } //not permanent declaration
        else if (Matcher.Match("18", declaration)) {
            idName = declaration.getToken(TokenType.ID).token.getLexeme();
            DataType type = DataType.valueOf(declaration.getTree("datatype").getChild(0).token.getLexeme());
            Variable temp = new Variable(new ID(idName, type, false));
            declare(idName, temp);
        } else {
            System.out.println("Invalid Declaration Tree.");
            return null;
        }
        return idName;
    }

    void AssignmentStatement(TreeNode AssignmentStatement) throws IOException{
        if(Matcher.Match("45", AssignmentStatement)){
            String idName = AssignmentStatement.getToken(TokenType.ID).token.getLexeme();
            Object assignedValue = E(AssignmentStatement.getTree("E"));
            
            assign(idName, assignedValue);
        }
        else{
            System.out.println("Invalid string Assignment Statement Tree.");
        }
    }
    
    Object E(TreeNode e) throws IOException {
        //E + T
        if (Matcher.Match("60", e)) {
            
            Cast castE = new Cast(E(e.getTree("E")));
            Cast castT = new Cast(T(e.getTree("T")));
            return (Double) (castE.getNUMBER() + castT.getNUMBER());
        }
        //E - T
        else if (Matcher.Match("61", e)) {
            Cast castE = new Cast(E(e.getTree("E")));
            Cast castT = new Cast(T(e.getTree("T")));
            return (Double) (castE.getNUMBER() - castT.getNUMBER());
        }
        else if(Matcher.Match("62", e)){
            return T(e.getTree("T"));
        }
        else{
            System.out.println("Invalid tree for E");
        }
        return null;
    }

    Object T(TreeNode t) throws IOException {
        //t mul f
        if (Matcher.Match("63", t)) {
            Cast castT = new Cast(T(t.getTree("T")));
            Cast castF = new Cast(F(t.getTree("F")));
            return (Double) (castT.getNUMBER() * castF.getNUMBER());
        } //t / f
        else if (Matcher.Match("64", t)) {
            Cast castT = new Cast(T(t.getTree("T")));
            Cast castF = new Cast(F(t.getTree("F")));
            if (castF.getNUMBER() == 0) {
                System.out.println("\n\nError! Division by 0");
                return 0;
            }
            return (Double) (castT.getNUMBER() / castF.getNUMBER());
        } //t % f
        else if (Matcher.Match("65", t)) {
            Cast castT = new Cast(T(t.getTree("T")));
            Cast castF = new Cast(F(t.getTree("F")));
            return (Double) (castT.getNUMBER() % castF.getNUMBER());
        } 
        else if(Matcher.Match("66", t)){
            return F(t.getTree("F"));
        }
        else {
            System.out.println("Invalid Tree for Term.");
            return null;
        }
    }

    Object F(TreeNode f) throws IOException {
        //f.
        // (E)
        //System.out.println(f.getChildren());
        if (Matcher.Match("67", f)) {
            return E(f.getTree("E"));
        } //id
        else if (Matcher.Match("68", f)) {
            return getIDValue(f.getToken(TokenType.ID).token.getLexeme());
        } else if (Matcher.Match("69", f)) {
            return Value(f.getTree("value"));
        } else {
            
            System.out.println("Invalid tree for F.");
        }
        return null;
    }

    Object Value(TreeNode Value) throws IOException {
        //unary
        if (Matcher.Match("71", Value)) {
            Double number = Double.parseDouble(Value.getToken(TokenType.NUMCONST).token.getLexeme());
            String sign = Unaryoperator(Value.getTree("unaryoperator"));
            if (sign == "+") {
                return number;
            } else if (sign == "-") {
                return number * -1;
            } else {
                System.out.println("Invalid tree for TreeNode containing unaryoperator.");
                return null;
            }
        } //litstring
        else if (Matcher.Match("70", Value)) {
            return Value.getToken(TokenType.LITSTRING).token.getLexeme();
        } else if (Matcher.Match("72", Value)) {
            if (Value.getToken(TokenType.BOOLEANCONST).token.getLexeme() == "TRUE") {
                return true;
            }
            if (Value.getToken(TokenType.BOOLEANCONST).token.getLexeme() == "FALSE") {
                return false;
            } else {
                return null;
            }
        } else if (Matcher.Match("73", Value)) {
            return Double.parseDouble(Value.getToken(TokenType.NUMCONST).token.getLexeme());
        } else if (Matcher.Match("74", Value)) {
            return TaskCall(Value.getTree("taskcall"));
        } else {
            System.out.println("Invalid TreeNode for value.");
            return null;
        }
    }

    Object TaskCall(TreeNode TaskCall) throws IOException {
        //contains only id
        if (Matcher.Match("14", TaskCall)) {
            String idName = TaskCall.getToken(TokenType.ID).token.getLexeme();
            //check if task call
            if (TaskMap.containsKey(idName)) {
                TaskObject taskcall = TaskMap.get(idName);
                //push new variable table stack
                LocalStackVariable.add(new HashMap());
                Statements(taskcall.getStatementList());
                LocalStackVariable.pop();
                //pop local stack variable
                //taskcall.
            } //check if declared at local scope
            else if (LocalStackVariable.peek().containsKey(idName)) {
                return LocalStackVariable.peek().get(idName).getValue();
            } //check to global variable
            else if (GlobalVariable.containsKey(idName)) {
                return GlobalVariable.get(idName).getValue();
            } else {
                System.out.println("\n\nError! Undeclared variable use: " + idName);
                return null;
            }
        }
        return null;
    }

    String Unaryoperator(TreeNode unaryoperator) {
        if (Matcher.Match("75", unaryoperator)) {
            return "+";
        } else if (Matcher.Match("76", unaryoperator)) {
            return "-";
        } else {
            System.out.println("Invalid Tree for unary operator.");
            return null;
        }
    }

    void declare(String idName, Variable var) {
        if (scopeFlagGlobal == true) {
            GlobalVariable.put(idName, var);
        } else {
            LocalStackVariable.peek().put(idName, var);
        }
    }

    //checks data type 
    void assign(String idName, Object Value) throws IOException {
        //process excape characters
        Properties p = new Properties();
        p.load(new StringReader("key=" + Value));
        //System.out.println("Escaped value: " + );
        Value = p.getProperty("key");
        //insert casting here
        Cast castValue = new Cast(Value);
        Variable currentVariable;
        //get Variable
        //check existence of variable at local scope
        if (scopeFlagGlobal == false && LocalStackVariable.peek().containsKey(idName)) {
            currentVariable = LocalStackVariable.peek().get(idName);
        } //check if variable exist at global scope
        else if (GlobalVariable.containsKey(idName)) {
            currentVariable = GlobalVariable.get(idName);
        } else {
            System.out.println("Variable " + idName + " is undeclared.");
            ErrorFlag = true;
            return;
        }
        //System.out.println(idName + " = " + Value);
        switch (currentVariable.idAttributes.getDataType()) {
            case NUMBER:
                updateVariable(currentVariable, castValue.getNUMBER());
                break;
            case BOOLEAN:
                updateVariable(currentVariable, castValue.getBOOLEAN());
                break;
            case STRING:
                updateVariable(currentVariable, castValue.getSTRING());
                break;
        }
    }

    //checks if permanent
    void updateVariable(Variable a, Object Value) {
        if (a.idAttributes.IsPermanent() == false || a.idAttributes.isInitialized() == false) {
            a.setValue(Value);
        } else {
            System.out.println("\n\nError! Invalid assignment to a Permanent variable: " + a.idAttributes.getIDName() + "\n\n");
            ErrorFlag = true;
        }
    }

    void FirstPass(TreeNode root) {
        //Create map for task id and root reference, parameter protocol.
        createTaskMap(root);
        //System.out.println(TaskMap.toString());
    }

    void addTaskAttribute(String idName, TaskObject taskAttribute) {
        //System.out.println(taskAttribute.toString());
        TaskMap.put(idName, taskAttribute);
    }

    void createTaskMap(TreeNode root) {
        //check if main root contains TASK
        while (root.getChildren().size() >= 5 && root.getChildren().get(4).getValue() == "task") {
            //root == Task
            root = root.getChildren().get(4);

            TreeNode TaskDeclaration = root.getChildren().get(0);
            if (TaskDeclaration.getValue() == "taskdeclaration") {
                Token tempID = TaskDeclaration.getChild(1).token;
                if (tempID.getKind() == TokenType.ID) {
                    //System.out.println("task found: " + tempID.getLexeme());
                    //check if taskdeclaration contains parameter passing through 3rd token
                    if (TaskDeclaration.getChild(2).token.getKind() == TokenType.PERIOD) {
                        //if taskdeclaration -> task id period
                        addTaskAttribute(tempID.getLexeme(), new TaskObject(tempID.getLexeme(), root, 0, new <ID> ArrayList()));
                    } else if (TaskDeclaration.getChild(2).token.getKind() == TokenType.INVOLVES) {
                        TemporaryProtocol = new ArrayList();
                        int NumberOfChild = CheckParamList(TaskDeclaration.getChild(4));
                        addTaskAttribute(tempID.getLexeme(), new TaskObject(tempID.getLexeme(), root, NumberOfChild, TemporaryProtocol));
                    } else {
                        System.out.println("Invalid children for taskcalldeclaration.");
                        ErrorFlag = true;
                        return;
                    }
                    //assign the parent and parameter size to ID
                }
            } else {
                System.out.println("Error Invalid Tree for TASK");
                ErrorFlag = true;
                return;
            }
        }
    }

    //counts number of parameters
    int CheckParamList(TreeNode TaskCallDec) {
        if (TaskCallDec.Variable != "taskcalldec") {
            System.out.println("Error in checking parameter list. Wrong Tree Input.");
            return 0;
        } else {
            //traverse declaration child to instantiate

            //if production taskcalldec -> DECLARATION
            if (TaskCallDec.getChildren().size() == 1 && TaskCallDec.getChild(0).Variable == "declaration") {
                TreeNode Declaration = TaskCallDec.getChild(0);
                UpdateTempParamList(Declaration);

                return 1;
            } //traverse to suceeding taskcalldec
            else if (TaskCallDec.getChildren().size() == 3 && TaskCallDec.getChild(0).Variable == "declaration") {
                TreeNode Declaration = TaskCallDec.getChild(0);
                UpdateTempParamList(Declaration);
                return CheckParamList(TaskCallDec.getChild(2)) + 1;
            } else {
                System.out.println("Error in TaskCallDec Tree Child");
                ErrorFlag = true;
                return 0;
            }
        }
    }

    void UpdateTempParamList(TreeNode Declaration) {
        if (Declaration.Variable == "declaration") {
            //System.out.println(Declaration.getChildren());
            //check if permanent
            if (Declaration.getChild(0).token != null && Declaration.getChild(0).token.getKind() == TokenType.PERMANENT) {
                //System.out.println("Permanent");
                String idName = Declaration.getChild(2).token.getLexeme();
                DataType dt = DataType.valueOf(Declaration.getChild(1).getChild(0).token.getKind().name());
                ID tempID = new ID(idName, dt, true);
                TemporaryProtocol.add(tempID);
                //System.out.println(tempID.toString());
            } else if (Declaration.getChild(0).getValue() == "datatype") {
                //System.out.println("Not permanent.");
                String idName = Declaration.getChild(1).token.getLexeme();
                DataType dt = DataType.valueOf(Declaration.getChild(0).getChild(0).token.getKind().name());
                ID tempID = new ID(idName, dt, false);
                TemporaryProtocol.add(tempID);
                //System.out.println(tempID.toString());
            } else {
                System.out.println("Invalid Children of Declaration.");
                ErrorFlag = true;
                return;
            }
        } else {
            System.out.println("Invalid Tree for declaration.");
            ErrorFlag = true;
            return;
        }
    }
}
