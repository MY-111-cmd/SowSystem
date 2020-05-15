package test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import ClientDemo.HCNetSDK;
import ClientDemo.HCNetSDK.NET_DVR_DEVICEINFO_V30;
import ClientDemo.HCNetSDK.NET_DVR_IPPARACFG;
import ClientDemo.HCNetSDK.NET_DVR_JPEGPARA;
import ClientDemo.HCNetSDK.NET_DVR_WORKSTATE_V30;
import jcifs.smb.SmbFile;

public class TestHikvision {
	/*
	 * private ByteBuffer bytebuffer ;
	 * 
	 * public ByteBuffer getBytebuffer() { return bytebuffer; }
	 * 
	 * public void setBytebuffer(ByteBuffer bytebuffer) { this.bytebuffer =
	 * bytebuffer; }
	 */
	// private static HCNetSDK sdk = HCNetSDK.INSTANCE;
	public String cameraIp;
	public String userName;
	public String userPwd;
	public int cameraPort;
	public String desPath;
	public TestHikvision(String cameraIp,String userName, String userPwd,int cameraPort,String desPath){
		this.cameraIp=cameraIp;
		this.userName=userName;
		this.userPwd=userPwd;
		this.cameraPort=cameraPort;
		this.desPath=desPath;
	}
	

