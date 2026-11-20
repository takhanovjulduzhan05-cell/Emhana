package kz.kaznaru.capstone.service;

/** Command: each reversible use case is a standalone object. */
public interface UndoableCommand { void execute(); void undo(); }
