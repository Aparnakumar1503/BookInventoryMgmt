package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.request.PublisherRequestDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.DTO.response.PublisherResponseDTO;

import java.util.List;

public interface PublisherService {

    List<PublisherResponseDTO> getAllPublishers();

    PublisherResponseDTO getPublisherById(Integer id);

    PublisherResponseDTO createPublisher(PublisherRequestDTO dto);

    PublisherResponseDTO updatePublisher(Integer id, PublisherRequestDTO dto);

    void deletePublisher(Integer id);
}