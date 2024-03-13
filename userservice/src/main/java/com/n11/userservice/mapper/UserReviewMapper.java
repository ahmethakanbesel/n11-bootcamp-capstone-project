package com.n11.userservice.mapper;

import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.request.CreateUserReviewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserReviewMapper {
    UserReviewMapper INSTANCE = Mappers.getMapper(UserReviewMapper.class);

    UserReview convertToUserReview(CreateUserReviewRequest request);

    // Uncomment the following line if you are using join column in the entity
    // @Mapping(source = "user.id", target = "userId")
    UserReviewDTO convertToUserReviewDTO(UserReview userReview);

    List<UserReviewDTO> convertToUserReviewDTOList(Page<UserReview> reviews);
}

