package springevaluateexp.Spring.Boot.Evaluate.Exp;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Stack;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class EvaluateBoolean {
	
	String content ;
	public String mainCalculator (Expression eb) {
		
		
		 		 
	        content = eb.getExpression();
	
		   
			System.out.println("content:" + content);	
			String logic = findCondition(content);   
			System.out.println("logic:" + logic );
			
			
			ObjectMapper mapper = new ObjectMapper();
            // Java objects to JSON string - compact-print
            String jsonString;
            Result result = new Result();
            result.setResult(parseBoolExpr(logic));            
			try {
				jsonString = mapper.writeValueAsString(result);
				System.out.println("jsonresult:" + jsonString);
				System.out.println("Result:" + result.isResult());
				return jsonString;
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
			return "abc";
			

	}
	
	public String findCondition (String str) {
		int i =0 ;
		char[] ch = str.toCharArray(); 
		String tempStr = "" ;
		String simpleCond = "";
		
		String result = "";
		String substring = "";
		ObjectMapper mapper = new ObjectMapper();
		
		int len = str.length();
		while (i < len){
			
		    if (tempStr.equals(" ")) {
		    	tempStr = Character.toString(ch[i]);
		    	System.out.println("temp:"+tempStr);
		    }else {
			tempStr += ch[i] ;
			System.out.println("temp:"+tempStr);
		    }
			if( tempStr.equals("{\"$AND\":")) {
				System.out.println("temp:"+tempStr);
				result += "&(";	
				tempStr = " ";
			}else if (tempStr.equals("{\"$OR\":") ){
				System.out.println("temp:"+tempStr);
				result += "|(";
				tempStr = " ";
			}else if (tempStr.equals("{\"$NOT\":")) {
				System.out.println("temp:"+tempStr);
				result += "!(";
				tempStr = " ";
		    }else if (tempStr.equals("{\"$IS\":")) {
		    	System.out.println("temp:"+tempStr);
		    	result += "$(";
		    	tempStr = " ";
		    }else if (tempStr.equals(",")) {
		    	System.out.println("temp:"+tempStr);
		    	result += ",";
		    	tempStr = " ";
		    }else if (tempStr.equals("[")) {
		    	System.out.println("temp:"+tempStr);
		    	tempStr = " ";
		    }else if (tempStr.equals("]")) {
		    	System.out.println("temp:"+tempStr);
		    	tempStr = " ";
		    }else if (tempStr.equals("}")) {
		    	System.out.println("temp:"+tempStr);
		    	result += ")";
		    	tempStr = " ";
		    }else if (tempStr.equals("{\"COND\":")) {
		    	System.out.println("temp:"+tempStr);
		    	tempStr = " ";
		    	simpleCond ="";
		    	substring = str.substring(i+1);
		    	String[] parts = substring.split("}}");
		    	String condString = parts[0]; 
		    	String remainderString = parts[1];
		    	simpleCond = condString + "}";
		    	System.out.println("Simple cond:" + simpleCond);
		    	System.out.println("Cond:" + condString);
		    	System.out.println("Remainder:" + remainderString);
		    		           
	            try {
	            	Cond condition = mapper.readValue(simpleCond, Cond.class);
	     	        		System.out.println("Value:"+condition.getVal());
	     	        		System.out.println("Variable:"+condition.getVar());
	     	        		System.out.println("Operator:"+condition.getOperator());
	            	result = evaluateExpression(condition, result);
	            	} catch (JsonMappingException e) {
	            	// TODO Auto-generated catch block
	            	e.printStackTrace();
	            	} catch (JsonProcessingException e) {
	            	// TODO Auto-generated catch block
	            	e.printStackTrace();
		        }
	             i = i + condString.length() + 2 ;
	            
		    }else if (tempStr.equals("{\"FLAG\":")) {
		    	tempStr = " ";
		    	simpleCond ="";
		       	substring = str.substring(i+1);
		    	String[] parts = substring.split("}}");
		    	String condString = parts[0]; 
		    	String remainderString = parts[1];
		    	simpleCond = condString + "}";
		    	System.out.println("Simple cond:" + simpleCond);
		    	System.out.println("Cond:" + condString);
		    	System.out.println("Remainder:" + remainderString);
		    	try {
	            	Flag condition = mapper.readValue(simpleCond, Flag.class);
	            	result = evaluateFlag(condition, result);
	            	} catch (JsonMappingException e) {
	            	// TODO Auto-generated catch block
	            	e.printStackTrace();
	            	} catch (JsonProcessingException e) {
	            	// TODO Auto-generated catch block
	            	e.printStackTrace();
		        }
		        i = i + condString.length() + 2 ;
		    }
			i++;
		}
		return result ;

}
	public String evaluateExpression(Cond condition1, String result1) {
		System.out.println("Value:"+condition1.getVal());
 		System.out.println("Variable:"+condition1.getVar());
 		System.out.println("Operator:"+condition1.getOperator());
		String string1 = "";
		switch (condition1.getVar()) {
		case "VAR1":
			string1 = "VAL1";
			break;
		case "VAR2":
			string1 = "VAL2";
			break;
		case "VAR3":
			string1 = "VAL3";
			break;
		case "VAR4":
			string1 = "VAL4";
			break;
		case "VAR5":
			string1 = "VAL5";
			break;
		case "VAR6":
			string1 = "VAL6";
			break;
		case "VAR7":
			string1 = "VAL7";
			break;
		case "VAR8":
			string1 = "VAL8";			
		}
		System.out.println("String1:"+string1);
		System.out.println("Value1:"+condition1.getVal());
		switch (condition1.getOperator()) {
	
		case "==":
			if (string1.equals(condition1.getVal())){
				result1 += "t";
			}else {
				result1 += "f";
			}
			System.out.println("result1:" + result1);
		}
		
		return result1;
	}
	
	
	public String evaluateFlag (Flag condition1, String result1) {
		String string1 = "";
		switch (condition1.getFlag()) {
		case "DECEASED":
			string1 = "N";
			break;
		case "RESIDENT":
			string1 = "Y";
			break;
		}
		if (string1.equals("Y")) {
			result1 += "t";
		}else {
			result1 += "f";
		}
		System.out.println("result1:" + result1);
	
		return result1;
	}
	
	public boolean parseBoolExpr(String expression) {
        Stack<Character> operators = new Stack<>();
        Stack<Character> operands = new Stack<>();
        
        for (char c: expression.toCharArray()) {
            if (c == 't' || c == 'f' || c == '(') {
                operands.push(c);
            } else if (c == '!' || c == '&' || c == '|' || c == '$') {
                operators.push(c);
            } else if (c == ')') {
                char operator = operators.pop();
                boolean result = operands.pop() == 't' ? true : false;
                if (operator == '!') {
                    result = !result;
                } else if (operator == '$') {
                	result = result;
                }
                
                while (operands.peek() != '(') {
                    boolean operand = operands.pop() == 't' ? true : false;
                    if (operator == '&') {
                        result = result && operand;
                    } else {
                        result = result || operand;
                    }
                }
                operands.pop();
                
                operands.push(result == true ? 't' : 'f');
            }
        }
        
        return operands.peek() == 't' ? true : false;
    }
	
}

