/**
 * @(#) Multiply.java
 */

package Interpreter;

public class Multiply 

	extends Expression{

Expression left, right;
	
	public Multiply(Expression exp1, Expression exp2) {
		left = exp1;
		right = exp2;
	}

	public Multiply() {
		left = null;
		right = null;
	}
	
	@Override
	public float execute() {
		return left.execute() * right.execute(); //context.multiply(param);
	}
}
