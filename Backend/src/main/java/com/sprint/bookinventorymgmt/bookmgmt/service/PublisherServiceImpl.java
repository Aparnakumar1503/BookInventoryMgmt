package com.sprint.bookinventorymgmt.bookmgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.PublisherRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.PublisherResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;
import com.sprint.bookinventorymgmt.bookmgmt.entity.State;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.PublisherNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.StateNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.repository.PublisherRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PublisherServiceImpl implements IPublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private StateRepository stateRepository;

    @Override
    public List<PublisherResponseDTO> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public PublisherResponseDTO getPublisherById(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() ->
                        new PublisherNotFoundException("Publisher not found with ID: " + id));

        return mapToDTO(publisher);
    }

    @Override
    public PublisherResponseDTO createPublisher(PublisherRequestDTO dto) {

        State state = stateRepository.findById(dto.getStateCode())
                .orElseThrow(() ->
                        new StateNotFoundException("State not found with code: " + dto.getStateCode()));

        Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        publisher.setCity(dto.getCity());
        publisher.setState(state);

        return mapToDTO(publisherRepository.save(publisher));
    }

    @Override
    public PublisherResponseDTO updatePublisher(Integer id, PublisherRequestDTO dto) {

        Publisher existing = publisherRepository.findById(id)
                .orElseThrow(() ->
                        new PublisherNotFoundException("Publisher not found with ID: " + id));

        State state = stateRepository.findById(dto.getStateCode())
                .orElseThrow(() ->
                        new StateNotFoundException("State not found with code: " + dto.getStateCode()));

        existing.setName(dto.getName());
        existing.setCity(dto.getCity());
        existing.setState(state);

        return mapToDTO(publisherRepository.save(existing));
    }

    @Override
    public String deletePublisher(Integer id) {

        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() ->
                        new PublisherNotFoundException("Publisher not found with ID: " + id));

        publisherRepository.delete(publisher);

        return "Publisher deleted successfully with ID: " + id;
    }

    private PublisherResponseDTO mapToDTO(Publisher publisher) {

        PublisherResponseDTO dto = new PublisherResponseDTO();

        dto.setPublisherId(publisher.getPublisherId());
        dto.setName(publisher.getName());
        dto.setCity(publisher.getCity());

        if (publisher.getState() != null) {
            dto.setStateCode(publisher.getState().getStateCode());
            dto.setStateName(publisher.getState().getStateName());
        }

        return dto;
    }
}