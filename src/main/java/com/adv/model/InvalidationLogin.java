package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "invalidationlogin")
public class InvalidationLogin {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ID")
		private Long id;

		@Column(name = "TOKEN", columnDefinition = "TEXT")
		private String token;

		@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
		@Column(name = "CREATION_DATE")
		private Timestamp creationDate;

		@Column(name = "username")
		private String username;
		
		@Column(name = "isvalidate")
		private boolean isvalidate;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Timestamp getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Timestamp creationDate) {
			this.creationDate = creationDate;
		}
		public boolean isIsvalidate() {
			return isvalidate;
		}

		public void setIsvalidate(boolean isvalidate) {
			this.isvalidate = isvalidate;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
		
		
		
		
	
}
