package com.example.geogeo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.min;

public class DatabaseHandler {
    private static final String DATABASE_NAME = "db.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseHandler instance;

    public DatabaseHandler(Context context) {
        this.openHelper = new SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        open();
    }

    public static SQLiteAssetHelper createSQLiteAssetHelper(Context context) {
        return new SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler(context);
        }
        return instance;
    }

    public void open() {

        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

//    public int getRandomPicQuestion() {
//        open();
//        Random rand = new Random();
//        Cursor cur = db.rawQuery("SELECT * FROM PicQuestion", null);
//        int result = rand.nextInt(cur.getCount()) + 1;
//        cur.close();
//        db.close();
//        return result;
//    }
    /**
     * Returns the id for a random picture question
     * @param  blacklist  a list of PictureIds that have already been chosen
     * @param  continent parameter to limit questions to a specific continent
     * @return      id of selected picture question
     */
    public int getRandomPicQuestion(ArrayList<Integer[]> blacklist, String continent) {

        String table = "PicQuestion INNER JOIN Category ON PicQuestion.CategoryId = Category.Id";

        // continent check, joining with category
        //if (!continent.equals("all")){
        //    table = "PicQuestion INNER JOIN Category ON PicQuestion.CategoryId = Category.Id";
        //}



        String sql = "SELECT PicQuestion.Id FROM " + table + " WHERE";

        // only added if continent is chosen
        if(!continent.equals("all")) {
            sql += " Continent = '"  + continent + "' AND ";
        }
        for (Integer[] v : blacklist) {
            if (v[0] == 1) {
                sql += " PicQuestion.Id <> " + v[1] + " AND ";
            }
        }
        sql = sql.substring(0, sql.length() - 5);
        Random rand = new Random();
        Cursor cur = db.rawQuery(sql, null);
        int[] qIds = new int[cur.getCount()];
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToNext();
            qIds[i] = cur.getInt(0);
        }
        cur.close();

        int result = rand.nextInt(qIds.length);
        System.out.println("Pic Question Id is: " + qIds[result]);
        return qIds[result];
    }

