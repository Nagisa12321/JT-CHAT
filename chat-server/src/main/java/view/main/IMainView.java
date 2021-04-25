package view.main;

import view.IView;

import javax.swing.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 13:23
 */
public interface IMainView extends IView {

	JTextField getPort();

	JButton getOpenServer();

	JButton getCloseServer();

	JButton getAddGroup();

	JButton getGroupInformation();
}
