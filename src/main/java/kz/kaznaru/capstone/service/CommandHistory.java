package kz.kaznaru.capstone.service;

import java.util.ArrayDeque;
import java.util.Deque;
public final class CommandHistory {
    private final Deque<UndoableCommand> history=new ArrayDeque<>();
    public void execute(UndoableCommand command){command.execute();history.push(command);}
    public void undo(){if(history.isEmpty())throw new IllegalStateException("History empty");UndoableCommand c=history.peek();c.undo();history.pop();}
}
