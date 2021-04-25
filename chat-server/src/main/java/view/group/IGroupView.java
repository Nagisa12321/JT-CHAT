package view.group;

import observer.Observer;
import view.IView;

import javax.swing.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:26
 */
public interface IGroupView extends IView {

	void setGroupName(String groupName);
}
