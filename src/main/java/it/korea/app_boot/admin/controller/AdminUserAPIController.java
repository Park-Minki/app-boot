package it.korea.app_boot.admin.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.korea.app_boot.admin.dto.AdminUserDTO;
import it.korea.app_boot.admin.dto.AdminUserSearchDTO;
import it.korea.app_boot.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminUserAPIController {

    private final AdminUserService userService;

    @GetMapping("/admin/user")
    public ResponseEntity<Map<String, Object>> getUserList(
        @PageableDefault(page = 0, size = 10, sort = "createDate", direction = Direction.DESC) Pageable pageable,
        AdminUserSearchDTO searchDTO) throws Exception{

        Map<String, Object> resultMap = userService.getUserList(pageable, searchDTO);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PutMapping("/admin/user/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable String userId,
            @RequestBody AdminUserDTO dto) throws Exception {

        userService.updateUser(userId, dto);
        return ResponseEntity.ok("사용자 수정 완료");
    }


    @DeleteMapping("/admin/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("fail: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
