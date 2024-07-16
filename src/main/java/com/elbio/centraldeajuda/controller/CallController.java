package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.controller.dto.CallDto;
import com.elbio.centraldeajuda.entities.User;
import com.elbio.centraldeajuda.service.CallService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/calls")
@AllArgsConstructor
public class CallController {

    private CallService callService;

    public ResponseEntity<CallDto> createCall(UUID UIIUser,
                                              @RequestBody CallDto callDto) {
        CallDto newCallDto = callService.createCall(UIIUser, callDto.subject(), callDto.description());
        return ResponseEntity.ok(newCallDto);
    }

    @GetMapping
    public ResponseEntity<Page<CallDto>> listUserCalls(UUID UIIUser,
                                                       Pageable pageable) {
        Page<CallDto> callDtos = callService.listUserCalls(UIIUser, pageable);
        return ResponseEntity.ok(callDtos);
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<CallDto> closeCall(@PathVariable Long id, UUID closedBy, @RequestParam int rating) {
        CallDto callDto = callService.closeCall(id, closedBy, rating);
        return ResponseEntity.ok(callDto);
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<CallDto> rateCall(@PathVariable Long id, @RequestParam int rating) {
        CallDto callDto = callService.rateCall(id, rating);
        return ResponseEntity.ok(callDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CallDto>> searchCalls(@RequestParam String query) {
        List<CallDto> callDtos = callService.searchCalls(query, Long.parseLong(query));
        return ResponseEntity.ok(callDtos);
    }
}
