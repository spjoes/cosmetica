package ru.pinkgoosik.goosiklib.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.goosiklib.client.GoosikLibClient;
import ru.pinkgoosik.goosiklib.client.PlayerCapes;

import java.util.UUID;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerMixin extends PlayerEntity {

    public AbstractClientPlayerMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    void getCapeTexture(CallbackInfoReturnable<Identifier> cir){
        PlayerCapes capes = GoosikLibClient.getPlayerCapes();
        String capeId = "goosiklib:textures/cape/type.png";

        if(capes != null){
            capes.getEntries().forEach(entry -> {
                if(!entry.getPlayerUuid().equals("-") && this.getUuid().equals(UUID.fromString(entry.getPlayerUuid()))){
                    if(entry.getCape().equals("uni")){
                        cir.setReturnValue(new Identifier(getUniCapeType()));
                    }else if(capes.getAvailableCapes().contains(entry.getCape())){
                        cir.setReturnValue(new Identifier(capeId.replaceAll("type", entry.getCape())));
                    }
                }else if(this.getName().asString().equals(entry.getPlayerName())){
                    if(entry.getCape().equals("uni")){
                        cir.setReturnValue(new Identifier(getUniCapeType()));
                    }else if(capes.getAvailableCapes().contains(entry.getCape())){
                        cir.setReturnValue(new Identifier(capeId.replaceAll("type", entry.getCape())));
                    }
                }
            });
        }
    }

    @Unique
    private String getUniCapeType(){
        String capeId = "goosiklib:textures/cape/type.png";
        RegistryKey<World> worldKey = this.world.getRegistryKey();
        if(worldKey.equals(World.OVERWORLD))capeId = capeId.replaceAll("type", "green");
        else if(worldKey.equals(World.NETHER))capeId = capeId.replaceAll("type", "red");
        else if(worldKey.equals(World.END))capeId = capeId.replaceAll("type", "purple");
        else capeId = capeId.replaceAll("type", "green");
        return capeId;
    }
}
