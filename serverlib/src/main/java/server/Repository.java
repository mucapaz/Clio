package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import shared.Message;

public class Repository {

    ArrayList<Profile> profiles;
    ArrayList<String> imagesName;

    public static String newline = System.getProperty("line.separator");

    public Repository() {
    	profiles = new ArrayList<Profile>();
    	imagesName = new ArrayList<String>();
    	
        generateProfiles();
        generateImagesPath();
    }

    public boolean existProfile(String id){
        for(Profile prof : profiles){
            if(prof.getId() == id) return true;
        }
        return false;
    }

    public Profile findProfile(String id){
        for(Profile prof : profiles){
            if(prof.getId() == id) return prof;
        }
        return null;
    }

    public Profile createProfile(String id){
        Profile profile = new Profile(id,"",0,0);
        profiles.add(profile);

        try {

            File file = new File("server_files//profiles//" + profile.getId() + ".txt");

            if(file.exists()){
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                int photos, likes;
                photos = Integer.parseInt(bufferedReader.readLine());
                likes = Integer.parseInt(bufferedReader.readLine());
                profile.setCounter(photos);
                profile.setLikes(likes);

                bufferedReader.close();
                fileReader.close();

            }else{ // FIRST TIME LOGIN
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write("0");
                bufferedWriter.newLine();
                bufferedWriter.write("0");
                bufferedWriter.newLine();

                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.close();

                file = new File("server_files//profiles//profiles.txt");
                fileWriter = new FileWriter(file, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(profile.getId());
                bufferedWriter.newLine();
                bufferedWriter.flush();
                bufferedWriter.close();
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profile;
    }

    private void generateImagesPath() {
        try {
            FileReader fileReader = new FileReader("server_files//images//imagesList.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String path = bufferedReader.readLine();
            while (path != null) {
                imagesName.add(path);
                path = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateProfiles() {
        try {
            FileReader fileReader = new FileReader("server_files//profiles//profiles.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String id;
            id = bufferedReader.readLine();
            while(id!= null){
                profiles.add(new Profile(id,"",0,0));
                id = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();

            int v1, v2;
            for(int x=0;x<profiles.size();x++){
                id = profiles.get(x).getId();

                fileReader = new FileReader("server_files//profiles//" + id + ".txt");
                bufferedReader = new BufferedReader(fileReader);
                v1 = Integer.parseInt(bufferedReader.readLine());
                v2 = Integer.parseInt(bufferedReader.readLine());

                profiles.get(x).setCounter(v1);
                profiles.get(x).setLikes(v2);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createImage(Message message, String id) {
        Profile profile = new Profile("VISH","VISH",0,0);

            for(int x=0;x<profiles.size();x++){
                if(profiles.get(x).getId().equals(id)){
                    profile = profiles.get(x);
                    break;
                }
            }

        FileWriter writer = null;
        try {
            writer = new FileWriter("server_files//images//" + profile.getId() + profile.getCounter() + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            System.out.println(message.getJson());
            bufferedWriter.write(message.getJson());
            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();

            imagesName.add(profile.getId() + profile.getCounter());

            writer = new FileWriter("server_files//images//imagesList.txt", true);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(profile.getId() + "" + profile.getCounter());
            bufferedWriter.newLine();
            bufferedWriter.close();
            writer.close();

            profile.increaseCounter();

            writer = new FileWriter("server_files//profiles//" + profile.getId()+".txt");
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(Integer.toString(profile.getCounter()));
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(profile.getLikes()));
            bufferedWriter.newLine();

            bufferedWriter.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String chooseRandomJsonImage() {

        if(imagesName.size() > 0) {
            Random rand = new Random();
            int randInt = rand.nextInt(imagesName.size());
            if (randInt < 0) randInt *= -1;

            return readJsonImage(imagesName.get(randInt));

        }

        return "IUAHSDHSADKJH";
    }

    public String readJsonImage(String path){ // isso pode dar merda na forma q ta lendo o json la
        String ret = "";
        try {
        	FileInputStream fin = new FileInputStream(new File("server_files//images//" + path + ".txt"));
        	
        	Scanner scanner = new Scanner(fin);
        	
        	String line;
        	while(scanner.hasNextLine()){
        		line = scanner.nextLine();
        		ret += line;
        	}
        	
        	scanner.close();
        	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}


