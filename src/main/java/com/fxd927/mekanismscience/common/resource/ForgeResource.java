package com.fxd927.mekanismscience.common.resource;

import lombok.Getter;
import mekanism.common.resource.IResource;

public enum ForgeResource implements IResource {

    ALUMINUM("aluminum", 0xFFE3E3E3),
    NICKEL("nickel", 0xFFA9A984),
    PLATINUM("platinum", 0xFFB5B5FF),
    SILVER("silver", 0xFFA4E0E7),
    ZINC("zinc", 0xFFB5B5B5),
    IRIDIUM("iridium", 0xFFC0C0C0);

    @Getter
    private final String registrySuffix;
    @Getter
    private final int tint;

    ForgeResource(String registrySuffix, int tint) {
        this.registrySuffix = registrySuffix;
        this.tint = tint;
    }
}
