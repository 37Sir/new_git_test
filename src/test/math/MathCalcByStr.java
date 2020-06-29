package test.math;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class MathCalcByStr {
	private static final String INT = "INT";
	private static final String MIN = "MIN";
	private static final String MAX = "MAX";
	private static final String SIN = "SIN";
	private static final String COS = "COS";
	
	private static final String N = "n"; 
	
	public double eval(String exp, int n){
		List<String> list = infixExpToPostExp(exp, n);
		return doEval(list);
	} 
	
	public int evalReturnInt(String exp, int n){
		return (int) eval(exp, n);
	}
	
	private double doEval(List<String> list){
		Stack<String> stack = new Stack<String>();
		String element;
		double n1, n2, result;
		try{
			for (int i = 0; i < list.size(); i++) {
				element = list.get(i);
				if(isOperation(element)){
					n2 = Double.parseDouble(stack.pop());
					n1 = Double.parseDouble(stack.pop());
					
					result = doOperation(n1, n2, element);
					stack.push(new Double(result).toString());
				} else if(isFunc(element)){
					result = doFunc(stack, element);
					stack.push(new Double(result).toString());
				} else{
					stack.push(element);
				}
			}
			return Double.parseDouble(stack.pop());
		}catch(Exception e){
			throw new IllegalExpressionException(e.getMessage());
		}
	}
	
	private double doFunc(Stack<String> stack, String operator){
		if(INT.equals(operator)){
			double n2 = Double.parseDouble(stack.pop());
			return (int)n2;
		}else if(SIN.equals(operator)){
			double n2 = Double.parseDouble(stack.pop());
			return Math.sin(n2);
		}else if(COS.equals(operator)){
			double n2 = Double.parseDouble(stack.pop());
			return Math.cos(n2);
		}else if(MIN.equals(operator)){
			double n2 = Double.parseDouble(stack.pop());
			double n1 = Double.parseDouble(stack.pop());
			return Math.min(n2, n1);
		}else if(MAX.equals(operator)){
			double n2 = Double.parseDouble(stack.pop());
			double n1 = Double.parseDouble(stack.pop());
			return Math.max(n2, n1);
		}
		return 0;
	}
	
	private double doOperation(double n1, double n2, String operator){
		if(operator.equals("+")){
			return n1 + n2;
		}else if(operator.equals("-")){
			return n1 - n2;
		}else if(operator.equals("*")){
			return n1 * n2;
		}else if(operator.equals("/")){
			return n1 / n2;
		}
		return 0;
	}
	
	private boolean isOperation(String str){
		return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
	}
	
	private boolean isFunc(String str){
		return INT.startsWith(str.toUpperCase()) || MIN.startsWith(str.toUpperCase()) || MAX.startsWith(str.toUpperCase()) || SIN.startsWith(str.toUpperCase()) || COS.startsWith(str.toUpperCase());
	}
	
	private List<String> infixExpToPostExp(String exp, int n){
		List<String> postExp = new ArrayList<String>();
		StringBuffer numBuffer = new StringBuffer();
		StringBuffer funcBuffer = new StringBuffer();
		Stack<String> opStack = new Stack<String>();
		String ch, preChar;
		try{
			opStack.push("BEGIN");
			
			for (int i = 0; i < exp.length(); i++) {
				ch = String.valueOf(exp.charAt(i));
				switch (ch) {
				case "+":
				case "-":
				case "*":
				case "/":
					preChar = opStack.peek();
					while (priority(preChar) >= priority(ch)) {
						postExp.add(preChar);
						opStack.pop();
						preChar = opStack.peek();
					}
					opStack.push(ch);
					break;
					
				case "(":
					opStack.push(ch);
					break;
				case ",":
					String tmpc = opStack.pop();
					while(!isFunc(tmpc)){
						if(!tmpc.equals("(")){
							postExp.add(tmpc);
						}
						tmpc = opStack.pop();
					}
					//把左括号放回去
					opStack.push(tmpc);
					opStack.push("(");
					break;
				case ")":
					String c = opStack.pop();
					while(!c.equals("(")){
						postExp.add(c);
						c = opStack.pop();
					}
					c = opStack.pop();
					if(isFunc(c)){
						postExp.add(c);
					}else{
						opStack.push(c);
					}
					break;
				default:
					if(".".equals(ch) || Character.isDigit(ch.toCharArray()[0])){
						while(Character.isDigit(ch.toCharArray()[0]) || ".".equals(ch)){
							numBuffer.append(ch);
							if(++i < exp.length())
								ch = String.valueOf(exp.charAt(i));
							else
								break;
						}
						i--;
						postExp.add(numBuffer.toString());
						numBuffer = new StringBuffer();
					}else if(Character.isLetter(ch.toCharArray()[0])){
						while(Character.isLetter(ch.toCharArray()[0])){
							funcBuffer.append(ch);
							if(++i < exp.length())
								ch = String.valueOf(exp.charAt(i));
							else
								break;
						}
						i--;
						
						if(isFunc(funcBuffer.toString())){
							opStack.add(funcBuffer.toString().toUpperCase());
						}
						if(isVariable(funcBuffer.toString())){
							postExp.add(String.valueOf(n));
						}
						funcBuffer = new StringBuffer();
					}
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//最后清理
		String c1;
		while(!opStack.isEmpty()){
			c1 = opStack.pop();
			if(!c1.equals("BEGIN")){
				postExp.add(c1);
			}
		}
		
		return postExp;
	}
	
	private boolean isVariable(String str){
		boolean result = false;
		if(str.equals(N)){
			result = true;
		}
		return result;
	}
	
	private int priority(String op){
		switch(op){
		case "BEGIN":
		case "(":
			return 0;
			
		case "+":
		case "-":
			return 1;
			
		case "*":
		case "/":
			return 2;
		}
		throw new IllegalExpressionException("Illegal operator");
	}
	
	class IllegalExpressionException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public IllegalExpressionException() {

		}
		public IllegalExpressionException(String info) {
			super(info);
		}
	}
	
	public static void main(String[] args) {
//		String exp = "20000 + 1 * 100 + (n - 1) * 500";
//		
//		double result = new MathCalcByStr().eval(exp, 2);
//		System.out.println(result);
	}
}
