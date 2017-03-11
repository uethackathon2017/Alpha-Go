package com.quang.tracnghiemtoan.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;

import com.quang.tracnghiemtoan.constants.Constant;
import com.quang.tracnghiemtoan.models.Problem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SQLiteDataController extends SQLiteOpenHelper {
    private static final String DB_PATH = "/data/data/com.quang.tracnghiemtoan/databases/";
    private static final String DB_NAME = "data.sqlite";
    private SQLiteDatabase database;
    private final Context context;

    public SQLiteDataController(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
        } else {
            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {
        File databasePath = context.getDatabasePath(DB_NAME);
        return databasePath.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = this.context.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int length = myInput.read(buffer);
            if (length > 0) {
                myOutput.write(buffer, 0, length);
            } else {
                myOutput.flush();
                myOutput.close();
                myInput.close();
                return;
            }
        }
    }

    public boolean deleteDatabase() {
        return new File(DB_PATH + DB_NAME).delete();
    }

    public void openDataBase() throws SQLException {
        this.database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close() {
        if (this.database != null) {
            this.database.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase db) {

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //kind là chuyên đề, level là độ khó
    public ArrayList<Problem> getAllProblem(String kind) {
        ArrayList<Problem> listProblem = new ArrayList<>();
        try {
            openDataBase();
            String[] columns = new String[]{"iD", "dera", "giai", "dokho", "dapan"};
            Cursor cursor = this.database.query(kind, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String question = cursor.getString(1);
                String answer = cursor.getString(2);
                String level = cursor.getString(3);
                String rightAnswer = cursor.getString(4);
                listProblem.add(new Problem(id, question, answer, level, rightAnswer));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listProblem;
    }

    public ArrayList<ArrayList<Problem>> getAllExam() {
        ArrayList<Problem> listProblem = new ArrayList<>();
        ArrayList<ArrayList<Problem>> listExam = new ArrayList<>();
        try {
            openDataBase();
            String[] columns = new String[]{"iD", "dera", "giai", "dokho", "dapan"};
            Cursor cursor = this.database.query(Constant.KIND_LUYENDE, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String question = cursor.getString(1);
                String answer = cursor.getString(2);
                String level = cursor.getString(3);
                String rightAnswer = cursor.getString(4);
                if (!answer.equals(".") && !answer.equals("..") && listProblem.size() != 0) {
                    listExam.add(listProblem);
                    listProblem = new ArrayList<>();
                }
                listProblem.add(new Problem(id, question, answer, level, rightAnswer));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listExam;
    }

    public ArrayList<ArrayList<Problem>> getAllProblem() {
        ArrayList<Problem> listProblem;
        ArrayList<ArrayList<Problem>> listExam = new ArrayList<>();
        try {
            openDataBase();
            String[] columns = new String[]{"iD", "dera", "giai", "dokho", "dapan"};
            String[] kind = new String[]{Constant.KIND_HAMSO, Constant.KIND_HINHHOCKG, Constant.KIND_MATTRONXOAY, Constant.KIND_MULOGARIT, Constant.KIND_SOPHUC, Constant.KIND_TICHPHAN};
            for (int i = 0; i < kind.length; i++) {
                listProblem = new ArrayList<>();
                Cursor cursor = this.database.query(kind[i], columns, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String question = cursor.getString(1);
                    String answer = cursor.getString(2);
                    String level = cursor.getString(3);
                    String rightAnswer = cursor.getString(4);
                    listProblem.add(new Problem(id, question, answer, level, rightAnswer));
                }
                listExam.add(listProblem);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return listExam;
    }

    public boolean insertProblem(String kind, Problem problem) {
        try {
            openDataBase();
            ContentValues content = new ContentValues();
            content.put("iD", problem.getId());
            content.put("dera", problem.getQuestion());
            content.put("dapan", problem.getAnswer());
            content.put("dokho", problem.getLevel());
            content.put("giai", problem.getRightAnswer());
            if (this.database.insert(kind, null, content) == -1) {
                close();
                return false;
            }
            close();
            return true;
        } catch (Exception e) {
            Log.w("insertInsert", "Error");
        } finally {
            close();
        }
        return false;
    }

    public boolean insertTable(String chuyende) {
        try {
            openDataBase();
            this.database.execSQL("CREATE TABLE " + chuyende + " (Id TEXT, dera TEXT, giai TEXT, dokho TEXT, dapan TEXT)");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
