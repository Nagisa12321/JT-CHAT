package view.login;

import view.IView;

import javax.swing.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/18 9:48
 */
public interface ILoginView extends IView {

	JButton getConnect();

	JTextField getIp();

	JTextField getPort();

	JTextField getUsername();

	void connectSuccess();

	void open();

}
