package red.felnull.otyacraftengine.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.util.StringRepresentable;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class IKSGDokataUtil {
    private static final Map<String, String> DOKATAS = new HashMap<>();
    private static final Gson gson = new Gson();
    private static boolean INITED;

    private static void init() {
        if (INITED)
            return;
        INITED = true;
        try {
            InputStream stream = IKSGDokataUtil.class.getResourceAsStream("/data/otyacraftengine/dokata.gz");
            JsonObject jo = gson.fromJson(new String(IKSGDataUtil.inputStreamToByteArray(IKSGDataUtil.unzipGz(stream))), JsonObject.class);
            jo.entrySet().forEach(n -> {
                DOKATAS.put(n.getKey(), IKSGStringUtil.decodeUTFEscapeSequence(n.getValue().getAsString()));
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getDokata(String string) {
        init();
        if (DOKATAS.containsKey(string))
            return DOKATAS.get(string);
        return "DokataError!!";
    }

    public static String getKusomamirede() {
        return getDokata("kusomamire");
    }

    public static String getYattaze() {
        return getDokata("yattaze");
    }

    public static String getYaritai() {
        return getDokata("yaritai");
    }

    public static String getSenzuri() {
        return getDokata("senzuri");
    }

    public static String getKusoKusoKuso() {
        return getDokata("kusokusokuso");
    }

    public static String getKusomamire() {
        return getDokata("kusomamirede");
    }

    public static String getKusozukiOyajiJisamaBoshu() {
        return getDokata("kusozukioyajijisamaboshu");
    }

    public static String getKusomamirede2() {
        return getDokata("kusomamirede2");
    }

    public static String getHayakuYariMakuriTaiyo() {
        return getDokata("hayakuyarimakuritaiyo");
    }

    public static String getHayakuKusoMamireni() {
        return getDokata("hayakukusomamireni");
    }

    public static String getKusomamire2() {
        return getDokata("kusomamire2");
    }

    public static String getKusomamire3() {
        return getDokata("kusomamire3");
    }

    public static String getKindanSyouJoga() {
        return getDokata("kindansyoujoga");
    }

    public static String getKusoOyaji() {
        return getDokata("kusooyaji");
    }

    public static String getYarouze() {
        return getDokata("yarouze");
    }

    public static String getYagaiDe() {
        return getDokata("yagaide");
    }

    public static String getKusomamire4() {
        return getDokata("kusomamire4");
    }

    public static String getYaritexe() {
        return getDokata("yaritexe");
    }

    public static String getKusomamirede3() {
        return getDokata("kusomamirede3");
    }

    public static String getKusomamire5() {
        return getDokata("kusomamire5");
    }

    public static String getKuso() {
        return getDokata("kuso");
    }

    public static String getKusoDaisuki() {
        return getDokata("kusodaisuki");
    }

    public static String getYamaNoNakaDe() {
        return getDokata("yamanonakade");
    }

    public static String getYamaNoNakaDe2() {
        return getDokata("yamanonakade2");
    }

    public static String getYamaNoNakaDe3() {
        return getDokata("yamanonakade3");
    }

    public static String getSibatte() {
        return getDokata("sibatte");
    }

    public static String getHonkide() {
        return getDokata("honkide");
    }

    public static String getHentaiDaisuki() {
        return getDokata("hentaidaisuki");
    }

    public static String getFuroShaNoOssan() {
        return getDokata("furoshanoossan");
    }

    public static String getYagaiDe2() {
        return getDokata("yagaide2");
    }

    public static String getMakkuro() {
        return getDokata("makkuro");
    }

    public static String getYarimakuri() {
        return getDokata("yarimakuri");
    }

    public static String getKushizashi() {
        return getDokata("kushizashi");
    }

    public static String getRoshutsuShitaZe() {
        return getDokata("roshutsushitaze");
    }

    public static String getYarouze2() {
        return getDokata("yarouze2");
    }

    public static String getIssyoni() {
        return getDokata("issyoni");
    }

    public static String getOkayamade() {
        return getDokata("okayamade");
    }

    public static String getKurashikiMukaiyamaRoshutsuZuki() {
        return getDokata("kurashikimukaiyamaroshutsuzuki");
    }

    public static enum Dokata implements StringRepresentable {
        KUSOMAMIRE(getKusomamire()),
        YATTAZE(getYattaze()),
        YARITAI(getYaritai()),
        SENZURI(getSenzuri()),
        KUSOKUSOKUSO(getKusoKusoKuso()),
        KUSOMAMIREDE(getKusomamirede()),
        KUSOZUKIOYAJIJISAMABOSHU(getKusozukiOyajiJisamaBoshu()),
        KUSOMAMIREDE2(getKusomamirede2()),
        HAYAKUYARIMAKURITAIYO(getHayakuYariMakuriTaiyo()),
        HAYAKUKUSOMAMIRENI(getHayakuKusoMamireni()),
        KUSOMAMIRE2(getKusomamire2()),
        KUSOMAMIRE3(getKusomamire3()),
        KINDANSYOUJOGA(getKindanSyouJoga()),
        KUSOOYAJI(getKusoOyaji()),
        YAROUZE(getYarouze()),
        YAGAIDE(getYagaiDe()),
        KUSOMAMIRE4(getKusomamire4()),
        YARITEXE(getYaritexe()),
        KUSOMAMIREDE3(getKusomamirede3()),
        KUSOMAMIRE5(getKusomamire5()),
        KUSO(getKuso()),
        KUSODAISUKI(getKusoDaisuki()),
        YAMANONAKADE(getYamaNoNakaDe()),
        YAMANONAKADE2(getYamaNoNakaDe2()),
        YAMANONAKADE3(getYamaNoNakaDe3()),
        SIBATTE(getSibatte()),
        HONKIDE(getHonkide()),
        HENTAIDAISUKI(getHentaiDaisuki()),
        FUROSHANOOSSAN(getFuroShaNoOssan()),
        YAGAIDE2(getYagaiDe2()),
        MAKKURO(getMakkuro()),
        YARIMAKURI(getYarimakuri()),
        KUSHIZASHI(getKushizashi()),
        ROSHUTSUSHITAZE(getRoshutsuShitaZe()),
        YAROUZE2(getYarouze2()),
        ISSYONI(getIssyoni()),
        OKAYAMADE(getOkayamade()),
        KURASHIKIMUKAIYAMAROSHUTSUZUKI(getKurashikiMukaiyamaRoshutsuZuki());
        public String str;

        private Dokata(String st) {
            this.str = st;
        }

        @Override
        public String getSerializedName() {
            return str;
        }
    }
}
