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

@RestController
@RequestMapping("/api/calls")
@AllArgsConstructor
public class CallController {

    private CallService callService;

    public ResponseEntity<CallDto> createCall(@AuthenticationPrincipal User user,
                                              @RequestBody CallDto callDto) {
        CallDto newCallDto = callService.createCall(user, callDto.subject(), callDto.description());
        return ResponseEntity.ok(newCallDto);
    }

    @GetMapping
    public ResponseEntity<Page<CallDto>> listUserCalls(@AuthenticationPrincipal User user,
                                                       Pageable pageable) {
        Page<CallDto> callDtos = callService.listUserCalls(user, pageable);
        return ResponseEntity.ok(callDtos);
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<CallDto> closeCall(@PathVariable Long id, @AuthenticationPrincipal User closedBy, @RequestParam int rating) {
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
