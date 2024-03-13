package com.n11.userservice.mapper;

import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.entity.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecommendationMapper {
    RecommendationMapper INSTANCE = Mappers.getMapper(RecommendationMapper.class);

    RecommendationDTO convertToRecommendationDTO(Recommendation recommendation);

    Recommendation convertToRecommendation(RecommendationDTO recommendationDTO);

    List<RecommendationDTO> convertToRecommendationDTOList(List<Recommendation> recommendations);
}
