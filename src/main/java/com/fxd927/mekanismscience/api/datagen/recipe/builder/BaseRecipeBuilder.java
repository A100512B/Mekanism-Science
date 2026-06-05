package com.fxd927.mekanismscience.api.datagen.recipe.builder;

import com.google.gson.JsonObject;
import mekanism.api.JsonConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.common.util.RegistryUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@NothingNullByDefault
public abstract class BaseRecipeBuilder<BUILDER extends BaseRecipeBuilder<BUILDER>> extends MekanismRecipeBuilder<BUILDER> {

    protected final Item result;
    protected final int count;
    private RecipeCategory category = RecipeCategory.MISC;
    @Nullable
    private String group;

    protected BaseRecipeBuilder(RecipeSerializer<?> serializer, ItemLike result, int count) {
        super(RegistryUtils.getName(serializer));
        this.result = result.asItem();
        this.count = count;
    }

    @SuppressWarnings("unchecked")
    private BUILDER self() {
        return (BUILDER) this;
    }

    public BUILDER group(String group) {
        this.group = group;
        return self();
    }

    public BUILDER category(RecipeCategory category) {
        this.category = category;
        return self();
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        build(consumer, result);
    }

    //Copied from CraftingRecipeBuilder#determineBookCategory
    protected StringRepresentable determineBookCategory() {
        return switch (category) {
            case BUILDING_BLOCKS -> CraftingBookCategory.BUILDING;
            case TOOLS, COMBAT -> CraftingBookCategory.EQUIPMENT;
            case REDSTONE -> CraftingBookCategory.REDSTONE;
            default -> CraftingBookCategory.MISC;
        };
    }

    protected abstract class BaseRecipeResult extends RecipeResult {

        protected BaseRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (group != null && !group.isEmpty()) {
                json.addProperty("group", group);
            }
            serializeResult(json);
        }

        protected void serializeResult(JsonObject json) {
            StringRepresentable category = determineBookCategory();
            if (category != CraftingBookCategory.MISC && category != CookingBookCategory.MISC) {
                json.addProperty("category", category.getSerializedName());
            }
            JsonObject jsonResult = new JsonObject();
            jsonResult.addProperty(JsonConstants.ITEM, RegistryUtils.getName(result).toString());
            if (count > 1) {
                jsonResult.addProperty(JsonConstants.COUNT, count);
            }
            json.add("result", jsonResult);
        }
    }

    public static class RecipePattern {
        public final String row1;
        @Nullable
        public final String row2;
        @Nullable
        public final String row3;

        private RecipePattern(String row1) {
            this(row1, null, null);
        }

        private RecipePattern(String row1, @Nullable String row2) {
            this(row1, row2, null);
        }

        private RecipePattern(String row1, @Nullable String row2, @Nullable String row3) {
            this.row1 = row1;
            this.row2 = row2;
            this.row3 = row3;
        }

        //For 1x2 recipes
        public static RecipePattern createPattern(DoubleLine row1) {
            return new RecipePattern(row1.columns);
        }

        //For 2x1 recipes
        public static RecipePattern createPattern(char row1, char row2) {
            return new RecipePattern(Character.toString(row1), Character.toString(row2));
        }

        //For 2x2 recipes
        public static RecipePattern createPattern(DoubleLine row1, DoubleLine row2) {
            return new RecipePattern(row1.columns, row2.columns);
        }

        //For 1x3 recipes
        public static RecipePattern createPattern(TripleLine row1) {
            return new RecipePattern(row1.columns);
        }

        //For 2x3 recipes
        public static RecipePattern createPattern(TripleLine row1, TripleLine row2) {
            return new RecipePattern(row1.columns, row2.columns);
        }

        //For 3x1 recipes
        public static RecipePattern createPattern(char row1, char row2, char row3) {
            return new RecipePattern(Character.toString(row1), Character.toString(row2), Character.toString(row3));
        }

        //For 3x2 recipes
        public static RecipePattern createPattern(DoubleLine row1, DoubleLine row2, DoubleLine row3) {
            return new RecipePattern(row1.columns, row2.columns, row3.columns);
        }

        //For 3x3 recipes
        public static RecipePattern createPattern(TripleLine row1, TripleLine row2, TripleLine row3) {
            return new RecipePattern(row1.columns, row2.columns, row3.columns);
        }

        public static class DoubleLine {

            private final String columns;

            private DoubleLine(String columns) {
                this.columns = columns;
            }

            public static DoubleLine of(char column1, char column2) {
                return new DoubleLine(Character.toString(column1) + column2);
            }
        }

        public static class TripleLine {

            private final String columns;

            private TripleLine(String columns) {
                this.columns = columns;
            }

            public static TripleLine of(char column1, char column2, char column3) {
                return new TripleLine(Character.toString(column1) + column2 + column3);
            }
        }
    }
}
