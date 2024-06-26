package io.github.betterclient.version.mixin.client.entity;

import io.github.betterclient.version.access.ItemEntityRotator;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Code "borrowed" from: <a href="https://github.com/Draylar/better-dropped-items/blob/1.16.2/src/main/java/bdi/mixin/ItemEntityMixin.java">...</a>
 */
@Mixin(ItemEntity.class)
public class MixinItemEntity implements ItemEntityRotator {

    private Vec3d rotation = new Vec3d(0, 0, 0);

    @Override
    public Vec3d getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(Vec3d rotation) {
        this.rotation = rotation;
    }
}