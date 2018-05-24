package com.risingstone.risingstoneui.Threading;

import java.util.LinkedList;
import java.util.Queue;

class PhysicsThread extends ThreadBase {

    private static PhysicsThread instance = null;

    private Queue<Task> taskQueue = new LinkedList<>();

    boolean running = false;

    private PhysicsThread() {
        //Setting priority to max.
        instance.setPriority(Thread.MAX_PRIORITY);
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

    //Creates a singleton for PhysicsThread is doesn't exist, starts it, and returns.
    static PhysicsThread getInstance() {
        if (instance == null) {
            instance = new PhysicsThread();
            instance.start();
        }
        return instance;
    }
}