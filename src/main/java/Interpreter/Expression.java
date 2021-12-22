/**
 * @(#) Expression.java
 */

package Interpreter;

public abstract class Expression
{
	public abstract float execute();

    public static boolean isOperator(String token){
		String operators = "\\+-\\*/%()";
        if (operators.contains(token)) {
             return true;
         }
         return false;
    }
	
	
	
}
