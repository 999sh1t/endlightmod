package com.endlight.mixin;

import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public abstract class RubberSaplingPlantMixin {

    @Inject(
            method = "canPlantOnTop(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void allowEndStoneForRubberSapling(BlockState floor, BlockView world, BlockPos pos,
                                               CallbackInfoReturnable<Boolean> cir) {
        // 用注册名直接比对
        Block self = (Block) (Object) this;
        Identifier id = Registries.BLOCK.getId(self);

        // 只有橡胶树苗才继续判断
        if ("techreborn".equals(id.getNamespace()) && "rubber_sapling".equals(id.getPath())) {
            if (!cir.getReturnValueZ() && floor.isOf(Blocks.END_STONE)) {
                cir.setReturnValue(true);
            }
        }
    }
}