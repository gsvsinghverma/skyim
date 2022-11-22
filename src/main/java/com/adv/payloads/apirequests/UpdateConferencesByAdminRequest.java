package com.adv.payloads.apirequests;

import java.sql.Timestamp;

public class UpdateConferencesByAdminRequest {
private Long id;

private boolean leaderWait;

private boolean leaderLeave;
private boolean talkerOptimization;
private boolean talkerDetection;
private boolean quietMode;

private boolean userCount;
private boolean userJoinLeave;
private boolean musicOnHold;
private boolean allowMenu;

private boolean recordConference;

private boolean muteOnJoin;

private String exten;
private String userpin;
private String adminpin;

private String description;
private String joinmsgId;
private String music;
private int users;
private String language;
private int timeout;

private Timestamp creationDate;

private Timestamp updationDate;

private String details;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public boolean isLeaderWait() {
	return leaderWait;
}

public void setLeaderWait(boolean leaderWait) {
	this.leaderWait = leaderWait;
}

public boolean isLeaderLeave() {
	return leaderLeave;
}

public void setLeaderLeave(boolean leaderLeave) {
	this.leaderLeave = leaderLeave;
}

public boolean isTalkerOptimization() {
	return talkerOptimization;
}

public void setTalkerOptimization(boolean talkerOptimization) {
	this.talkerOptimization = talkerOptimization;
}

public boolean isTalkerDetection() {
	return talkerDetection;
}

public void setTalkerDetection(boolean talkerDetection) {
	this.talkerDetection = talkerDetection;
}

public boolean isQuietMode() {
	return quietMode;
}

public void setQuietMode(boolean quietMode) {
	this.quietMode = quietMode;
}

public boolean isUserCount() {
	return userCount;
}

public void setUserCount(boolean userCount) {
	this.userCount = userCount;
}

public boolean isUserJoinLeave() {
	return userJoinLeave;
}

public void setUserJoinLeave(boolean userJoinLeave) {
	this.userJoinLeave = userJoinLeave;
}

public boolean isMusicOnHold() {
	return musicOnHold;
}

public void setMusicOnHold(boolean musicOnHold) {
	this.musicOnHold = musicOnHold;
}

public boolean isAllowMenu() {
	return allowMenu;
}

public void setAllowMenu(boolean allowMenu) {
	this.allowMenu = allowMenu;
}

public boolean isRecordConference() {
	return recordConference;
}

public void setRecordConference(boolean recordConference) {
	this.recordConference = recordConference;
}

public boolean isMuteOnJoin() {
	return muteOnJoin;
}

public void setMuteOnJoin(boolean muteOnJoin) {
	this.muteOnJoin = muteOnJoin;
}

public String getExten() {
	return exten;
}

public void setExten(String exten) {
	this.exten = exten;
}

public String getUserpin() {
	return userpin;
}

public void setUserpin(String userpin) {
	this.userpin = userpin;
}

public String getAdminpin() {
	return adminpin;
}

public void setAdminpin(String adminpin) {
	this.adminpin = adminpin;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getJoinmsgId() {
	return joinmsgId;
}

public void setJoinmsgId(String joinmsgId) {
	this.joinmsgId = joinmsgId;
}

public String getMusic() {
	return music;
}

public void setMusic(String music) {
	this.music = music;
}

public int getUsers() {
	return users;
}

public void setUsers(int users) {
	this.users = users;
}

public String getLanguage() {
	return language;
}

public void setLanguage(String language) {
	this.language = language;
}

public int getTimeout() {
	return timeout;
}

public void setTimeout(int timeout) {
	this.timeout = timeout;
}

public Timestamp getCreationDate() {
	return creationDate;
}

public void setCreationDate(Timestamp creationDate) {
	this.creationDate = creationDate;
}

public Timestamp getUpdationDate() {
	return updationDate;
}

public void setUpdationDate(Timestamp updationDate) {
	this.updationDate = updationDate;
}

public String getDetails() {
	return details;
}

public void setDetails(String details) {
	this.details = details;
}

}
