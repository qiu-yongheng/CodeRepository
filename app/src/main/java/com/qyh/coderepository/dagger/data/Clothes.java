package com.qyh.coderepository.dagger.data;

/**
 * @author 邱永恒
 * @time 2017/11/15  10:07
 * @desc ${TODD}
 */

public class Clothes {
    private Cloth cloth;

    public Clothes(Cloth cloth) {
        this.cloth = cloth;
    }

    public Cloth getCloth() {
        return cloth;
    }

    @Override
    public String toString() {
        return cloth.getColor() + "衣服";
    }
}
