package com.backend.service;

import java.util.List;

import com.backend.dto.AddressRequest;
import com.backend.dto.AddressResponse;

public interface AddressService {
	AddressResponse addAddress(Long userId, AddressRequest request);
	List<AddressResponse> getAddressByUserId(Long userId);
}
