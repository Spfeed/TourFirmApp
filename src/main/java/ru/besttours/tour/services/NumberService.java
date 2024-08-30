package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.NumberForCrudDTO;
import ru.besttours.tour.dto.NumberToFormDTO;
import ru.besttours.tour.dto.NumberTypeDTO;
import ru.besttours.tour.models.Hotel;
import ru.besttours.tour.models.Number;
import ru.besttours.tour.repo.HotelRepository;
import ru.besttours.tour.repo.NumberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NumberService {

    private final NumberRepository numberRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public NumberService(NumberRepository numberRepository, HotelRepository hotelRepository) {
        this.numberRepository = numberRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Number> findAll() {
        return numberRepository.findAll();
    }

    public Number findOne(int id) {
        return numberRepository.findById(id).orElse(null);
    }

    @Transactional
    public Number create(Number number) {
        return numberRepository.save(number);
    }

    @Transactional
    public Number update(int id, Number updatedNumber) {
        updatedNumber.setId(id);
        return numberRepository.save(updatedNumber);
    }

    @Transactional
    public void delete(int id) {
        numberRepository.deleteById(id);
    }

    public List<NumberToFormDTO> getNumbersForHotel(int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new IllegalArgumentException("Отель с таким id не найден"));
        List<Number> numbers = hotel.getNumbers();

        List<NumberToFormDTO> dtos = new ArrayList<>();

        for (Number number : numbers) {
            NumberToFormDTO dto = new NumberToFormDTO();
            dto.setId(number.getId());
            dto.setName(number.getNumberType().getName());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<NumberForCrudDTO> getNumberForCrudPC() {
        List<Number> numbers = numberRepository.findAll();
        List<NumberForCrudDTO> dtos = new ArrayList<>();

        for (Number number : numbers){
            NumberForCrudDTO dto = new NumberForCrudDTO();
            dto.setId(number.getId());
            dto.setHotelName(number.getHotel().getName());
            dto.setNumberTypeName(number.getNumberType().getName());
            dtos.add(dto);
        }
        return dtos;
    }
}
