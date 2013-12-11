
public class Subject 
{
	// Standard observer design pattern 
	
	protected Observer observer;

	public Observer getObserver() 
	{
		return observer;
	}

	public void setObserver(Observer observer)
	{
		this.observer = observer;
	}
	
	public void notifyObserver() 
	{
		observer.update();
	}
}
