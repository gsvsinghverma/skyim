package com.adv.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PagingsRequest {
	
	private int start;
	private int length;
	
	private int draw;


}
