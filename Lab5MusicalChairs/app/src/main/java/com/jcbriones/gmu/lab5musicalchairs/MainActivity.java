package com.jcbriones.gmu.lab5musicalchairs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private static final int SITTING = 0;
    private static final int STANDING = 1;
    private static final int UPDATEPLAYER = 2;
    private static final int LOSERINFO = 3;
    private static final int FREEZE_BUTTON = 4;
    private static final int UNFREEZE_BUTTON = 5;

    private static final int NUM_PLAYERS = 6;
    public int num_seats = NUM_PLAYERS-1;

    private Button playbutton;
    private TextView info;
    public Player p[];

    public Seat[] seats;
    public ImageView[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seats = new Seat[NUM_PLAYERS];
        playbutton = (Button)findViewById(R.id.playbutton);
        info = (TextView)findViewById(R.id.loserInfo);
        p = new Player[NUM_PLAYERS];
        image = new ImageView[NUM_PLAYERS];

        int[] imageName = {R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5,R.id.img6};

        for (int i = 0;i<NUM_PLAYERS;i++) {
            p[i] = new Player(i);

            // Initialize image
            image[i] = (ImageView)findViewById(imageName[i]);
            image[i].setImageBitmap(getImage(i));
        }
    }


    class Seat {
        // This class implements the seats.  Initially a seat is empty
        // (isTaken = false) - once some thread calls sit, it claims that
        // seat (isTaken = true) and any other player is returned a false value.
        // Notice that the 'sit' function is synchronized - only one player can be
        // trying to sit at a time.
        boolean isTaken;
        int who;
        Seat() {
            isTaken=false;
            who = -1;
        }
        int whoseSeat() {
            return who;
        }
        synchronized boolean sit(int i) {
            if (isTaken) {
                return false;
            } else {
                who = i;
                isTaken = true;
                return true;
            }
        }
        int get_player() {
            return who;
        }
        void reset_seat() {
            isTaken = false;
        }
    }

    public void doRound(View v) {
        Round r = new Round(num_seats);
        num_seats--;
        Thread t = new Thread(r);
        t.start();

//        Thread new Thread(new Player(0)).start();
//        new Thread(new Player(1)).start();
//        new Thread(new Player(2)).start();
//        new Thread(new Player(3)).start();
//        new Thread(new Player(4)).start();
//        new Thread(new Player(5)).start();

        if (num_seats <= 0) {
            playbutton.setEnabled(false);
            playbutton.setText("Done");

        } else {
            playbutton.setText(" Next Round: "+num_seats + " Remaining Seats");
        }

    }

    // remove the handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ImageView img;
            switch (msg.what) {
                case SITTING:
                    break;
                case STANDING:
                    break;
                case UPDATEPLAYER:
                    break;
                case LOSERINFO:
                    info.setText((info.getText() + "," + msg.obj));
                    break;
                case FREEZE_BUTTON:
                    playbutton.setEnabled(false);
                    break;
                case UNFREEZE_BUTTON:
                    playbutton.setEnabled(true);
                    break;
            }
        }};

    class Round implements Runnable{
        int num_seats;

        Round(int seats) {
            num_seats = seats;

        }
        public void run() {


            for (int i = 0;i<NUM_PLAYERS;i++) seats[i] = new Seat();  // create seats for the round

            for (int i = 0;i<NUM_PLAYERS;i++) {
                if (!p[i].isLoser()) {
                    Thread pt = new Thread(p[i]);
                    pt.start();
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private Bitmap getImage(int num) {
        switch (num) {
            case 0:
                return BitmapFactory.decodeResource(getResources(), R.drawable.one);
            case 1:
                return BitmapFactory.decodeResource(getResources(), R.drawable.two);
            case 2:
                return BitmapFactory.decodeResource(getResources(), R.drawable.three);
            case 3:
                return BitmapFactory.decodeResource(getResources(), R.drawable.four);
            case 4:
                return BitmapFactory.decodeResource(getResources(), R.drawable.five);
            case 5:
                return BitmapFactory.decodeResource(getResources(), R.drawable.six);
        }
        return BitmapFactory.decodeResource(getResources(),R.drawable.robot);
    }

    class Player implements Runnable{
        private int which_player;   // so they know which player they represent
        private boolean loser;      // keep track of whether or not they have lost

        //   - this is not used in the 1-round version
        private Random r = new Random();

        // Constructor
        Player(int which) {
            which_player = which;
            loser = false;
        }

        // next two functions are not used in 1-round version but I found them
        // useful for the full game
        public boolean isLoser() {
            return loser;
        }

        public void lost() {
            loser = true;
        }

        // run() gets called when the thread is started
        public void run() {
            // start with random wait
            try {
                int i = r.nextInt(10);
                Thread.sleep(10+i);
            }
            catch(InterruptedException e) {}

            // then try to get a seat
            for (int j = 0;j<(num_seats+2);j++) {
                if (seats[j].sit(which_player)) {
                    // update seat
                    if (j == num_seats+1) {
                        // update loser info
                        loser = true;
                    }
                    break;
                }
            }
        }
    }

}