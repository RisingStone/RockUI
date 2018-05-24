package com.risingstone.risingstoneui.Threading;

abstract class ThreadBase extends Thread {

    //Implement to add task to task queue and return whether task was successfully added.
    abstract boolean addTask(Task task);

    //Implement to return current task queue size.
    abstract int taskCount();

    //Implement to pop a task off the task queue and execute it (call work).
    abstract void executeTask();

    //Force subclasses to implement run.
    public abstract void run();
}