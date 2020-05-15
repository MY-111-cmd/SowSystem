package test;

import com.sun.jna.NativeLong;

/*cameraInfo.setCameraIp("****");
cameraInfo.setCameraPort(8000);
cameraInfo.setUserName("admin");
cameraInfo.setUserPwd("****");
setUserId
setChannel
*/
public class MonitorCameraInfo {

	private String cameraIp;
	private int cameraPort;
	private String userName;
	private String userPwd;
	
	private NativeLong userId;

	private NativeLong channel;

	public String getCameraIp() {
		return cameraIp;
	}

	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
	}

	public int getCameraPort() {
		return cameraPort;
	}

	public void setCameraPort(int cameraPort) {
		this.cameraPort = cameraPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public NativeLong getUserId() {
		return userId;
	}

	public void setUserId(NativeLong userId) {
		this.userId = userId;
	}

	public NativeLong getChannel() {
		return channel;
	}

	public void setChannel(NativeLong channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "MonitorCameraInfo [cameraIp=" + cameraIp + ", cameraPort=" + cameraPort + ", userName=" + userName
				+ ", userPwd=" + userPwd + ", userId=" + userId + ", channel=" + channel + "]";
	}
	
	
	
	
	
}
