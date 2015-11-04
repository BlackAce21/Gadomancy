package makeo.gadomancy.common.registration;

import makeo.gadomancy.common.utils.Injector;
import net.minecraft.potion.Potion;

/**
 * This class is part of the Gadomancy Mod
 * Gadomancy is Open Source and distributed under the
 * GNU LESSER GENERAL PUBLIC LICENSE
 * for more read the LICENSE file
 * <p/>
 * Created by makeo @ 31.10.2015 23:09
 */
public class RegisteredPotions {
    public static void init() {
    }

    private static <T extends Potion> T registerPotion(Class<T> potionClass) {
        int id = Potion.potionTypes.length;
        Potion[] potions = new Potion[Potion.potionTypes.length + 1];
        System.arraycopy(Potion.potionTypes, 0, potions, 0, Potion.potionTypes.length);
        Potion.potionTypes = potions;

        return new Injector(potionClass).invokeConstructor(int.class, id);
    }
}
