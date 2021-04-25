package view;

import observer.Observer;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 13:21
 */
public interface IView extends Observer {
	void openMessage(String s);

	void open();
}
