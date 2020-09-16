package red.felnull.otyacraftengine.data;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPathUtil;
import red.felnull.otyacraftengine.util.IKSGStringUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SendReceiveLogger {
    private String time;
    private String name;
    private String log;
    private Dist side;
    private SndOrRec sr;

    public SendReceiveLogger(String name, String details, Dist side, SndOrRec sr) {
        this.time = IKSGStringUtil.getTimeStamp();
        this.name = name;
        this.side = side;
        this.log = details + " - " + sr.getName().getString() + " Log \n";
        this.sr = sr;

    }

    public void addExceptionLogLine(Exception ex) {
        addLogLine(new TranslationTextComponent("rslog.exception", ex.toString()));
    }

    public void addProgress(int sent, int remaing, long progress, long last, SndOrRec sr) {
        if (sr == SndOrRec.SEND) {
            addLogLine(new TranslationTextComponent("rslog.sendProgress", sent + "byte", remaing + "byte", progress + "ms", last + "ms"));
        } else if (sr == SndOrRec.RECEIVE) {
            addLogLine(new TranslationTextComponent("rslog.receiveProgress", sent + "byte", remaing + "byte", progress + "ms", last + "ms"));
        }


    }

    public void addStartFailureLogLine(ITextComponent st) {
        addLogLine(new TranslationTextComponent("rslog.start.failure", sr.getName(), st));
    }

    public void addFinishLogLine(Result result, long time, int allbyte) {
        String ep = time + "ms";
        if (result == Result.SUCCESS) {
            String speed = allbyte / time + "byte/ms";
            addLogLine(new TranslationTextComponent("rslog.finish.success", sr.getName(), ep, speed));
        } else if (result == Result.FAILURE) {
            addLogLine(new TranslationTextComponent("rslog.finish.failure", sr.getName(), ep));
        }
    }

    public void addStartLogLine() {
        addLogLine(new TranslationTextComponent("rslog.start.success", sr.getName()));
    }

    public void addLogLine(ITextComponent log) {
        this.log += IKSGStringUtil.getTimeStamp() + "-" + log.getString() + "\n";
    }

    public void createLog() {
        Path savedpath = null;
        if (side.isClient()) {
            savedpath = Paths.get("srlogs");
        } else if (side.isDedicatedServer()) {
            savedpath = IKSGPathUtil.getWorldSaveDataPath().resolve(Paths.get("srlogs"));
        }
        IKSGFileLoadUtil.txtWriter(log, savedpath.resolve(time.replace(":", "-").replace(" ", "-") + "-" + name.replace(":", "-") + "-" + sr.getInitials() + ".log"));
    }

    public enum SndOrRec {
        SEND(new TranslationTextComponent("rslog.sending"), "s"),
        RECEIVE(new TranslationTextComponent("rslog.receiving"), "r");
        private ITextComponent name;
        private String initials;

        private SndOrRec(ITextComponent name, String initials) {
            this.name = name;
            this.initials = initials;
        }

        public ITextComponent getName() {
            return name;
        }

        public String getInitials() {
            return initials;
        }
    }

    public static enum Result {
        SUCCESS(new TranslationTextComponent("rslog.success")),
        FAILURE(new TranslationTextComponent("rslog.failure"));
        private ITextComponent name;

        private Result(ITextComponent name) {
            this.name = name;
        }

        public ITextComponent getName() {
            return name;
        }
    }
}
