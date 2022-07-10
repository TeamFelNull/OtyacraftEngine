package dev.felnull.otyacraftengine.fabric.client.model.impl;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Mtl;

public class OBJMtl implements Mtl {
    private final Mtl baseMtl;
    private int tintIndex = -1;
    private boolean useDiffuseColor = false;

    public OBJMtl(Mtl baseMtl) {
        this.baseMtl = baseMtl;
    }

    @Override
    public String getName() {
        return baseMtl.getName();
    }

    @Override
    public FloatTuple getKa() {
        return baseMtl.getKa();
    }

    @Override
    public void setKa(float ka0, float ka1, float ka2) {
        baseMtl.setKa(ka0, ka1, ka2);
    }

    @Override
    public FloatTuple getKs() {
        return baseMtl.getKs();
    }

    @Override
    public void setKs(float ks0, float ks1, float ks2) {
        baseMtl.setKs(ks0, ks1, ks2);
    }

    @Override
    public FloatTuple getKd() {
        return baseMtl.getKd();
    }

    @Override
    public void setKd(float kd0, float kd1, float kd2) {
        baseMtl.setKd(kd0, kd1, kd2);
    }

    @Override
    public String getMapKd() {
        return baseMtl.getMapKd();
    }

    @Override
    public void setMapKd(String mapKd) {
        baseMtl.setMapKd(mapKd);
    }

    @Override
    public float getNs() {
        return baseMtl.getNs();
    }

    @Override
    public void setNs(float ns) {
        baseMtl.setNs(ns);
    }

    @Override
    public float getD() {
        return baseMtl.getD();
    }

    @Override
    public void setD(float d) {
        baseMtl.setD(d);
    }

    public int getTintIndex() {
        return tintIndex;
    }

    public boolean isUseDiffuseColor() {
        return useDiffuseColor;
    }

    public void setTintIndex(int tintIndex) {
        this.tintIndex = tintIndex;
    }

    public void setUseDiffuseColor(boolean useDiffuseColor) {
        this.useDiffuseColor = useDiffuseColor;
    }
}
