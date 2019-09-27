package com.example.geogeo;

import android.content.Context;
import java.util.ArrayList;
import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;


public class Controller {
    private Context context;
    private DatabaseHandler db;
    Random random = new Random();

    public Controller(Context context) {
        this.context = context;
        db = DatabaseHandler.getInstance(context);

    }

    public int getQuestionCount() {

        return db.getQuestionCount();

    }

    public void showPic(ImageView image, int questionId) {
        byte[] bits = db.getbytes(questionId);
        Bitmap b = BitmapFactory.decodeByteArray(bits, 0, bits.length);
        //image.setImageBitmap(Bitmap.createScaledBitmap(b, 150, 150, false));
        image.setImageBitmap(b);
    }

    public void showText(TextView text, int questionId) {

        String input = db.getTextQuestion(questionId);
        text.setText(input);


    }


    public boolean isGameOver(int gameId) {
        return db.isGameOver(gameId);
    }

    // kind can be random, text or pic
    // TODO: make sure that the amount is less or equal to the amount of questions
    public int createGame(int amount, String kind, String type, String continent) {
        // blacklist ist Array von Array von ints (Mit 1. 0 oder 1 2. id von question)
        if (kind.equals("random")) {
            if (!db.checkAmount(amount, "pic") || !db.checkAmount(amount, "text")) {

                return 0;
            }
        } else if (!db.checkAmount(amount, kind)) {
            return 0;
        }


        int gameId = db.createGame(amount);

        int id = 0;
        ArrayList<Integer[]> blacklist = new ArrayList<>();
        String randomChosen = "";

        for (int i = 0; i < amount; i++) {
            if (kind == "random") {
                if (random.nextBoolean()) {
                    randomChosen = "pic";
                } else {
                    randomChosen = "text";
                }
            }
            if (kind == "pic" || randomChosen == "pic") {
                id = db.getRandomPicQuestion(blacklist, continent); // continent selection
                blacklist.add(new Integer[]{1, id});
            }
            if (kind == "text" || randomChosen == "text") {
                id = db.getRandomTextQuestion(blacklist, type, continent); // continent selection
                blacklist.add(new Integer[]{0, id});
            }
            db.addRoundToGame(gameId, blacklist.get(i)[0], blacklist.get(i)[1]);
        }
        if (blacklist.size() == amount) {
            return gameId;
        } else {
            return 0;
        }
    }

    public void endGame(int gameId) {
        // Because for now we have only one user
        int id = 1;
        db.countPointsOfRounds(gameId);
        db.addGameToStatistics(id, gameId);

    }

    public String[] getStats(int userId) {

        String[] result = db.getStats(userId);

        return result;

    }

    public int[] getNextQuestionInfo(int gameId) {

        int[] QuestionInfo = db.getNextQuestion(gameId);
        int questionId = QuestionInfo[1];
        int isPicQuestion = QuestionInfo[0];

        int[] result = {questionId, isPicQuestion};
        return result;

    }

    // check stringConversion
    public int answerToRound(double x, double y, int gameId) {

        int[] QuestionInfo = db.getNextQuestion(gameId);
        int questionId = QuestionInfo[1];
        int answerId = db.getAnswerId(QuestionInfo[0], QuestionInfo[1]);
        int points = answerQuestion(gameId, answerId, x, y);
        //untested part
        db.updateRound(gameId, questionId, QuestionInfo[0], x, y, points);

        return points;
    }

    // returns distance to of given answer to correct location
    public double checkDistanceToAnswer(int answerId, double xGuess, double yGuess) {

        Double[] Cords = new Double[2];
        Cords = db.getAnswerCords(answerId);

        return coordDistance(Cords[0], Cords[1], xGuess, yGuess);
    }

    // calculates distance between to coordinates in km
    public double coordDistance(double lon1, double lat1, double lon2, double lat2) {
        double xDifference = degreeToRadians(lon1) - degreeToRadians(lon2);
        double yDifference = degreeToRadians(lat1) - degreeToRadians(lat2);
        double angle = (Math.pow(Math.sin(xDifference / 2), 2) + Math.cos(yDifference) * Math.cos(xDifference) * (Math.pow(Math.sin(yDifference / 2), 2)));
        double c = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1 - angle));
        double distance = 6378 * c;
        return distance;
    }

    // convert degree to radians
    public double degreeToRadians(double degree) {
        return degree * Math.PI / 180;
    }

    public int answerQuestion(int gameId, int answerId, double xGuess, double yGuess) {
        int points = 1;
        double distance = checkDistanceToAnswer(answerId, xGuess, yGuess);
        // add a more sensible point calculation here
        points = Points(distance);
        return points;

    }

    public Double[] getAnswer(int isPicQuestion, int questionId) {
        int answerId = db.getAnswerId(isPicQuestion, questionId);
        return db.getAnswerCords(answerId);
    }
    public String getAnswerAnswer(int isPicQuestion, int questionId){
        int answerId = db.getAnswerId(isPicQuestion, questionId);
        return db.getAnswerAnswer(answerId);
    }

    public String[] getPointsPerRound(int gameId) {
        return db.getPointsPerRound(gameId);
    }


    public int Points(double distance){
        
        return Math.min(500, Math.max(0, (int) Math.ceil(613.5694 - 0.6220731*distance + 0.0003569621*Math.pow(distance, 2) - 1.028715e-7*Math.pow(distance, 3)
                + 1.072978e-11*Math.pow(distance, 4))));

    }


    public int getPoints(int gameId){
        return db.getPoints(gameId);
    }

    public boolean checkAmount(int amount, String kind, String continent) {
        return db.checkAmount(amount, kind, continent);
    }
}
