package com.backend.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.AddressRequest;
import com.backend.dto.AddressResponse;
import com.backend.service.AddressService;
import com.backend.utils.ApiPath;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.V1 + "/users/{userId}/addresses")
@RequiredArgsConstructor
@Validated
@CrossOrigin("http://localhost:5173/")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @Operation(description = "Add a new address for user")
    public ResponseEntity<AddressResponse> addAddress(@PathVariable Long userId,
            @RequestBody @Valid AddressRequest request) {
        return new ResponseEntity<>(addressService.addAddress(userId, request), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(description = "Get all addresses for user")
    public ResponseEntity<?> getAddressByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddressByUserId(userId));
    }
}
