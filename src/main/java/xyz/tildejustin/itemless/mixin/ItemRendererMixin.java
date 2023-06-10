package xyz.tildejustin.itemless.mixin;

import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    private static final String ItemStack = "net/minecraft/class_1071";
    private static final String count = "field_4376";
    private static final String getCount = "method_13652";
    private static final String isEmpty = "method_13654";
    private static final String ItemStack$count = "L" + ItemStack + ";" + count + ":I";
    private static final String ItemStack$getCount = "L" + ItemStack + ";" + getCount + "()I";
    private static final String ItemStack$isEmpty = "L" + ItemStack + ";" + isEmpty + "()I";


    // method_1549 = 1.3.x-1.4.x
    // method_5178 = 1.5.x-1.7.x
    // method_10228 (renderGuiItemOverlay) = 1.8+
    // 1.8.0-1.12.0 already checks ItemStack.count != 1 instead of > 1 so the only change the does nothing
    // making a mod that just rendered 1 stacks would be one of the most :tf: mods that could ever be made
    // 1.12.1+ checks ItemStack$isEmpty as a anti-conditional for renderGuiItemOverlay,
    // which returns true if ItemStack$count < 1, so to render negative numbers that is forced to return false
    // It also calls ItemStack$getCount instead of accessing a field, b/c it's quirky like that


    // 1.3.0-1.7.x support
    @Redirect(
            method = {"method_1549", "method_5178"},
            at = @At(
                    value = "FIELD",
                    target = ItemStack$count,
                    opcode = Opcodes.GETFIELD,
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    private int itemless$alwaysRenderItemString(ItemStack itemStack) {
        return itemStack.count == 1 ? 1 : 2; // 2 > 1, 1 <= 1
    }

    // 1.12.1+ support
    @Redirect(
            method = "method_10228",
            at = @At(
                    value = "INVOKE",
                    target = ItemStack$getCount,
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    private int itemless$alwaysRenderItemStringOneTwelve(ItemStack itemStack) {
        return itemStack.count == 1 ? 1 : 2;
    }

    // 1.12.1+ support
    @Redirect(
            method = "method_10228",
            at = @At(
                    value = "INVOKE",
                    target = ItemStack$isEmpty,
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    private boolean itemless$lieAboutNotBeingEmpty() {
        return false;
    }
}
