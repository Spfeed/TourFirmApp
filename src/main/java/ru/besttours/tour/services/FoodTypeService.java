package ru.besttours.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.besttours.tour.dto.PcCrudDTO;
import ru.besttours.tour.models.FoodType;
import ru.besttours.tour.repo.FoodTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FoodTypeService {

    private final FoodTypeRepository foodTypeRepository;

    @Autowired
    public FoodTypeService(FoodTypeRepository foodTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
    }

    //CRUD methods

    public List<FoodType> findAll() {
        return foodTypeRepository.findAll();
    }

    public FoodType findOne(int id) {
        Optional<FoodType> foundFoodType = foodTypeRepository.findById(id);
        return foundFoodType.orElse(null);
    }

    @Transactional
    public FoodType save(FoodType foodType) {
        return foodTypeRepository.save(foodType);
    }

    @Transactional
    public FoodType update(int id, FoodType updatedType) {
        updatedType.setId(id);
        return foodTypeRepository.save(updatedType);
    }

    @Transactional
    public void delete(int id) {
        foodTypeRepository.deleteById(id);
    }

    public List<PcCrudDTO> getFoodTypesForCrudPC(){
        List<FoodType> foodTypes = foodTypeRepository.findAll();
        List<PcCrudDTO> dtos = new ArrayList<>();

        for (FoodType foodType : foodTypes){
            PcCrudDTO dto = new PcCrudDTO();
            dto.setId(foodType.getId());
            dto.setName(foodType.getName());
            dtos.add(dto);
        }
        return dtos;
    }
}
