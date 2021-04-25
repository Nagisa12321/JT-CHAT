package view.chat;

import observer.Observer;
import view.IView;

import javax.swing.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 14:46
 */
public interface IChatView extends Observer, IView {


	JTextArea getArea();

	JButton getLeave();

	JButton getSend();

	JTextArea getMessageArea();

	String getGroupName();
}
