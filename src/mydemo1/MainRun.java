package mydemo1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import myTread.TestThread;

/**
 * 摄像头截图
 * 
 * @author Administrator
 *
 */
public class MainRun {
	// public static ExecutorService es = Executors.newCachedThreadPool();
	public static ScheduledExecutorService es1 = Executors.newScheduledThreadPool(60);
	public static void main(String[] args) {
		System.out.println("程序已启动");
		// es.execute(new
		// TestThread("192.168.8.100","admin","b307.,,,",8000,"D:/picture/001/image/"));
		for(int i =1;i<=48;i++){
			if(i<10){
				es1.scheduleAtFixedRate(new TestThread("192.168.1."+i, "admin", "b307b307,.", 8000, "D:/picture/00"+i+"/"), 0, 8000,
						TimeUnit.SECONDS);
			}
			else{
				es1.scheduleAtFixedRate(new TestThread("192.168.1."+i, "admin", "b307b307,.", 8000, "D:/picture/0"+i+"/"), 0, 8000,
						TimeUnit.SECONDS);
			}
			
		}
		/*es1.scheduleAtFixedRate(new TestThread("192.168.1.1", "admin", "b307b307,.", 8000, "D:/picture/001/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.2", "admin", "b307b307,.", 8000, "D:/picture/002/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.3", "admin", "b307b307,.", 8000, "D:/picture/003/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.4", "admin", "b307b307,.", 8000, "D:/picture/004/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.5", "admin", "b307b307,.", 8000, "D:/picture/005/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.6", "admin", "b307b307,.", 8000, "D:/picture/006/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.7", "admin", "b307b307,.", 8000, "D:/picture/007/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.8", "admin", "b307b307,.", 8000, "D:/picture/008/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.9", "admin", "b307b307,.", 8000, "D:/picture/009/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.10", "admin", "b307b307,.", 8000, "D:/picture/010/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.11", "admin", "b307b307,.", 8000, "D:/picture/011/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.12", "admin", "b307b307,.", 8000, "D:/picture/012/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.13", "admin", "b307b307,.", 8000, "D:/picture/013/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.14", "admin", "b307b307,.", 8000, "D:/picture/014/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.15", "admin", "b307b307,.", 8000, "D:/picture/015/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.16", "admin", "b307b307,.", 8000, "D:/picture/016/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.17", "admin", "b307b307,.", 8000, "D:/picture/017/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.18", "admin", "b307b307,.", 8000, "D:/picture/018/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.19", "admin", "b307b307,.", 8000, "D:/picture/019/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.20", "admin", "b307b307,.", 8000, "D:/picture/020/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.21", "admin", "b307b307,.", 8000, "D:/picture/021/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.22", "admin", "b307b307,.", 8000, "D:/picture/022/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.23", "admin", "b307b307,.", 8000, "D:/picture/023/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.24", "admin", "b307b307,.", 8000, "D:/picture/024/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.25", "admin", "b307b307,.", 8000, "D:/picture/025/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.26", "admin", "b307b307,.", 8000, "D:/picture/026/"), 0, 600,
				TimeUnit.SECONDS);

		es1.scheduleAtFixedRate(new TestThread("192.168.1.27", "admin", "b307b307,.", 8000, "D:/picture/027/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.28", "admin", "b307b307,.", 8000, "D:/picture/028/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.29", "admin", "b307b307,.", 8000, "D:/picture/029/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.30", "admin", "b307b307,.", 8000, "D:/picture/030/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.31", "admin", "b307b307,.", 8000, "D:/picture/031/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.32", "admin", "b307b307,.", 8000, "D:/picture/032/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.33", "admin", "b307b307,.", 8000, "D:/picture/033/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.34", "admin", "b307b307,.", 8000, "D:/picture/034/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.35", "admin", "b307b307,.", 8000, "D:/picture/035/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.36", "admin", "b307b307,.", 8000, "D:/picture/036/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.37", "admin", "b307b307,.", 8000, "D:/picture/037/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.38", "admin", "b307b307,.", 8000, "D:/picture/038/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.39", "admin", "b307b307,.", 8000, "D:/picture/039/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.40", "admin", "b307b307,.", 8000, "D:/picture/040/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.41", "admin", "b307b307,.", 8000, "D:/picture/041/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.42", "admin", "b307b307,.", 8000, "D:/picture/042/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.43", "admin", "b307b307,.", 8000, "D:/picture/043/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.44", "admin", "b307b307,.", 8000, "D:/picture/044/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.45", "admin", "b307b307,.", 8000, "D:/picture/045/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.46", "admin", "b307b307,.", 8000, "D:/picture/046/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.47", "admin", "b307b307,.", 8000, "D:/picture/047/"), 0, 600,
				TimeUnit.SECONDS);
		es1.scheduleAtFixedRate(new TestThread("192.168.1.48", "admin", "b307b307,.", 8000, "D:/picture/048/"), 0, 600,
				TimeUnit.SECONDS);*/

	}

}
