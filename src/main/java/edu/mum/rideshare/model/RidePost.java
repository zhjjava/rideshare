package edu.mum.rideshare.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the sys_user database table.
 * 
 */
@Entity
@Table(name="ride_post")
public class RidePost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="post_id")
	private long postId;

	@Column(name="notes")
	private String notes;

	@Column(name="src_place")
	private String srcPlace;

	@Column(name="dest_place")
	private String destPlace;

	
	@Column(name="src_coordination")
	private String srcCoordination;
	
	@Column(name="dest_coordination")
	private String destCoordination;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="status", columnDefinition="TINYINT(1) default '0'")
	private Integer status;//deleted==1 or normal==0


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private SysUser sysUser;
	
	@Column(name="offerOrAsk", columnDefinition="TINYINT(1) default '0'")
	private Integer offerOrAsk;//offer==0 or ask==1

	
	public RidePost() {
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSrcPlace() {
		return srcPlace;
	}

	public void setSrcPlace(String srcPlace) {
		this.srcPlace = srcPlace;
	}

	public String getDestPlace() {
		return destPlace;
	}

	public void setDestPlace(String destPlace) {
		this.destPlace = destPlace;
	}

	public String getSrcCoordination() {
		return srcCoordination;
	}

	public void setSrcCoordination(String srcCoordination) {
		this.srcCoordination = srcCoordination;
	}

	public String getDestCoordination() {
		return destCoordination;
	}

	public void setDestCoordination(String destCoordination) {
		this.destCoordination = destCoordination;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Integer getOfferOrAsk() {
		return offerOrAsk;
	}

	public void setOfferOrAsk(Integer offerOrAsk) {
		this.offerOrAsk = offerOrAsk;
	}


}