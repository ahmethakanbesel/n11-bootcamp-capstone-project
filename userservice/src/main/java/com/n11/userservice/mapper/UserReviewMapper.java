package com.n11.userservice.mapper;

import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.request.CreateUserReviewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserReviewMapper {
    UserReviewMapper INSTANCE = Mappers.getMapper(UserReviewMapper.class);

    UserReview convertToUserReview(CreateUserReviewRequest request);

    UserReviewDTO convertToUserReviewDTO(UserReview userReview);

    List<UserReviewDTO> convertToUserReviewDTOList(List<UserReview> reviews);
}

