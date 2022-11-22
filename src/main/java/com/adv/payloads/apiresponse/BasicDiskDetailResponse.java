package com.adv.payloads.apiresponse;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class BasicDiskDetailResponse {

    private Long totalspace;
    private Long freespace;
    private Long usablespace;



}