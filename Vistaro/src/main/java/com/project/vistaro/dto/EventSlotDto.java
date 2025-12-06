package com.project.vistaro.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.project.vistaro.model.EventFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class EventSlotDto {

    private Integer slotId;

    @NotNull
    private Integer eventId;

    @NotNull
    private Integer venueId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private EventFormat format;
    private String language;

    @DecimalMin("0.0")
    private BigDecimal basePrice;

    private LocalDateTime createdAt;

    // ⬇ VENUE DETAILS (DTO ONLY, NOT DATABASE)
    private String venueName;
    private String screenName;
    private String venueCity;
    private String venueType;
	public Integer getSlotId() {
		return slotId;
	}
	public void setSlotId(Integer slotId) {
		this.slotId = slotId;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Integer getVenueId() {
		return venueId;
	}
	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public EventFormat getFormat() {
		return format;
	}
	public void setFormat(EventFormat format) {
		this.format = format;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getVenueCity() {
		return venueCity;
	}
	public void setVenueCity(String venueCity) {
		this.venueCity = venueCity;
	}
	public String getVenueType() {
		return venueType;
	}
	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}

    // Getters + Setters …
    
    
}
