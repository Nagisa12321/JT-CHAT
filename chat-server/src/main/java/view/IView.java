package view;

import observer.Observer;

import javax.swing.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 17:26
 */
public interface IView extends Observer {


	JTextField getPort();

	JButton getOpenServer();

	JButton getCloseServer();

	void openMessage(String s);

}
