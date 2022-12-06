package fudan.pm.fudanCompass.repository;

import fudan.pm.fudanCompass.entity.LikeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeInfoRepository extends JpaRepository<LikeInfo, Long> {

    LikeInfo findFirstByLikeIdAndTypeAndUserIdAndIsCancelled(Long articleId, Integer type,  Long userId, Boolean isCancelled);

}
