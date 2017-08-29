package lightwebview.sina.cn.com.library;

import java.io.Serializable;

public class CookieModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// {
	// "retcode": 20000000,
	// "msg": "succ",
	// "data": {
	// "expiretime": 1417335331,
	// ".sina.com.cn": {
	// "SUE":
	// "es%3D27343946f2b42f3d57feca0a0264d709%26ev%3Dv1%26es2%3D890b8ba0a84594b01f849cc5651bc599%26rs0%3DHOptEVtO4TSyhE%252Fh6wKq6p1volHsUn3WiXY4JD9fXP%252FRojDykVDiIpYt4ncGCgRU%252B2NM7Q3FcItl4MyUh4O2FRAIJK3noLGJzgkcyOoMvMOkEwjwh8FvBTtp70w7BRxl9%252B41FNJwRvmX4mgWVAaAumDIo7szjLXGL%252BvxD70bfx8%253D%26rv%3D0",
	// "SUP":
	// "cv%3D1%26bt%3D1417248931%26et%3D1417335331%26d%3D40c3%26i%3Dc9a0%26us%3D%26vf%3D%26vt%3D%26ac%3D%26st%3D0%26lt%3D1%26uid%3D1789578644%26user%3Dxiaokai.shi%2540gmail.com%26ag%3D4%26name%3Dxiaokai.shi%2540gmail.com%26nick%3D%25E5%258F%25B2%25E5%25B0%258F%25E5%2587%25AF%26sex%3D%26ps%3D0%26email%3D%26dob%3D%26ln%3D%26os%3D%26fmp%3D%26lcp%3D",
	// "gsid_CTandWM": "4uYO3ace1QtXxaRLehxtU7vyaao"
	// }
	// }
	// }


	private long expiretime;
	private String data;//json串

	public long getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(long expiretime) {
		this.expiretime = expiretime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	//	private String SUE;
//	private String SUP;
//	private String gsid_CTandWM;
//	public long getExpiretime() {
//		return expiretime;
//	}
//	public void setExpiretime(long expiretime) {
//		this.expiretime = expiretime;
//	}
//	public String getSUE() {
//		return SUE;
//	}
//	public void setSUE(String sUE) {
//		SUE = sUE;
//	}
//	public String getSUP() {
//		return SUP;
//	}
//	public void setSUP(String sUP) {
//		SUP = sUP;
//	}
//	public String getGsid_CTandWM() {
//		return gsid_CTandWM;
//	}
//	public void setGsid_CTandWM(String gsid_CTandWM) {
//		this.gsid_CTandWM = gsid_CTandWM;
//	}
	
	
	
	
}
