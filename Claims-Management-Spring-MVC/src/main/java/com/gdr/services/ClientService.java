package com.gdr.services;

import java.util.List;

import com.gdr.dto.ClientDto;
import com.gdr.dto.PasswordUpdateDto;

public interface ClientService {

	public List<ClientDto> getAllClients();
	public void saveClient(ClientDto clientDto);
	public ClientDto getClient(String publicId);
	public ClientDto deleteClient(String publicId);
	public void updateClient(ClientDto clientDto);
	public ClientDto blockClient(ClientDto clientDto);
	public void updateClientPssword(PasswordUpdateDto passwordUpdateDto);
	
}
