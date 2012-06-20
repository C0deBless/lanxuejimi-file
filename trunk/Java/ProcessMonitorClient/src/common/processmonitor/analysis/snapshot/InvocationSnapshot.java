package common.processmonitor.analysis.snapshot;

import java.util.ArrayList;
import java.util.List;

public class InvocationSnapshot {
	private long id;
	private StackTraceElement[] stacks;
	private long begin;
	private long end;
	private long elapse;
	private long parent;
	private final List<InvocationSnapshot> childs = new ArrayList<>();

	public long getParent() {
		return parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public long getElapse() {
		return elapse;
	}

	public StackTraceElement[] getStacks() {
		return stacks;
	}

	public void setStacks(StackTraceElement[] stacks) {
		this.stacks = stacks;
	}

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
		this.elapse = end - begin;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id:" + id + " ");
		sb.append("parent:" + parent + " ");
		sb.append("begin:" + begin + " ");
		sb.append("end:" + end + " ");
		sb.append("elapse:" + elapse + " ");
		sb.append("stacks:");
		for (int i = 0; i < stacks.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			StackTraceElement stack = stacks[i];
			sb.append(stack.getClassName() + "." + stack.getMethodName() + "-"
					+ stack.getLineNumber());
		}
		return sb.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<InvocationSnapshot> getChilds() {
		return childs;
	}
}
