package io.github.huimad13.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "Videos")
@Getter
@Setter
@NoArgsConstructor // JPA cần constructor rỗng
public class Video implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "video_id")
	private int id;

	@NotBlank(message = "Tiêu đề không được rỗng")
	@Size(max = 255, message = "Tiêu đề tối đa 255 ký tự")
	@Column(name = "title", nullable = false, columnDefinition = "nvarchar(255)")
	private String title;

	@Column(name = "description", columnDefinition = "nvarchar(max)")
	private String description;

	// Tên file video trong Constant.DIR, phát qua /image?fname=...
	@Column(name = "url", nullable = false, columnDefinition = "nvarchar(255)")
	private String url;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	// n video - 1 user: khoá ngoại Videos.user_id -> [User].id
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
