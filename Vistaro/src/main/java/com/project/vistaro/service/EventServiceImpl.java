package com.project.vistaro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.vistaro.exception.ResourceNotFoundException;
import com.project.vistaro.model.Event;
import com.project.vistaro.repository.EventDao;

@Service
public class EventServiceImpl implements EventService {

    private final EventDao dao;

    public EventServiceImpl(EventDao dao) {
        this.dao = dao;
    }

    @Override
    public Event create(Event e) {
        return dao.save(e);
    }

    @Override
    public Event findById(int eventId) {
        return dao.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
    }

    @Override
    public List<Event> findAllByCity(String city) {
        return dao.findAllByCity(city);
    }

    @Override
    public List<Event> searchByTitle(String city, String title) {
        return dao.searchByTitleAndCity(title, city);
    }

    @Override
    public List<Event> searchByCategory(String city, String category) {
        return dao.searchByCategoryAndCity(category, city);
    }

    @Override
    public List<Event> searchBySubCategory(String city, String subCategory) {
        return dao.searchBySubCategoryAndCity(subCategory, city);
    }

    @Override
    public Event update(int id, Event e) {
        Event existing = findById(id);

        if (e.getTitle() != null) existing.setTitle(e.getTitle());
        if (e.getDescription() != null) existing.setDescription(e.getDescription());
        if (e.getCategory() != null) existing.setCategory(e.getCategory());
        if (e.getSubCategory() != null) existing.setSubCategory(e.getSubCategory());
        if (e.getBannerUrl() != null) existing.setBannerUrl(e.getBannerUrl());
        if (e.getThumbnailUrl() != null) existing.setThumbnailUrl(e.getThumbnailUrl());
        if (e.getStartTime() != null) existing.setStartTime(e.getStartTime());
        if (e.getEndTime() != null) existing.setEndTime(e.getEndTime());

        return dao.update(existing);
    }

	@Override
	public void deleteEvent(Integer eventId) {
		// TODO Auto-generated method stub
		dao.findById(eventId).orElseThrow(()-> new ResourceNotFoundException("Event with id :"+eventId+" not found"));
		dao.delete(eventId);
		
	}
}
