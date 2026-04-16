package com.fxd927.mekanismscience.common.tile.machine;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.math.FloatingLong;
import mekanism.common.integration.computer.*;
import mekanism.common.integration.computer.annotation.MethodFactory;
import net.minecraft.world.item.ItemStack;

@MethodFactory(target = TileEntityPressurizedPolymerizingChamber.class)
public class TileEntityPressurizedPolymerizingChamber$ComputerHandler extends ComputerMethodFactory<TileEntityPressurizedPolymerizingChamber> {

    public TileEntityPressurizedPolymerizingChamber$ComputerHandler() {
        this.register(MethodData.builder("getInput", TileEntityPressurizedPolymerizingChamber$ComputerHandler::inputTank$getInput).returnType(ChemicalStack.class).methodDescription("Get the contents of the input tank."));
        this.register(MethodData.builder("getInputCapacity", TileEntityPressurizedPolymerizingChamber$ComputerHandler::inputTank$getInputCapacity).returnType(Long.TYPE).methodDescription("Get the capacity of the input tank."));
        this.register(MethodData.builder("getInputNeeded", TileEntityPressurizedPolymerizingChamber$ComputerHandler::inputTank$getInputNeeded).returnType(Long.TYPE).methodDescription("Get the amount needed to fill the input tank."));
        this.register(MethodData.builder("getInputFilledPercentage", TileEntityPressurizedPolymerizingChamber$ComputerHandler::inputTank$getInputFilledPercentage).returnType(Double.TYPE).methodDescription("Get the filled percentage of the input tank."));
        this.register(MethodData.builder("getOutput", TileEntityPressurizedPolymerizingChamber$ComputerHandler::outputTank$getOutput).returnType(ChemicalStack.class).methodDescription("Get the contents of the output tank."));
        this.register(MethodData.builder("getOutputCapacity", TileEntityPressurizedPolymerizingChamber$ComputerHandler::outputTank$getOutputCapacity).returnType(Long.TYPE).methodDescription("Get the capacity of the output tank."));
        this.register(MethodData.builder("getOutputNeeded", TileEntityPressurizedPolymerizingChamber$ComputerHandler::outputTank$getOutputNeeded).returnType(Long.TYPE).methodDescription("Get the amount needed to fill the output tank."));
        this.register(MethodData.builder("getOutputFilledPercentage", TileEntityPressurizedPolymerizingChamber$ComputerHandler::outputTank$getOutputFilledPercentage).returnType(Double.TYPE).methodDescription("Get the filled percentage of the output tank."));
        this.register(MethodData.builder("getInputItem", TileEntityPressurizedPolymerizingChamber$ComputerHandler::inputSlot$getInputItem).returnType(ItemStack.class).methodDescription("Get the contents of the input slot."));
        this.register(MethodData.builder("getOutputItem", TileEntityPressurizedPolymerizingChamber$ComputerHandler::outputSlot$getOutputItem).returnType(ItemStack.class).methodDescription("Get the contents of the output slot."));
        this.register(MethodData.builder("getEnergyItem", TileEntityPressurizedPolymerizingChamber$ComputerHandler::energySlot$getEnergyItem).returnType(ItemStack.class).methodDescription("Get the contents of the energy slot."));
        this.register(MethodData.builder("getEnergyUsage", TileEntityPressurizedPolymerizingChamber$ComputerHandler::getEnergyUsage_0).returnType(FloatingLong.class).methodDescription("Get the energy used in the last tick by the machine"));
    }

    public static Object inputTank$getInput(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getStack(subject.inputTank));
    }

    public static Object inputTank$getInputCapacity(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getCapacity(subject.inputTank));
    }

    public static Object inputTank$getInputNeeded(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getNeeded(subject.inputTank));
    }

    public static Object inputTank$getInputFilledPercentage(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getFilledPercentage(subject.inputTank));
    }

    public static Object outputTank$getOutput(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getStack(subject.outputTank));
    }

    public static Object outputTank$getOutputCapacity(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getCapacity(subject.outputTank));
    }

    public static Object outputTank$getOutputNeeded(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getNeeded(subject.outputTank));
    }

    public static Object outputTank$getOutputFilledPercentage(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.getFilledPercentage(subject.outputTank));
    }

    public static Object inputSlot$getInputItem(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.inputSlot));
    }

    public static Object outputSlot$getOutputItem(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.outputSlot));
    }

    public static Object energySlot$getEnergyItem(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.getStack(subject.energySlot));
    }

    public static Object getEnergyUsage_0(TileEntityPressurizedPolymerizingChamber subject, BaseComputerHelper helper) throws ComputerException {
        return helper.convert(subject.getEnergyUsed());
    }
}
