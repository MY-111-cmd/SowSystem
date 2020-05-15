package myTread;

import java.io.IOException;

import test.MonitorCameraInfo;
import test.TestHikvision;

public class TestThread implements Runnable{
	public String cameraIp;
	public String userName;
	public String userPwd;
	public int cameraPort;
	public String desPath;
	private static Object obj =new Object();
	public TestThread(String cameraIp,String userName, String userPwd,int cameraPort,String desPath){
		this.cameraIp=cameraIp;
		this.userName=userName;
		this.userPwd=userPwd;
		this.cameraPort=cameraPort;
		this.desPath=desPath;
	}
	@Override
	public void run() {
//		synchronized (obj) {
			// TODO Auto-generated method stub
			//System.out.println("执行run方法");
			TestHikvision app = new TestHikvision(cameraIp,userName,userPwd,cameraPort,desPath);
			MonitorCameraInfo cameraInfo = new MonitorCameraInfo();// 需要新建MonitorCameraInfo类
			cameraInfo.setCameraIp(cameraIp);
			cameraInfo.setCameraPort(cameraPort);
			// cameraInfo.setUserName("admin");
			cameraInfo.setUserName(userName);
			cameraInfo.setUserPwd(userPwd);
			System.out.println(userName);
			while(true){
				//调用摄像头注册验证功能模块
				System.out.println("调用摄像头注册验证功能模块");
				app.getDVRConfig(cameraInfo);
				System.out.println("执行完getDVRConfig");
				try {
					Thread.sleep(13000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//主要作用是截图并存储
				try {
					System.out.println("开始截图");
					app.getDVRPic(cameraInfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
			
		}

//	}

}
