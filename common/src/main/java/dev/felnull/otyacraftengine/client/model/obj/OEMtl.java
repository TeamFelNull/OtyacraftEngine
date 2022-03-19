package dev.felnull.otyacraftengine.client.model.obj;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Mtl;
import de.javagl.obj.Mtls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class OEMtl implements Mtl {
    private final Mtl material;
    private int tintIndex = -1;
    private boolean useDiffuseColor = false;

    public OEMtl(Mtl material) {
        this.material = material;
    }

    @Override
    public String getName() {
        return material.getName();
    }

    @Override
    public FloatTuple getKa() {
        return material.getKa();
    }

    @Override
    public void setKa(float ka0, float ka1, float ka2) {
        material.setKa(ka0, ka1, ka2);
    }

    @Override
    public FloatTuple getKs() {
        return material.getKs();
    }

    @Override
    public void setKs(float ks0, float ks1, float ks2) {
        material.setKs(ks0, ks1, ks2);
    }

    @Override
    public FloatTuple getKd() {
        return material.getKd();
    }

    @Override
    public void setKd(float kd0, float kd1, float kd2) {
        material.setKd(kd0, kd1, kd2);
    }

    @Override
    public String getMapKd() {
        return material.getMapKd();
    }

    @Override
    public void setMapKd(String mapKd) {
        material.setMapKd(mapKd);
    }

    @Override
    public float getNs() {
        return material.getNs();
    }

    @Override
    public void setNs(float ns) {
        material.setNs(ns);
    }

    @Override
    public float getD() {
        return material.getD();
    }

    @Override
    public void setD(float d) {
        material.setD(d);
    }

    public int getTintIndex() {
        return this.tintIndex;
    }

    public void setTintIndex(int tintIndex) {
        this.tintIndex = tintIndex;
    }

    public boolean useDiffuseColor() {
        return this.useDiffuseColor;
    }

    public void setUseDiffuseColor() {
        this.useDiffuseColor = true;
    }

    public static List<OEMtl> read(InputStream inputStream)
            throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        return readImpl(reader);
    }

    private static List<OEMtl> readImpl(BufferedReader reader)
            throws IOException {
        List<OEMtl> mtlList = new ArrayList<>();

        OEMtl currentMtl = null;

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }

            line = line.trim();

            boolean finished = false;
            while (line.endsWith("\\")) {
                line = line.substring(0, line.length() - 2);
                String nextLine = reader.readLine();
                if (nextLine == null) {
                    finished = true;
                    break;
                }
                line += " " + nextLine;
            }
            if (finished) {
                break;
            }

            StringTokenizer st = new StringTokenizer(line);
            if (!st.hasMoreTokens()) {
                continue;
            }

            String identifier = st.nextToken();
            if (identifier.equalsIgnoreCase("newmtl")) {
                String name = line.substring("newmtl".length()).trim();
                currentMtl = new OEMtl(Mtls.create(name));
                mtlList.add(currentMtl);
            } else if (identifier.equalsIgnoreCase("Ka")) {
                float ka0 = parseFloat(st.nextToken());
                float ka1 = parseFloat(st.nextToken());
                float ka2 = parseFloat(st.nextToken());
                currentMtl.setKa(ka0, ka1, ka2);
            } else if (identifier.equalsIgnoreCase("Ks")) {
                float ks0 = parseFloat(st.nextToken());
                float ks1 = parseFloat(st.nextToken());
                float ks2 = parseFloat(st.nextToken());
                currentMtl.setKs(ks0, ks1, ks2);
            } else if (identifier.equalsIgnoreCase("Kd")) {
                float kd0 = parseFloat(st.nextToken());
                float kd1 = parseFloat(st.nextToken());
                float kd2 = parseFloat(st.nextToken());
                currentMtl.setKd(kd0, kd1, kd2);
            } else if (identifier.equalsIgnoreCase("map_Kd")) {
                String mapKd = line.substring("map_Kd".length()).trim();
                currentMtl.setMapKd(mapKd);
            } else if (identifier.equalsIgnoreCase("d")) {
                float d = parseFloat(st.nextToken());
                currentMtl.setD(d);
            } else if (identifier.equalsIgnoreCase("Ns")) {
                float ns = parseFloat(st.nextToken());
                currentMtl.setNs(ns);
            } else if (identifier.equalsIgnoreCase("tintindex")) {
                int tint = parseInt(st.nextToken());
                currentMtl.setTintIndex(tint);
            } else if (identifier.equalsIgnoreCase("use_diffuse")) {
                currentMtl.setUseDiffuseColor();
            }
        }

        return mtlList;
    }

    private static float parseFloat(String s) throws IOException {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            throw new IOException(e);
        }
    }

    private static int parseInt(String s) throws IOException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IOException(e);
        }
    }
}