//    public int getRandomTextQuestion(String type) {
//        open();
//        Random rand = new Random();
//        Cursor cur;
//        if (type.equals("all")) {
//            cur = db.rawQuery("SELECT * FROM TextQuestion", null);
//        } else {
//            cur = db.rawQuery("SELECT * FROM TextQuestion WHERE Type = " + type, null);
//        }
//        int result = rand.nextInt(cur.getCount()) + 1;
//        cur.close();
//        db.close();
//        return result;
//    }
    /**
     * Returns the id for a random text question
     * @param  blacklist  a list of PictureIds that have already been chosen
     * @param type parameter to limit questions to a specific text type (choose event or riddle)
     * @param  continent parameter to limit questions to a specific continent
     * @return      id of selected picture question
     */
    public int getRandomTextQuestion(ArrayList<Integer[]> blacklist, String type, String continent ) {

        String table = "TextQuestion INNER JOIN Category ON TextQuestion.CategoryId = Category.Id";

        // continent check, joining with category
        //if (!continent.equals("all")){
        //    table = "TextQuestion INNER JOIN Category ON TextQuestion.CategoryId = Category.Id";
        //}
        String sql = "SELECT TextQuestion.Id FROM " + table + " WHERE";
        if (!type.equals("all")) {
            sql += " Type = '" + type + "' AND ";
        }
        // only added if continent is chosen
        if(!continent.equals("all")) {
           sql += " Continent = '"  + continent + "' AND ";
        }
        for (Integer[] v : blacklist) {
            if (v[0] == 0) {
                sql += " TextQuestion.Id <> '" + v[1] + "' AND ";
            }
        }
        sql = sql.substring(0, sql.length() - 5);
        Random rand = new Random();
        Cursor cur = db.rawQuery(sql, null);
        int[] qIds = new int[cur.getCount()];
        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToNext();
            qIds[i] = cur.getInt(0);
        }
        cur.close();

        int result = rand.nextInt(qIds.length);
        System.out.println("Text Question Id is: " + qIds[result]);
        return qIds[result];
    }

    // returns picture path of a question given the questionId
    public byte[] getbytes(int questionId) {

        Cursor c = db.rawQuery("SELECT Pic FROM PicQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        byte[] result = c.getBlob(0);
        c.close();

        return result;
    }

    // function to check for the minimum amount of type questions
    public int getQuestionCount(){

        Cursor c = db.rawQuery("SELECT * FROM PicQuestion", null);
        int count1 = c.getCount();
        c.close();
        c = db.rawQuery("SELECT * FROM TextQuestion", null);
        int count2 = c.getCount();
        c.close();


        return min(count1,count2);
    }
    /**
     * Returns the text form of the given questionId
     * @param  questionId
     * @return the String of the Textquestion with the provided Id
     */
    public String getTextQuestion(int questionId) {

        Cursor c = db.rawQuery("SELECT Text FROM TextQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String result = c.getString(0);
        c.close();

        return result;
    }
    /**
     * Returns the type of a text question
     * @param  questionId
     * @return type of the text question (event or riddle)
     */
    public String getTextType(int questionId) {

        Cursor c = db.rawQuery("SELECT Type FROM TextQuestion WHERE Id =" + questionId, null);
        c.moveToFirst();
        String Type = c.getString(0);
        c.close();

        return Type;
    }

    // delete??
    public String getAnswerAnswer(int answerId) {

        Cursor c = db.rawQuery("SELECT Answer FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        String Answer = c.getString(0);
        c.close();

        return Answer;
    }
    /**
     * Returns the coordinates of a certain answer
     * @param  answerId
     * @return List of 2 doubles in coordinate form
     */
    public Double[] getAnswerCords(int answerId) {

        Double[] Cords = new Double[2];
        Cursor c = db.rawQuery("SELECT X FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        Double x = c.getDouble(0);
        Cords[0] = x;
        c.close();

        c = db.rawQuery("SELECT Y FROM Answer WHERE Id =" + answerId, null);
        c.moveToFirst();
        Double y = c.getDouble(0);
        Cords[1] = y;
        c.close();

        return Cords;
    }

    /**
     * Returns the AnswerId of a given question
     * @param  isPicQuestion int (interpreted as boolean) to determine if its a Picture or a Textquestion
     * @param questionId
     * @return AnswerId
     */
    public int getAnswerId(int isPicQuestion, int questionId) {

        int AnswerId;
        // check PicQuestions
        if (isPicQuestion != 0) {
            Cursor c = db.rawQuery("SELECT AnswerId FROM PicQuestion WHERE ID =" + questionId, null);
            c.moveToFirst();
            AnswerId = c.getInt(0);
            c.close();
        } else { // check TextQuestion
            Cursor c = db.rawQuery("SELECT AnswerId FROM TextQuestion WHERE ID =" + questionId, null);
            c.moveToFirst();
            AnswerId = c.getInt(0);
            c.close();
        }

        return AnswerId;
    }
    /**
     * Returns Information of the next question in a given game ( based on unanswered rounds)
     * @param  gameId
     * @return tuple of Integers, first is 0 if it is a Textquestion, 0 if PictureQuestion,
     *                            second is the Id of the question
     */
    public int[] getNextQuestion(int gameId) {

        Cursor cur = db.rawQuery("SELECT IsPicQuestion, QuestionId FROM Round WHERE Points IS NULL AND GameId =" + gameId + " ;", null);
        cur.moveToFirst();
        if (cur.getCount() == 0) {

            return new int[]{-1, -1};
        }
        int isPic = cur.getInt(0);
        int qId = cur.getInt(1);
        cur.close();

        return new int[]{isPic, qId};
    }
    /**
     * creates a game anda determined number of rounds within the database
     * @param  amount number of rounds for the game
     * @return returns the id of the new game
     */
    public int createGame(int amount) {

        db.execSQL("INSERT INTO Game (Amount, Points) VALUES (" + Integer.valueOf(amount) + ", 0)");
        Cursor c = db.rawQuery("SELECT MAX(Id) FROM Game", null);
        c.moveToFirst();
        String id = c.getString(0);
        c.close();

        return Integer.valueOf(id);
    }

    public boolean checkAmount(int amount, String kind) {

        boolean result;
        if (kind.equals("pic")) {
            result = amount <= db.rawQuery("SELECT * FROM PicQuestion", null).getCount();
        } else {
            result = amount <= db.rawQuery("SELECT * FROM TextQuestion", null).getCount();
        }

        return result;
    }
    /**
     * adds an unanswered round to a game ( only called during create game)
     * @param  gameId
     * @param isPicQuestion boolean to determine if its a Picture or a Textquestion
     * @param questionId
     */
    public void addRoundToGame(int gameId, int isPicQuestion, int questionId) {

        db.execSQL("INSERT INTO Round (GameId, QuestionId, isPicQuestion, Points, AnswerX, AnswerY) " +
                "VALUES (" + Integer.toString(gameId) + ", " + Integer.toString(questionId) + ", " +
                Integer.toString(isPicQuestion) + ", null, null, null)");

    }

    /**
     * updates the local database with the information of the finished game
     * @param  userId
     * @param gameId
     */
    public void addGameToStatistics(int userId, int gameId) {

        Cursor c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                " WHERE Id = " + userId, null);
        System.out.println(c.getCount());
        if (c.getCount() == 0) {
            db.execSQL("INSERT INTO Statistics (Games, AverageScore, TotalPoints) VALUES " +
                    "(0, 0, 0)");
            c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                    " WHERE Id = " + userId, null);
        }
        c.moveToFirst();

        Cursor d = db.rawQuery("SELECT Points FROM Game WHERE Id =" + gameId, null);
        d.moveToFirst();
        int gamePoints = d.getInt(0);

        int newGames = c.getInt(0) + 1;
        int newTotal = c.getInt(2) + gamePoints;
        float newAverage = newTotal / newGames;

        db.execSQL("UPDATE Statistics SET Games =" + newGames +
                ", AverageScore =" + newAverage + ", TotalPoints =" + newTotal +
                " WHERE Id =" + userId);


    }

    /**
     * updates a round in the database with the player answer and the achieved points
     * @param  gameId
     * @param questionId
     * @param isPicQuestion boolean to determine if its a Picture or a Textquestion
     * @param x coordinate of answer
     * @param y coordinate of answer
     * @param points amount of achieved points
     */
    public void updateRound(int gameId, int questionId, int isPicQuestion, double x, double y, int points) {

        db.execSQL("UPDATE Round " +
                "SET AnswerX = " + x + ", AnswerY = " + y + ", Points = " + points +
                " WHERE GameId = " + gameId + " AND questionId = " + questionId + " And " +
                "IsPicQuestion = " + isPicQuestion);

    }

    public String[] getStats(int userId) {

        Cursor c = db.rawQuery("SELECT Games, AverageScore, TotalPoints FROM Statistics WHERE Id = " + userId, null);
        if (c.getCount() == 0) {
            db.execSQL("INSERT INTO Statistics (Games, AverageScore, TotalPoints) VALUES " +
                    "(0, 0, 0)");
            c = db.rawQuery("SELECT Games, AverageScore, TotalPoints  FROM Statistics" +
                    " WHERE Id = " + Integer.toString(userId), null);
        }
        c.moveToFirst();
        String[] stats = {Integer.toString(c.getInt(0)), Integer.toString(c.getInt(1)), Integer.toString(c.getInt(2))};

        return stats;
    }
    /**
     * checks if any rounds are left unanswered
     * @param  gameId
     * @return boolean whether or not the game is finished
     */
    public boolean isGameOver(int gameId) {

        Cursor c = db.rawQuery("SELECT AnswerX FROM Round " +
                "WHERE GameId = " + gameId + " AND AnswerX IS NUll", null);
        if (c.getCount() == 0) {

            return true;
        }

        return false;
    }

    // check this one?
    public int getPoints(int gameId){

        Cursor c = db.rawQuery("SELECT SUM(Points) FROM ROUND WHERE GameId = " + gameId, null);
        c.moveToFirst();
        if (! (c.getCount() == 0)){

            return c.getInt(0);
        }

        return 0;
    }
    /**
     * Updates the Game table with the sum of the achieved points in all rounds of the game
     * @param  gameId
     */
    public void countPointsOfRounds(int gameId) {

        Cursor c = db.rawQuery("SELECT SUM(Points) FROM Round WHERE GameId = " + gameId, null);
        c.moveToFirst();
        String result = c.getString(0);
        db.execSQL("UPDATE Game SET Points = " + result + " WHERE Id = " + gameId);

    }
    /**
     * Iterates through the rounds of a given game to determine the points per round
     * @param  gameId
     * @return a list of Strings containing the points per round
     */
    public String[] getPointsPerRound(int gameId) {

        Cursor c = db.rawQuery("SELECT Points FROM Round WHERE GameId = " + gameId, null);
        c.moveToFirst();
        String[] result = new String[c.getCount()];
        for (int i = 0; i < c.getCount(); i++) {
            result[i] = c.getString(0);
            c.moveToNext();
        }

        return result;
    }

    public boolean checkAmount(int amount, String kind, String continent) {
        if (kind.equals("text")){
            Cursor c = db.rawQuery("SELECT TextQuestion.Id FROM TextQuestion INNER JOIN Category " +
                    "ON TextQuestion.CategoryId = Category.Id WHERE Continent = '" + continent + "'", null);
            if (c.getCount() < amount){
                return false;
            }
        }
        if (kind.equals("pic")){
            Cursor c = db.rawQuery("SELECT PicQuestion.Id FROM PicQuestion INNER JOIN Category " +
                    "ON PicQuestion.CategoryId = Category.Id WHERE Continent = '" + continent + "'", null);
            if (c.getCount() < amount){
                return false;
            }
        }
        return true;
    }
}
