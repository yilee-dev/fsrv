package yilee.fsrv.login.service;

import yilee.fsrv.login.domain.dto.MemberDto;
import yilee.fsrv.login.domain.dto.SignUpRequest;
import yilee.fsrv.login.domain.dto.SignUpResponse;
import yilee.fsrv.login.domain.entity.Member;
import yilee.fsrv.login.domain.enums.MemberRole;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface MemberService {

    SignUpResponse signUp(SignUpRequest request);

    MemberDto find(Long id);

    MemberDto find(String username);

    void modify(MemberDto dto);

    void remove(Long id);

    default MemberDto fromEntity(Member member) {
        List<String> roles = member.getMemberRoleList()
                .stream().map(Enum::name)
                .toList();

        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .isDisabled(member.isDisabled())
                .disabledAt(member.getDisabledAt())
                .roles(roles)
                .build();
    }

    default Member toEntity(MemberDto dto) {
        Set<MemberRole> roles = dto.getRoles()
                .stream().map(MemberRole::valueOf)
                .collect(Collectors.toSet());

        return Member.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .isDisabled(dto.getIsDisabled())
                .password(dto.getPassword())
                .disabledAt(dto.getDisabledAt())
                .memberRoleList(roles)
                .build();
    }
}
