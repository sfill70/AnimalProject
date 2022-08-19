package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.herbivore.*;

public class Chupacabra extends Wolf {
    static {
        UtilAnimal.putMapAmountAnimal(Chupacabra.class, 30);
        UtilAnimal.putSpeedAnimal(Chupacabra.class, 3);
        UtilAnimal.putAbilityToReproduce(Chupacabra.class, 4);
    }

    public Chupacabra() {
        super();
        this.foodConsumption = 16;
    }
}
