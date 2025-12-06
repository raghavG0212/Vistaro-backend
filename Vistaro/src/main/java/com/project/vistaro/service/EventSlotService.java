package com.project.vistaro.service;

import java.util.List;

import com.project.vistaro.dto.EventSlotDto;
import com.project.vistaro.model.EventSlot;

public interface EventSlotService {

    EventSlot addEventSlot(EventSlot slot);
    EventSlot updateEventSlot(Integer slotId, EventSlot slot);

    void deleteEventSlot(Integer slotId);

    EventSlotDto getById(Integer slotId);
    List<EventSlotDto> getByEventId(Integer eventId);
    List<EventSlotDto> getByCity(String city);
    List<EventSlotDto> getAllSlots();
}
