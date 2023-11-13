package game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
	private List<WorkerThread> workersList;
	private BlockingQueue<Runnable> tasksBlockingQueue;
	private boolean isShutDown = false;

	public ThreadPool(int numberOfThreads) {
		workersList = new ArrayList<>();
		tasksBlockingQueue = new LinkedBlockingQueue<>();

		for (int i = 0; i < numberOfThreads; i++) {
			WorkerThread worker = new WorkerThread();
			workersList.add(worker);
			worker.start();
		}

	}

	public void submit(Runnable task) {
		if (!isShutDown) {
			tasksBlockingQueue.add(task);
		}
	}

	public void shutdownRow() {
		isShutDown = true;
		for (WorkerThread worker : workersList) {
			worker.interrupt();
		}
		tasksBlockingQueue.clear();
	}

	private class WorkerThread extends Thread {
		public void run() {
			while (!isShutDown || !tasksBlockingQueue.isEmpty()) {
				try {
					Runnable task = tasksBlockingQueue.take();
					task.run();
					
				} catch (InterruptedException e) {
					if (isShutDown) {
						return;
					}
				}
			}
		}
	}

}
