package com.nastech.upmureport.domain.pk;

import java.io.Serializable;
import java.util.Date;

import com.nastech.upmureport.domain.entity.Attachment;

public class LogAttachmentPK implements Serializable{
	private Date newDate;
	private Integer attachmentNum;
	private Integer userId;
}
