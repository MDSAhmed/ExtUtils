package com.ahmed.utils.logs;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadDumpUtil {
	
	public static String generateThreadDump() {

		final StringBuilder dump = new StringBuilder();
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 1000);
	
		dump.append("\n <<<Thread Dump>>> \n");
		
		for (ThreadInfo threadInfo : threadInfos) {
			dump.append("\n");
			dump.append(threadInfo.getThreadName());
			dump.append("\"");
			
			final Thread.State state = threadInfo.getThreadState();
			dump.append("\n java.lang.thread.state:: ");
			dump.append(state);
			
			final StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				dump.append("\n at ");				
				dump.append(stackTraceElement);
			}
			dump.append("\n");	
		}
		return dump.toString();
	}

	public static void main(String[] args) {
		
		System.out.println(generateThreadDump());
		
	}
}
