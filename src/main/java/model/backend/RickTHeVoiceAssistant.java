package model.backend;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.*;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class RickTHeVoiceAssistant {


    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {

        File theTimeIs = new File("src\\main\\resources\\Speech\\theTimeIs.wav");
        ArrayList<File> fileHour = new ArrayList<File>();
        for (int i = 0; i < 13; i++) {
            fileHour.add(new File("src\\main\\resources\\Speech\\hours\\" + i + ".wav"));
        }
        ArrayList<File> fileMinute = new ArrayList<File>();
        for (int i = 1; i < 20; i++) {
            fileMinute.add(new File("src\\main\\resources\\Speech\\Minutes\\" + i + ".wav"));
        }
        for (int i = 2; i < 6; i++) {
            fileMinute.add(new File("src\\main\\resources\\Speech\\Minutes\\" + i + "0.wav"));
        }
        ArrayList<File> fileAMOrPM = new ArrayList<File>();
        fileAMOrPM.add(new File("src\\main\\resources\\Speech\\aMOrPM\\PM.wav"));
        fileAMOrPM.add(new File("src\\main\\resources\\Speech\\aMOrPM\\AM.wav"));

        Configuration command = new Configuration();

        command.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        command.setDictionaryPath("src\\main\\resources\\3077.dic");
        command.setLanguageModelPath("src\\main\\resources\\3077.lm");

        try {
            LiveSpeechRecognizer speech = new LiveSpeechRecognizer(command);
            speech.startRecognition(true);

            SpeechResult speechResult = null;

            while ((speechResult = speech.getResult()) != null) {
                String voiceCommand = speechResult.getHypothesis();
                System.out.println("Voice Command is " + voiceCommand);

                if (voiceCommand.equalsIgnoreCase("What is the time?")) {
                    theTimeIs(fileHour, theTimeIs, fileAMOrPM, fileMinute);
                } else if (voiceCommand.equalsIgnoreCase("Rick Roll")) {
                    Runtime.getRuntime().exec("cmd.exe /c start microsoftedge https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley");
                } else if (voiceCommand.equalsIgnoreCase("shuffle spotify")) {
                    Runtime.getRuntime().exec("cmd.exe /c cd C:\\Users\\Eyad Tamer\\Documents\\eclipse-workspace\\RickTheVoiceAssistant\\src\\main\\resources && start spotify.vbs ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void theTimeIs(ArrayList<File> fileHour, File theTimeIs, ArrayList<File> fileAMOrPM, ArrayList<File> fileMinute) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
        playSound(theTimeIs);
        String h = DateTimeFormatter.ofPattern("h").format(LocalTime.now());
        String m = DateTimeFormatter.ofPattern("mm").format(LocalTime.now());
        String a = DateTimeFormatter.ofPattern("a").format(LocalTime.now());
        int hour = Integer.parseInt(h);
        int minute = Integer.parseInt(m);
        for (int i = 0; i < 12; i++) {
            if (hour == i) {
                //the arraylist is sorted, so we know which file we need to access...
                playSound(fileHour.get(i));
            }
        }

        //for minutes we need to check in which range our time is... if from 1 to 19 then we will use int i = 0; i < 19; i++
        // else we will look at the first digit and play its sound followed by the 2nd playing its sound...
        if (minute <= 19) {
            for (int j = 0; j < 19; j++)
                if (minute - 1 == j)
                    playSound(fileMinute.get(j));
        } else {
            int digit1 = minute / 10;
            switch (digit1) {
                case 2:
                    playSound(fileMinute.get(19));
                    break;
                case 3:
                    playSound(fileMinute.get(20));
                    break;
                case 4:
                    playSound(fileMinute.get(21));
                    break;
                case 5:
                    playSound(fileMinute.get(22));
                    break;
            }
            int digit2 = minute % 10;
            for (int i = 0; i < 9; i++)
                if (digit2 - 1 == i)
                    playSound(fileMinute.get(i));
        }

        if (a.equals("AM")) {
            playSound(fileAMOrPM.get(1));
        } else {
            playSound(fileAMOrPM.get(0));
        }
    }

    public static void playSound(File soundFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
        AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
        Thread.sleep(1245);
    }
}