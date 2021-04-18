/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/15 21:45
 */
public class VolatileTest {
	static volatile int a = 1;

	public static void main(String[] args) throws InterruptedException {

		Runnable runnable = () -> {
			while (true) {
				System.out.println(a);
			}
		};

		new Thread(runnable).start();

		Thread.sleep(3000);

		a = 2;

		Thread.sleep(3000);
	}

}
