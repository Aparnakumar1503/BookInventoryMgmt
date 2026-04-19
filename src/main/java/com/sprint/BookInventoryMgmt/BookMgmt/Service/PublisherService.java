package com.sprint.BookInventoryMgmt.bookmgmt.service;

import com.sprint.BookInventoryMgmt.bookmgmt.dto.request.PublisherRequestDTO;
import com.sprint.BookInventoryMgmt.bookmgmt.dto.response.PublisherResponseDTO;

import java.util.List;

public interface PublisherService {

    List<PublisherResponseDTO> getAllPublishers();

    PublisherResponseDTO getPublisherById(Integer id);

    PublisherResponseDTO createPublisher(PublisherRequestDTO dto);

    PublisherResponseDTO updatePublisher(Integer id, PublisherRequestDTO dto);

    void deletePublisher(Integer id);
}