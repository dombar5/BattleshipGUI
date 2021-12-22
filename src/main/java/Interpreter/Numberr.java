/**
 * @(#) Number.java
 */

package Interpreter;

public class Numberr  extends Expression{

	private float value;
	
	public Numberr(float newValue) {
		value = newValue;
	}
	
	@Override
	public float execute() {		
		return value;
	}
}
