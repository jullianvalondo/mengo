package mengo;

import java.util.HashMap;

public class State {

    public final String state;
    //KEY (TokenType)
    private HashMap<TokenType, ActionItem> ActionItemList = new HashMap<>();
    //Key (Variable)
    private HashMap<String, GotoItem> GotoItemList = new HashMap<>();

    State(String s) {
        state = s;
    }

    Action getLRAction(TokenType tokentype) {
        ActionItem a = ActionItemList.get(tokentype);
        return a.GetLRAction();
    }
    String getGotoVariable(String variable){
        if (GotoItemList.get(variable) == null) {
            return "no action. Error, goto item not found";
        }
        //System.out.println(GotoItemList.get(variable).GotoState);
        return GotoItemList.get(variable).getGotoItem();
    }
    void addActionItem(TokenType tt, LRAction act, String state) {
        if (state == null) {
            if (act == LRAction.accept || act == LRAction.no_action) {
                ActionItemList.put(tt, new ActionItem(tt, act));
            } else {
                System.out.println("Error. Not able to add action item.");
            }
        } else {
            ActionItemList.put(tt, new ActionItem(tt, act, state));
        }
    }

    void addGotoItem(String v, String gs) {
        GotoItemList.put(v, new GotoItem(v, gs));
    }

    String getAction(String g) {
        if (GotoItemList.get(g) == null) {
            return "no action. Error, goto item not found";
        }
        return GotoItemList.get(g).getGotoItem();
    }
}

enum LRAction {

    no_action,
    shift,
    reduce,
    accept
}

class GotoItem {

    //find other data type for variable to ensure to add only variables that exist in the grammar.
    public final String Variable;
    public final String GotoState;

    GotoItem(String v, String gs) {
        if (v != null && gs != null) {
            Variable = v;
            GotoState = gs;
        } else if (v != null && gs == null) {
            Variable = v;
            GotoState = "No action, Error in table.";
        } else {
            Variable = "Error";
            GotoState = "No action, Error in table.";
        }
    }

    public String getGotoItem() {
        return GotoState;
    }
}

class ActionItem {

    //fix objects, create proper constructors
    public final TokenType Token;
    public final Action Act;

    ActionItem(TokenType t, LRAction a) {
        Act = new Action();
        Token = t;
        Act.Action = a;
        Act.ActionState = null;
    }

    ActionItem(TokenType t, LRAction a, String s) {
        Act = new Action();
        Token = t;
        Act.Action = a;
        Act.ActionState = s;
    }

    Action GetLRAction() {
        return Act;
    }

    @Override
    public String toString() {
        switch (Act.Action) {
            case accept:
                return "Accept";
            case no_action:
                return "No Action";
            case reduce:
                return "Reduce" + Act.ActionState;
            case shift:
                return "Shift" + Act.ActionState;
            default:
                return "Parsing Error, no action assigned to token " + Token.toString();
        }
    }

    //kalat here haha create proper constructors
}

class Action {

    public LRAction Action;
    public String ActionState;
}
