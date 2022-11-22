package com.adv.openfire.model;

import java.io.Serializable;

public class OfGroupUserId implements Serializable {

	private String groupName;
	private String username;
	private boolean administrator;

	public OfGroupUserId() {
	}

	public OfGroupUserId(String groupName, String username, boolean administrator) {
		super();
		this.groupName = groupName;
		this.username = username;
		this.administrator = administrator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (administrator ? 1231 : 1237);
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfGroupUserId other = (OfGroupUserId) obj;
		if (administrator != other.administrator)
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}