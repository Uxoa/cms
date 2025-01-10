package io.airboss.cms.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Modifying
    @Query("UPDATE Profile p SET p.profileImage = :profileImage WHERE p.id = :id")
    void setProfileImage(@Param("profileImage") String profileImage, @Param("id") Long id);
    
}
