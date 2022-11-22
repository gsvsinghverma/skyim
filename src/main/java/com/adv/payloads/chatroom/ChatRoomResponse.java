package com.adv.payloads.chatroom;

import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ChatRoomResponse {

	private Long id;
	private String name;
	private String roomName;
	private String profilePhoto;

//	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Timestamp creationDate;

	private ChatRoomUserResponse creator;
	private ChatRoomUserResponse admin;
	private Set<ChatRoomUserResponse> participants;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public ChatRoomUserResponse getCreator() {
		return creator;
	}

	public void setCreator(ChatRoomUserResponse creator) {
		this.creator = creator;
	}

	public ChatRoomUserResponse getAdmin() {
		return admin;
	}

	public void setAdmin(ChatRoomUserResponse admin) {
		this.admin = admin;
	}

	public Set<ChatRoomUserResponse> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<ChatRoomUserResponse> participants) {
		this.participants = participants;
	}

}