package com.risingstone.risingstoneui.Threading;

import java.util.LinkedList;
import java.util.Queue;

class UIThread extends ThreadBase {

    private static UIThread instance = null;

    private Queue<Task> taskQueue = new LinkedList<>();

    boolean running = false;

    private UIThread() {
        //Setting priority 1 below max, which should be above every other thread except PhysicsThread.
        instance.setPriority(Thread.MAX_PRIORITY - 1);
    }

    //Add task to queue and return whether is was successful or not.
    @Override
    boolean addTask(Task task) {
        return taskQueue.add(task);
    }

    //Returns task queue queue count.
    @Override
    int taskCount() {
        return taskQueue.size();
    }

    //Pops a task off the task queue and executes it.
    @Override
    void executeTask() {
        taskQueue.remove().work();
    }

    //Basic execution loop.
    @Override
    public void run() {
        running = true;
        while(running) {
            executeTask();
        }
    }

    //Creates a singleton for UIThread is doesn't exist, starts it, and returns.
    static UIThread getInstance() {
        if (instance == null) {
            instance = new UIThread();
            instance.start();
        }
        return instance;
    }
}