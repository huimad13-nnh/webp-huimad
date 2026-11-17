package vn.iotstar.utils;

public class Constant {
	public static final String SESSION_ACCOUNT = "account";
	public static final String SESSION_PENDING = "pendingRegister"; // đăng ký đang chờ nhập OTP
	public static final String COOKIE_REMEMBER = "username";

	// Thư mục lưu ảnh upload (nằm ngoài project để redeploy không mất ảnh)
	public static final String DIR = "D:\\upload";

	public static class Path {
		public static final String LOGIN = "/views/login.jsp";
		public static final String REGISTER = "/views/register.jsp";
		public static final String HOME = "/views/home.jsp";
		public static final String VERIFY = "/views/verify.jsp";
	}
}
