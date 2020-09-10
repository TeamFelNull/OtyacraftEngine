package red.felnull.otyacraftengine.util;

import java.util.HashMap;
import java.util.Map;

public class ClockTimer {
    private Map<String, Long> timers = new HashMap<>();
    private Map<String, ITask> tasks = new HashMap<>();
    private Map<String, Long> taskTimes = new HashMap<>();

    private IAllExist allExist;

    public ClockTimer() {
        TaskThread tt = new TaskThread(this);
    }

    public ClockTimer(IAllExist isExist) {
        this.allExist = isExist;
    }

    public void setTimer(String name) {
        timers.put(name, System.currentTimeMillis());
    }


    public long getTime(String name) {
        if (timers.containsKey(name)) {
            return timers.get(name);
        }
        return 0;
    }


    public long getElapsedTime(String name) {
        return System.currentTimeMillis() - getTime(name);
    }

    public void addTask(String name, ITask task) {
        this.tasks.put(name, task);
        this.taskTimes.put(name, 0l);
    }

    public void removeTask(String name) {
        this.tasks.remove(name);
        this.taskTimes.remove(name);
    }

    public interface IAllExist {
        boolean isStop(ClockTimer timer);
    }

    public interface ITask {
        boolean isStop(ClockTimer timer);

        void run(ClockTimer timer);

        long time(ClockTimer timer);
    }

    public class TaskThread extends Thread {
        private ClockTimer ct;

        public TaskThread(ClockTimer ct) {
            this.ct = ct;
        }

        @Override
        public void run() {
            while (ct.allExist.isStop(ct)) {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                }
                for (Map.Entry<String, Long> nums : ct.taskTimes.entrySet()) {
                    ct.taskTimes.put(nums.getKey(), nums.getValue() + 1);
                    if (ct.taskTimes.get(nums.getKey()) >= ct.tasks.get(nums.getKey()).time(ct)) {
                        ct.tasks.get(nums.getKey()).run(ct);
                        ct.taskTimes.put(nums.getKey(), 0l);
                    }
                }
            }
        }
    }
}


