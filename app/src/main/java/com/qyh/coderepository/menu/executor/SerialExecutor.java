package com.qyh.coderepository.menu.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 邱永恒
 * @time 2017/12/12  21:19
 * @desc ${TODD}
 */
class SerialExecutor implements Executor {
    private final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
    private final ExecutorService executor;
    private Runnable active;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();

    SerialExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public synchronized void execute(final Runnable r) {
        // 添加任务到队列
        tasks.offer(new Runnable() {
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });
        if (active == null) {
            scheduleNext();
        }
    }

    private synchronized void scheduleNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }
}
