/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mengo;

import java.util.ArrayList;

/**
 *
 * @author Chester
 */
public class Rule {

    private final String RuleNumber;
    private final int NumberOfTokens;
    private ArrayList<TreeNode> RHS;
    private final String LHS;
    @Override
    public String toString(){
        if(RHS != null)
            return RuleNumber +": " + LHS + RHS;
        else{
            return RuleNumber +": " + LHS;
        }
    }
    Rule(String rulenum, String LeftHandSide, int numberOfTokens, ArrayList<TreeNode> production) {
        LHS = LeftHandSide;
        RHS = production;
        NumberOfTokens = numberOfTokens;
        RuleNumber = rulenum;
        if(production.size() != numberOfTokens){
            System.out.println("Invalid Initialization of Rule: " + rulenum +": " + production);
        }
    }

    Rule(String rulenum, String LeftHandSide, int numberOfTokens) {
        LHS = LeftHandSide;
        NumberOfTokens = numberOfTokens;
        RuleNumber = rulenum;
    }

    int getNumberOfTokens() {
        return NumberOfTokens;
    }

    String getRuleNumber() {
        return RuleNumber;
    }

    String getLeftHandSide() {
        return LHS;
    }

    Boolean MatchChildren(TreeNode ComparedTree) {
        ArrayList<TreeNode> TreeChild = ComparedTree.getChildren();

        if (TreeChild.size() != RHS.size()) {
            System.out.println("Not same");
            return false;
        } else {
            for (int i = 0; i < TreeChild.size(); i++) {
                TreeNode a = TreeChild.get(i);
                TreeNode b = RHS.get(i);
                //System.out.println(a.getComparison() + " " + b.getComparison());
                if (a.getComparison() != b.getComparison()) {
                    return false;
                }
            }
            return true;
        }
    }
}
