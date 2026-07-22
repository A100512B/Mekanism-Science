package com.fxd927.mekanismscience.datagen.client.lang;

import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.registries.*;
import com.thevortex.allthemodium.registry.mek_reg.ATMResource;
import mekanism.common.resource.IResource;
import mekanism.common.resource.MiscResource;
import mekanism.common.resource.PrimaryResource;
import net.allthemods.alltheores.blocks.mek_reg.ATOResource;
import net.minecraft.data.PackOutput;

public class MSZhCnLangProvider extends MSBaseLangProvider {

    public MSZhCnLangProvider(PackOutput output) {
        super(output, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addItems();
        addBlocks();
        addFluids();
        addGases();
        addSubtitles();
        addMisc();
    }

    private void addItems() {
        add(MSItems.ACIDIC_SUBSTRATE, "酸性基片");
        add(MSItems.ALKALINE_SUBSTRATE, "碱性基片");
        add(MSItems.BONE_ASHES, "骨灰");
        add(MSItems.BONE_ASHES_WITH_CARBON, "含碳骨灰");
        add(MSItems.DUST_CALCIUM_CHLORIDE, "氯化钙");
        add(MSItems.DUST_CALCIUM_OXIDE, "生石灰粉");
        add(MSItems.EXCIPIENT, "赋形剂");
        add(MSItems.HIGH_DENSITY_NEUTRON_SOURCE_PELLET, "高密度中子源靶丸");
        add(MSItems.NEUTRON_SOURCE_PELLET, "中子源靶丸");
        add(MSItems.PTFE_PELLET, "聚四氟乙烯丸");
        add(MSItems.PTFE_SHEET, "聚四氟乙烯片");
        add(MSItems.REFINED_CALIFORNIUM_INGOT, "强化锎锭");
        add(MSItems.TABLET_ANESTHETIC, "麻醉药片");
        add(MSItems.TABLET_FIRE_RESISTANCE, "抗火药片");
        add(MSItems.TABLET_IODINE, "碘片");
        add(MSItems.TABLET_MUSCLE_ENHANCEMENT, "肌肉强化药片（WIP）");
        add(MSItems.TABLET_POISON, "毒药片（WIP）");
        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO.getBucket(), translateResource(resource) + "浓缩液桶"));
        MSFluids.PROCESSED_EXTRACT_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO.getBucket(), translateResource(resource) + " P204 萃取液桶"));
        MSFluids.PROCESSED_LEACHATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO.getBucket(), translateResource(resource) + "酸浸液桶"));
    }

    private void addBlocks() {
        add(MSBlocks.ACID_LEACHER, "酸浸室");
        add(MSBlocks.ADSORPTION_SEPARATOR, "吸附分离机");
        add(MSBlocks.AIR_COMPRESSOR, "空气压缩机");
        add(MSBlocks.ANTI_EXTRACTING_PILLAR, "反萃取柱");
        add(MSBlocks.ANTI_EXTRACTING_PLANT_CASING, "反萃取工厂外壳");
        add(MSBlocks.ANTI_EXTRACTING_PLANT_PORT, "反萃取工厂端口");
        add(MSBlocks.EXTRACTING_PILLAR, "萃取柱");
        add(MSBlocks.EXTRACTING_PLANT_CASING, "萃取工厂外壳");
        add(MSBlocks.EXTRACTING_PLANT_PORT, "萃取工厂端口");
        add(MSBlocks.IRRADIATOR, "辐照器");
        add(MSBlocks.METAL_ELECTROLYSIS_CHAMBER, "金属电解池");
        add(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER, "加压聚合室");
        add(MSBlocks.SEAWATER_PUMP, "海水泵");
    }

    private void addFluids() {
        addFluid(MSFluids._2_BUTENE, "液态 2-丁烯");
        addFluid(MSFluids._2_ETHYL_2_HEXENAL, "液态 2-乙基-2-己醛");
        addFluid(MSFluids.AMMONIA, "液氨");
        addFluid(MSFluids.AQUA_REGIA, "液态王水");
        addFluid(MSFluids.BERYLLIUM, "液态铍");
        addFluid(MSFluids.BROMINE, "液溴");
        addFluid(MSFluids.BUTYRALDEHYDE_MIXTURE, "液态丁醛混合物");
        addFluid(MSFluids.CHLOROMETHANE, "液态一氯甲烷");
        addFluid(MSFluids.COMPRESSED_AIR, "液态压缩空气");
        addFluid(MSFluids.CONCENTRATED_SEAWATER, "浓缩海水");
        addFluid(MSFluids.ETHANOL, "液态乙醇");
        addFluid(MSFluids.HELIUM, "液氦");
        addFluid(MSFluids.HYDRAZINE, "液态肼");
        addFluid(MSFluids.IMPURE_PHOSPHORYL_CHLORIDE, "不纯的液态三氯氧磷");
        addFluid(MSFluids.IODINE, "液态碘");
        addFluid(MSFluids.ISOBUTYRALDEHYDE, "液态异丁醛");
        addFluid(MSFluids.ISOOCTANOL, "液态异辛醇");
        addFluid(MSFluids.LACTOSE, "液态乳糖");
        addFluid(MSFluids.METHANOL, "液态甲醇");
        addFluid(MSFluids.METHYLAMINE, "液态甲胺");
        addFluid(MSFluids.MILK, "牛奶");
        addFluid(MSFluids.N_BUTYRALDEHYDE, "液态正丁醛");
        addFluid(MSFluids.NITRIC_ACID, "液态硝酸");
        addFluid(MSFluids.NITRIC_OXIDE, "液态一氧化氮");
        addFluid(MSFluids.NITROGEN, "液氮");
        addFluid(MSFluids.NITROGEN_DIOXIDE, "液态二氧化氮");
        addFluid(MSFluids.P204, "液态 P204");
        addFluid(MSFluids.PHOSPHORYL_CHLORIDE, "液态三氯氧磷");
        addFluid(MSFluids.POTASSIUM_NITRATE, "液态硝酸钾");
        addFluid(MSFluids.PROPYLENE, "液态丙烯");
        addFluid(MSFluids.PTFE, "液态聚四氟乙烯");
        addFluid(MSFluids.SEAWATER, "海水");
        addFluid(MSFluids.SODIUM_HYDROXIDE, "液态氢氧化钠");
        addFluid(MSFluids.STRONTIUM, "液态锶");
        addFluid(MSFluids.SUPERHEATED_HELIUM, "液态过热氦");
        addFluid(MSFluids.TETRAFLUOROETHYLENE, "液态四氟乙烯");
        addFluid(MSFluids.WATER_GAS, "液态水煤气");
        addFluid(MSFluids.WHEY, "液态乳清");
        addFluid(MSFluids.YTTRIUM, "液态钇");
        MSFluids.PROCESSED_CONCENTRATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO, translateResource(resource) + "浓缩液"));
        MSFluids.PROCESSED_EXTRACT_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO, translateResource(resource) + " P204 萃取液"));
        MSFluids.PROCESSED_LEACHATE_RESOURCES.forEach((resource, fluidRO) -> add(fluidRO, translateResource(resource) + "酸浸液"));
    }

    private void addGases() {
        add(MSGases._2_BUTENE, "2-丁烯");
        add(MSGases._2_ETHYL_2_HEXENAL, "2-乙基-2-己醛");
        add(MSGases.AMERICIUM, "镅（WIP）");
        add(MSGases.AMMONIA, "氨气");
        add(MSGases.AQUA_REGIA, "王水");
        add(MSGases.BERYLLIUM, "铍");
        add(MSGases.BROMINE, "溴");
        add(MSGases.BUTYRALDEHYDE_MIXTURE, "丁醛混合物");
        add(MSGases.CALIFORNIUM, "锎");
        add(MSGases.CHLOROMETHANE, "一氯甲烷");
        add(MSGases.COMPRESSED_AIR, "压缩空气");
        add(MSGases.CONCENTRATED_SEAWATER, "浓缩海水");
        add(MSGases.ETHANOL, "乙醇");
        add(MSGases.HELIUM, "氦气");
        add(MSGases.HYDRAZINE, "肼");
        add(MSGases.IMPURE_PHOSPHORYL_CHLORIDE, "不纯的三氯氧磷");
        add(MSGases.IODINE, "碘");
        add(MSGases.ISOBUTYRALDEHYDE, "异丁醛");
        add(MSGases.ISOOCTANOL, "异辛醇");
        add(MSGases.LACTOSE, "乳糖");
        add(MSGases.METHANOL, "甲醇");
        add(MSGases.METHYLAMINE, "甲胺");
        add(MSGases.N_BUTYRALDEHYDE, "正丁醛");
        add(MSGases.NITRIC_ACID, "硝酸");
        add(MSGases.NITRIC_OXIDE, "一氧化氮");
        add(MSGases.NITROGEN, "氮气");
        add(MSGases.NITROGEN_DIOXIDE, "二氧化氮");
        add(MSGases.P204, "P204");
        add(MSGases.PHOSPHORYL_CHLORIDE, "三氯氧磷");
        add(MSGases.POTASSIUM_NITRATE, "硝酸钾");
        add(MSGases.PROPYLENE, "丙烯");
        add(MSGases.PTFE, "聚四氟乙烯");
        add(MSGases.SEAWATER, "海水");
        add(MSGases.SODIUM_HYDROXIDE, "氢氧化钠");
        add(MSGases.STRONTIUM, "锶（WIP）");
        add(MSGases.SUPERHEATED_HELIUM, "过热氦");
        add(MSGases.TETRAFLUOROETHYLENE, "四氟乙烯");
        add(MSGases.WATER_GAS, "水煤气");
        add(MSGases.WHEY, "乳清");
        add(MSGases.YTTRIUM, "钇（WIP)");
    }

    private void addSubtitles() {
        add(MSSounds.ACID_LEACHER, "酸浸机：工作");
        add(MSSounds.AIR_COMPRESSOR, "空气压缩机：嗡嗡声");
        add(MSSounds.ANTI_EXTRACTING_PLANT, "反萃取工厂：液体翻滚");
        add(MSSounds.EXTRACTING_PLANT, "萃取工厂：液体翻滚");
        add(MSSounds.METAL_ELECTROLYSIS_CHAMBER, "金属电解池：滋滋");
        add(MSSounds.PRESSURIZED_POLYMERIZING_CHAMBER, "加压聚合室：嗡嗡");
    }

    public void addMisc() {
        // Mod Name
        add(MSLang.MEKANISM_SCIENCE, "通用机械：科学");
        // Descriptions
        add(MSLang.DESCRIPTION_ACID_LEACHER, "一台巨大的化学惰性机器，可以安全存储大量危险的酸并用其浸出绝大多数金属。");
        add(MSLang.DESCRIPTION_ADSORPTION_SEPARATOR, "一台简单的机器，可以用指定的吸附剂吸附特定物质。");
        add(MSLang.DESCRIPTION_AIR_COMPRESSOR, "一台气密性良好，能把空气压缩至高压的机器。");
        add(MSLang.DESCRIPTION_ANTI_EXTRACTING_PILLAR, "用存储的酸液从萃取液中反萃取金属离子的化学惰性柱。");
        add(MSLang.DESCRIPTION_ANTI_EXTRACTING_PLANT_CASING, "化学惰性的外壳，用于建造反萃取工厂，能保护你的基地免于被酸腐蚀。");
        add(MSLang.DESCRIPTION_ANTI_EXTRACTING_PLANT_PORT, "化学惰性的端口，用于建造反萃取工厂，使液体和化学品在其中迅速流动。");
        add(MSLang.DESCRIPTION_EXTRACTING_PILLAR, "用存储的萃取剂从酸浸液中萃取金属离子的化学惰性柱。");
        add(MSLang.DESCRIPTION_EXTRACTING_PLANT_CASING, "化学惰性的外壳，用于建造萃取工厂，能保护你的基地免于被酸腐蚀。");
        add(MSLang.DESCRIPTION_EXTRACTING_PLANT_PORT, "化学惰性的端口，用于建造萃取工厂，使液体和化学品在其中迅速流动。");
        add(MSLang.DESCRIPTION_IRRADIATOR, "一台可快速辐照物品的机器。核电，轻而易举啊！");
        add(MSLang.DESCRIPTION_METAL_ELECTROLYSIS_CHAMBER, "一台用于强力电解溶液得到高纯金属的大型机器。");
        add(MSLang.DESCRIPTION_PRESSURIZED_POLYMERIZING_CHAMBER, "一台高级的用于高压聚合有机化合物的机器。");
        add(MSLang.DESCRIPTION_SEAWATER_PUMP, "一台专为在海洋群系抽取海水而设计的泵。");
        // Extracting Plant
        add(MSLang.EXTRACTING_PLANT, "萃取工厂");
        add(MSLang.EXTRACTING_PLANT_INVALID_EVEN_LENGTH, "多方块结构无法成型，结构的长宽必须是奇数。");
        add(MSLang.EXTRACTING_PLANT_INVALID_MALFORMED_EXTRACTING_PILLARS, "多方块结构无法成型，发现错误的萃取柱位置。");
        add(MSLang.EXTRACTING_PLANT_INVALID_NOT_SQUARE, "多方块结构无法成型，结构的长宽必须相等。");
        add(MSLang.EXTRACTING_PLANT_PORT_MODE_INPUT_EXTRACTANT, "输入萃取剂");
        add(MSLang.EXTRACTING_PLANT_PORT_MODE_INPUT_LEACHATE, "输入酸浸液");
        add(MSLang.EXTRACTING_PLANT_PORT_MODE_OUTPUT, "输出");
        // Anti-Extracting Plant
        add(MSLang.ANTI_EXTRACTING_PLANT, "反萃取工厂");
        add(MSLang.ANTI_EXTRACTING_PLANT_INVALID_EVEN_LENGTH, "多方块结构无法成型，结构的长宽必须是奇数。");
        add(MSLang.ANTI_EXTRACTING_PLANT_INVALID_MALFORMED_ANTI_EXTRACTING_PILLARS, "多方块结构无法成型，发现错误的反萃取柱位置。");
        add(MSLang.ANTI_EXTRACTING_PLANT_INVALID_NOT_SQUARE, "多方块结构无法成型，结构的长宽必须相等。");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_ANTI_EXTRACTANT, "输入反萃取剂");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_EXTRACT, "输入萃取液");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_CONCENTRATE, "输出浓缩液");
        add(MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_EXTRACTANT, "输出萃取剂");
    }

    private static String translateResource(IResource resource) {
        if (resource instanceof PrimaryResource res) {
            return switch (res) {
                case TIN -> "锡";
                case GOLD -> "金";
                case IRON -> "铁";
                case LEAD -> "铅";
                case COPPER -> "铜";
                case OSMIUM -> "锇";
                case URANIUM -> "铀";
            };
        } else if (resource instanceof MiscResource res) {
            // Although we only make use of netherite and redstone, we might use
            // other resources in future updates as well.
            return switch (res) {
                case BRONZE -> "黄铜";
                case CARBON -> "碳";
                case CHARCOAL -> "木炭";
                case COAL -> "煤炭";
                case DIAMOND -> "钻石";
                case EMERALD -> "绿宝石";
                case NETHERITE -> "下界合金";
                case LAPIS_LAZULI -> "青金石";
                case LITHIUM -> "锂";
                case OBSIDIAN -> "黑曜石";
                case QUARTZ -> "下界石英";
                case REDSTONE -> "红石";
                case REFINED_GLOWSTONE -> "强化萤石";
                case REFINED_OBSIDIAN -> "强化黑曜石";
                case STEEL -> "钢";
                case SULFUR -> "硫";
                case FLUORITE -> "氟石";
            };
        } else if (resource instanceof ATMResource res) {
            return switch (res) {
                case ATM -> "ATM";
                case VIB -> "振金";
                case UNOB -> "难得素";
            };
        } else if (resource instanceof ATOResource res) {
            return switch (res) {
                case ALUMINUM -> "铝";
                case NICKEL -> "镍";
                case PLATINUM -> "铂";
                case SILVER -> "银";
                case ZINC -> "锌";
                case IRIDIUM -> "铱";
            };
        } else return "未知";  // Unrecognized resource type
    }
}
