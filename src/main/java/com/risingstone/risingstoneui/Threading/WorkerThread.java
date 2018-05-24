package com.risingstone.risingstoneui.Threading;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class WorkerThread extends ThreadBase {

    private static List<WorkerThread> workerList = new ArrayList<>();

    private Queue<Task> taskQueue = new LinkedList<>();

    boolean running = false;

    private WorkerThread() {
        //Setting priority to middle.
        setPriority(Thread.MAX_PRIORITY / 2);
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

    //Adds a worker task to the worker with the least amount of tasks queued.
    static boolean addWork(Task task) {
        WorkerThread workerToWork = workerList.size() > 0 ? workerList.get(0) : null;
        for (WorkerThread worker : workerList) {
            if (worker.taskCount() == 0) {
                return worker.addTask(task);
            } else if (workerToWork.taskCount() < worker.taskCount()) {
                workerToWork = worker;
            }
        }
        return workerToWork != null && workerToWork.addTask(task);
    }

    //Adds a worker to the worker list.
    static void addWorker() {
        WorkerThread worker = new WorkerThread();
        worker.start();
        workerList.add(worker);
    }

    //Gets worker with the highest task count that isn't already set to stop running and sets it to stop running.
    static void removeWorker() {
        WorkerThread workerToRemove = workerList.size() > 0 ? workerList.get(0) : null;
        for (WorkerThread worker : workerList) {
            if (worker.running && worker.taskCount() > workerToRemove.taskCount()) {
                workerToRemove = worker;
            }
        }

        if (workerToRemove != null) {
            workerToRemove.running = false;
        }
    }

    //Returns current amount of running workers.
    static int getWorkerCount() {
        return workerList.size();
    }
}
