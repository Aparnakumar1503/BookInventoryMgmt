package com.sprint.bookinventorymgmt.bookmgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.PublisherRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.PublisherResponseDTO;

import java.util.List;

public interface IPublisherService {

    List<PublisherResponseDTO> getAllPublishers();

    PublisherResponseDTO getPublisherById(Integer id);

    PublisherResponseDTO createPublisher(PublisherRequestDTO dto);

    PublisherResponseDTO updatePublisher(Integer id, PublisherRequestDTO dto);

    String deletePublisher(Integer id); // ✅ FIXED
}