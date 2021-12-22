/**
 * @(#) Divide.java
 */

package Interpreter;

public class Divide extends Expression{

Expression left, right;
	
	public Divide(Expression exp1, Expression exp2) {
		left = exp1;
		right = exp2;
	}

	public Divide() {
		left = null;
		right = null;
	}
	
	@Override
	public float execute() {
		return left.execute() / right.execute(); //context.multiply(param);
	}
	
}
