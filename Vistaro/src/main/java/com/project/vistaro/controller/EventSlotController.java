package com.project.vistaro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.vistaro.dto.EventSlotDto;
import com.project.vistaro.model.EventSlot;
import com.project.vistaro.service.EventSlotService;

@RestController
@RequestMapping("/api/v1/eventslot")
public class EventSlotController {

    private final EventSlotService eventSlotService;

    public EventSlotController(EventSlotService eventSlotService) {
        this.eventSlotService = eventSlotService;
    }

    @PostMapping
    public ResponseEntity<EventSlot> create(@RequestBody EventSlot slot) {
        return ResponseEntity.ok(eventSlotService.addEventSlot(slot));
    }

    @PutMapping("/{slotId}")
    public ResponseEntity<EventSlot> update(
            @PathVariable Integer slotId,
            @RequestBody EventSlot slot) {
        return ResponseEntity.ok(eventSlotService.updateEventSlot(slotId, slot));
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> delete(@PathVariable Integer slotId) {
        eventSlotService.deleteEventSlot(slotId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{slotId}")
    public ResponseEntity<EventSlotDto> getById(@PathVariable Integer slotId) {
        return ResponseEntity.ok(eventSlotService.getById(slotId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventSlotDto>> getByEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventSlotService.getByEventId(eventId));
    }

    @GetMapping("/search/city")
    public ResponseEntity<List<EventSlotDto>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(eventSlotService.getByCity(city));
    }

    @GetMapping
    public ResponseEntity<List<EventSlotDto>> getAll() {
        return ResponseEntity.ok(eventSlotService.getAllSlots());
    }
}
