package io.github.huimad13.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
// "User" là từ khoá của SQL Server -> dấu ` để Hibernate tự bọc thành [User]
@Table(name = "`User`")
@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.userName = :username")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Email không được rỗng")
	@Email(message = "Email không hợp lệ")
	@Column(name = "email", columnDefinition = "nvarchar(255)")
	private String email;

	@NotBlank(message = "Tài khoản không được rỗng")
	@Size(min = 3, max = 50, message = "Tài khoản phải từ 3 đến 50 ký tự")
	@Column(name = "username", nullable = false, unique = true, columnDefinition = "nvarchar(50)")
	private String userName;

	@Size(max = 255, message = "Họ tên tối đa 255 ký tự")
	@Column(name = "fullname", columnDefinition = "nvarchar(255)")
	private String fullName;

	@NotBlank(message = "Mật khẩu không được rỗng")
	@Size(min = 3, max = 255, message = "Mật khẩu phải từ 3 ký tự trở lên")
	@Column(name = "password", nullable = false, columnDefinition = "nvarchar(255)")
	private String passWord;

	@Column(name = "avatar", columnDefinition = "nvarchar(255)")
	private String avatar;

	@Column(name = "roleid", nullable = false)
	private int roleid = 5; // 1: admin, 2: manager, 5: user

	// Cho phép rỗng; nếu nhập thì phải là 9-11 chữ số
	@Pattern(regexp = "^$|^\\d{9,11}$", message = "Số điện thoại phải gồm 9-11 chữ số")
	@Column(name = "phone", columnDefinition = "nvarchar(20)")
	private String phone;

	@Column(name = "createdDate")
	private LocalDate createdDate;

	public User() {
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	public String getPassWord() { return passWord; }
	public void setPassWord(String passWord) { this.passWord = passWord; }
	public String getAvatar() { return avatar; }
	public void setAvatar(String avatar) { this.avatar = avatar; }
	public int getRoleid() { return roleid; }
	public void setRoleid(int roleid) { this.roleid = roleid; }
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public LocalDate getCreatedDate() { return createdDate; }
	public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
}
