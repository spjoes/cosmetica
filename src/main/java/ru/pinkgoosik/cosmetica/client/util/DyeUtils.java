package ru.pinkgoosik.cosmetica.client.util;

import com.google.common.collect.Maps;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DyeColor;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.stream.Collectors;

public class DyeUtils {

	private static final EnumMap<DyeColor, float[]> COLOR_ARRAY_BY_COLOR = Maps.<DyeColor, float[]>newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(dyeColor -> dyeColor, DyeUtils::createSheepColor)));

	private static float[] createSheepColor(DyeColor color) {
		if (color == DyeColor.WHITE) {
			return new float[] {0.9019608F, 0.9019608F, 0.9019608F};
		} else {
			float[] fs = color.getColorComponents();
			float f = 0.75F;
			return new float[] {fs[0] * 0.75F, fs[1] * 0.75F, fs[2] * 0.75F};
		}
	}

	public static float[] getColorArray(DyeColor dyeColor) {
		return COLOR_ARRAY_BY_COLOR.get(dyeColor);
	}

	public static float[] createJebColorTransition(LivingEntity entity, float tickDelta) {
		int agePlusId = entity.age / 25 + entity.getId();
		int dyeColorsAmount = DyeColor.values().length;
		int p = agePlusId % dyeColorsAmount;
		int q = (agePlusId + 1) % dyeColorsAmount;
		float r2 = ((entity.age % 25) + tickDelta) / 25.0F;
		float[] fs = getColorArray(DyeColor.byId(p));
		float[] gs = getColorArray(DyeColor.byId(q));
		float r = fs[0] * (1.0F - r2) + gs[0] * r2;
		float g = fs[1] * (1.0F - r2) + gs[1] * r2;
		float b = fs[2] * (1.0F - r2) + gs[2] * r2;
		return new float[] {r, g, b};
	}

}
