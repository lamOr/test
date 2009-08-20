package Main;

import java.io.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author Administrator
 */
public class Score implements CommandListener{
    private Form score;
    private Command backCommand;
    String playerName[];
    int playerScore[];
    int numberOfPlay;
    public Score(){
        score = new Form("Score");
        backCommand = new Command("BACK",Command.BACK,0);
        score.addCommand(backCommand);
        score.setCommandListener(this);
        numberOfPlay=0;
        //////////read data
        StringItem title = new StringItem("Score of the Player","");
        title.setLayout(Item.LAYOUT_CENTER);
        score.append(title);
        for(int i=0;i<numberOfPlay;i++){
            StringItem current = new StringItem(playerName[i]+": ",""+playerScore[i]);
            score.append(current);
        }

    }
    public void showScoreMenu(){
         TouristMIDlet.getDisplay().setCurrent(score);
    }

    public void commandAction(Command c, Displayable d) {
        if(c==backCommand){
            TouristMIDlet.getInstance().getMenu().showMainMenu();
        }
    }

    public void readScore() throws IOException{
        /*
        DataInputStream input = new DataInputStream(new FileInputStream("temp.dat"));
        try{
            while(true)
            System.out.println(input.readUTF() + " " + input.readDouble());
        }
        catch(IOException io){

        }*/
    }
    public boolean CompareScore(int score1,int score2){
        if(score1>score2)
            return true;
        else
            return false;

    }

    public void arrangeScore(){
        int number= this.numberOfPlay;
        int currentScore;
        String currentName;
        while(number>0)
        if(CompareScore(playerScore[number],playerScore[number-1]))
        {
                currentScore=playerScore[number];
                currentName=playerName[number];
                playerScore[number]=playerScore[number-1];
                playerName[number]=playerName[number-1];
                playerScore[number-1]=currentScore;
                playerName[number-1]=currentName;
                number--;
        }
    }

    public void reArrangeALL(){
          int number = this.numberOfPlay;
          for(numberOfPlay=1;numberOfPlay<number;numberOfPlay++)
              arrangeScore();                   
    }

}
