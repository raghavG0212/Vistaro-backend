package com.project.vistaro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.vistaro.dto.EventSlotDto;
import com.project.vistaro.exception.ResourceNotFoundException;
import com.project.vistaro.model.EventSlot;
import com.project.vistaro.model.Venue;
import com.project.vistaro.repository.EventSlotDao;
import com.project.vistaro.repository.SeatDao;
import com.project.vistaro.repository.VenueDao;

@Service
public class EventSlotServiceImpl implements EventSlotService {

    private final EventSlotDao eventSlotDao;
    private final VenueDao venueDao;
    private final SeatDao seatDao;

    public EventSlotServiceImpl(EventSlotDao eventSlotDao, VenueDao venueDao, SeatDao seatDao) {
        this.eventSlotDao = eventSlotDao;
        this.venueDao = venueDao;
        this.seatDao = seatDao;
    }

    private EventSlotDto toDto(EventSlot slot) {
        EventSlotDto dto = new EventSlotDto();

        dto.setSlotId(slot.getSlotId());
        dto.setEventId(slot.getEventId());
        dto.setVenueId(slot.getVenueId());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        dto.setFormat(slot.getFormat());
        dto.setLanguage(slot.getLanguage());
        dto.setBasePrice(slot.getBasePrice());
        dto.setCreatedAt(slot.getCreatedAt());

        // ⬇ FETCH VENUE HERE
        Venue venue = venueDao.findById(slot.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        dto.setVenueName(venue.getName());
        dto.setScreenName(venue.getScreenName());
        dto.setVenueCity(venue.getCity());
        dto.setVenueType(venue.getVenueType().name());

        return dto;
    }

    @Override
    public EventSlot addEventSlot(EventSlot slot) {
        return eventSlotDao.add(slot);
    }

    @Override
    public EventSlot updateEventSlot(Integer slotId, EventSlot slot) {
        EventSlot existing = eventSlotDao.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("EventSlot not found: " + slotId));

        // Preserve eventId from DB
        slot.setSlotId(slotId);
        slot.setEventId(existing.getEventId());

        eventSlotDao.update(slot);
        return slot;
    }


    @Override
    public void deleteEventSlot(Integer slotId) {
        eventSlotDao.deleteById(slotId);
    }

    @Override
    public EventSlotDto getById(Integer slotId) {
        EventSlot slot = eventSlotDao.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));
        return toDto(slot);
    }

    @Override
    public List<EventSlotDto> getByEventId(Integer eventId) {
        return eventSlotDao.findByEventId(eventId)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventSlotDto> getByCity(String city) {
        return eventSlotDao.findByCity(city)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventSlotDto> getAllSlots() {
        return eventSlotDao.findAll()
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }
}
