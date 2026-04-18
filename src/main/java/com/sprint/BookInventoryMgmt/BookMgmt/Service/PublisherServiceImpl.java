package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.requestdto.PublisherRequestDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.DTO.responsedto.PublisherResponseDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.State;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.PublisherNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.StateNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.PublisherRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final StateRepository stateRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository,
                                StateRepository stateRepository) {
        this.publisherRepository = publisherRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public List<PublisherResponseDTO> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PublisherResponseDTO getPublisherById(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + id));

        return mapToDTO(publisher);
    }

    @Override
    public PublisherResponseDTO createPublisher(PublisherRequestDTO dto) {

        State state = stateRepository.findById(dto.getStateCode())
                .orElseThrow(() -> new StateNotFoundException("State not found with code: " + dto.getStateCode()));

        Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        publisher.setCity(dto.getCity());
        publisher.setState(state);

        Publisher saved = publisherRepository.save(publisher);

        return mapToDTO(saved);
    }

    @Override
    public PublisherResponseDTO updatePublisher(Integer id, PublisherRequestDTO dto) {

        Publisher existing = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + id));

        State state = stateRepository.findById(dto.getStateCode())
                .orElseThrow(() -> new StateNotFoundException("State not found with code: " + dto.getStateCode()));

        existing.setName(dto.getName());
        existing.setCity(dto.getCity());
        existing.setState(state);

        Publisher updated = publisherRepository.save(existing);

        return mapToDTO(updated);
    }

    @Override
    public void deletePublisher(Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + id));

        publisherRepository.delete(publisher);
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