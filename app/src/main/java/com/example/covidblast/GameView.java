//package com.example.covidblast;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.drawable.Drawable;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class GameView extends SurfaceView implements Runnable {
//
//    private Difficulties difficulty;
//    private Context context;
//    private Thread thread;
//    private boolean isPlaying = false, isGameOver = false, isHit = false;
//    private int score = 0;
//    private Paint paint;
//    private Syringe syringe;
//    private ArrayList<VirusView> viruses = new ArrayList<>();
//    Bitmap bg;
//
//
//    public GameView(Context context, int screenX, int screenY, Difficulties diff){
//        super(context);
//
//        bg = BitmapFactory.decodeResource(getResources(), R.drawable.cartoon_sky);
//
//
//
//        // Set difficulty level (easy/medium/hard/extreme) and set to this.difficulty.
//        Difficulty.getInstance().setCurrentDifficulty(diff);
//        this.difficulty = Difficulty.getInstance().getCurrentDifficulty();
//
//        this.context= context;
//
////        syringe = new Fish(screenY, getResources(),level.getFishPic());
//
//
//        paint = new Paint();
//        paint.setTextSize(100);
//        paint.setColor(Color.WHITE);
//
//
//        for(int i = 0; i< Difficulty.getInstance().getVirusAmount(); i++){
//            viruses.add(new VirusView(getResources(), 5, 10));
//        }
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawBitmap(bg, 0, 0, null);
//    }
//
//    @Override
//    public void run() {
//        System.out.println("isplaying: " + isPlaying);
//        while (isPlaying) {
//            update();
//            draw();
//            sleep();
//        }
//    }
//
//
//    private void update() {
//
//        for (int i = 0; i < viruses.size(); i++){
//            viruses.get(i).objectUpdate(0, 0);
//
//            // Check if syringe got hit by virus.
//            isHit = viruses.get(i).hit(syringe, score);
//            if (isHit){
//                isGameOver = true;
//                return;
//            }
//        }
//    }
//
//
//    private void draw() {
//
//        if(getHolder().getSurface().isValid()){
//
//            Canvas canvas = getHolder().lockCanvas();
//
////            // Background
//////            Drawable bg = findViewById(R.id.root_container).getBackground();
//////            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
//////                    findViewById(R.id.root_container).getDrawingCacheBackgroundColor());
////            Bitmap check =BitmapFactory.decodeResource(getResources(), R.drawable.river_boat);
////            canvas.drawBitmap(check, MainActivity.SCREEN_WIDTH, MainActivity.SCREEN_HEIGHT, paint);
//
//            // Draw viruses
//            for (VirusView virus : viruses)
//                canvas.drawBitmap(virus.getObject(), virus.getX(), virus.getY(), paint);
//
//            // Score
//            Paint scoreText = new Paint();
//            scoreText.setTextSize(100);
//            scoreText.setColor(Color.WHITE);
//            scoreText.isFakeBoldText();
//            String myScore = score + "";
//            Rect bounds = new Rect();
//            scoreText.getTextBounds(myScore, 0, myScore.length(), bounds);
//
//            canvas.drawText(myScore, (int)(this.getWidth()/2), 50 , scoreText);
//
//            // Check is the game is over (if syringe got hit).
//            if(isGameOver) {
//                // TODO: add disqualification sound.
//                isPlaying = false;
//                DifficultySelectionFragment.GAME_STARTED = false;
//                // making all the viruses on the screen disappear
//                getHolder().unlockCanvasAndPost(canvas);
//
//                ((Activity)getContext()).runOnUiThread(new Runnable() {
//                    @SuppressLint("SetTextI18n")
//                    public void run() {
//                        //check if the user score is big the high score of the his level
//                        //if - yes - create the alert dialog that save his name and add this to
//                        //the high score table
//                        if(score > 100) {
//                            // Inflate Game-over-dialog fragment.
//                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
//                            View gameOverFragView = inflater.inflate(R.layout.fragment_game_over_dialog, null);
//                            builder.setView(gameOverFragView);
//                            AlertDialog finishDialog = builder.create();
//                            finishDialog.setCancelable(false);
//                            finishDialog.show();
//
//                            ImageButton ok_btn = gameOverFragView.findViewById(R.id.finish_btn);
//                            ok_btn.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    // Extract name from dialog.
//                                    EditText enterName = gameOverFragView.findViewById(R.id.user_name_et);
//                                    String username = enterName.getText().toString();
//
//                                    if (username.matches("")) // In case name is empty.
//                                        Toast.makeText((getContext()), R.string.insert_name_game_over, Toast.LENGTH_SHORT).show();
//                                    else { // Name was entered.
//                                        finishDialog.dismiss();
//                                        // TODO: save score to ScoreTable.
////                                        Intent intent = new Intent((getContext()), ScoreTable.class);
////                                        (getContext()).startActivity(intent);
////                                        intent.putExtra("score_user", score);
////                                        intent.putExtra("user_name", username);
////                                        intent.putExtra("level_name", level.getNumLevel());
////                                        (getContext()).startActivity(intent);
////                                        ((Activity) getContext()).finish();
//                                    }
//                                }
//                            });
//
//                            TextView scoreTV = gameOverFragView.findViewById(R.id.final_score_tv);
//                            scoreTV.setText(score + "");
//                        }
//
//
//                    }
//                });
//                return;
//            }
//
//            // If game continues - redraw viruses
//            for (VirusView virus : viruses)
//                canvas.drawBitmap(virus.getObject(), virus.getX(), virus.getY(), paint);
//            getHolder().unlockCanvasAndPost(canvas);
//        }
//    }
//
//
//    private void sleep() {
//        try{
//            Thread.sleep(17);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void resume () {
//        isPlaying = true;
//        thread = new Thread(this);
//        thread.start();
//    }
//
//    public void pause () {
//        try {
//            isPlaying = false;
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
////    @Override
////    public boolean onTouchEvent(MotionEvent event) {
////
////        if (DifficultySelectionFragment.GAME_STARTED) {
////            switch (event.getAction()){
////                case MotionEvent.ACTION_DOWN :
////                case MotionEvent.ACTION_MOVE :
////                    if (event.getRawX() <= 75) {
////                        MainActivity.syringeIV.animate().x(50).setDuration(0).start();
////                    }
////                    else if (event.getRawX() >= MainActivity.SCREEN_WIDTH - 60) {
////                        MainActivity.syringeIV.animate().x(MainActivity.SCREEN_WIDTH - 85).setDuration(0).start();
////                    }
////                    else { // between
////                        MainActivity.syringeIV.animate().x(event.getRawX() - 25).setDuration(0).start();
////                    }
////            }
////        }
////        return true;
////
////    }
//
//}
