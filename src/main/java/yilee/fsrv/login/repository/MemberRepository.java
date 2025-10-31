package yilee.fsrv.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yilee.fsrv.login.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
