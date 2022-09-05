package com.alkemy.ong.service;

import com.alkemy.ong.dto.request.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityResponseDTO;

public interface IActivityService {
    ActivityResponseDTO create(ActivityRequestDTO dto);
}
