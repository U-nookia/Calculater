package com.xpjun;

import java.util.Stack;

/**
 * Created by lenovo on 2016/11/3.
 */
public class Calculation {
    private Stack<String> numStack,numStack_temp;
    private Stack<String> symbolStack,symbolStack_temp;
    private boolean bracket = false,bracketFinish = false;

    public Calculation() {
    }

    public double getResult(String formula){
        if (bracket){
            numStack_temp = numStack;
            symbolStack_temp = symbolStack;
            numStack = new Stack<>();
            symbolStack = new Stack<>();
        }else {
            numStack = new Stack<>();
            symbolStack = new Stack<>();
        }
        numStack.removeAllElements();
        symbolStack.removeAllElements();
        double result = 0;
        if (""==formula.trim()) return 0;
        if (formula.trim().matches("[a-zA-Z]+")) return -1;
        if (formula.trim().matches("[`~!@#$%^&'.>|}]")) return -1;
        StringBuilder num_string = new StringBuilder();
        double num_double = 0;

        for(int i =0;i<formula.trim().length();i++){
            char numOrSym = formula.trim().charAt(i);
            if (numOrSym=='+'||numOrSym=='-'){
                num_double = Double.parseDouble(num_string.toString());
                if (symbolStack.empty()) numStack.push(num_string.toString());
                else {
                    switch (symbolStack.peek()){
                        case "+":
                            numStack.push(String.valueOf(Double.valueOf(numStack.pop())+Double.valueOf(num_string.toString())));
                            symbolStack.pop();
                            break;
                        case "-":
                            numStack.push(String.valueOf(Double.valueOf(numStack.pop())-Double.valueOf(num_string.toString())));
                            symbolStack.pop();
                            break;
                        case "*":
                            numStack.push(String.valueOf(Double.valueOf(numStack.pop())*Double.valueOf(num_string.toString())));
                            symbolStack.pop();
                            break;
                        case "/":
                            numStack.push(String.valueOf(Double.valueOf(numStack.pop())/Double.valueOf(num_string.toString())));
                            symbolStack.pop();
                            break;
                    }
                }
                symbolStack.push(String.valueOf(numOrSym));
                num_string.delete(0,num_string.length());
            }else if (numOrSym=='*'||numOrSym=='/'){
                num_double = Double.parseDouble(num_string.toString());
                if (symbolStack.empty()) numStack.push(num_string.toString());
                else {
                    switch (symbolStack.peek()){
                        case "*":
                            numStack.push(String.valueOf( Double.valueOf(numStack.pop())*Double.valueOf(num_string.toString())));
                            symbolStack.pop();
                            break;
                        case "/":
                            numStack.push(String.valueOf( Double.valueOf(numStack.pop())/Double.valueOf(num_string.toString())));
                            symbolStack.pop();
                            break;
                        default:
                            numStack.push(num_string.toString());
                            break;
                    }
                }
                symbolStack.push(String.valueOf(numOrSym));
                num_string.delete(0,num_string.length());
            }else if (numOrSym=='('){
                bracket = true;
                String numInBrackets = formula.trim().substring(i+1,formula.trim().indexOf(")"));
                double resultInBrackets = getResult(numInBrackets);
                num_string.append(String.valueOf(resultInBrackets));
                System.out.println(resultInBrackets+"");
                i = formula.trim().indexOf(")");
                if (bracketFinish){
                    numStack = numStack_temp;
                    symbolStack = symbolStack_temp;
                }
            }else {
                num_string.append(numOrSym);
            }
        }

        numStack.push(String.valueOf(num_string));
        double pop1,pop2;
        while (!symbolStack.empty()){
            pop1 = Double.valueOf(numStack.pop());
            pop2 = Double.valueOf(numStack.pop());
            switch (symbolStack.pop()){
                case "+":
                    if (!symbolStack.empty()){
                        switch (symbolStack.peek()){
                            case "+":
                                numStack.push(String.valueOf(pop2+pop1));
                                break;
                            case "-":
                                numStack.push(String.valueOf(pop2-pop1));
                                break;
                        }
                    }else {
                        numStack.push(String.valueOf(pop2+pop1));
                    }
                    break;
                case "-":
                    if (!symbolStack.empty()){
                        switch (symbolStack.peek()){
                            case "+":
                                numStack.push(String.valueOf(pop2-pop1));
                                break;
                            case "-":
                                numStack.push(String.valueOf(pop2+pop1));
                                break;
                        }
                    }else {
                        numStack.push(String.valueOf(pop2-pop1));
                    }
                    break;
                case "*":
                    numStack.push(String.valueOf(pop2*pop1));
                    break;
                case "/":
                    numStack.push(String.valueOf(pop2/pop1));
                    break;
            }
        }
        if (bracket){
            bracket = false;
            bracketFinish = true;
        }
        return Double.valueOf(numStack.pop());
    }
}
