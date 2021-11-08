/**
 * @(#) IObserver.java
 */
package Observer;

public interface IObserver
{
	void update( String data );
	
	void notifyServer(String result);
	
	void setServer(Server server );
	
	
}
