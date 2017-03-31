package guepardoapps.guepardonotes.controller;

import java.util.ArrayList;

import android.content.Context;

import guepardoapps.guepardonotes.common.constants.Enables;
import guepardoapps.guepardonotes.database.Database;
import guepardoapps.guepardonotes.model.Note;

import guepardoapps.library.toolset.common.Logger;

public class DatabaseController {

	private static final String TAG = DatabaseController.class.getSimpleName();
	private static Logger _logger;

	private static final DatabaseController DATABASE_CONTROLLER_SINGLETON = new DatabaseController();

	private boolean _initialized;

	private Context _context;
	private static Database _database;

	private DatabaseController() {
		_logger = new Logger(TAG, Enables.DEBUGGING_ENABLED);
		_logger.Debug(TAG + " created...");
	}

	public static DatabaseController getInstance() {
		_logger.Debug("getInstance");
		return DATABASE_CONTROLLER_SINGLETON;
	}

	public boolean Initialize(Context context) {
		if (_logger == null) {
			_logger = new Logger(TAG, Enables.DEBUGGING_ENABLED);
			_logger.Debug(TAG + " created...");
		}

		_logger.Debug("Initialize");

		if (_initialized) {
			_logger.Error("Already initialized!");
			return false;
		}

		_context = context;
		_database = new Database(_context);
		_database.Open();

		_initialized = true;

		return true;
	}

	public ArrayList<Note> GetNotes() {
		_logger.Debug("GetNotes");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return new ArrayList<Note>();
		}

		ArrayList<Note> notes = _database.GetNotes();
		return notes;
	}

	public void SaveNote(Note newNote) {
		_logger.Debug("SaveNote");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		_database.CreateEntry(newNote);
	}

	public void UpdateNote(Note updateNote) {
		_logger.Debug("UpdateNote");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		_database.Update(updateNote);
	}

	public void DeleteNote(Note deleteNote) {
		_logger.Debug("DeleteNote");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		_database.Delete(deleteNote);
	}

	public void ClearNotes() {
		_logger.Debug("ClearNotes");

		if (!_initialized) {
			_logger.Error("Not initilialized...");
			return;
		}

		ArrayList<Note> notes = _database.GetNotes();
		for (Note entry : notes) {
			_database.Delete(entry);
		}
	}

	public void Dispose() {
		_logger.Debug("Dispose");
		_database.Close();
		_database = null;
		_logger = null;
		_context = null;
		_initialized = false;
	}
}
