package view.main;

import observer.Observer;
import view.IView;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 21:20
 */
public interface IMainView extends IView, Observer {

	String getChooseGroupName();

}
