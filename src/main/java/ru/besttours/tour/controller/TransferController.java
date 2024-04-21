package ru.besttours.tour.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.besttours.tour.dto.TransferDTO;
import ru.besttours.tour.models.Transfer;
import ru.besttours.tour.services.TransferService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;
    private final ModelMapper modelMapper;

    @Autowired
    public TransferController(TransferService transferService, ModelMapper modelMapper) {
        this.transferService = transferService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<TransferDTO> getAllTransfers(){
        return transferService.findAll().stream().map(this::convertToTransferDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TransferDTO getOneTransfer(@PathVariable int id){
        return convertToTransferDTO(transferService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createTransfer(@RequestBody @Valid TransferDTO transferDTO){
        Transfer transfer = transferService.create(convertToTransfer(transferDTO));
        if (transfer == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<TransferDTO> updateTransfer(@RequestBody @Valid TransferDTO transferDTO, int id){
        Transfer transfer = transferService.update(id, convertToTransfer(transferDTO));
        if (transfer == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(convertToTransferDTO(transfer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransfer(@PathVariable int id){
        transferService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private TransferDTO convertToTransferDTO(Transfer transfer){
        return modelMapper.map(transfer, TransferDTO.class);
    }

    private Transfer convertToTransfer(TransferDTO transferDTO){
        return modelMapper.map(transferDTO, Transfer.class);
    }
}
