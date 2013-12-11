
public class Subject 
{
	protected Observer observer;

	public Observer getObserver() 
	{
		return observer;
	}

	public void setObserver(Observer observer)
	{
		this.observer = observer;
	}
	
	public void notifyObservers() 
	{
		observer.update();
	}
}