	public void getDVRConfig(MonitorCameraInfo cameraInfo) {
		System.out.println("开始执行getDVRConfig");
		
		HCNetSDK sdk = HCNetSDK.INSTANCE;
		
		System.out.println("执行完sdk");
		// 判断摄像头是否开启
		if (!sdk.NET_DVR_Init()) {
			System.out.println("SDK初始化失败");
			return;
		}
		// 设置连接时间与重连
		// sdk.NET_DVR_SetConnectTime(2000, 1);
		// sdk.NET_DVR_SetReconnect(10000, true);

		NET_DVR_DEVICEINFO_V30 devinfo = new NET_DVR_DEVICEINFO_V30();// 设备信息

		 //System.out.println("设备信息："+devinfo);

		// 登录信息
		NativeLong id = sdk.NET_DVR_Login_V30(cameraInfo.getCameraIp(), (short) cameraInfo.getCameraPort(),
				cameraInfo.getUserName(), cameraInfo.getUserPwd(), devinfo);
		System.out.println(id);
		cameraInfo.setUserId(id);// 返回一个用户编号，同时将设备信息写入devinfo
		// 输出int数据
		System.out.println("intValue:" + cameraInfo.getUserId().intValue());
		if (cameraInfo.getUserId().intValue() < 0) {
			System.out.println("设备注册失败" + sdk.NET_DVR_GetLastError());
			return;
		}
		// DVR工作状态
		NET_DVR_WORKSTATE_V30 devwork = new NET_DVR_WORKSTATE_V30();
		if (!sdk.NET_DVR_GetDVRWorkState_V30(cameraInfo.getUserId(), devwork)) {
			// 返回Boolean值，判断是否获取设备能力
			System.out.println("返回设备状态失败");
		}

		IntByReference ibrBytesReturned = new IntByReference(0);// 获取IP接入配置参数
		NET_DVR_IPPARACFG ipcfg = new NET_DVR_IPPARACFG();// IP接入配置结构
		ipcfg.write();
		Pointer lpIpParaConfig = ipcfg.getPointer();
		// 获取相关参数配置
		sdk.NET_DVR_GetDVRConfig(cameraInfo.getUserId(), HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0),
				lpIpParaConfig, ipcfg.size(), ibrBytesReturned);
		ipcfg.read();
		System.out.print("IP地址:" + cameraInfo.getCameraIp());
		System.out.println("|设备状态：" + devwork.dwDeviceStatic);// 0正常，1CPU占用率过高，2硬件错误，3未知
		// System.out.println("ChanNum"+devinfo.byChanNum);
		// 显示模拟通道
		for (int i = 0; i < devinfo.byChanNum; i++) {
			System.out.print("Camera" + i + 1);// 模拟通道号名称
			System.out.print("|是否录像:" + devwork.struChanStatic[i].byRecordStatic);// 0不录像，不录像
			System.out.print("|信号状态:" + devwork.struChanStatic[i].bySignalStatic);// 0正常，1信号丢失
			System.out.println("|硬件状态:" + devwork.struChanStatic[i].byHardwareStatic);// 0正常，1异常
		}
		// 注销用户
		sdk.NET_DVR_Logout(cameraInfo.getUserId());// 释放SDK资源
		sdk.NET_DVR_Cleanup();
	}

	// 抓拍图片
	public void getDVRPic(MonitorCameraInfo cameraInfo) throws Exception {

		// 设置通道号，其中1正常，-1不正常
		NativeLong chanLong = new NativeLong(1);
		cameraInfo.setChannel(chanLong);
		// System.out.println("Channel:"+chanLong);
		long startTime = System.currentTimeMillis();
		HCNetSDK sdk = HCNetSDK.INSTANCE;
		if (!sdk.NET_DVR_Init()) {
			System.out.println("SDK初始化失败");
			return;
		}

		NET_DVR_DEVICEINFO_V30 devinfo = new NET_DVR_DEVICEINFO_V30();// 设备信息

		// 注册设备
		NativeLong id = sdk.NET_DVR_Login_V30(cameraInfo.getCameraIp(), (short) cameraInfo.getCameraPort(),
				cameraInfo.getUserName(), cameraInfo.getUserPwd(), devinfo);
		cameraInfo.setUserId(id);// 返回一个用户编号，同时将设备信息写入devinfo
		if (cameraInfo.getUserId().intValue() < 0) {
			System.out.println("设备注册失败" + sdk.NET_DVR_GetLastError());
			return;
		} else {
			System.out.println("id：" + cameraInfo.getUserId().intValue());
		}
		NET_DVR_WORKSTATE_V30 devwork = new NET_DVR_WORKSTATE_V30();
		if (!sdk.NET_DVR_GetDVRWorkState_V30(cameraInfo.getUserId(), devwork)) {
			// 返回Boolean值，判断是否获取设备能力
			System.out.println("返回设备状态失败");
		}

		// System.out.println("设备注册耗时：[" + (System.currentTimeMillis() -
		// startTime) + "]");

		startTime = System.currentTimeMillis();
		// 图片质量
		NET_DVR_JPEGPARA jpeg = new NET_DVR_JPEGPARA();
		// 设置图片的分辨率
		jpeg.wPicSize = 5;
		// 设置图片质量
		jpeg.wPicQuality = 0;
		// jpeg.wPicSize=0;
		int dwPicSize = 200 * 1024;
		IntByReference a = new IntByReference();
		// 设置图片大小
		ByteBuffer jpegBuffer = ByteBuffer.allocate(1024 * 1024);
		// 创建文件目录，文件？？？？？
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		// String fileNameString =
		// "D:/JAVATest/Showpicture/image/"+"A001001_"+"000001"+".jpg";//+".jpg"
		//String fileNameString = desPath + sdf.format(date) + ".jpg";// +".jpg"
		
		
		String fileNameString = "";
		if (cameraIp.equals("192.168.1.1")) {
			fileNameString = desPath  + "A001001_" + sdf.format(date) + ".jpg";
		} else if (cameraIp.equals("192.168.1.2")) {
			fileNameString = desPath  + "A001002_" + sdf.format(date) + ".jpg";
		} else if(cameraIp.equals("192.168.1.3")){
			fileNameString = desPath  + "A001003_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.4")){
			fileNameString = desPath  + "A001004_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.5")){
			fileNameString = desPath  + "A001005_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.6")){
			fileNameString = desPath  + "A001006_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.7")){
			fileNameString = desPath  + "A001007_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.8")){
			fileNameString = desPath  + "A001008_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.9")){
			fileNameString = desPath  + "A001009_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.10")){
			fileNameString = desPath  + "A001010_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.11")){
			fileNameString = desPath  + "A001011_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.12")){
			fileNameString = desPath  + "A001012_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.13")){
			fileNameString = desPath  + "A001013_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.14")){
			fileNameString = desPath  + "A001014_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.15")){
			fileNameString = desPath  + "A001015_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.16")){
			fileNameString = desPath  + "A001016_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.17")){
			fileNameString = desPath  + "A001017_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.18")){
			fileNameString = desPath  + "A001018_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.19")){
			fileNameString = desPath  + "A001019_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.20")){
			fileNameString = desPath  + "A001020_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.21")){
			fileNameString = desPath  + "A001021_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.22")){
			fileNameString = desPath  + "A001022_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.23")){
			fileNameString = desPath  + "A001023_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.24")){
			fileNameString = desPath  + "A001024_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.25")){
			fileNameString = desPath  + "A001025_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.26")){
			fileNameString = desPath  + "A001026_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.27")){
			fileNameString = desPath  + "A001027_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.28")){
			fileNameString = desPath  + "A001028_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.29")){
			fileNameString = desPath  + "A001029_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.30")){
			fileNameString = desPath  + "A001030_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.31")){
			fileNameString = desPath  + "A001031_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.32")){
			fileNameString = desPath  + "A001032_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.33")){
			fileNameString = desPath  + "A001033_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.34")){
			fileNameString = desPath  + "A001034_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.35")){
			fileNameString = desPath  + "A001035_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.36")){
			fileNameString = desPath  + "A001036_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.37")){
			fileNameString = desPath  + "A001037_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.38")){
			fileNameString = desPath  + "A001038_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.39")){
			fileNameString = desPath  + "A001039_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.40")){
			fileNameString = desPath  + "A001040_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.41")){
			fileNameString = desPath  + "A001041_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.42")){
			fileNameString = desPath  + "A001042_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.43")){
			fileNameString = desPath  + "A001043_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.44")){
			fileNameString = desPath  + "A001044_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.45")){
			fileNameString = desPath  + "A001045_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.46")){
			fileNameString = desPath  + "A001046_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.47")){
			fileNameString = desPath  + "A001047_" +sdf.format(date) + ".jpg";
		}else if(cameraIp.equals("192.168.1.48")){
			fileNameString = desPath  + "A001048_" +sdf.format(date) + ".jpg";
		}
		
	
		
		// 还需要设置抓拍图片格式
		/* String pathString = cameraInfo.getFilePath(); */
		File file = new File(fileNameString);
		/*
		 * if (!file.exists()) { file.mkdirs(); //创建目录 }
		 */
		// file.mkdirs();

		// 抓图到内存，单帧数据捕获并保存成JPEG存放在指定的内存空间中
		// 需要加入通道
		boolean is = sdk.NET_DVR_CaptureJPEGPicture_NEW(cameraInfo.getUserId(), cameraInfo.getChannel(), jpeg,
				jpegBuffer, 1024 * 1024, a);

		// Pointer p = new Memory(200*1024);
		// boolean is =
		// sdk.NET_DVR_CaptureJPEGPicture_NEW(cameraInfo.getUserId(),
		// cameraInfo.getChannel(), jpeg,
		// p, 1024 * 1024, a);
		// boolean is =
		// sdk.NET_DVR_CaptureJPEGPicture_NEW(cameraInfo.getUserId(),
		// cameraInfo.getChannel(), jpeg, p, dwPicSize, a);
		// System.out.println("Channel:"+cameraInfo.getChannel());
		System.out.println("抓图到内存耗时：[" + (System.currentTimeMillis() - startTime) + "ms]");

		// 抓图到文件o
		// boolean is =
		// sdk.NET_DVR_CaptureJPEGPicture(cameraInfo.getUserId(),cameraInfo.getChannel(),jpeg,
		// fileNameString);
		// boolean is =
		// sdk.NET_DVR_CaptureJPEGPicture(cameraInfo.getUserId(),"Cameral",jpeg,
		// fileNameString);
		// System.out.println(is);
		// System.out.println(sdk.NET_DVR_GetLastError());
		System.out.println(a.getValue());
		if (is) {
			System.out.println("抓取成功,返回长度：" + a.getValue());
		} else {
			System.out.println("抓取失败：" + sdk.NET_DVR_GetLastError());
		}

		// startTime = System.currentTimeMillis();
		// 存储本地，写入内容

		BufferedOutputStream outputStream = null;
		// ExecutorService executor = Executors.newFixedThreadPool(5);
		try {
			outputStream = new BufferedOutputStream(new FileOutputStream(file));
			outputStream.write(jpegBuffer.array(), 0, a.getValue());
			outputStream.flush();
			Thread.sleep(60);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			// System.out.println("存储本地耗时：[" + (System.currentTimeMillis() -
			// startTime) + "]");
			sdk.NET_DVR_Logout(cameraInfo.getUserId());
			sdk.NET_DVR_Cleanup();
	/*	while (true) {

			try {
				outputStream = new BufferedOutputStream(new FileOutputStream(file));
				outputStream.write(jpegBuffer.array(), 0, a.getValue());
				outputStream.flush();
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
				// System.out.println("存储本地耗时：[" + (System.currentTimeMillis() -
				// startTime) + "]");
				sdk.NET_DVR_Logout(cameraInfo.getUserId());
				sdk.NET_DVR_Cleanup();
			}*/
		}
	}

