package observer;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:29
 */
public interface Observerable {
	void rigisterObserver(Observer observer);

	void notifyObserver();
}
