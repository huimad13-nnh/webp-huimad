package io.github.huimad13.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "Category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
@Getter
@Setter
@NoArgsConstructor // JPA cần constructor rỗng
public class Category implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cate_id")
	private int cateid;

	@NotBlank(message = "Tên danh mục không được rỗng")
	@Size(max = 255, message = "Tên danh mục tối đa 255 ký tự")
	@Column(name = "cate_name", nullable = false, columnDefinition = "nvarchar(255)")
	private String catename;

	@Column(name = "icons", columnDefinition = "nvarchar(255)")
	private String icon;
}
