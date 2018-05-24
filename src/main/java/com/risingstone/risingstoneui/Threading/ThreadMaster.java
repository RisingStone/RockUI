package com.risingstone.risingstoneui.Threading;

class ThreadMaster {

    private static ThreadMaster instance = null;
    private static UIThread uiThread = null;
    private static PhysicsThread physicsThread = null;

    private boolean initialized = false;

    private ThreadMaster() {
        //Spin up UIThread and PhysicsThread.
        uiThread = UIThread.getInstance();
        physicsThread = PhysicsThread.getInstance();

        //Spin up as many worker threads as there are processors available.
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            addWorker();
        }

        //Start render loop.
        uiThread.addTask(() -> {
            //TODO add Render Loop and return whatever gets returned when loop ends.
            return null;
        });

        //Start physics loop.
        physicsThread.addTask(() -> {
           //TODO add Physics Loop and return whatever gets returned when loop ends.
           return null;
        });

        initialized = true;
    }

    //Gets total background workers.
    public static int getWorkerCount() {
        checkInitialized();
        return WorkerThread.getWorkerCount();
    }

    //Adds a task for workers to do. Work is split among workers.
    public static boolean addWork(Task task) {
        checkInitialized();
        return WorkerThread.addWork(task);
    }

    //Spins up a new worker thread.
    public static void addWorker() {
        WorkerThread.addWorker();
    }

    //Schedules the worker with the most current work to stop when it's finished with current work.
    public static void removeWorker() {
        checkInitialized();
        WorkerThread.removeWorker();
    }

    //Checks if ThreadMaster is initialized and throws a runtime exception if not.
    private static void checkInitialized() {
        if (!getInstance().initialized) {
            throw new RuntimeException(ThreadMaster.class.getSimpleName() + " not initialized!");
        }
    }

    private static ThreadMaster getInstance() {
        return instance;
    }

    //Initialize ThreadMaster and by design UIThread, PhysicsThread, and a few WorkerThreads.
    public static void initialize() {
        if (instance == null) {
            instance = new ThreadMaster();
        }
    }
}