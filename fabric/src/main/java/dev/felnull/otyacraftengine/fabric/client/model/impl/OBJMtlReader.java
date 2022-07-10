package dev.felnull.otyacraftengine.fabric.client.model.impl;

import de.javagl.obj.Mtls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class OBJMtlReader {
    public static List<OBJMtl> read(Reader reader) throws IOException {
        if (reader instanceof BufferedReader)
            return readImpl((BufferedReader) reader);
        return readImpl(new BufferedReader(reader));
    }

    private static List<OBJMtl> readImpl(BufferedReader reader)
            throws IOException {
        List<OBJMtl> mtlList = new ArrayList<>();

        OBJMtl currentMtl = null;

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
                currentMtl = new OBJMtl(Mtls.create(name));
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
                currentMtl.setUseDiffuseColor(true);
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
