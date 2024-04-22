package com.api.deso.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data // Lombok 어노테이션: getter, setter, toString, equals, hashCode 메서드를 자동 생성합니다.
@Entity // 데이터베이스 테이블과 매핑되는 엔티티 클래스임을 나타냅니다.
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동 생성합니다.
@NoArgsConstructor // 파라미터가 없는 생성자를 자동 생성합니다.
@Builder // 빌더 패턴을 구현합니다.
@Accessors(chain = true) // setter 메서드를 체인 형태로 사용할 수 있도록 합니다.
@ToString(exclude = {"friendList", "blacklistList","bookMarkList","alimList","userLog"}) // 해당 필드를 toString 메서드에서 제외합니다.
@EntityListeners(AuditingEntityListener.class) // 엔티티의 변경 사항을 자동으로 감지합니다.
public class User {

    @Id // 엔티티의 주키(primary key)를 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 값의 전략을 지정합니다. (예: auto_increment)
    private Long id; // 사용자 엔티티의 고유 식별자입니다.

    private String email; // 사용자의 이메일 주소입니다.

    private String password; // 사용자의 비밀번호입니다.

    private String phoneNumber; // 사용자의 전화번호입니다.

    private String nickname; // 사용자의 닉네임입니다.

    private String profileImage; // 사용자의 프로필 이미지의 URL 또는 경로입니다.

    private String description; // 사용자의 소개 또는 자기 소개입니다.

    private String profileOpen; // 사용자의 프로필 공개 여부를 나타냅니다.

    private String role; // 사용자에게 할당된 역할입니다. (쉼표로 구분된 문자열)

    private Long level; // 사용자의 레벨 또는 순위입니다.

    private String status; // 사용자 계정의 상태입니다. (활성, 비활성 등)

    private String authKakao; // Kakao 로그인에 대한 인증 토큰입니다.

    private String authApple; // Apple 로그인에 대한 인증 토큰입니다.

    private Boolean adPush; // 사용자가 광고 푸시 알림을 허용하는지 여부를 나타냅니다.

    private Boolean adKakao; // 사용자가 Kakao 기반 광고를 허용하는지 여부를 나타냅니다.

    @CreatedDate // 엔티티가 생성된 날짜를 나타냅니다.
    private LocalDateTime createdAt; // 사용자 엔티티가 생성된 시간입니다.

    private LocalDateTime unregisteredAt; // 사용자가 등록 해제한 날짜입니다.

    @LastModifiedDate // 엔티티가 마지막으로 수정된 날짜를 나타냅니다.
    private LocalDateTime updatedAt; // 사용자 엔티티가 마지막으로 업데이트된 시간입니다.

    private Long warning; // 사용자에게 발생한 경고 횟수입니다.

    // 사용자의 역할 목록을 가져오는 메서드입니다.
    public List<String> getRoleList() {
        if(!this.role.isEmpty()) { // 역할이 지정되어 있는지 확인합니다.
            return Arrays.asList(this.role.split(",")); // 역할을 쉼표로 구분하여 리스트로 반환합니다.
        }
        return new ArrayList<>(); // 역할이 지정되어 있지 않으면 빈 리스트를 반환합니다.
    }

    // 일대다 관계: 사용자와 친구 엔티티 간의 관계
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Friend> friendList;

    // 일대다 관계: 사용자와 블랙리스트 엔티티 간의 관계
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Blacklist> blacklistList;

    // 일대다 관계: 사용자와 북마크 엔티티 간의 관계
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<BookMark> bookMarkList;

    // 일대다 관계: 사용자와 알림 엔티티 간의 관계 (알림은 알림 또는 경고를 나타냄)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "from")
    private List<Alim> alimList;

    // 일대다 관계: 사용자와 사용자 로그 엔티티 간의 관계 (사용자 로그는 사용자 활동과 관련된 로그를 나타냄)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserLog> userLog;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PurchaseLog> purchase_log;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Notice> notices;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<EventPostReview> eventPostReviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<EventReview> eventReviews;
}
