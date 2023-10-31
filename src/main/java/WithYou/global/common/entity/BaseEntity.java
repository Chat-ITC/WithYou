package WithYou.global.common.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Transient
    ZoneId seoulZone = ZoneId.of("Asia/Seoul"); // ZoneId가 칼럼이 되는 것을 방지
    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now(seoulZone);
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
