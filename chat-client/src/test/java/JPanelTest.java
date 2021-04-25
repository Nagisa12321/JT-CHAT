/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/19 9:53
 */
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanelTest extends JFrame {

	private static final long serialVersionUID = -6740703588976621222L;

	public JPanelTest() {
		super("布局测试");
		Container c = this.getContentPane();

		c.add(getJButton());

		this.setSize(600, 300);
		this.setUndecorated(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JPanel getJButton() {
		JPanel jP = new JPanel();
		jP.setOpaque(false);
		jP.setLayout(null);// 设置空布局，即绝对布局

		JButton jButton1 = new JButton("测试1");
		jButton1.setBounds(100, 60, 85, 35);// 设置位置及大小
		jP.add(jButton1);

		JButton jButton2 = new JButton("测试2");
		jButton2.setBounds(400, 60, 85, 35);// 设置位置及大小
		jP.add(jButton2);

		JButton jButton3 = new JButton("测试3");
		jButton3.setBounds(250, 150, 85, 35);// 设置位置及大小
		jP.add(jButton3);
		return jP;
	}

	public static void main(String[] args) {
		new JPanelTest();
	}
}